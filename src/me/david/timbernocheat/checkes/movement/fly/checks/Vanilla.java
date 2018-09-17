package me.david.timbernocheat.checkes.movement.fly.checks;

import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.Disable;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.checkes.movement.fly.Fly;
import me.david.timbernocheat.checkes.movement.fly.AbstractFlyCheck;
import me.david.timbernocheat.checkes.movement.fly.FlyData;
import me.david.timbernocheat.checkes.movement.fly.FlyMoveData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerKickEvent;

@Disable(reason = "This Check is handled by the Main Flying Check")
public class Vanilla extends AbstractFlyCheck {

    public Vanilla(Fly flyCheck) {
        super("Vanilla", flyCheck);
    }

    @EventHandler(ignoreCancelled = true)
    public void kick(PlayerKickEvent event) {
        if(CheckManager.getInstance().isvalid_create(event.getPlayer()) && event.getReason().startsWith("Flying is not")) {
            if(updateVio(this, event.getPlayer(), 1))
                setBack(event.getPlayer());
        }
    }

    @Override public void onMove(FlyData flyData, Player player, PlayerData playerData, FlyMoveData move) {}
}
