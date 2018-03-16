package me.david.timbernocheat.checkes.movement.fly;

import org.bukkit.event.entity.EntityDamageEvent;

public class FlyData {

    //General
    private FlyMoveData lastMove;
    private double slimePeek = 0;
    private double groundDistance = 0;
    private boolean isFalling = false;
    private boolean waitingForVelocity = false;
    private EntityDamageEvent.DamageCause lastHurtCause;


    public FlyMoveData getLastMove() {
        return lastMove;
    }

    public void setLastMove(FlyMoveData lastMove) {
        this.lastMove = lastMove;
    }

    public double getSlimePeek() {
        return slimePeek;
    }

    public void setGroundDistance(double groundDistance) {
        this.groundDistance = groundDistance;
    }

    public double getGroundDistance() {
        return groundDistance;
    }

    public void setSlimePeek(double slimePeek) {
        this.slimePeek = slimePeek;
    }

    public boolean isFalling() {
        return isFalling;
    }

    public void setFalling(boolean falling) {
        isFalling = falling;
    }
}
