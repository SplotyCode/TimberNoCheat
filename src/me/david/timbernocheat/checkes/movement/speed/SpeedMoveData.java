package me.david.timbernocheat.checkes.movement.speed;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.NumberConversions;

public class SpeedMoveData {

    private final PlayerMoveEvent moveEvent;
    private final double xDiff, zDiff, xDiffRaw, zDiffRaw, xzDiff, xzDiffRaw;

    public SpeedMoveData(PlayerMoveEvent moveEvent) {
        this.moveEvent = moveEvent;
        Location from = moveEvent.getFrom(), to = moveEvent.getTo();
        xDiffRaw = to.getX() - from.getX();
        zDiffRaw = to.getZ() - from.getZ();

        xDiff = Math.abs(xDiffRaw);
        zDiff = Math.abs(zDiffRaw);

        xzDiffRaw = Math.sqrt(NumberConversions.square(to.getX() - from.getX()) + NumberConversions.square(to.getZ() - from.getZ()));
        xzDiff = Math.abs(xzDiffRaw);
    }

    public PlayerMoveEvent getMoveEvent() {
        return moveEvent;
    }

    public double getxDiff() {
        return xDiff;
    }

    public double getzDiff() {
        return zDiff;
    }

    public double getxDiffRaw() {
        return xDiffRaw;
    }

    public double getzDiffRaw() {
        return zDiffRaw;
    }

    public double getXzDiff() {
        return xzDiff;
    }

    public double getXZDiffRaw() {
        return xzDiffRaw;
    }
}
