package me.david.timbernocheat.checkes.movement.fly.checks;

import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.checkes.movement.fly.*;
import org.bukkit.entity.Player;

public class WrongDirection extends FlyCheck {

    public WrongDirection(Fly flyCheck) {
        super("WrongDirection", flyCheck);
    }

    @Override
    public void onMove(FlyData flyData, Player player, PlayerData playerData, FlyMoveData move) {
        if(flyData.getLastData().isFalling() && !flyData.isFalling() && !move.isToGround()){
            if(!excused(flyData)) {
                if(updateVio(this, player, 1)) setBack(player);
            } else {
                flyData.setExcused(false);
                flyData.setLastExcused(0);
            }
        }
    }

    private boolean excused(FlyData data){
        if(!data.isExcused()) return false;
        if(System.currentTimeMillis()-data.getLastExcused() > 200){
            data.setExcused(false);
            data.setLastExcused(0);
            return false;
        }
        return true;
    }

    private void excuse(FlyData data){
        data.setExcused(true);
        data.setLastExcused(System.currentTimeMillis());
    }

    private void excuse(FlyData data, long time){
        data.setExcused(true);
        data.setLastExcused(System.currentTimeMillis()+time);
    }

    @Override
    public void reset(ResetReason reason, FlyData flyData, Player player) {
        excuse(flyData);
    }

    @Override
    public void onSlime(FlyData data, double fallDistance) {
        excuse(data, (long) (fallDistance/2)*1000);
    }

    @Override
    public void attack(FlyData flyData, Player player, int knockback) {
        excuse(flyData);
    }

    @Override
    public void bow(FlyData flyData, Player player, int strength) {
        excuse(flyData);
    }

    @Override
    public void rod(FlyData flyData, Player player) {
        excuse(flyData);
    }

    @Override
    public void explostion(FlyData flyData, Player player, float strength) {
        excuse(flyData, (long) (strength+1000*15));
    }
}
