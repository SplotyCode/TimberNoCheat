package me.david.timbernocheat.checkes.combat;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import me.david.timbernocheat.checkmanager.PlayerData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Accuracy extends Check {

    private final int mindata;
    private final long mindelay;
    private final float minaccuracy;

    public Accuracy() {
        super("Accuracy", Category.COBMAT);
        mindata = getInt("mindata");
        mindelay = getLong("mindelay");
        minaccuracy = (float) getDouble("minaccuracy");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onHit(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player) || event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK || event.getDamager().getNearbyEntities(5.8, 5.8, 5.8).stream().anyMatch(en -> en instanceof LivingEntity))return;
        if(TimberNoCheat.checkmanager.isvalid_create((Player) event.getDamager()))
            check((Player) event.getDamager(), false);
    }

    @EventHandler
    private void onFail(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (event.getAction() != Action.LEFT_CLICK_AIR || p.getNearbyEntities(5.8, 5.8, 5.8).stream().anyMatch(en -> en instanceof LivingEntity)) return;
        if(TimberNoCheat.checkmanager.isvalid_create(p))
            check(p, true);
    }

    private void check(Player p, boolean miss) {
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        pd.getAccuracy().put(System.currentTimeMillis(), miss);
        if(pd.getAccuracy().size() == mindata){
            final long delay = System.currentTimeMillis()-(long)pd.getAccuracy().keySet().toArray()[0];
            if(delay <= mindelay){
                int hits = 0;
                for(Boolean bool : pd.getAccuracy().values()) if(!bool) hits++;
                float per = hits/pd.getAccuracy().size()*100;
                if(per >= minaccuracy) updateVio(this, p, per/2, " §6HitPercentage: §b" + per, " §6IN: §b" + delay/1000 + "s");
            }
            pd.getAccuracy().clear();
        }
    }
}
