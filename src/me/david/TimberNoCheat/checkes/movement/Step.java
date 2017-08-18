package me.david.TimberNoCheat.checkes.movement;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checktools.Velocity;
import me.david.api.utils.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

public class Step extends Check {

    public Step(){
        super("Step", Category.MOVEMENT);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onMove(PlayerMoveEvent e) {
        final Player p = e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        if(p.getVehicle() != null || e.isCancelled() || !PlayerUtil.isOnGround(p) || p.getAllowFlight() || PlayerUtil.slabsNear(p.getLocation()) || PlayerUtil.stairsNear(p.getLocation()) || p.hasPotionEffect(PotionEffectType.JUMP) || Velocity.velocity.containsKey(p.getUniqueId())){
            return;
        }
        double yDist = e.getTo().getY() - e.getFrom().getY();
        if(yDist > 0.9D) {
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6HEIGHT: §b" + yDist);
        }
    }
}
