package me.david.timbernocheat.checkes.movement.fly.checks;

import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.checkes.movement.fly.AbstractFlyCheck;
import me.david.timbernocheat.checkes.movement.fly.Fly;
import me.david.timbernocheat.checkes.movement.fly.FlyData;
import me.david.timbernocheat.checkes.movement.fly.FlyMoveData;
import me.david.timbernocheat.util.CheckUtils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class DistanceUpgoing extends AbstractFlyCheck {

    public DistanceUpgoing(Fly flyCheck) {
        super("Distance Upgoing", flyCheck);
    }

    @Override
    public void onMove(FlyData flyData, Player player, PlayerData playerData, FlyMoveData move) {
        if (move.isFromGround() && !move.isToGround()) {
            flyData.setJumpPotionThisJump(CheckUtils.getPotionEffectLevel(player, PotionEffectType.JUMP));
            flyData.setCurrentUp(move.getyDiff());
            double maxJump = 0.5;
            maxJump += flyData.getJumpPotionThisJump() * 0.11;
            flyData.setMaxUp(flyData.getMaxUp() + maxJump);
        } else if (move.isUpGoing()) {
            flyData.setCurrentUp(flyData.getCurrentUp() + move.getyDiff());
            if (flyData.getCurrentUp() > flyData.getMaxUp() && updateVio(this, player, (flyData.getCurrentUp() - flyData.getMaxUp()) * 2.8)) {
                setBack(player);
            }
        }
        if (move.isToGround()) {
            flyData.setCurrentUp(-1);
            flyData.setJumpPotionThisJump(CheckUtils.getPotionEffectLevel(player, PotionEffectType.JUMP));
            flyData.setMaxUp(0);
        }
    }

    @Override
    public void velocity(FlyData flyData, Player player, Vector velocity) {
        flyData.setMaxUp(flyData.getMaxUp() + velocity.getY());
    }
}
