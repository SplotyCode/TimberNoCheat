package me.david.timbernocheat.checkes.combat;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerFishEvent;

public class SelfHit extends Check {

    public SelfHit(){
        super("SelfHit", Category.COBMAT);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(EntityDamageByEntityEvent e) {
        if(e.isCancelled() || e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) return;
        if (!(e.getDamager() instanceof Player) || !(e.getEntity() instanceof Player)) return;
        final Player p = (Player) e.getDamager();
        if (!TimberNoCheat.checkmanager.isvalid_create(p)) return;
        if(e.getEntity().getEntityId() == e.getDamager().getEntityId()){
            e.setCancelled(true);
            updateVio(this, p, 1);
        }
    }

    @EventHandler
    public void onFish(final PlayerFishEvent event){
        final Player player = event.getPlayer();
        final Entity caught = event.getCaught();
        if (!TimberNoCheat.checkmanager.isvalid_create(player)) return;
        if(caught.getEntityId() == player.getEntityId()){
            event.setCancelled(true);
            updateVio(this, player, 1);
        }
    }

}
