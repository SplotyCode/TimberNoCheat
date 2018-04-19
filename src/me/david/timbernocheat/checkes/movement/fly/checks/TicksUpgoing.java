package me.david.timbernocheat.checkes.movement.fly.checks;

import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.checkes.movement.fly.Fly;
import me.david.timbernocheat.checkes.movement.fly.AbstractFlyCheck;
import me.david.timbernocheat.checkes.movement.fly.FlyData;
import me.david.timbernocheat.checkes.movement.fly.FlyMoveData;
import me.david.timbernocheat.runnable.Tps;
import me.david.timbernocheat.util.CheckUtils;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class TicksUpgoing extends AbstractFlyCheck {

    public TicksUpgoing(Fly flyCheck) {
        super("TicksUpgoing", flyCheck);
    }

    @Override
    public void onMove(FlyData flyData, Player player, PlayerData playerData, FlyMoveData move) {
        if(move.isUpGoing()){
            boolean newTick = false;
            if(flyData.getLastCountTick() != Tps.tickCount) {
                flyData.setTicksUpgoing(flyData.getTicksUpgoing()+1);
                flyData.setLastCountTick(Tps.tickCount);
                debug("Change Ticksup for " + player.getName() + " to " + flyData.getTicksUpgoing(), "ticksup");
                newTick = true;
            }
            int max = Math.max(
                NORMAL_JUMPTIME,
                Math.max(
                    getJumpPotionTime(flyData),
                    getSlimeBlocksTime(flyData)
                )
            );
            debug("Max upgoingticks is " + max + " for " + player.getName(), "max");
            if(flyData.getTicksUpgoing() > max)
                if(updateVio(this, player, max-flyData.getTicksUpgoing()*2.8))
                    setBack(player);

            if(newTick) flyData.setJumpPotionLastTick(CheckUtils.getPotionEffectLevel(player, PotionEffectType.JUMP));
        }else {
            if(flyData.getTicksUpgoing() != 0 ) debug("ticksup reset for " + player.getName(), "ticksup");
            flyData.setTicksUpgoing(0);
            flyData.setLastSlime(0);
            flyData.setMabySlimeJump(false);
        }
    }

    @Override
    public void onSlime(FlyData data, double fallDistance) {
        data.setMabySlimeJump(true);
        data.setLastSlime(data.getSlimePeek());
    }

    @Override
    public void velocity(FlyData flyData, Player player, Vector velocity) {
        flyData.getVelocity().add(velocity.clone());
    }

    private int NORMAL_JUMPTIME = -1;

    private int getJumpPotionTime(FlyData flyData){return -1;}
    private int getSlimeBlocksTime(FlyData flyData){return -1;}
    private int getVelocityTime(FlyData flyData){return -1;}


}
