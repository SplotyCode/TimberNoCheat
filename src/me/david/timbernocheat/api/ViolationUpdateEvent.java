package me.david.timbernocheat.api;

import me.david.timbernocheat.checkbase.Check;
import org.bukkit.entity.Player;

/**
 * Called avery Time the Violation-Level of a Player changes
 * This event is cancelable
 */
public class ViolationUpdateEvent extends TNCCancelEvent{

    /** The Player where the Violation-Level changes */
    private final Player player;
    /** The Violation-Level that the player will have after this Event */
    private double newViolation;
    /** The Violation-Level that the player hat before this Event*/
    private final double oldViolation;
    /** The Check that triggers the Violation-Level update*/
    private final Check check;

    public ViolationUpdateEvent(Player player, double newViolation, double oldViolation, Check check){
        this.player = player;
        this.newViolation = newViolation;
        this.oldViolation = oldViolation;
        this.check = check;
    }

    public Check getCheck() {
        return check;
    }

    public Player getPlayer() {
        return player;
    }

    public double getNewViolation() {
        return newViolation;
    }

    public void setNewViolation(double newViolation) {
        this.newViolation = newViolation;
    }

    public double getOldViolation() {
        return oldViolation;
    }

}
