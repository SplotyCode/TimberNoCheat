package me.david.timbernocheat.checkes.combat;

import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerFishEvent;

public class SelfHit extends Check {

    public SelfHit(){
        super("SelfHit", Category.COMBAT);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(EntityDamageByEntityEvent event) {
        if(event.isCancelled() || event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) return;
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) return;
        final Player player = (Player) event.getDamager();
        if (!CheckManager.getInstance().isvalid_create(player)) return;
        if(event.getEntity().getEntityId() == event.getDamager().getEntityId()){
            if(updateVio(this, player, 1))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFish(final PlayerFishEvent event){
        final Player player = event.getPlayer();
        final Entity caught = event.getCaught();
        if (!CheckManager.getInstance().isvalid_create(player)) return;
        if(caught.getEntityId() == player.getEntityId()){
            event.setCancelled(true);
            updateVio(this, player, 1);
        }
    }

}
