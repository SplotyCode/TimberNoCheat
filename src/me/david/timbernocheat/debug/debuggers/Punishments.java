package me.david.timbernocheat.debug.debuggers;

import me.david.timbernocheat.api.PunishmentEvent;
import me.david.timbernocheat.debug.Debuggers;
import me.david.timbernocheat.debug.ExternalDebugger;
import org.bukkit.event.EventHandler;

public class Punishments extends ExternalDebugger {

    @EventHandler
    public void onPunish(PunishmentEvent event) {
        send(Debuggers.PUNISHMENTS, event.getPlayer().getName() + " " + event.getCheck().displayName() + " " + event.getType().name());
    }

}
