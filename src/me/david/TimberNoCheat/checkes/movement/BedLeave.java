package me.david.TimberNoCheat.checkes.movement;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class BedLeave extends Check{

    private final int maxrange;
    public BedLeave(){
        super("BedLeave", Category.MOVEMENT);
        maxrange = getInt("maxrange");
    }

    @EventHandler
    public void onBedLeave(PlayerBedLeaveEvent e){
        if(!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer())) return;
        Location loc = e.getBed().getLocation();
        for(int x = loc.getBlockX() - maxrange; x < loc.getBlockX() + maxrange; ++x) {
            for(int y = loc.getBlockY() - maxrange; y < loc.getBlockY() + maxrange; ++y) {
                for(int z = loc.getBlockZ() - maxrange; z < loc.getBlockZ() + maxrange; ++z) {
                    Block b = (new Location(loc.getWorld(), x, y, z)).getBlock();
                    if(b.getType().equals(Material.BED) || b.getType().equals(Material.BED_BLOCK)) {
                        return;
                    }
                }
            }
        }
        updatevio(this, e.getPlayer(), 1);
    }
}
