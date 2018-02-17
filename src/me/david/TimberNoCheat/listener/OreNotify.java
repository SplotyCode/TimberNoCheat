package me.david.TimberNoCheat.listener;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.command.oreNotify.OreNotifyData;
import me.david.TimberNoCheat.command.oreNotify.OreNotifyManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Arrays;

public class OreNotify implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBreak(final BlockBreakEvent event){
        final Player player = event.getPlayer();
        final Material material = event.getBlock().getType();
        if(Arrays.asList(OreNotifyManager.POSSBILE_ORES).contains(material)){
            TimberNoCheat.instance.getOreNotifyManager().notifyOre(player, material);
        }
    }
}
