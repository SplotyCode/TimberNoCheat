package me.david.timbernocheat.checkes.movement;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
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
        if(!CheckManager.getInstance().isvalid_create(e.getPlayer())) return;
        TimberNoCheat.getInstance().getMoveprofiler().start("BedLeave");
        Location loc = e.getBed().getLocation();
        for(int x = loc.getBlockX() - maxrange; x < loc.getBlockX() + maxrange; ++x)
            for(int y = loc.getBlockY() - maxrange; y < loc.getBlockY() + maxrange; ++y)
                for(int z = loc.getBlockZ() - maxrange; z < loc.getBlockZ() + maxrange; ++z) {
                    Block b = (new Location(loc.getWorld(), x, y, z)).getBlock();
                    if(b.getType().equals(Material.BED) || b.getType().equals(Material.BED_BLOCK))
                        return;
                }

        updateVio(this, e.getPlayer(), 1);
        TimberNoCheat.getInstance().getMoveprofiler().end();
    }
}
