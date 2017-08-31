package me.david.TimberNoCheat.checkmanager;

public class Violation {
    public enum ViolationTypes{
        NOTIFY,
        CMD,
        KICK,
        MESSAGE;
    }
    private int level;
    private ViolationTypes type;
    private String rest;

    public Violation(int level, ViolationTypes type, String rest) {
        this.level = level;
        this.type = type;
        this.rest = rest;
    }

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
