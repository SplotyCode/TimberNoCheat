package me.david.timbernocheat.checkes.movement.fly;

import me.david.timbernocheat.util.CheckUtils;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class FlyMoveData {

    private final PlayerMoveEvent event;
    private final double yDiff, rawYDiff, distance, yDiffBlock;
    private final boolean onGround, fromGround, toGround;
    private boolean upGoing, downGoing, straight;
    private final double yVelocity, velocityDistance;
    private final Vector velocity;

    public FlyMoveData(PlayerMoveEvent event) {
        this.event = event;
        distance = event.getFrom().distance(event.getTo());
        rawYDiff = event.getTo().getY()-event.getFrom().getY();
        yDiffBlock = event.getTo().getY()-event.getFrom().getBlockY();
        yDiff = Math.abs(rawYDiff);
        velocity = event.getPlayer().getVelocity();
        yVelocity = velocity.getY();
        velocityDistance = velocity.length();
        if(yDiff == 0) straight = true;
        else if(yDiff < 0) downGoing = true;
        else upGoing = true;

        onGround = CheckUtils.onGround(event.getPlayer());
        fromGround = CheckUtils.onGround(event.getFrom());
        toGround = CheckUtils.onGround(event.getTo());
    }

    public Location getTo(){
        return event.getTo();
    }

    public Location getFrom(){
        return event.getFrom();
    }

    public PlayerMoveEvent getEvent() {
        return event;
    }

    public double getyDiff() {
        return yDiff;
    }

    public double getRawYDiff() {
        return rawYDiff;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public boolean isFromGround() {
        return fromGround;
    }

    public boolean isToGround() {
        return toGround;
    }

    public double getDistance() {
        return distance;
    }

    public double getyDiffBlock() {
        return yDiffBlock;
    }

    public double getyVelocity() {
        return yVelocity;
    }

    public double getVelocityDistance() {
        return velocityDistance;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public boolean isUpGoing() {
        return upGoing;
    }

    public void setUpGoing(boolean upGoing) {
        this.upGoing = upGoing;
    }

    public boolean isDownGoing() {
        return downGoing;
    }

    public void setDownGoing(boolean downGoing) {
        this.downGoing = downGoing;
    }

    public boolean isStraight() {
        return straight;
    }

    public void setStraight(boolean straight) {
        this.straight = straight;
    }
}
