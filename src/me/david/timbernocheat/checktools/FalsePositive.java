package me.david.timbernocheat.checktools;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.PlayerData;
import me.david.timbernocheat.util.SpeedUtil;
import me.david.api.anotations.Nullable;
import me.david.api.utils.player.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldBorder;
import org.bukkit.block.Block;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
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
        /* Was the Player that Tick on Ground? */
        Bukkit.getScheduler().runTaskTimer(TimberNoCheat.instance, () -> {
            for(Player p : Bukkit.getOnlinePlayers())
                if(TimberNoCheat.checkmanager.isvalid_create(p))
                    TimberNoCheat.checkmanager.getPlayerdata(p).getFalsepositives().wasOnGroundTick = false;
        }, 1, 1);
    }

    public static class FalsePositiveChecks {

        /* Last Time the player was near a Piston that gets extendet */
        long piston;

        /* Last Time the player was bouncing on a Slime */
        long slime;

        /* Last Time the player was collidating with a Bed(1.12 capability) */
        long bed;

        /*
         * Is the user currently throwing an enderpearl
         * Causing weird collidations and teleports
         */
        public boolean enderpearl;

        /*
         * Last Time that Player was teleportet
         */
        long teleport;

        /*
         * Last Time the Player switches a world
         * Can cause blockglitching also sends more packets
         */
        long world;

        /*
         * Last Time the Player was ona a vehicle
         * Weird movements when leaving or entering a vehicle
         */
        long vehicle;

        /*
         * Last Time the Player was hitten with a Rod
         * Causing higher jumps and strange velocity's
         */
        long rod;

        /*
         * Last Time the Player was hitten or should by a Bow
         * Causing higher jumps and strange velocity's
         */
        long hitorbow;

        /*
         * Last Time the Player was near an Explosion
         * strange velocity's and a big boost
         */
        long explosion;

        /*
         * Last Time the Player was in something Liquid
         * weird movements on the y axis
         */
        long liquid;

        /*
         * Last Time the Player clips up a web, wine or ladder
         * weird movements on the y axis
         */
        long climp;

        /*
         * Last Time the Player was getting knockback thought something more special
         */
        long otherknockbag;

        /* Last Time the Player was standing on a Chest
         * Chest have an smaller hithox
         */
        long chest;

        /* Last Time the Player died or Respawned
         * Causing more Packets (world download)
         */
        long deathorrespawn;

        /* Last Time the Player gets Velocity */
        long knockbag;

        /* Last Time the Player had an Speed potion */
        long speed;

        /* Was the player onground in this tick? */
        boolean wasOnGroundTick;

        /* The last Time a Player was hurt by The new Magma Blocks */
        long lastMagma;

        /* The last Time a Player was damaged by an Firework */
        long lastFirework;

        /* The last time a Player collidates with a Bed */
        long lastBed;

        /* The last time a Player collidates with a Slme*/
        long lastSlime;

        /* Has the Player JumpBoost or clout the currently be in such a Jump? */
        long jumpBoost;
        public boolean jumpboost(Player player){
            if (System.currentTimeMillis()- jumpBoost < 100) return true;
            boolean bool = (player.hasPotionEffect(PotionEffectType.JUMP)) && (SpeedUtil.getPotionEffectLevel(player, PotionEffectType.JUMP) < 128 || SpeedUtil.getPotionEffectLevel(player, PotionEffectType.JUMP) > 250);
            if (bool) jumpBoost = System.currentTimeMillis();
            return bool;
        }

        /* Is the near a boat */
        public boolean nearboat(Player p){
            for (Entity localEntity : p.getNearbyEntities(0.7D, 0.61D, 0.7D))
                if ((localEntity instanceof Boat))
                    return true;
            return false;
        }



        /* Is the Player near the WorldBoarder */
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

        public boolean hasMagma(long l){ return System.currentTimeMillis()- lastMagma <l;}
        public boolean hasMagma(){ return hasMagma(30*20);}

        public boolean hasFirework(long l){ return System.currentTimeMillis()-lastFirework<l;}
        public boolean hasFirework(){ return hasFirework(10*20);}

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

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void onVelocity(PlayerVelocityEvent event){
        if(TimberNoCheat.checkmanager.isvalid_create(event.getPlayer()))
            TimberNoCheat.checkmanager.getPlayerdata(event.getPlayer()).getFalsepositives().knockbag = System.currentTimeMillis();
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void onHitorBow(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            if(!TimberNoCheat.checkmanager.isvalid_create((Player) event.getEntity())){
                return;
            }
            PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata((Player) event.getEntity());
            if(event.getDamager() instanceof Firework && event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)
                pd.getFalsepositives().lastFirework = System.currentTimeMillis();
            if ((event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE))
                pd.getFalsepositives().hitorbow = System.currentTimeMillis();
            else if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)
                pd.getFalsepositives().explosion = System.currentTimeMillis();
            else if(event.getCause() == EntityDamageEvent.DamageCause.MAGIC || event.getCause() == EntityDamageEvent.DamageCause.CONTACT || event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING || event.getCause() == EntityDamageEvent.DamageCause.THORNS || event.getCause() == EntityDamageEvent.DamageCause.VOID || event.getCause() == EntityDamageEvent.DamageCause.POISON)
                pd.getFalsepositives().otherknockbag = System.currentTimeMillis();
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player)return;
        final Player player = (Player) event.getEntity();
        if(TimberNoCheat.checkmanager.isvalid_create(player)){
            PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata((Player) event.getEntity());
            if(event.getCause() == EntityDamageEvent.DamageCause.valueOf("HOT_FLOOR")) pd.getFalsepositives().lastMagma = System.currentTimeMillis();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void onMove(PlayerMoveEvent event) {
        if(!TimberNoCheat.checkmanager.isvalid_create(event.getPlayer())){
            return;
        }
        Player p = event.getPlayer();
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        Material under = p.getLocation().subtract(0, 1, 0).getBlock().getType();
        Location to = event.getTo();
        Location fr = event.getFrom();
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
        if(PlayerUtil.isOnGround(p)) pd.getFalsepositives().wasOnGroundTick = true;
        if(!(to.getX() == fr.getX() && to.getY() == fr.getY() && to.getZ() == to.getZ())){

        }
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
        if(TimberNoCheat.checkmanager.isvalid_create(event.getPlayer())) TimberNoCheat.checkmanager.getPlayerdata(event.getPlayer()).getFalsepositives().deathorrespawn = System.currentTimeMillis();
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

    /* Returns the Distance between two Position */
    private double getDistance(@Nullable Location loc1, @Nullable Location loc2) {
        return loc1 == null || (loc2 == null) || (loc1.getWorld() != loc2.getWorld()) ? 0.0D : loc1.distance(loc2);
    }

}
