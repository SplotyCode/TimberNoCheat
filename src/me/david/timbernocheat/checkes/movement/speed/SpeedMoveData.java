package me.david.timbernocheat.checkes.movement.speed;

import me.david.timbernocheat.util.CheckUtils;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.NumberConversions;

public class SpeedMoveData {

    private final PlayerMoveEvent moveEvent;
    private final double xDiff, zDiff, xDiffRaw, zDiffRaw, xzDiff, xzDiffRaw;
    private final double yDiff, yDiffRaw;
    private final boolean fromGround, toGround;

    public SpeedMoveData(PlayerMoveEvent moveEvent) {
        this.moveEvent = moveEvent;
        Location from = moveEvent.getFrom(), to = moveEvent.getTo();
        xDiffRaw = to.getX() - from.getX();
        zDiffRaw = to.getZ() - from.getZ();
        yDiffRaw = to.getY() - from.getY();

        xDiff = Math.abs(xDiffRaw);
        zDiff = Math.abs(zDiffRaw);
        yDiff = Math.abs(yDiffRaw);

        fromGround = CheckUtils.onGround(from);
        toGround = CheckUtils.onGround(to);

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

    public boolean isFromGround() {
        return fromGround;
    }

    public boolean isToGround() {
        return toGround;
    }

    public Location getTo() {
        return moveEvent.getTo();
    }

    public Location getFrom() {
        return moveEvent.getFrom();
    }

    public boolean isUpward() {
        return yDiffRaw > 0;
    }

    public boolean isDownward() {
        return yDiffRaw < 0;
    }
}
