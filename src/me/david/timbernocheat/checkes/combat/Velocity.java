package me.david.timbernocheat.checkes.combat;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checktools.FalsePositive;
import me.david.api.utils.cordinates.LocationUtil;
import me.david.timbernocheat.runnable.TimberScheduler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerVelocityEvent;

import java.util.HashMap;

public class Velocity extends Check {

    private final boolean teleport;

    public Velocity() {
        super("Velocity", Category.COBMAT);
        teleport = getBoolean("teleport");
        register(new TimberScheduler("Velocity-Check", () -> {
            for(Player p : Bukkit.getOnlinePlayers())
                if(TimberNoCheat.getCheckManager().isvalid_create(p))
                    check(p);
        }).startTimer(1));
    }

    private HashMap<Player, Location> velocity = new HashMap<>();
    private HashMap<Player, Location> back = new HashMap<>();

    @EventHandler
    private void quit(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        velocity.remove(p);
        back.remove(p);
    }

    @EventHandler
    private void teleport(PlayerTeleportEvent event) {
        Player p = event.getPlayer();
        velocity.remove(p);
        back.remove(p);
    }

    private void check(Player p){
        if (getCountTick(p, "scheduler") == 1 && (velocity.containsKey(p)) && (checke(p))){
            Location loc1 = p.getLocation().clone();
            Location loc2 = velocity.get(p).clone();
            if (loc1.getWorld() == loc2.getWorld()) {
                double hdis = LocationUtil.getHorizontalDistance(loc1, loc2);
                double ydis = loc1.getY() - loc2.getY();
                double dis = loc1.distance(loc2);
                double d4 = ydis + hdis;
                int j = !countTickexsits(p, "projectile") ? 2 : 3;
                double d5;
                double d7;
                if (dis >= 1 && !countTickexsits(p, "knockback")) {
                    d5 = hdis + Math.abs(ydis);
                    d7 = Math.abs(dis - d5);
                    if (d7 >= 1.5D) {
                        setCountTick(p, "extreme", 77);
                        if (getCountTick(p, "extreme") >= j)
                            updateVio(this, p, d7*3);
                    }
                }
                if (dis <= 0.3D && ydis != 0) {
                    setCountTick(p, "hard", 77);
                    if (getCountTick(p, "hard") >= j)
                    updateVio(this, p, dis*4);
                }
                if (d4 == dis && countTickexsits(p, "projectile")) {
                    setCountTick(p, "combined", 55);
                    if (getCountTick(p, "combined") >= j) updateVio(this, p, 8);
                }
                if (dis <= 0.15D) {
                    setCountTick(p, "distance", 66);
                    if (getCountTick(p, "distance") >= j) updateVio(this, p, dis*2.8);
                }
                if (ydis > 0 && ydis <= 0.5D && countTickexsits(p, "projectile")) {
                    d5 = Math.abs(ydis - getCountTick(p, "ver"));
                    if (d5 <= 0.01D && getCountTick(p, "ver") != -1) {
                        setCountTick(p, "vertical", 77);
                        if (getCountTick(p, "vertical") >= j) updateVio(this, p, getCountTick(p, "vertical")-j*3.2);
                    }
                    setCountTick(p, "ver", ydis);
                } else resetCountTick(p, "ver");
                if (ydis > 0 && hdis == 0) {
                    setCountTick(p, "horizontal", 99);
                    if (getCountTick(p, "horizontal") >= 2) updateVio(this, p, hdis*2.8);
                }
                Location loc3;
                if (dis >= 2.5D && countTickexsits(p, "projectile") && countTickexsits(p, "direction")) {
                    loc3 = loc2.clone();
                    loc3 = loc3.add(loc3.getDirection().multiply(dis));
                    double d6 = LocationUtil.getHorizontalDistance(loc3, loc1);
                    if (d6 <= 0.5D) updateVio(this, p, 5);
                }
                if (back.containsKey(p) && dis >= 3 && countTickexsits(p, "projectile"))
                {
                    loc3 = back.get(p);
                    Location localLocation4 = loc2.add(loc3.getDirection().multiply(dis));
                    d7 = LocationUtil.getHorizontalDistance(localLocation4, loc1);
                    double d9 = d7 - dis;
                    if (d7 >= 2.5D && d9 >= 1) {
                        updateVio(this, p, 1);
                        if (teleport) p.teleport(loc2);
                    }
                }
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGH)
    private void damage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player p = (Player)event.getEntity();
            if(!TimberNoCheat.getCheckManager().isvalid_create(p)) return;
            Entity damager = event.getDamager();
            EntityDamageEvent.DamageCause cause = event.getCause();
            if (cause == EntityDamageEvent.DamageCause.ENTITY_ATTACK || (cause == EntityDamageEvent.DamageCause.PROJECTILE && damager instanceof Arrow)) {
                double d = p.getLocation().getDirection().distance(damager.getLocation().getDirection());
                velocity.put(p, p.getLocation());
                setCountTick(p, "damage", 2);
                setCountTick(p, "scheduler", 5);
                if (!(damager instanceof Player)) back.put(p, damager.getLocation());
                if (d <= 1)
                    setCountTick(p, "direction", 5);
                if (cause == EntityDamageEvent.DamageCause.PROJECTILE) {
                    setCountTick(p, "projectile", 5);
                    setCountTick(p, "knockback", 5);
                } else if (((damager instanceof Player)) && (!p.equals(damager))) {
                    Player localPlayer2 = (Player)event.getDamager();
                    if (localPlayer2.getItemInHand().containsEnchantment(Enchantment.KNOCKBACK) || (localPlayer2.isFlying())) {
                        setCountTick(p, "knockback", 5);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void move(PlayerMoveEvent event) {
        if (LocationUtil.distance(event.getFrom(), event.getTo()) == 0) return;
        Player p = event.getPlayer();
        if(!TimberNoCheat.getCheckManager().isvalid_create(p)) return;
        FalsePositive.FalsePositiveChecks fp = TimberNoCheat.getCheckManager().getPlayerdata(p).getFalsepositives();
        if (!checke(p) || walk(p) != 0.0F || (fp.jumpboost(p)) ||
        (fp.hasKnockbag(80) || countTickexsits(p, "scheduler") || countTickexsits(p, "knockback"))){
            resetCount(p, "cache");
            return;
        }
        Location from = event.getFrom();
        double dis = event.getTo().distance(from);
        double cache = getCount(p, "cache");
        double d3 = Math.abs(dis - cache);
        if (d3 >= fp.speed(p)){
            updateVio(this, p, d3*3.2);
            if (teleport) p.teleport(from);
        }
        setCount(p, "cache", dis);
    }

    @EventHandler(priority=EventPriority.HIGH)
    private void velocity(PlayerVelocityEvent event) {
        if(!TimberNoCheat.getCheckManager().isvalid_create(event.getPlayer())) return;
        if (event.isCancelled())
            whitelist(event.getPlayer(), 10L);
    }

    private static boolean checke(Player p){
        FalsePositive.FalsePositiveChecks fp = TimberNoCheat.getCheckManager().getPlayerdata(p).getFalsepositives();
        return !fp.hasVehicle(80) && !fp.hasClimp(60) && !p.isFlying() && !fp.worldboarder(p);
    }

    private float walk(Player p){
        float base = p.getWalkSpeed() - 0.2F;
        if (base > 0) setCountTick(p, "walkdiff", 40);
        else if (countTickexsits(p, "walkdiff")) return 1.0F;
        return base < 0 ? 0 : base;
    }
}
