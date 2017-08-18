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

    public Clip(){
        super("Clip", Category.MOVEMENT);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        final Player p = e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        if(p.getAllowFlight() || p.getVehicle() != null || !(p.getLocation().getY() >= 0.0D && p.getLocation().getY() <= p.getWorld().getMaxHeight())){
            return;
        }
        Location to = e.getTo().clone();
        Location from = e.getFrom().clone();
        double dis = to.getX() - from.getX();
        int blocks = 0;
        double xDist = to.getX() - from.getX();
        if(xDist < -0.9D || xDist > 0.9D) {
            int x = (int)Math.round(Math.abs(xDist));

            for(int i = 0; i < x; ++i) {
                Location l = xDist < -0.9D?to.clone().add((double)i, 0.0D, 0.0D):from.clone().add((double)i, 0.0D, 0.0D);
                if(l.getBlock() != null && l.getBlock().getType().isSolid() && l.getBlock().getType().isBlock() && l.getBlock().getType() != Material.AIR) {
                    if(TimberNoCheat.instance.settings.movement_clip_onlyfullblocks){
                        if(!BlockUtil.HOLLOW_MATERIALS.contains(l.getBlock().getType())){
                            blocks++;
                        }
                    }else{
                        blocks++;
                    }
                }
            }
        }

        double zDist = to.getX() - from.getX();
        if(zDist < -0.9D || zDist > 0.9D) {
            int z = (int)Math.round(Math.abs(xDist));

            for(int i = 0; i < z; ++i) {
                Location l = zDist < -0.9D?to.clone().add(0.0D, 0.0D, (double)i):from.clone().add(0.0D, 0.0D, (double)i);
                if(l.getBlock() != null && l.getBlock().getType().isSolid() && l.getBlock().getType().isBlock() && l.getBlock().getType() != Material.AIR) {
                    if(TimberNoCheat.instance.settings.movement_clip_onlyfullblocks){
                        if(!BlockUtil.HOLLOW_MATERIALS.contains(l.getBlock().getType())){
                            blocks++;
                        }
                    }else{
                        blocks++;
                    }
                }
            }
        }
        double yDist = to.getY() - from.getY();
        if(yDist < -0.9D || yDist > 0.9D) {
            int y = (int)Math.round(Math.abs(yDist));

            for(int i = 0; i < y; ++i) {
                Location l = yDist < -0.9D?to.clone().add(0.0D, (double)i, 0.0D):from.clone().add(0.0D, (double)i, 0.0D);
                if(l.getBlock() != null && l.getBlock().getType().isSolid() && l.getBlock().getType().isBlock() && l.getBlock().getType() != Material.AIR) {
                    if(TimberNoCheat.instance.settings.movement_clip_onlyfullblocks){
                        if(!BlockUtil.HOLLOW_MATERIALS.contains(l.getBlock().getType())){
                            blocks++;
                        }
                    }else{
                        blocks++;
                    }
                }
            }
        }

        if(blocks > 0) {
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p);
        }
    }
}
