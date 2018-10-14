package me.david.timbernocheat.debug.debuggers;

import me.david.timbernocheat.debug.Debuggers;
import me.david.timbernocheat.debug.ExternalDebugger;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import java.text.DecimalFormat;

public class SpigotMoveEvent extends ExternalDebugger {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        DecimalFormat format = new DecimalFormat("#.###");
        Location to = event.getTo(), from = event.getFrom();
        String toX = format.format(to.getX()),
               toY = format.format(to.getY()),
               toZ = format.format(to.getZ()),
               toYaw = format.format(to.getYaw()),
               toPitch = format.format(to.getPitch()),
               fromX = format.format(from.getX()),
               fromY = format.format(from.getY()),
               fromZ = format.format(from.getZ()),
               fromYaw = format.format(from.getYaw()),
               fromPitch = format.format(from.getPitch());
                send(Debuggers.MOVE_EVENTS, event.getPlayer().getName() + " " + toX + "|" + toY + "|" + toZ + "/" + toPitch + "|" + toYaw + " " + fromX + "|" + fromY + "|" + fromZ + "/" + fromPitch + "|" + fromYaw);
    }

}
