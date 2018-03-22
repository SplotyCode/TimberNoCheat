package me.david.timbernocheat.checkes.movement.fly.checks;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.checkes.movement.fly.Fly;
import me.david.timbernocheat.checkes.movement.fly.FlyCheck;
import me.david.timbernocheat.checkes.movement.fly.FlyData;
import me.david.timbernocheat.checkes.movement.fly.FlyMoveData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerKickEvent;

public class Vanilla extends FlyCheck {

    public Vanilla(Fly flyCheck) {
        super("Vanilla", flyCheck);
    }

    @EventHandler(ignoreCancelled = true)
    public void kick(PlayerKickEvent e) {
        if(TimberNoCheat.getCheckManager().isvalid_create(e.getPlayer()) && e.getReason().startsWith("Flying is not")) {
            if(updateVio(this, e.getPlayer(), 1))
                setBack(e.getPlayer());
        }
    }

    @Override public void onMove(FlyData flyData, Player player, PlayerData playerData, FlyMoveData move) {}
}
