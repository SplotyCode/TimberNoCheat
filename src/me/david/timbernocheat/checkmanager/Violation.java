package me.david.timbernocheat.checkmanager;

/*
 * Represents a Violation in the Config
 */
public class Violation {

    /* Types of Violation */
    public enum ViolationTypes{
        /* Notify Staff */
        NOTIFY,
        /* Execute a Command (over the console) */
        CMD,
        /* Kicks a Player */
        KICK,
        /* Sends a Message to the Player */
        MESSAGE,
        /* Damage the Player */
        DAMAGE
    }

    /* The Level when the Violation will trigger*/
    private int level;
    /* The Execute type*/
    private ViolationTypes type;
    /*
     * Depends on the Type
     * NOTIFY: -
     * CMD: The Command use ':' for more than one command
     */
    private String rest;

    public Violation(int level, ViolationTypes type, String rest) {
        this.level = level;
        this.type = type;
        this.rest = rest;
    }

    /* Default Getters and Setters */


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ViolationTypes getType() {
        return type;
    }

    public void setType(ViolationTypes type) {
        this.type = type;
    }

    public String getRest() {
        return rest;
    }

    public void setRest(String rest) {
        this.rest = rest;
    }
}
