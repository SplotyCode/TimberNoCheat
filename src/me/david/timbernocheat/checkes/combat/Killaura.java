package me.david.timbernocheat.checkes.combat;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import me.david.timbernocheat.checkmanager.PlayerData;
import me.david.timbernocheat.debug.Scheduler;
import me.david.timbernocheat.runnable.TimberScheduler;
import me.david.timbernocheat.runnable.Velocity;
import me.david.timbernocheat.debug.Debuggers;
import me.david.api.utils.player.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;


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
    private final float swingvio;
    private final boolean aimbot;
    private final int max_targets;

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
        swingvio = (float) getDouble("swinghitviomodi");
        aimbot = getBoolean("aimbot");
        max_targets = getInt("multi_maxtargets");

        register(new PacketAdapter(TimberNoCheat.instance,
                PacketType.Play.Client.USE_ENTITY) {
            public void onPacketReceiving(final PacketEvent event) {
                final Player player = event.getPlayer();
                if (player == null) {
                    return;
                }
                if (TimberNoCheat.checkmanager.isvalid_create(player)) {
                    PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(player);
                    pd.setPackethit(pd.getPackethit()+1);
                }
            }
        });
        register(new PacketAdapter(TimberNoCheat.instance,
                PacketType.Play.Client.ARM_ANIMATION) {
            public void onPacketReceiving(final PacketEvent event) {
                final Player player = event.getPlayer();
                if (player == null) {
                    return;
                }
                if (TimberNoCheat.checkmanager.isvalid_create(player)) {
                    PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(player);
                    pd.setPacketswing(pd.getPacketswing()+1);
                }
            }
        });
    }

    @Override
    public void startTasks() {
        register(new TimberScheduler(Scheduler.KILLAURA, () -> {
            for(Player player : Bukkit.getOnlinePlayers())
                if (TimberNoCheat.checkmanager.isvalid_create(player)){
                    PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(player);
                    if(pd.getPackethit() < pd.getPacketswing()) updateVio(Killaura.this, player, (pd.getPacketswing()-pd.getPackethit())*swingvio, " §6TYPE: §bHITSWING");
                    pd.setPackethit(0);
                    pd.setPacketswing(0);
                }
        }).startTimer(20));
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
        if(aimbot) check_aimbot(e, pd);
    }

    private void check_multi(EntityDamageByEntityEvent e, PlayerData pd){
        if(pd.getLasthitentity() == 0){
            pd.setLasthitentity(e.getEntity().getEntityId());
        }
        pd.getHittetEntitys().put(e.getEntity().getEntityId(), System.currentTimeMillis());
        for(Map.Entry<Integer, Long> pair : ((HashMap<Integer, Long>)pd.getHittetEntitys().clone()).entrySet())
            if(System.currentTimeMillis()-pair.getValue() > 800)
                pd.getHittetEntitys().remove(pair.getKey());
        if(pd.getHittetEntitys().size() > max_targets){
            updateVio(this, (Player) e.getDamager(), pd.getHittetEntitys().size()-max_targets*6, " §6TYPE: §bMULTI_AURA_TARGETS", " §6TARGETS: §b" + pd.getHittetEntitys().size());
        }
        long delay = System.currentTimeMillis()-pd.getLasthitmutli();
        if(pd.getLasthitentity() != e.getEntity().getEntityId() && delay < multi_delay){
            e.setCancelled(true);
            updateVio(this, (Player) e.getDamager(), multi_delay-delay*1.6, " §6TYPE: §bMULTI_AURA_DELAY", " §6DELAY: §b" + delay);
        }
        pd.setLasthitmutli(System.currentTimeMillis());
        pd.setLasthitentity(e.getEntity().getEntityId());
    }

    private void check_aimbot(EntityDamageByEntityEvent e, PlayerData pd){
        Player damager = (Player) e.getDamager();
        if (damager.getAllowFlight() || !((e.getEntity()) instanceof Player)) return;
        Location from = pd.getAimborloc();
        Location to = damager.getLocation();
        pd.setAimborloc(damager.getLocation());
        double LastDifference = pd.getAimbotdiff()==-1?-111111.0:pd.getAimbotdiff();
        if (from == null || (to.getX() == from.getX() && to.getZ() == from.getZ())) return;
        double diffnow = Math.abs(to.getYaw() - from.getYaw());
        if (diffnow == 0.0) return;
        if (diffnow > 2.4) {
            double diff = Math.abs(LastDifference - diffnow);
            if(e.getEntity().getVelocity().length() < 0.1) {
                if(diff < 1.4) addCount(damager, "aimbot");
                else resetCount(damager, "aimbot");
            } else {
                if(diff < 1.8) addCount(damager, "aimbot");
                else resetCount(damager, "aimbot");
            }
        }
        pd.setAimbotdiff(diffnow);
        if (hasReached(damager, "aimbot", 3)) {
            resetCount(damager, "aimbot");
            updateVio(this, damager, 6);
        }
    }

    private void check_range(EntityDamageByEntityEvent e, PlayerData pd){
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

        if(player.getVelocity().length() <= max_velocity && !Velocity.velocity.containsKey(player.getUniqueId())) {
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
            TimberNoCheat.instance.getDebugger().sendDebug(Debuggers.ATTACK_RANGE, "MaxReach=" + maxreach + " Reach=" + reach);

            if(reach > maxreach) {
                updateVio(this, damager, reach-maxreach*viomodifier, " §6TYPE: §bREACH", " §6MAXREACH: §b" + maxreach, " §6REACH: §b" + reach);
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
