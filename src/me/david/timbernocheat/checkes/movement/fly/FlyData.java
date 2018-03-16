package me.david.timbernocheat.checkes.movement.fly;

import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public class FlyData {

    //General
    private FlyMoveData lastMove;
    private double slimePeek = 0;
    private double groundDistance = 0;
    private boolean isFalling = false;
    private boolean waitingForVelocity = false;
    private EntityDamageEvent.DamageCause lastHurtCause;
    private Vector specialVelocity = new Vector();
    private EntityDamageEvent.DamageCause specialVelocityCouse;
    private boolean wasFalling = false;

    //WrongDirection
    private boolean excused = false;
    private long lastExcused = 0L;

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

    public boolean isWaitingForVelocity() {
        return waitingForVelocity;
    }

    public void setWaitingForVelocity(boolean waitingForVelocity) {
        this.waitingForVelocity = waitingForVelocity;
    }

    public EntityDamageEvent.DamageCause getLastHurtCause() {
        return lastHurtCause;
    }

    public void setLastHurtCause(EntityDamageEvent.DamageCause lastHurtCause) {
        this.lastHurtCause = lastHurtCause;
    }

    public Vector getSpecialVelocity() {
        return specialVelocity;
    }

    public void setSpecialVelocity(Vector specialVelocity) {
        this.specialVelocity = specialVelocity;
    }

    public boolean isWasFalling() {
        return wasFalling;
    }

    public void setWasFalling(boolean wasFalling) {
        this.wasFalling = wasFalling;
    }

    public void setSpecialVelocityCouse(EntityDamageEvent.DamageCause specialVelocityCouse) {
        this.specialVelocityCouse = specialVelocityCouse;
    }

    public EntityDamageEvent.DamageCause getSpecialVelocityCouse() {
        return specialVelocityCouse;
    }

    public long getLastExcused() {
        return lastExcused;
    }

    public void setExcused(boolean excused) {
        this.excused = excused;
    }

    public void setLastExcused(long lastExcused) {
        this.lastExcused = lastExcused;
    }

    public boolean isExcused() {
        return excused;
    }
}
