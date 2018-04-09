package me.david.timbernocheat.command.blocktrigger;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.storage.YamlFile;
import me.david.timbernocheat.storage.YamlSection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TriggerBlockManager implements Listener {

    private ArrayList<TriggerBlock> triggerBlocks = new ArrayList<>();
    private HashMap<UUID, TriggerAction> pending = new HashMap<>();
    private final YamlFile file;

    public TriggerBlockManager(final Plugin plugin, final File file) throws IOException {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        boolean first = !file.exists();
        if(first) file.createNewFile();
        this.file = new YamlFile(file);
        if(first){
            this.file.set("numbers", 0);
            this.file.save();
        }
        for(int i = 0;i < this.file.getInt("numbers");i++){
            TriggerBlock block = new TriggerBlock();
            block.read(this.file.getYamlSection(i + ""));
            triggerBlocks.add(block);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onRedstone(final BlockRedstoneEvent event){
        final boolean wasOn = event.getOldCurrent() > 0;
        final boolean isOn = event.getNewCurrent() > 0;
        final Location location = event.getBlock().getLocation();
        if(isOn != wasOn)
            for(final TriggerBlock triggerBlock : triggerBlocks)
                if(location.equals(triggerBlock.getLocation()))
                    triggerBlock.getAction().getAction().trigger(location, triggerBlock.getAtributes(), isOn);
    }

    @EventHandler(ignoreCancelled = true)
    public void onChat(final AsyncPlayerChatEvent event){
        final UUID uuid = event.getPlayer().getUniqueId();
        final Player player = event.getPlayer();
        final TriggerAction action = pending.get(uuid);
        if(action == null)return;
        pending.remove(uuid);
        final String[] arguments = event.getMessage().split(", ");
        final Location location = player.getTargetBlock((Set<Material>) null, 5).getLocation();
        final AttributeList atributes = new AttributeList(arguments, action.getClasses());
        if(!atributes.getErrors().isEmpty()){
            player.sendMessage(TimberNoCheat.getInstance().prefix + "§cFehler bei der Argument übersetzung sind aufgetreten: ");
            for(AtributeParseErrors error : atributes.getErrors())
                player.sendMessage(TimberNoCheat.getInstance().prefix + "§cFehler: " + error.name());
            return;
        }
        final TriggerBlock block = new TriggerBlock(location, action, uuid, atributes);
        triggerBlocks.add(block);
        player.sendMessage(TimberNoCheat.getInstance().prefix + "Added Block!");
        //save
        block.save(new YamlSection(file));
        file.save();
    }

    public HashMap<UUID, TriggerAction> getPending() {
        return pending;
    }

    public ArrayList<TriggerBlock> getTriggerBlocks() {
        return triggerBlocks;
    }
}
