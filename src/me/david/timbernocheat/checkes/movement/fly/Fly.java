package me.david.timbernocheat.checkes.movement.fly;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.checkes.movement.fly.checks.*;
import me.david.timbernocheat.checktools.FalsePositive;
import me.david.timbernocheat.checktools.General;
import me.david.timbernocheat.util.CheckUtils;
import me.david.timbernocheat.util.MoveingUtils;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Fly extends Check {

    private final String setback;

    public Fly(){
        super("Fly", Category.MOVEMENT);
        setback = getString("setbackmethode");
        registerChilds(new Vanilla(this), new AirFall(this),
                       new WrongDirection(this), new TicksUpgoing(this),
                       new WrongDamage(this), new Ability(this),
                       new DistanceUpgoing(this));
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void playerMove(PlayerMoveEvent event){
        final Player player = event.getPlayer();
        if (!CheckManager.getInstance().isvalid_create(player)) return;
        PlayerData pd = CheckManager.getInstance().getPlayerdata(player);
        General.GeneralValues general = pd.getGenerals();
        FalsePositive.FalsePositiveChecks fp = pd.getFalsePositives();
        TimberNoCheat.getInstance().getMoveprofiler().start("Fly ");
        FlyMoveData move = new FlyMoveData(event);
        FlyData data = pd.getFlyData();

        if(player.getVehicle() != null){
            forChilds((check) -> check.onSkipMove(SkipReason.VEHICLE));
            return;
        }
        if(CheckUtils.doesColidateWithMaterial(Material.LADDER, move.getTo())){
            forChilds((check) -> check.onSkipMove(SkipReason.LADDER));
            return;
        }
        if(CheckUtils.doesColidateWithMaterial(Material.WATER, move.getTo()) || CheckUtils.doesColidateWithMaterial(Material.STATIONARY_WATER, move.getTo()) || CheckUtils.doesColidateWithMaterial(Material.STATIONARY_LAVA, move.getTo()) || CheckUtils.doesColidateWithMaterial(Material.LAVA, move.getTo())){
            forChilds((check) -> check.onSkipMove(SkipReason.LIQUID));
            return;
        }
        if(move.isToGround()) data.setGroundDistance(data.getGroundDistance()+move.getRawYDiff());

        if(move.isToGround()) data.setFalling(false);
        else if(move.getTo().getY() < move.getFrom().getY() && !data.isFalling()) {
            data.setFalling(true);
            forChilds((check) -> check.onFall(MoveingUtils.groundDistance(player)));
        }

        //Slime Call
        if(data.getLastMove() != null) {
            if (!data.getLastMove().isToGround() && move.isToGround()) {
                if (CheckUtils.doesColidateWithMaterial(Material.SLIME_BLOCK, player))
                    getSubChecks().forEach((check) -> check.onSlime(data, data.getSlimePeek()));
                data.setSlimePeek(0);
            } else if (data.getGroundDistance() > data.getSlimePeek()) {
                data.setSlimePeek(data.getGroundDistance());
            }
        }

        //Main Move Call
        if(data.getLastMove() == null) forChilds((check) -> check.onSkipMove(SkipReason.FIRSTMOVE));
        else if(fp.enderpearl) forChilds((check) -> check.onSkipMove(SkipReason.ENDERPERL));
        else if(fp.hasVehicle(950)) forChilds((check -> check.onSkipMove(SkipReason.VEHICLE_LEAVE)));
        else forChilds((check) -> check.onMove(data, player, pd, move));

        data.setLastData(null);
        data.setLastData(data.clone());
        data.setLastMove(move);
        TimberNoCheat.getInstance().getMoveprofiler().end();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onTeleport(PlayerTeleportEvent event){
        final Player player = event.getPlayer();
        if (!CheckManager.getInstance().isvalid_create(player)) return;
        PlayerData pd = CheckManager.getInstance().getPlayerdata(player);
        forChilds((check -> check.reset(ResetReason.TELEPORT, pd.getFlyData(), player)));
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onWorldSwitch(PlayerChangedWorldEvent event){
        final Player player = event.getPlayer();
        if (!CheckManager.getInstance().isvalid_create(player)) return;
        final PlayerData pd = CheckManager.getInstance().getPlayerdata(player);
        forChilds((check -> check.reset(ResetReason.WORLDSWITCH, pd.getFlyData(), player)));
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onDamage(final EntityDamageEvent event){
        if(event.getEntityType() != EntityType.PLAYER || !CheckManager.getInstance().isvalid_create((Player) event.getEntity()))return;
        final Player player = (Player) event.getEntity();
        final FlyData data = CheckManager.getInstance().getPlayerdata(player).getFlyData();
        switch (event.getCause()){
            case ENTITY_EXPLOSION:case BLOCK_EXPLOSION:case PROJECTILE:case WITHER:case ENTITY_ATTACK:
                data.setWaitingForVelocity(true);
            break;
        }
        switch (event.getCause()){
            case BLOCK_EXPLOSION:
                forChilds((check) -> check.explostion(data, player, 4*2));
            break;
        }
        forChilds(check -> check.damage(data, player, event.getCause(), event.getOriginalDamage(EntityDamageEvent.DamageModifier.BASE), event.getDamage()));
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onEntityDamage(final EntityDamageByEntityEvent event){
        if(event.getEntityType() != EntityType.PLAYER || !CheckManager.getInstance().isvalid_create((Player) event.getEntity()))return;
        final Player player = (Player) event.getEntity();
        final Entity entity = event.getDamager();
        final FlyData data = CheckManager.getInstance().getPlayerdata(player).getFlyData();
        switch (event.getCause()){
            case ENTITY_EXPLOSION:
                forChilds((check) -> check.explostion(data, player, (((Creeper) entity).isPowered()?2*2: 2)));
            break;
            case ENTITY_ATTACK:
                forChilds((check) -> check.attack(data, player, CheckUtils.getKnockbag(entity)));
            break;
            case PROJECTILE:
                if(entity instanceof Arrow)
                    forChilds((check) -> check.bow(data, player, ((Arrow) entity).getKnockbackStrength()));
            break;
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerHitFishingRodEvent(final PlayerFishEvent event) {
        if(!CheckManager.getInstance().isvalid_create(event.getPlayer()))return;
        final Player player = event.getPlayer();
        final FlyData data = CheckManager.getInstance().getPlayerdata(player).getFlyData();
        if (event.getCaught() instanceof Player) {
            final Player caught = (Player) event.getCaught();
            if (player.getItemInHand().getType() == Material.FISHING_ROD) {
                forChilds((check) -> check.rod(data, caught));
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onVelocity(PlayerVelocityEvent event){
        if(!CheckManager.getInstance().isvalid_create(event.getPlayer()))return;
        final Player player = event.getPlayer();
        final FlyData data = CheckManager.getInstance().getPlayerdata(player).getFlyData();
        if(data.isWaitingForVelocity()){
            data.setWaitingForVelocity(false);
            data.setSpecialVelocity(event.getVelocity());
            data.setSpecialVelocityCouse(data.getLastHurtCause());
            data.setLastHurtCause(null);
            forChilds((check) -> check.velocity(data, player, event.getVelocity()));
        }
    }

    private void forChilds(Consumer<? super AbstractFlyCheck> consumer){
        getSubChecks().forEach(consumer);
    }

    private ArrayList<AbstractFlyCheck> getSubChecks(){
        ArrayList<AbstractFlyCheck> list = new ArrayList<>();
        for(Check check : getChildes()) list.add((AbstractFlyCheck) check);
        return list;
    }

    //Do we need this Methode as we use Move Velocity and TicksInAir?>?
    private double getJump(Player p) {
        double d;
        if (p.hasPotionEffect(PotionEffectType.JUMP)) {
            int level = CheckUtils.getPotionEffectLevel(p, PotionEffectType.JUMP);
            switch (level){
                case 1:
                    d = 1.9;
                    break;
                case 2:
                    d = 2.7;
                    break;
                case 3:
                    d = 3.36;
                    break;
                case 4:
                    d = 4.22;
                    break;
                case 5:
                    d = 5.16;
                    break;
                default:
                    d = (level) + 1;
                    break;
            }
        }else return 0;
        return d+1.35;
    }

    public String getSetback() {
        return setback;
    }
}
