package me.david.timbernocheat.checkes.movement.fly.checks;

import me.david.timbernocheat.checkbase.Disable;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.checkes.movement.fly.AbstractFlyCheck;
import me.david.timbernocheat.checkes.movement.fly.Fly;
import me.david.timbernocheat.checkes.movement.fly.FlyData;
import me.david.timbernocheat.checkes.movement.fly.FlyMoveData;
import me.david.timbernocheat.runnable.Tps;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

@Disable(reason = "This Check is handled by the Main Flying Check")
public class WrongDamage extends AbstractFlyCheck {

    public WrongDamage(Fly check) {
        super("WrongDamage", check);
    }

    @Override
    public void onMove(FlyData flyData, Player player, PlayerData playerData, FlyMoveData move) {
        if (!move.isOnlyRotation()) {
            if (!flyData.getLastMove().isToGround() && move.isToGround()) {
                if (flyData.isNeedFall()) {
                    if (!flyData.isStillOkay())
                        if (updateVio(this, player, 2, "Fall twice, did not handle first"))
                            player.damage(flyData.getNextDamageDistance() - 3 + 1.2);
                    reset(flyData);
                }
                flyData.setNeedFall(true);
                if (flyData.getHighestDis() >= 3.6) {
                    flyData.setNextDamageDistance(flyData.getHighestDis());
                } else flyData.setStillOkay(true);
                flyData.setHitGroundTick(Tps.tickCount);
                return;
            } else if (!move.isToGround() && flyData.getHighestDis() < move.getToGroundDistance()) {
                flyData.setHighestDis(move.getToGroundDistance());
            }
        }
        flyData.setMoveSince(true);
        flyData.setMovesSince(flyData.getMovesSince()+1);

        if (flyData.isNeedFall() && !flyData.isStillOkay()) {
            int tickDiff = Tps.tickCount - flyData.getHitGroundTick();
            if (flyData.getMovesSince() > 2 && tickDiff > 5) {
                if (updateVio(this, player, (tickDiff - 4) * flyData.getMovesSince() - 1, "Forgot to take fall Damage [Moving]"))
                    player.damage(flyData.getNextDamageDistance() - 3 + 1.2);
            } else if (tickDiff > 25) {
                if (updateVio(this, player, (tickDiff - 25) * (flyData.getNextDamageDistance() * 1.4), "Forgot to take fall Damage [Not reacting]"))
                    player.damage(flyData.getNextDamageDistance() - 3 + 1.2);
            }
        }
    }

    @Override
    public void damage(FlyData flyData, Player player, EntityDamageEvent.DamageCause couse, double mcDamage, double endDamage) {
        if (couse == EntityDamageEvent.DamageCause.FALL) {
            if (flyData.isNeedFall()) {
                if (!flyData.isStillOkay()) {
                    double damage = flyData.getNextDamageDistance() - 3;
                    double diff = Math.abs(mcDamage - damage);
                    if (diff > 1)
                        if (updateVio(this, player, diff * 2.4, "No Low damage"))
                            player.damage(diff + 1.2);
                }
            } else
                updateVio(this, player, 1, "Why is he Falling?");
            reset(flyData);
        }
    }
    private void reset(final FlyData flyData) {
        flyData.setNextDamageDistance(0);
        flyData.setNeedFall(false);
        flyData.setStillOkay(false);
        flyData.setHitGroundTick(0);
        flyData.setMoveSince(false);
        flyData.setMovesSince(0);
        flyData.setHighestDis(0);
    }

}
