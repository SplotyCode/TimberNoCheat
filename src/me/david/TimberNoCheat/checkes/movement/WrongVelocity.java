package me.david.TimberNoCheat.checkes.movement;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.debug.Debuggers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class WrongVelocity extends Check {

    public WrongVelocity() {
        super("WrongVelocity", Category.MOVEMENT);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        TimberNoCheat.instance.getDebuger().sendDebug(Debuggers.MOVE_VELOCITY, "" + event.getPlayer().getVelocity().getY());
    }
}
