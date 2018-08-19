package me.david.timbernocheat.exeptions.logging;

import java.util.function.Function;

public class IdentifierChecker implements Function<BlockingInformation, Boolean> {

    protected int identifier;

    public IdentifierChecker(int identifier) {
        this.identifier = identifier;
    }

    @Override
    public Boolean apply(BlockingInformation identifier) {
        return identifier instanceof IdentifierBlockingInformation && ((IdentifierBlockingInformation) identifier).getIdentifier() == this.identifier;
    }
}
