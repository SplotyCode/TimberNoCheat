package me.david.timbernocheat.exeptions.logging;

public enum Identifiers {

    ;

    private long blockedTime;

    public long getBlockedTime() {
        return blockedTime;
    }

    public int identifier() {
        return ordinal();
    }
}
