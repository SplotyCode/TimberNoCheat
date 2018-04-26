package me.david.timbernocheat.checkes.movement.fly;

import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class FlyData implements Cloneable {

    //General
    private FlyMoveData lastMove;
    private FlyData lastData;
    private double slimePeek = 0;
    private double groundDistance = 0;
    private boolean isFalling = false;
    private boolean waitingForVelocity = false;
    private EntityDamageEvent.DamageCause lastHurtCause;
    private Vector specialVelocity = new Vector();
    private EntityDamageEvent.DamageCause specialVelocityCouse;

    //WrongDirection
    private boolean excused = false;
    private long lastExcused = 0L;

    //TicksUpgoing
    private int ticksUpgoing = 0;
    private int lastCountTick = -1;

    //TicksUpgoing
    private boolean mabySlimeJump = false;
    private double lastSlime = 0;
    private int jumpPotion = 0;
    private int jumpPotionLastTick = 0;
    private ArrayList<Vector> velocity = new ArrayList<>();

    /* Wrong Damage */
    private double highestDis = 0, nextDamageDistance = 0;
    private boolean needFall = false, stillOkay;
    private int hitGroundTick = 0;
    private boolean moveSince = false;
    private int movesSince = 0;


    @Override
    public FlyData clone() {
        FlyData flyData = new FlyData();
        flyData.setLastMove(lastMove);
        flyData.setLastData(lastData);
        flyData.setLastSlime(slimePeek);
        flyData.setGroundDistance(groundDistance);
        flyData.setFalling(isFalling);
        flyData.setWaitingForVelocity(waitingForVelocity);
        flyData.setLastHurtCause(lastHurtCause);
        flyData.setSpecialVelocity(specialVelocity);
        flyData.setSpecialVelocityCouse(specialVelocityCouse);

        flyData.setExcused(excused);
        flyData.setLastExcused(lastExcused);

        flyData.setTicksUpgoing(ticksUpgoing);
        flyData.setLastCountTick(lastCountTick);

        flyData.setMabySlimeJump(mabySlimeJump);
        flyData.setLastSlime(lastSlime);
        flyData.setJumpPotion(jumpPotion);
        flyData.setJumpPotionLastTick(jumpPotionLastTick);
        flyData.setVelocity(velocity);
        return flyData;
    }

    public FlyMoveData getLastMove() {
        return lastMove;
    }

    public void setLastMove(FlyMoveData lastMove) {
        this.lastMove = lastMove;
    }

    public double getSlimePeek() {
        return slimePeek;
    }

    public boolean isStillOkay() {
        return stillOkay;
    }

    public void setStillOkay(boolean stillOkay) {
        this.stillOkay = stillOkay;
    }

    public double getNextDamageDistance() {
        return nextDamageDistance;
    }

    public void setMoveSince(boolean moveSince) {
        this.moveSince = moveSince;
    }

    public boolean isMoveSince() {
        return moveSince;
    }

    public int getMovesSince() {
        return movesSince;
    }

    public void setMovesSince(int movesSince) {
        this.movesSince = movesSince;
    }

    public void setNextDamageDistance(double nextDamageDistance) {
        this.nextDamageDistance = nextDamageDistance;
    }

    public int getHitGroundTick() {
        return hitGroundTick;
    }

    public void setHitGroundTick(int hitGroundTick) {
        this.hitGroundTick = hitGroundTick;
    }

    public double getHighestDis() {
        return highestDis;
    }

    public void setHighestDis(double highestDis) {
        this.highestDis = highestDis;
    }

    public boolean isNeedFall() {
        return needFall;
    }

    public void setNeedFall(boolean needFall) {
        this.needFall = needFall;
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

    public int getTicksUpgoing() {
        return ticksUpgoing;
    }

    public void setTicksUpgoing(int ticksUpgoing) {
        this.ticksUpgoing = ticksUpgoing;
    }

    public int getLastCountTick() {
        return lastCountTick;
    }

    public void setLastCountTick(int lastCountTick) {
        this.lastCountTick = lastCountTick;
    }

    public FlyData getLastData() {
        return lastData;
    }

    public void setLastData(FlyData lastData) {
        this.lastData = lastData;
    }

    public boolean isMabySlimeJump() {
        return mabySlimeJump;
    }

    public void setMabySlimeJump(boolean mabySlimeJump) {
        this.mabySlimeJump = mabySlimeJump;
    }

    public double getLastSlime() {
        return lastSlime;
    }

    public void setLastSlime(double lastSlime) {
        this.lastSlime = lastSlime;
    }

    public int getJumpPotion() {
        return jumpPotion;
    }

    public void setJumpPotion(int jumpPotion) {
        this.jumpPotion = jumpPotion;
    }

    public int getJumpPotionLastTick() {
        return jumpPotionLastTick;
    }

    public ArrayList<Vector> getVelocity() {
        return velocity;
    }

    public void setVelocity(ArrayList<Vector> velocity) {
        this.velocity = velocity;
    }

    public void setJumpPotionLastTick(int jumpPotionLastTick) {
        this.jumpPotionLastTick = jumpPotionLastTick;
    }
}
