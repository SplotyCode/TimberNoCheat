package me.david.timbernocheat.checkes.combat;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.PlayerData;
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
    private final int max_targets;

    public Killaura() {
        super("Killaura", Category.COBMAT);
        registerChilds(Types.values());
        Check range = getChildbyEnum(Types.RANGE);
        multi_delay = getChildbyEnum(Types.MULTIDELAY).getLong("delay");
        defaultrange = range.getDouble("defaultrange");
        speed = range.getDouble("speed");
        ping_100_200 = range.getDouble("ping_100_200");
        ping_200_250 = range.getDouble("ping_200_250");
        ping_250_300 = range.getDouble("ping_250_300");
        ping_300_350 = range.getDouble("ping_300_350");
        ping_350_400 = range.getDouble("ping_350_400");
        ping_over400 = range.getDouble("ping_over400");
        viomodifier = range.getDouble("viomodifier");
        max_velocity = range.getDouble("max_velocity");
        lowgroud_mofier = range.getDouble("lowgroud_mofier");
        max_targets = getChildbyEnum(Types.MULTITARGET).getInt("maxtargets");

        register(new PacketAdapter(TimberNoCheat.getInstance(),
                PacketType.Play.Client.USE_ENTITY) {
            public void onPacketReceiving(final PacketEvent event) {
                final Player player = event.getPlayer();
                if (player == null) {
                    return;
                }
                if (CheckManager.getInstance().isvalid_create(player)) {
                    PlayerData pd = CheckManager.getInstance().getPlayerdata(player);
                    pd.setPacketHit(pd.getPacketHit()+1);
                }
            }
        });
        register(new PacketAdapter(TimberNoCheat.getInstance(),
                PacketType.Play.Client.ARM_ANIMATION) {
            public void onPacketReceiving(final PacketEvent event) {
                final Player player = event.getPlayer();
                if (player == null) {
                    return;
                }
                if (CheckManager.getInstance().isvalid_create(player)) {
                    PlayerData pd = CheckManager.getInstance().getPlayerdata(player);
                    pd.setPacketSwing(pd.getPacketSwing()+1);
                }
            }
        });
    }

    public enum Types {

        HITSWING,
        MULTIDELAY,
        MULTITARGET,
        AIMBOT,
        RANGE

    }

    @Override
    public void startTasks() {
        register(new TimberScheduler(Scheduler.KILLAURA, () -> {
            for(Player player : Bukkit.getOnlinePlayers())
                if (CheckManager.getInstance().isvalid_create(player)){
                    PlayerData pd = CheckManager.getInstance().getPlayerdata(player);
                    if(pd.getPacketHit() < pd.getPacketSwing()) updateVio(Killaura.this, player, pd.getPacketSwing()-pd.getPacketHit());
                    pd.setPacketHit(0);
                    pd.setPacketSwing(0);
                }
        }).startTimer(20));
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onDamage(EntityDamageByEntityEvent e) {
        if(!(e.getDamager() instanceof Player)){
            return;
        }
        final Player p = (Player) e.getDamager();
        if (!CheckManager.getInstance().isvalid_create(p) || e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK || e.isCancelled()) {
            return;
        }
        PlayerData pd = CheckManager.getInstance().getPlayerdata(p);
        if(e.getEntity() instanceof Player && !((Player)e.getEntity()).getAllowFlight() && !p.getAllowFlight()){
            check_range(e, pd);
        }
        check_multi(e, pd);
        check_aimbot(e, pd);
    }

    private void check_multi(EntityDamageByEntityEvent event, PlayerData pd){
        if(pd.getLastHitEntity() == 0){
            pd.setLastHitEntity(event.getEntity().getEntityId());
        }
        pd.getHittetEntitys().put(event.getEntity().getEntityId(), System.currentTimeMillis());
        for(Map.Entry<Integer, Long> pair : ((HashMap<Integer, Long>)pd.getHittetEntitys().clone()).entrySet())
            if(System.currentTimeMillis()-pair.getValue() > 800)
                pd.getHittetEntitys().remove(pair.getKey());
        if(pd.getHittetEntitys().size() > max_targets){
            if(updateVio(getChildbyEnum(Types.MULTITARGET), (Player) event.getDamager(), pd.getHittetEntitys().size()-max_targets*6, " §6TARGETS: §b" + pd.getHittetEntitys().size())) event.setCancelled(true);
        }
        long delay = System.currentTimeMillis()-pd.getLastHitMutli();
        if(pd.getLastHitEntity() != event.getEntity().getEntityId() && delay < multi_delay){
            if(updateVio(getChildbyEnum(Types.MULTIDELAY), (Player) event.getDamager(), multi_delay-delay*1.6, " §6DELAY: §b" + delay)) event.setCancelled(true);
        }
        pd.setLastHitMutli(System.currentTimeMillis());
        pd.setLastHitEntity(event.getEntity().getEntityId());
    }

    private void check_aimbot(EntityDamageByEntityEvent event, PlayerData pd){
        Player damager = (Player) event.getDamager();
        if (damager.getAllowFlight() || !((event.getEntity()) instanceof Player)) return;
        Location from = pd.getAimBotLoc();
        Location to = damager.getLocation();
        pd.setAimBotLoc(damager.getLocation());
        double LastDifference = pd.getAimBotDiff()==-1?-111111.0:pd.getAimBotDiff();
        if (from == null || (to.getX() == from.getX() && to.getZ() == from.getZ())) return;
        double diffnow = Math.abs(to.getYaw() - from.getYaw());
        if (diffnow == 0.0) return;
        if (diffnow > 2.4) {
            double diff = Math.abs(LastDifference - diffnow);
            if(event.getEntity().getVelocity().length() < 0.1) {
                if(diff < 1.4) addCount(damager, "aimbot");
                else resetCount(damager, "aimbot");
            } else {
                if(diff < 1.8) addCount(damager, "aimbot");
                else resetCount(damager, "aimbot");
            }
        }
        pd.setAimBotDiff(diffnow);
        if (hasReached(damager, "aimbot", 3)) {
            resetCount(damager, "aimbot");
            if(updateVio(getChildbyEnum(Types.AIMBOT), damager, 6))
                event.setCancelled(true);
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
            TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.ATTACK_RANGE, "MaxReach=" + maxreach + " Reach=" + reach);

            if(reach > maxreach) {
                updateVio(getChildbyEnum(Types.RANGE), damager, reach-maxreach*viomodifier, " §6TYPE: §bREACH", " §6MAXREACH: §b" + maxreach, " §6REACH: §b" + reach);
                //pd.getReaches().add(reach);
                //pd.setLastreach(System.currentTimeMillis());
            }

            /*if(DateTimeUtil.elapsed(pd.getLastreach(), TimberNoCheat.getInstance().settings.fight_killaura_rangeclearaftermilis)) {
                pd.getReaches().clear();
                pd.setLastreach(System.currentTimeMillis());
            }

            if(pd.getReaches().size() > TimberNoCheat.getInstance().settings.fight_killaura_range_toohitslongtoreport) {
                Double average = MathUtil.averageDouble(pd.getReaches());
                Double a = TimberNoCheat.getInstance().settings.fight_killaura_range_levelrange - maxreach;
                Double b = average - maxreach;

                int level = (int)Math.round(b < 0.0D?0.00000000001D:b / a < 0.0D?0.00000000001D:a * 100);
                pd.getReaches().clear();
                CheckManager.getInstance().notify(this, damager, " §6TYPE: §bREACH", " §6WAHRSCHEINLICHKEIT: §b" + level +"%", " §6Reach: §b" + reach, " §6CalculatetMacReach: §b" + maxreach);
            }
            */
        }
    }


}
