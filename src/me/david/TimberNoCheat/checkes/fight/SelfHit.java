package me.david.TimberNoCheat.checkes.fight;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class SelfHit extends Check {

    public SelfHit(){
        super("SelfHit", Category.FIGHT);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(EntityDamageByEntityEvent e) {
        if(e.isCancelled() || e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK){
            return;
        }
        if (!(e.getDamager() instanceof Player) || !(e.getEntity() instanceof Player)) {
            return;
        }
        final Player p = (Player) e.getDamager();
        if (!TimberNoCheat.checkmanager.isvalid_create(p)) {
            return;
        }
        if(e.getEntity().getName().equals(e.getDamager().getName())){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, (Player)e.getDamager());
        }
    }

}
