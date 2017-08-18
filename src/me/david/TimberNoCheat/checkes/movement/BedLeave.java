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

    public BedLeave(){
        super("BedLeave", Category.MOVEMENT);
    }

    @EventHandler
    public void onBedLeave(PlayerBedLeaveEvent e){
        if(!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer())){
            return;
        }
        Location loc = e.getBed().getLocation();
        for(int x = loc.getBlockX() - 10; x < loc.getBlockX() + 10; ++x) {
            for(int y = loc.getBlockY() - 10; y < loc.getBlockY() + 10; ++y) {
                for(int z = loc.getBlockZ() - 10; z < loc.getBlockZ() + 10; ++z) {
                    Block b = (new Location(loc.getWorld(), x, y, z)).getBlock();
                    if(b.getType().equals(Material.BED) || b.getType().equals(Material.BED_BLOCK)) {
                        return;
                    }
                }
            }
        }
        TimberNoCheat.checkmanager.notify(this, e.getPlayer());
    }
}
