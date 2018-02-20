package me.david.timbernocheat.debug.debuggers;

import me.david.timbernocheat.debug.Debuggers;
import me.david.timbernocheat.debug.ExternalDebugger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveVelocity extends ExternalDebugger {

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        send(Debuggers.MOVE_VELOCITY, "" + event.getPlayer().getVelocity().getY());
    }
}
