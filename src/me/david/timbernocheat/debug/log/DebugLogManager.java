package me.david.timbernocheat.debug.log;

import javafx.util.Pair;
import me.david.api.utils.FileUtil;
import me.david.api.utils.RandomUtil;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.api.ViolationUpdateEvent;
import me.david.timbernocheat.event.internal.ShutdownEvent;
import me.david.timbernocheat.storage.BinaryComponent;
import me.david.timbernocheat.storage.BinarySerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DebugLogManager implements Listener, BinaryComponent {

    private HashMap<UUID, ArrayList<DebugEntry>> debugEntries = new HashMap<>();
    private HashMap<String, Pair<UUID, ArrayList<DebugEntry>>> savedEntries = new HashMap<>();

    private File file = new File(TimberNoCheat.getInstance().getDataFolder(), "savedids.rawbinary");

    public DebugLogManager(){
        if(!FileUtil.create(file)) {
            BinarySerializer serializer = new BinarySerializer();
            serializer.readFile(file);
            try {
                read(serializer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onViolation(final ViolationUpdateEvent event) {
        DebugEntry entry = new DebugEntry(System.currentTimeMillis(), event.getCheck().displayName(),
                event.getPlayer().getUniqueId(), event.getNewViolation(), event.getOldViolation(),
                null, event.isCancelled());
        if (debugEntries.containsKey(entry.getPlayer()))
            debugEntries.get(entry.getPlayer()).add(entry);
        else
            debugEntries.put(entry.getPlayer(), new ArrayList<>(Collections.singletonList(entry)));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onStop(ShutdownEvent event) {
        BinarySerializer serializer = new BinarySerializer();
        try {
            write(serializer);
            serializer.writeFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<UUID, ArrayList<DebugEntry>> getDebugEntries() {
        return debugEntries;
    }

    public DebugEntry getLast(final Player player){
        ArrayList<DebugEntry> list = debugEntries.get(player.getUniqueId());
        if(list == null) return null;

        return list.get(list.size()-1);
    }

    @Override
    public void read(BinarySerializer serializer) throws IOException {
        int max = serializer.readVarInt();
        for(int i = 0;i < max;i++){
            String id = serializer.readString();
            UUID uuid = new UUID(serializer.readLong(), serializer.readLong());
            int maxEntruy = serializer.readVarInt();
            ArrayList<DebugEntry> entries = new ArrayList<>();
            for(int i2 = 0;i2 < maxEntruy;i2++){
                DebugEntry entry = new DebugEntry();
                entry.read(serializer);
                entries.add(entry);
            }
            savedEntries.put(id, new Pair<>(uuid, entries));
        }
    }

    @Override
    public void write(BinarySerializer serializer) throws IOException {
        serializer.writeVarInt(savedEntries.size());
        for(Map.Entry<String, Pair<UUID, ArrayList<DebugEntry>>> entry : savedEntries.entrySet()){
            serializer.writeString(entry.getKey());
            serializer.writeLong(entry.getValue().getKey().getMostSignificantBits());
            serializer.writeLong(entry.getValue().getKey().getLeastSignificantBits());

            serializer.writeVarInt(entry.getValue().getValue().size());
            for(DebugEntry debugEntry : entry.getValue().getValue())
                debugEntry.write(serializer);
        }
    }

    public String generadeDebugId(final Player player){
        ArrayList<DebugEntry> entries = debugEntries.get(player.getUniqueId());
        if(entries == null) return "Unable to Track last Violations";
        String id = null;
        while (id == null || savedEntries.containsKey(id))
            id = new RandomUtil().groups(3).digits(4).build();
        savedEntries.put(id, new Pair<>(player.getUniqueId(), entries));
        return id;
    }
}
