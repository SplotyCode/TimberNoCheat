package me.david.timbernocheat.checkes.movement.speed;

import me.david.api.utils.BlockUtil;
import me.david.api.utils.player.PlayerUtil;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.checktools.FalsePositive;
import me.david.timbernocheat.checktools.General;
import me.david.timbernocheat.checktools.MaterialHelper;
import me.david.timbernocheat.debug.Debuggers;
import me.david.timbernocheat.util.CheckUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class SpeedXZLimit extends AbstractSpeed {

    private double groundBase;
    private double hopingBase;
    private double iceBase;
    private double iceBaseTop;

    private double groundBaseSprint;
    private double hopingBaseSprint;
    private double iceBaseSprint;
    private double iceBaseTopSprint;

    private double topModi;
    private double slabModi;
    private double stairModi;
    private double speedModiGround;
    private double speedModiHoping;

    public SpeedXZLimit(Speed speed) {
        super(speed, "XZLimit");
        groundBase = getDouble("groundBase");
        hopingBase = getDouble("hopingBase");
        iceBase = getDouble("iceBase");
        iceBaseTop = getDouble("iceBaseTop");

        groundBaseSprint = getDouble("groundBaseSprint");
        hopingBaseSprint = getDouble("hopingBaseSprint");
        iceBaseSprint = getDouble("iceBaseSprint");
        iceBaseTopSprint = getDouble("iceBaseTopSprint");

        topModi = getDouble("topModi");
        slabModi = getDouble("slabModi");
        stairModi = getDouble("stairModi");
        speedModiGround = getDouble("speedModiGround");
        speedModiHoping = getDouble("speedModiHoping");
    }

    @Override
    public void move(SpeedMoveData move, Player player, PlayerData data, FalsePositive.FalsePositiveChecks falsePositive, General.GeneralValues general) {
        boolean sprint = general.ticksSinceSprint() < 16;
        double limitXZ = ground(move) ? sprint?groundBaseSprint:groundBase : sprint?hopingBaseSprint:hopingBase;

        boolean top = PlayerUtil.getEyeLocation(player).clone().add(0, 1, 0).getBlock().getType() != Material.AIR &&
                      !BlockUtil.canStandWithin(PlayerUtil.getEyeLocation(player).clone().add(0, 1, 0).getBlock());
        if (top) limitXZ += topModi;

        if (ice(move))
            if (top) limitXZ = sprint?iceBaseTopSprint:iceBaseTop;
            else limitXZ = sprint?iceBaseSprint:iceBase;

        if (stair(move)) limitXZ += stairModi;
        if (slabs(move)) limitXZ += slabModi;


        limitXZ += (ground(move)?speedModiGround:speedModiHoping) * CheckUtils.getPotionEffectLevel(player, PotionEffectType.SPEED);

        TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.SPEED, " max=" + limitXZ + " player=" + move.getXzDiff());
        if(move.getXzDiff() > limitXZ)
            if (updateVio(this, player, move.getXzDiff()-limitXZ))
                move.getMoveEvent().setCancelled(true);
    }

    private boolean ground(SpeedMoveData data){
        return CheckUtils.onGround(data.getMoveEvent().getTo()) && CheckUtils.onGround(data.getMoveEvent().getFrom());
    }

    private boolean slabs(SpeedMoveData data){
        return slabs(data.getMoveEvent().getTo()) || slabs(data.getMoveEvent().getFrom());
    }

    boolean slabs(Location location){
        for(Material stair : MaterialHelper.SLAPS)
            if(CheckUtils.doesColidateWithMaterial(stair, location))
                return true;
        return false;
    }

    private boolean stair(SpeedMoveData data){
        return stair(data.getMoveEvent().getTo()) || stair(data.getMoveEvent().getFrom());
    }

    boolean stair(Location location){
        for(Material stair : MaterialHelper.STAIRS)
            if(CheckUtils.doesColidateWithMaterial(stair, location))
                return true;
        return false;
    }

    private boolean ice(SpeedMoveData data){
        return CheckUtils.doesColidateWithMaterial(Material.ICE, data.getMoveEvent().getFrom()) ||
               CheckUtils.doesColidateWithMaterial(Material.ICE, data.getMoveEvent().getTo());
    }

}
