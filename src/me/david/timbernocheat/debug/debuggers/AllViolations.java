package me.david.timbernocheat.debug.debuggers;

import me.david.timbernocheat.api.ViolationUpdateEvent;
import me.david.timbernocheat.debug.Debuggers;
import me.david.timbernocheat.debug.ExternalDebugger;
import org.bukkit.event.EventHandler;

public class AllViolations extends ExternalDebugger {

    @EventHandler
    public void onViolation(ViolationUpdateEvent event) {
        double diff = event.getNewViolation() - event.getOldViolation();
        String diffColor = diff < 0 ? "§c-" + diff : "§a+" + diff;
        send(Debuggers.ALL_VIOLATIONS, event.getPlayer().getName() + " " + event.getCheck().displayName() + " " + event.getNewViolation() + "§7[" + diffColor + "§7]");
    }

}
