package me.david.TimberNoCheat.checkes.combat;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import me.david.TimberNoCheat.checktools.Velocity;
import me.david.api.utils.player.PlayerUtil;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class Killaura extends Check {

    private final long multi_delay;
    private final double defaultrange;
    private final double speed;
    private final double ping_100_200;
    private final double ping_200_250;
    private final double ping_250_300;
    private final double ping_300_350;
    private final double ping_350_400;
    private final double ping_over400;
    private final double viomodifier;
    private final double max_velocity;
    private final double lowgroud_mofier;

    public Killaura() {
        super("Killaura", Category.COBMAT);
        multi_delay = getLong("multi_delay");
        defaultrange = getDouble("range.defaultrange");
        speed = getDouble("range.speed");
        ping_100_200 = getDouble("range.ping_100_200");
        ping_200_250 = getDouble("range.ping_200_250");
        ping_250_300 = getDouble("range.ping_250_300");
        ping_300_350 = getDouble("range.ping_300_350");
        ping_350_400 = getDouble("range.ping_350_400");
        ping_over400 = getDouble("range.ping_over400");
        viomodifier = getDouble("range.viomodifier");
        max_velocity = getDouble("range.max_velocity");
        lowgroud_mofier = getDouble("range.lowgroud_mofier");
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(EntityDamageByEntityEvent e) {
        if(!(e.getDamager() instanceof Player)){
            return;
        }
        final Player p = (Player) e.getDamager();
        if (!TimberNoCheat.checkmanager.isvalid_create(p) || e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK || e.isCancelled()) {
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        if(e.getEntity() instanceof Player && !((Player)e.getEntity()).getAllowFlight() && !p.getAllowFlight()){
            check_range(e, pd);
        }
        check_multi(e, pd);
    }

    private void check_multi(EntityDamageByEntityEvent e, PlayerData pd){
        if(pd.getLasthitentity() == 0){
            pd.setLasthitentity(e.getEntity().getEntityId());
        }
        long delay = System.currentTimeMillis()-pd.getLasthitmutli();
        if(pd.getLasthitentity() != e.getEntity().getEntityId() && delay < multi_delay){
            e.setCancelled(true);
            updatevio(this, (Player) e.getDamager(), 1, " §6TYPE: §bMULTI_AURA", " §6DELAY: §b" + delay);
        }
        pd.setLasthitmutli(System.currentTimeMillis());
        pd.setLasthitentity(e.getEntity().getEntityId());
    }
    public void check_range(EntityDamageByEntityEvent e, PlayerData pd){
        Player damager = (Player)e.getDamager();
        Player player = (Player)e.getEntity();

        double maxreach = defaultrange;
        if(damager.hasPotionEffect(PotionEffectType.SPEED)) {
            for(PotionEffect po : damager.getActivePotionEffects())
                if(po.getType().equals(PotionEffectType.SPEED)) {
                    maxreach = (po.getAmplifier()+1)*speed;
                    break;
                }
        }

        if(player.getVelocity().length() <= maxreach && !Velocity.velocity.containsKey(player.getUniqueId())) {
            double reach = PlayerUtil.getEyeLocation(damager).distance(player.getLocation());
            int ping = ((CraftPlayer)damager).getHandle().ping;
            if(ping >= 100 && ping < 200) {
                maxreach += ping_100_200;
            } else if(ping >= 200 && ping < 250) {
                maxreach += ping_200_250;
            } else if(ping >= 250 && ping < 300) {
                maxreach += ping_250_300;
            } else if(ping >= 300 && ping < 350) {
                maxreach += ping_300_350;
            } else if(ping >= 350 && ping < 400) {
                maxreach += ping_350_400;
            } else if(ping > 400) {
                maxreach += ping_over400;
            }

            if(damager.getLocation().getY() > player.getLocation().getY()) {
                maxreach += damager.getLocation().getY() - player.getLocation().getY() / lowgroud_mofier;
            } else if(player.getLocation().getY() > damager.getLocation().getY()) {
                maxreach += player.getLocation().getY() - damager.getLocation().getY() / lowgroud_mofier;
            }

            if(reach > maxreach) {
                updatevio(this, damager, reach-maxreach*12, " §6TYPE: §bREACH", " §6MAXREACH: §b" + maxreach, " §6REACH: §b" + reach);
                //pd.getReaches().add(reach);
                //pd.setLastreach(System.currentTimeMillis());
            }

            /*if(DateTimeUtil.elapsed(pd.getLastreach(), TimberNoCheat.instance.settings.fight_killaura_rangeclearaftermilis)) {
                pd.getReaches().clear();
                pd.setLastreach(System.currentTimeMillis());
            }

            if(pd.getReaches().size() > TimberNoCheat.instance.settings.fight_killaura_range_toohitslongtoreport) {
                Double average = MathUtil.averageDouble(pd.getReaches());
                Double a = TimberNoCheat.instance.settings.fight_killaura_range_levelrange - maxreach;
                Double b = average - maxreach;

                int level = (int)Math.round(b < 0.0D?0.00000000001D:b / a < 0.0D?0.00000000001D:a * 100);
                pd.getReaches().clear();
                TimberNoCheat.checkmanager.notify(this, damager, " §6TYPE: §bREACH", " §6WAHRSCHEINLICHKEIT: §b" + level +"%", " §6Reach: §b" + reach, " §6CalculatetMacReach: §b" + maxreach);
            }
            */
        }
    }


}
