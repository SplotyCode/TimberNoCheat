package me.david.TimberNoCheat.checktools;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import me.david.api.utils.player.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.potion.PotionEffectType;

public class FalsePositive implements Listener {

    public static class FalsePositiveChecks {
        long piston;
        long slime;
        long bed;
        public boolean enderpearl;
        long teleport;
        long world;
        long vehicle;
        long rod;
        long hitorbow;
        long explosion;
        long liquid;
        long climp;
        long otherknockbag;
        long chest;

        long jumpboost;
        public boolean jumpboost(Player player){
            if (System.currentTimeMillis()-jumpboost < 100) return true;
            boolean bool = (player.hasPotionEffect(PotionEffectType.JUMP)) && (SpeedUtil.getPotionEffectLevel(player, PotionEffectType.JUMP) < 128 || SpeedUtil.getPotionEffectLevel(player, PotionEffectType.JUMP) > 250);
            if (bool) jumpboost = System.currentTimeMillis();
            return bool;
        }

        public boolean nearboat(Player p){
            for (Entity localEntity : p.getNearbyEntities(0.7D, 0.61D, 0.7D))
                if ((localEntity instanceof Boat))
                    return true;
            return false;
        }

        public boolean hasPiston(long l){
            return System.currentTimeMillis()-piston<l;
        }

        public boolean hasSlime(long l){
            return System.currentTimeMillis()-slime<l;
        }

        public boolean hasBed(long l){
            return System.currentTimeMillis()-bed<l;
        }

        public boolean hasTeleport(long l){
            return System.currentTimeMillis()-teleport<l;
        }

        public boolean hasWorld(long l){
            return System.currentTimeMillis()-world<l;
        }

        public boolean hasVehicle(long l){
            return System.currentTimeMillis()-vehicle<l;
        }

        public boolean hasRod(long l){
            return System.currentTimeMillis()-rod<l;
        }

        public boolean hasHitorbow(long l){
            return System.currentTimeMillis()-hitorbow<l;
        }

        public boolean hasExplosion(long l){
            return System.currentTimeMillis()-explosion<l;
        }

        public boolean hasLiquid(long l){
            return System.currentTimeMillis()-liquid<l;
        }

        public boolean hasClimp(long l){
            return System.currentTimeMillis()-climp>l;
        }

        public boolean hasOtherKB(long l){
            return System.currentTimeMillis()-otherknockbag>l;
        }

        public boolean hasChest(long l){
            return System.currentTimeMillis()-chest>l;
        }
    }

    @EventHandler
    private void onRod(PlayerFishEvent event) {
        if (event.getCaught() != null && event.getCaught() instanceof Player)
            TimberNoCheat.checkmanager.getPlayerdata((Player) event.getCaught()).getFalsepositives().rod = System.currentTimeMillis();
    }

    @EventHandler
    private void onVehicle(VehicleExitEvent event) {
        if (event.getExited() instanceof Player)
            TimberNoCheat.checkmanager.getPlayerdata((Player) event.getExited()).getFalsepositives().vehicle = System.currentTimeMillis();
    }

    @EventHandler
    private void onHitorBow(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata((Player) event.getEntity());
            if ((event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE))
                pd.getFalsepositives().hitorbow = System.currentTimeMillis();
            else if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)
                pd.getFalsepositives().explosion = System.currentTimeMillis();
            else if(event.getCause() == EntityDamageEvent.DamageCause.MAGIC || event.getCause() == EntityDamageEvent.DamageCause.CONTACT || event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING || event.getCause() == EntityDamageEvent.DamageCause.THORNS || event.getCause() == EntityDamageEvent.DamageCause.VOID || event.getCause() == EntityDamageEvent.DamageCause.POISON)
                pd.getFalsepositives().otherknockbag = System.currentTimeMillis();
        }
    }

    @EventHandler
    private void onMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        Material under = p.getLocation().subtract(0, 1, 0).getBlock().getType();
        if (PlayerUtil.isOnGround(p) && under == Material.SLIME_BLOCK)
            pd.getFalsepositives().slime = System.currentTimeMillis();
        if(PlayerUtil.isOnGround(p) && (under == Material.CHEST || under == Material.ENDER_CHEST || under == Material.TRAPPED_CHEST))
            pd.getFalsepositives().chest = System.currentTimeMillis();
        if(PlayerUtil.isInLiquid(p))
            pd.getFalsepositives().liquid = System.currentTimeMillis();
        if(PlayerUtil.isOnClimbable(p))
            pd.getFalsepositives().climp = System.currentTimeMillis();
        if(pd.getFalsepositives().enderpearl && (event.getTo().getWorld() != event.getFrom().getWorld()) || (event.getTo().distance(event.getFrom()) >= 2.5D))
            pd.getFalsepositives().enderpearl = false;
    }

    @EventHandler
    private void onTeleport(PlayerTeleportEvent event) {
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(event.getPlayer());
        pd.getFalsepositives().teleport = System.currentTimeMillis();
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL)
            pd.getFalsepositives().enderpearl = true;
    }

    @EventHandler
    private void onbed(PlayerBedLeaveEvent event) {
        TimberNoCheat.checkmanager.getPlayerdata(event.getPlayer()).getFalsepositives().bed = System.currentTimeMillis();
    }

    @EventHandler
    private void onworld(PlayerChangedWorldEvent event) {
        TimberNoCheat.checkmanager.getPlayerdata(event.getPlayer()).getFalsepositives().world = System.currentTimeMillis();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onPistion(BlockPistonExtendEvent event) {
        Location loc = event.getBlock().getLocation();
        Location localLocation2;
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(player);
            localLocation2 = player.getLocation();
            if (getDistance(localLocation2, loc) <= 3.5D || getDistance(localLocation2.clone().add(0.0D, 1.0D, 0.0D), loc) <= 3.5D) {
                pd.getFalsepositives().piston = System.currentTimeMillis();
                return;
            }
            for (Block block : event.getBlocks())
                if (getDistance(localLocation2, block.getLocation()) <= 3.5D || getDistance(localLocation2.clone().add(0.0D, 1.0D, 0.0D), block.getLocation()) <= 3.5D) {
                    pd.getFalsepositives().piston = System.currentTimeMillis();
                }
        }
    }

    private double getDistance(Location loc1, Location loc2) {
        return loc1 == null || (loc2 == null) || (loc1.getWorld() != loc2.getWorld()) ? 0.0D : loc1.distance(loc2);
    }

}
