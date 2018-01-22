package me.david.TimberNoCheat.checkes.movement;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.api.utils.BlockUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class Clip extends Check{

    private final boolean onlyfull;
    public Clip(){
        super("Clip", Category.MOVEMENT);
        onlyfull = getBoolean("onlyfullblocks");
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        final Player p = e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        TimberNoCheat.instance.getMoveprofiler().start("Clip");
        if(p.getAllowFlight() || p.getVehicle() != null || !(p.getLocation().getY() >= 0.0D && p.getLocation().getY() <= p.getWorld().getMaxHeight())){
            return;
        }
        Location to = e.getTo().clone();
        Location from = e.getFrom().clone();

        double xDist = Math.abs(to.getX() - from.getX());
        if(xDist < -0.9D || xDist > 0.9D)
            for(int i = 0; i < Math.round(Math.abs(xDist)); ++i) {
                Location l = xDist < -0.9D?to.clone().add(i, 0, 0):from.clone().add(i, 0, 0.);
                if(checkblock(l)){
                    e.setCancelled(true);
                    updatevio(this, e.getPlayer(), 1);
                    return;
                }
            }
        double zDist = Math.abs(to.getZ() - from.getZ());
        if(zDist < -0.9D || zDist > 0.9D)
            for(int i = 0; i < Math.round(Math.abs(zDist)); ++i) {
                Location l = zDist < -0.9D?to.clone().add(0, 0, i):from.clone().add(0, 0, i);
                if(checkblock(l)){
                    e.setCancelled(true);
                    updatevio(this, e.getPlayer(), 1);
                    return;
                }
            }
        double yDist = Math.abs(to.getY() - from.getY());
        if(yDist < -0.9D || yDist > 0.9D)
            for(int i = 0; i < Math.round(Math.abs(yDist)); ++i) {
                Location l = yDist < -0.9D?to.clone().add(0, i, 0):from.clone().add(0, i, 0);
                if(checkblock(l)){
                    e.setCancelled(true);
                    updatevio(this, e.getPlayer(), 1);
                    return;
                }
            }
        TimberNoCheat.instance.getMoveprofiler().end();
    }
    public boolean checkblock(Location l){
        if(l.getBlock() != null && l.getBlock().getType().isSolid() && l.getBlock().getType().isBlock() && l.getBlock().getType() != Material.AIR) {
            if(!onlyfull){
                if(!BlockUtil.HOLLOW_MATERIALS.contains(l.getBlock().getType())){
                    return true;
                }
            }else{
                return true;
            }
        }
        return false;
    }
}