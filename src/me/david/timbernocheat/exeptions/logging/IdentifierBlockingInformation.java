package me.david.timbernocheat.exeptions.logging;

public class IdentifierBlockingInformation implements BlockingInformation {

    private final int identifier;

    public IdentifierBlockingInformation(final int identifier) {
        this.identifier = identifier;
    }

    public int getIdentifier() {
        return identifier;
    }
}
