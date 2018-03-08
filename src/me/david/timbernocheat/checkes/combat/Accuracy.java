package me.david.timbernocheat.checkes.combat;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.PlayerData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Accuracy extends Check {

    private final int MINDATA;
    private final long MINDELAY;
    private final float MINACCURACY;
    private final boolean MOVE;
    private final long MOVEDELAY;

    public Accuracy() {
        super("Accuracy", Category.COBMAT);
        MINDATA = getInt("mindata");
        MINDELAY = getLong("mindelay");
        MINACCURACY = (float) getDouble("minaccuracy");
        MOVE = getBoolean("move");
        MOVEDELAY = getLong("movedelay");
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onHit(final EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player) || event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK || event.getDamager().getNearbyEntities(5.8, 5.8, 5.8).stream().anyMatch(en -> en instanceof LivingEntity))return;
        final Player player = (Player) event.getDamager();
        if(TimberNoCheat.getCheckManager().isvalid_create(player))
            if(!MOVE || event.getEntity() instanceof Player)
                if(check(player, MOVE?(Player) event.getEntity():null, false)) event.setCancelled(true);
    }

    //TODO: Do we know to cancel this?
    @EventHandler
    private void onFail(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (event.getAction() != Action.LEFT_CLICK_AIR || player.getNearbyEntities(5.8, 5.8, 5.8).stream().anyMatch(en -> en instanceof LivingEntity)) return;
        if(TimberNoCheat.getCheckManager().isvalid_create(player))
            check(player, null, true);
    }

    private boolean check(final Player player, final Player target, boolean miss) {
        if(target != null){
            if(!TimberNoCheat.getCheckManager().isvalid_create(target))return false;
            long moveDelay = System.currentTimeMillis()-TimberNoCheat.getCheckManager().getPlayerdata(target).getGenerals().getLastRealMove();
            if(moveDelay > MOVEDELAY) return false;
        }
        PlayerData pd = TimberNoCheat.getCheckManager().getPlayerdata(player);
        pd.getAccuracy().put(System.currentTimeMillis(), miss);
        if(pd.getAccuracy().size() == MINDATA){
            final long delay = System.currentTimeMillis()-(long)pd.getAccuracy().keySet().toArray()[0];
            if(delay <= MINDELAY){
                int hits = 0;
                for(Boolean bool : pd.getAccuracy().values()) if(!bool) hits++;
                float per = hits/pd.getAccuracy().size()*100;
                if(per >= MINACCURACY)
                    return updateVio(this, player, per/2, " §6HitPercentage: §b" + per, " §6IN: §b" + delay/1000 + "s");
            }
            pd.getAccuracy().clear();
        }
        return false;
    }
}
