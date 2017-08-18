package me.david.TimberNoCheat.checkes.fight;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import me.david.TimberNoCheat.checktools.Velocity;
import me.david.api.utils.DateTimeUtil;
import me.david.api.utils.MathUtil;
import me.david.api.utils.PlayerUtil;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class Killaura extends Check {

    public Killaura() {
        super("Killaura", Category.FIGHT);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(EntityDamageByEntityEvent e) {
        if(!(e.getDamager() instanceof Player)){
            return;
        }
        final Player p = (Player) e.getDamager();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        if (e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK || e.isCancelled() || p.getAllowFlight()) {
            return;
        }
        if(e.getEntity() instanceof Player && !((Player)e.getEntity()).getAllowFlight()){
            check_range(e, pd);
        }
        check_multi(e, pd);
    }

    public void check_multi(EntityDamageByEntityEvent e, PlayerData pd){
        if(pd.getLasthitentity() == 0){
            pd.setLasthitentity(e.getEntity().getEntityId());
        }
        long delay = System.currentTimeMillis()-pd.getLasthitmutli();
        if(pd.getLasthitentity() != e.getEntity().getEntityId() && delay < TimberNoCheat.instance.settings.fight_killaura_multiauradelay){
            e.setCancelled(true);
            pd.setLasthitmutli(System.currentTimeMillis()-20000L);
            TimberNoCheat.checkmanager.notify(this, (Player) e.getDamager(), " §6TYPE: §bMULTI_AURA", " §6DELAY: §b" + delay);
            return;
        }
        pd.setLasthitmutli(System.currentTimeMillis());
        pd.setLasthitentity(e.getEntity().getEntityId());
    }
    public void check_range(EntityDamageByEntityEvent e, PlayerData pd){
        Player damager = (Player)e.getDamager();
        Player player = (Player)e.getEntity();

        double maxreach = TimberNoCheat.instance.settings.fight_killaura_defaultrange;
        if(damager.hasPotionEffect(PotionEffectType.SPEED)) {
            int level = 0;
            for(PotionEffect po : damager.getActivePotionEffects())
                if(po.getType().equals(PotionEffectType.SPEED)) {
                    level = po.getAmplifier();
                    break;
                }
            switch(level) {
                case 0:
                    maxreach = TimberNoCheat.instance.settings.fight_killaura_rangespeed1;
                    break;
                case 1:
                    maxreach = TimberNoCheat.instance.settings.fight_killaura_rangespeed2;
                    break;
            }
        }

        if(player.getVelocity().length() <= TimberNoCheat.instance.settings.fight_killaura_rangevectorlen && !Velocity.velocity.containsKey(player.getUniqueId())) {
            double reach = PlayerUtil.getEyeLocation(damager).distance(player.getLocation());
            int Ping = ((CraftPlayer)damager).getHandle().ping;
            if(Ping >= 100 && Ping < 200) {
                maxreach += TimberNoCheat.instance.settings.fight_killaura_rangeping_100_200;
            } else if(Ping >= 200 && Ping < 250) {
                maxreach += TimberNoCheat.instance.settings.fight_killaura_rangeping_200_250;
            } else if(Ping >= 250 && Ping < 300) {
                maxreach += TimberNoCheat.instance.settings.fight_killaura_rangeping_250_300;
            } else if(Ping >= 300 && Ping < 350) {
                maxreach += TimberNoCheat.instance.settings.fight_killaura_rangeping_300_350;
            } else if(Ping >= 350 && Ping < 400) {
                maxreach += TimberNoCheat.instance.settings.fight_killaura_rangeping_350_400;
            } else if(Ping > 400) {
                maxreach += TimberNoCheat.instance.settings.fight_killaura_rangeping_over400;
            }

            double diff;
            if(damager.getLocation().getY() > player.getLocation().getY()) {
                diff = damager.getLocation().getY() - player.getLocation().getY();
                maxreach += diff / TimberNoCheat.instance.settings.fight_killaura_rangeisup;
            } else if(player.getLocation().getY() > damager.getLocation().getY()) {
                diff = player.getLocation().getY() - damager.getLocation().getY();
                maxreach += diff / TimberNoCheat.instance.settings.fight_killaura_rangeisup;
            }

            if(reach > maxreach) {
                pd.getReaches().add(reach);
                pd.setLastreach(System.currentTimeMillis());
            }

            if(DateTimeUtil.elapsed(pd.getLastreach(), TimberNoCheat.instance.settings.fight_killaura_rangeclearaftermilis)) {
                pd.getReaches().clear();
                pd.setLastreach(System.currentTimeMillis());
            }

            if(pd.getReaches().size() > TimberNoCheat.instance.settings.fight_killaura_range_toohitslongtoreport) {
                Double average = MathUtil.averageDouble(pd.getReaches());
                Double a = TimberNoCheat.instance.settings.fight_killaura_range_levelrange - maxreach;
                Double b = average - maxreach;

                int level = (int)Math.round(b < 0.0D?0.00000000001D:b / a < 0.0D?0.00000000001D:a * 100);
                pd.getReaches().clear();
                TimberNoCheat.checkmanager.notify(this, damager, " §6TYPE: §bREACH", " §6Level: §b" + level, " §6Reach: §b" + reach, " §6CalculatetMacReach: §b" + maxreach);
            }
        }
    }


}
