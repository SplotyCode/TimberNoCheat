package me.david.timbernocheat.record;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.api.RefreshEvent;
import me.david.timbernocheat.api.ViolationUpdateEvent;
import me.david.timbernocheat.checkmanager.Check;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecordManager implements Listener {

    private final ConfigRecord record;
    private BukkitTask task;
    private final Set<Recording> recordings = (Set<Recording>)new HashSet<Recording>();

    public RecordManager(File config){
        record = new ConfigRecord(config);
        if(record.isEnable())
            startTaskTimer();
    }

    private void startTaskTimer(){
        task = Bukkit.getScheduler().runTaskTimer(TimberNoCheat.instance, this::tick, 1, 1);
    }

    private void tick(){
        for(Recording recording : recordings)
            if(recording.getState() == RecordState.RECORD)
                recording.tick();
    }

    public void stopAll(){
        for(Recording recording : recordings)
            if(!recording.isStopped())
                recording.stop();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onReload(RefreshEvent event){
        boolean oldToggle = record.isEnable();
        record.refresh();
        boolean newToggle = record.isEnable();
        if(newToggle != oldToggle){
            if(oldToggle)
                Bukkit.getScheduler().cancelTask(task.getTaskId());
            if(newToggle)
                startTaskTimer();
        }
    }

    @EventHandler
    public void onVio(ViolationUpdateEvent event){
        double vio = 0;
        for(Check check : TimberNoCheat.checkmanager.getChecks())
            vio += check.getViolation(event.getPlayer());
        if(vio >= getRecord().getMinvio() && getRecoardingbyMain(event.getPlayer()) == null){
            try {
                start(new File(TimberNoCheat.instance.getDataFolder(), "/records/" + System.currentTimeMillis() + ".tncrec"), event.getPlayer(), new ArrayList<>());
                TimberNoCheat.instance.notify("Start Auto-Record for " + event.getPlayer().getDisplayName() + " because he has an total Violation-Level from over " + getRecord().getMinvio() + "! Vio=" + vio);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Recording getRecoardingbyMain(Player player){
        for(Recording rec : recordings)
            if(rec.getSaveHandler().getMain() == player.getUniqueId())
                return rec;
        return null;
    }

    public void start(File file, Player main, List<Player> players) throws IOException {
        if(!record.isEnable()) return;
        file.createNewFile();
        Recording recording = new Recording(new RecordSaveHandler(main.getUniqueId(), file));
        for(Player player : players) recording.join(player);
        recording.start();
        recordings.add(recording);
    }

    public ConfigRecord getRecord() {
        return record;
    }

    public Set<Recording> getRecordings() {
        return recordings;
    }
}
