package me.david.timbernocheat.checkes.movement.fly;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.*;
import me.david.timbernocheat.debug.Debuggers;
import me.david.timbernocheat.util.MoveingUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

@Disable(reason = "This Check is handled by the Main Flying Check")
public abstract class AbstractFlyCheck extends Check {

    private Fly flyCheck;

    public AbstractFlyCheck(String name, Fly flyCheck) {
        super(name, Category.MOVEMENT, true, flyCheck);
        this.flyCheck = flyCheck;
    }

    public void onSlime(FlyData data, double fallDistance) {}
    public void onFall(double calculatedDistance) {}

    //Teleports, Worldswitch
    public void reset(ResetReason reason, FlyData flyData, Player player) {}
    public void onSkipMove(SkipReason skipReason) {}
    public abstract void onMove(FlyData flyData, Player player, PlayerData playerData, FlyMoveData move);
    public void attack(FlyData flyData, Player player, int knockback) {}
    public void bow(FlyData flyData, Player player, int strength) {}
    public void rod(FlyData flyData, Player player) {}
    public void explostion(FlyData flyData, Player player, float strength) {}
    public void velocity(FlyData flyData, Player player, Vector velocity) {}
    public void damage(FlyData flyData, Player player, EntityDamageEvent.DamageCause couse, double mcDamage, double pluginDamage) {}

    public void debug(String msg){
        TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.FLYDEBUG, msg);
    }

    public void debug(String msg, String toogle){
        TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.FLYDEBUG, msg, toogle);
    }



    public void setBack(Player player){
        PlayerData pd = CheckManager.getInstance().getPlayerdata(player);
        TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.FLY_SETBACK, flyCheck.getSetback() + " " + getClass().getSimpleName());
        switch (flyCheck.getSetback()) {
            case "cancel":
                player.teleport(pd.getGenerals().getLastOnGround(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                break;
            case "down":
                player.teleport(player.getLocation().clone().subtract(0, Math.min(3, MoveingUtils.groundDistance(player)), 0));
                break;
        }

    }


}
