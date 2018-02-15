package me.david.TimberNoCheat.debug.debuggers;

import me.david.TimberNoCheat.debug.Debuggers;
import me.david.TimberNoCheat.debug.ExternalDebuger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveVelocity extends ExternalDebuger {

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        send(Debuggers.MOVE_VELOCITY, "" + event.getPlayer().getVelocity().getY());
    }
}
