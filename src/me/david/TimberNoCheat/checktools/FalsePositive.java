package me.david.TimberNoCheat.checktools;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import me.david.api.utils.player.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldBorder;
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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.potion.PotionEffectType;

public class FalsePositive implements Listener {


    public FalsePositive(){
        Bukkit.getScheduler().runTaskTimer(TimberNoCheat.instance, new Runnable() {
            @Override
            public void run() {
                for(Player p : Bukkit.getOnlinePlayers())
                    if(TimberNoCheat.checkmanager.isvalid_create(p))
                        TimberNoCheat.checkmanager.getPlayerdata(p).getFalsepositives().wasongroundtick = false;
            }
        }, 1, 1);
    }

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
        long deathorrespawn;
        long knockbag;
        long speed;

        /* was the player onground in this tick? */
        public boolean wasongroundtick;

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

        public boolean worldboarder(Player p){
            WorldBorder border = p.getWorld().getWorldBorder();
            Location loc = p.getLocation();
            loc.setY(1.0D);
            Location center = border.getCenter();
            double xDiss = Math.abs(center.getX() - loc.getX());
            double yDiss = Math.abs(center.getZ() - loc.getZ());
            double d3 = loc.distance(center.clone().add(xDiss, 0, yDiss));
            double d4 = loc.distance(center.clone().add(-xDiss, 0, -yDiss));
            double d5 = loc.distance(center.clone().add(-xDiss, 0, yDiss));
            double d6 = loc.distance(center.clone().add(xDiss, 0, -yDiss));

            double size = border.getSize();
            return (d3 >= size-1 && d3 <= size + 1 && d4 > 1) || (d4 >= size-1 && d4 <= size+1 && d3 > 1) || (d5 >= size - 1 && d5 <= size + 1 && d6 > 1) || (d6 >= size - 1 && d6 <= size + 1 && d5 > 1);
        }

        public boolean hasSpeed(){
            return System.currentTimeMillis()-speed<60;
        }
        public boolean hasSpeed(long l){
            return System.currentTimeMillis()-speed<l;
        }

        public float speed(Player p){
            int speed = hasSpeed()?1:0;
            float base = 1.25f;
            if (speed != 0) {
                int j = SpeedUtil.getPotionEffectLevel(p, PotionEffectType.SPEED);
                if ((speed & (!p.hasPotionEffect(PotionEffectType.SPEED) ? 1 : 0)) != 0) {
                    j = 255;
                }
                base += (j + 1) * (1.25 / 4);
            }
            return base;
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

        public boolean hasKnockbag(long l){
            return System.currentTimeMillis()-knockbag<l;
        }

        public boolean hasWorld(long l){
            return System.currentTimeMillis()-world<l;
        }

        public boolean hasDeathorRespawn(long l){
            return System.currentTimeMillis()-deathorrespawn<l;
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
            return System.currentTimeMillis()-climp<l;
        }

        public boolean hasOtherKB(long l){
            return System.currentTimeMillis()-otherknockbag<l;
        }

        public boolean hasChest(long l){
            return System.currentTimeMillis()-chest<l;
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onRod(PlayerFishEvent event) {
        if (!event.isCancelled() && event.getCaught() != null && event.getCaught() instanceof Player && TimberNoCheat.checkmanager.isvalid_create((Player) event.getCaught()))
            TimberNoCheat.checkmanager.getPlayerdata((Player) event.getCaught()).getFalsepositives().rod = System.currentTimeMillis();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onVehicle(VehicleExitEvent event) {
        if (event.getExited() instanceof Player && TimberNoCheat.checkmanager.isvalid_create((Player) event.getExited()) && !event.isCancelled())
            TimberNoCheat.checkmanager.getPlayerdata((Player) event.getExited()).getFalsepositives().vehicle = System.currentTimeMillis();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onVelocity(PlayerVelocityEvent event){
        if(!event.isCancelled() && TimberNoCheat.checkmanager.isvalid_create(event.getPlayer()))
            TimberNoCheat.checkmanager.getPlayerdata(event.getPlayer()).getFalsepositives().knockbag = System.currentTimeMillis();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onHitorBow(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            if(event.isCancelled() || !TimberNoCheat.checkmanager.isvalid_create((Player) event.getEntity())){
                return;
            }
            PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata((Player) event.getEntity());
            if ((event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE))
                pd.getFalsepositives().hitorbow = System.currentTimeMillis();
            else if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)
                pd.getFalsepositives().explosion = System.currentTimeMillis();
            else if(event.getCause() == EntityDamageEvent.DamageCause.MAGIC || event.getCause() == EntityDamageEvent.DamageCause.CONTACT || event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING || event.getCause() == EntityDamageEvent.DamageCause.THORNS || event.getCause() == EntityDamageEvent.DamageCause.VOID || event.getCause() == EntityDamageEvent.DamageCause.POISON)
                pd.getFalsepositives().otherknockbag = System.currentTimeMillis();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onMove(PlayerMoveEvent event) {
        if(event.isCancelled() || !TimberNoCheat.checkmanager.isvalid_create(event.getPlayer())){
            return;
        }
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
        if(p.hasPotionEffect(PotionEffectType.SPEED))
            pd.getFalsepositives().speed = System.currentTimeMillis();
        if(pd.getFalsepositives().enderpearl && (event.getTo().getWorld() != event.getFrom().getWorld()) || (event.getTo().distance(event.getFrom()) >= 2.5D))
            pd.getFalsepositives().enderpearl = false;
        if(PlayerUtil.isOnGround(p)) pd.getFalsepositives().wasongroundtick = true;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onTeleport(PlayerTeleportEvent event) {
        if(event.isCancelled() || !TimberNoCheat.checkmanager.isvalid_create(event.getPlayer())){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(event.getPlayer());
        pd.getFalsepositives().teleport = System.currentTimeMillis();
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL)
            pd.getFalsepositives().enderpearl = true;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onbed(PlayerBedLeaveEvent event) {
        if(TimberNoCheat.checkmanager.isvalid_create(event.getPlayer())) TimberNoCheat.checkmanager.getPlayerdata(event.getPlayer()).getFalsepositives().bed = System.currentTimeMillis();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onworld(PlayerChangedWorldEvent event) {
        if(TimberNoCheat.checkmanager.isvalid_create(event.getPlayer())) TimberNoCheat.checkmanager.getPlayerdata(event.getPlayer()).getFalsepositives().world = System.currentTimeMillis();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void death(PlayerDeathEvent event){
        if(TimberNoCheat.checkmanager.isvalid_create(event.getEntity())) TimberNoCheat.checkmanager.getPlayerdata(event.getEntity()).getFalsepositives().deathorrespawn = System.currentTimeMillis();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void respawn(PlayerRespawnEvent event){
        TimberNoCheat.checkmanager.getPlayerdata(event.getPlayer()).getFalsepositives().deathorrespawn = System.currentTimeMillis();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onPistion(BlockPistonExtendEvent event) {
        Location loc = event.getBlock().getLocation();
        if(event.isCancelled()) return;
        Location localLocation2;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if(!TimberNoCheat.checkmanager.isvalid_create(player)) continue;
            PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(player);
            localLocation2 = player.getLocation();
            if (getDistance(localLocation2, loc) <= 3.5 || getDistance(localLocation2.clone().add(0, 1, 0), loc) <= 3.5D) {
                pd.getFalsepositives().piston = System.currentTimeMillis();
                return;
            }
            for (Block block : event.getBlocks())
                if (getDistance(localLocation2, block.getLocation()) <= 3.5D || getDistance(localLocation2.clone().add(0, 1, 0), block.getLocation()) <= 3.5D)
                    pd.getFalsepositives().piston = System.currentTimeMillis();
        }
    }

    private double getDistance(Location loc1, Location loc2) {
        return loc1 == null || (loc2 == null) || (loc1.getWorld() != loc2.getWorld()) ? 0.0D : loc1.distance(loc2);
    }

}
