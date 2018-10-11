package me.david.timbernocheat.checkes.movement.speed;

import me.david.timbernocheat.checkbase.Disable;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.checktools.FalsePositive;
import me.david.timbernocheat.checktools.General;
import me.david.timbernocheat.util.CheckUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@Disable(reason = "Startup is handled by Main Speed Check")
public class LowHop extends AbstractSpeed {

    public LowHop(Speed speed) {
        super(speed, "LowHop");
    }

    @Override
    public void move(SpeedMoveData move, Player player, PlayerData data, FalsePositive.FalsePositiveChecks falsePositive, General.GeneralValues general) {
        if (!move.isToGround()) {
            if (!move.isDownward()) {
                double jumpDiff = move.getTo().getY() - general.getLastOnGround().getY();
                data.setLowHopDiff(Math.max(jumpDiff, data.getLowHopDiff()));
                if (player.isInsideVehicle() || CheckUtils.collidate(player) || CheckUtils.collidateLiquid(player)) {
                    data.setLowHopInvalid(true);
                }
            } else {
                double jumpDiff = data.getLowHopDiff();

                if (jumpDiff != 0 && !data.isLowHopInvalid()) {
                    if (general.ticksSinceSneak() > general.getTicksInAir() && jumpDiff <= .5) {
                        updateVio(this, player, Math.abs(.6 - jumpDiff) * 6);
                    }
                    data.setLowHopInvalid(true);
                }
            }
        } else {
            data.setLowHopDiff(0);
            data.setLowHopInvalid(false);
        }
    }

}
