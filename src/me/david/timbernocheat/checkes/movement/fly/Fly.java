package me.david.timbernocheat.checkes.movement.fly;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.checkes.movement.fly.checks.AirFall;
import me.david.timbernocheat.checkes.movement.fly.checks.Vanilla;
import me.david.timbernocheat.checkes.movement.fly.checks.WrongDirection;
import me.david.timbernocheat.checktools.FalsePositive;
import me.david.timbernocheat.checktools.General;
import me.david.timbernocheat.checktools.MaterialHelper;
import me.david.timbernocheat.debug.Debuggers;
import me.david.timbernocheat.util.CheckUtils;
import me.david.timbernocheat.util.MovingUtils;
import me.david.timbernocheat.util.SpeedUtil;
import me.david.api.utils.BlockUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
        registerChilds(new Vanilla(this), new AirFall(this), new WrongDirection(this));
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void playerMove(PlayerMoveEvent event){
        final Player player = event.getPlayer();
        if (!TimberNoCheat.getCheckManager().isvalid_create(player)) return;
        PlayerData pd = TimberNoCheat.getCheckManager().getPlayerdata(player);
        General.GeneralValues general = pd.getGenerals();
        FalsePositive.FalsePositiveChecks fp = pd.getFalsepositives();
        TimberNoCheat.getInstance().getMoveprofiler().start("Fly ");
        FlyMoveData move = new FlyMoveData(event);
        FlyData data = pd.getFlyData();

        if(player.getVehicle() != null){
            forChilds((check) -> check.onSkipMove(SkipReason.VEHICLE));
            return;
        }
        if(move.isToGround()) data.setGroundDistance(data.getGroundDistance()+move.getRawYDiff());

        if(move.isToGround()) data.setFalling(false);
        else if(move.getTo().getY() < move.getFrom().getY() && !data.isFalling()) {
            data.setFalling(true);
            forChilds((check) -> check.onFall(MovingUtils.groundDistance(player)));
        }

        //Slime Call
        if(data.getLastMove() != null) {
            if (!data.getLastMove().isToGround() && data.getLastMove().isToGround()) {
                if (CheckUtils.doesColidateWithMaterial(Material.SLIME_BLOCK, player))
                    getSubChecks().forEach((check) -> check.onSlime(data.getSlimePeek()));
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

        data.setLastMove(move);
        TimberNoCheat.getInstance().getMoveprofiler().end();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onTeleport(PlayerTeleportEvent event){
        final Player player = event.getPlayer();
        if (!TimberNoCheat.getCheckManager().isvalid_create(player)) return;
        PlayerData pd = TimberNoCheat.getCheckManager().getPlayerdata(player);
        forChilds((check -> check.reset(ResetReason.TELEPORT, pd.getFlyData(), player)));
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onWorldSwitch(PlayerChangedWorldEvent event){
        final Player player = event.getPlayer();
        if (!TimberNoCheat.getCheckManager().isvalid_create(player)) return;
        final PlayerData pd = TimberNoCheat.getCheckManager().getPlayerdata(player);
        forChilds((check -> check.reset(ResetReason.WORLDSWITCH, pd.getFlyData(), player)));
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onDamage(final EntityDamageEvent event){
        if(event.getEntityType() != EntityType.PLAYER || !TimberNoCheat.getCheckManager().isvalid_create((Player) event.getEntity()))return;
        final Player player = (Player) event.getEntity();
        final FlyData data = TimberNoCheat.getCheckManager().getPlayerdata(player).getFlyData();
        switch (event.getCause()){
            case ENTITY_EXPLOSION:case BLOCK_EXPLOSION:case PROJECTILE:case WITHER:
                data.setWaitingForVelocity(true);
            break;
        }
        switch (event.getCause()){
            case BLOCK_EXPLOSION:
                forChilds((check) -> check.explostion(data, player, 4*2));
            break;
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onEntityDamage(final EntityDamageByEntityEvent event){
        if(event.getEntityType() != EntityType.PLAYER || !TimberNoCheat.getCheckManager().isvalid_create((Player) event.getEntity()))return;
        final Player player = (Player) event.getEntity();
        final Entity entity = event.getDamager();
        final FlyData data = TimberNoCheat.getCheckManager().getPlayerdata(player).getFlyData();
        switch (event.getCause()){
            case ENTITY_EXPLOSION:
                forChilds((check) -> check.explostion(data, player, (((Creeper) entity).isPowered()?2*2:1*2)));
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
        if(!TimberNoCheat.getCheckManager().isvalid_create(event.getPlayer()))return;
        final Player player = event.getPlayer();
        final FlyData data = TimberNoCheat.getCheckManager().getPlayerdata(player).getFlyData();
        if (event.getCaught() instanceof Player) {
            final Player caught = (Player) event.getCaught();
            if (player.getItemInHand().getType() == Material.FISHING_ROD) {
                forChilds((check) -> check.rod(data, caught));
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onVelocity(PlayerVelocityEvent event){
        if(!TimberNoCheat.getCheckManager().isvalid_create(event.getPlayer()))return;
        final Player player = event.getPlayer();
        final FlyData data = TimberNoCheat.getCheckManager().getPlayerdata(player).getFlyData();
        if(data.isWaitingForVelocity()){
            data.setWaitingForVelocity(false);
            data.setSpecialVelocity(event.getVelocity());
            data.setSpecialVelocityCouse(data.getLastHurtCause());
            data.setLastHurtCause(null);
        }
    }

    private void forChilds(Consumer<? super FlyCheck> consumer){
        getSubChecks().forEach(consumer);
    }

    private ArrayList<FlyCheck> getSubChecks(){
        ArrayList<FlyCheck> list = new ArrayList<>();
        for(Check check : getChilds()) list.add((FlyCheck) check);
        return list;
    }

    private double getJump(Player p) {
        double d;
        if (p.hasPotionEffect(PotionEffectType.JUMP)) {
            int level = SpeedUtil.getPotionEffectLevel(p, PotionEffectType.JUMP);
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
