package me.david.TimberNoCheat.command.blocktrigger;

import me.david.TimberNoCheat.checkmanager.Check;

public enum TriggerAction {

    VIOLATE(Check.class, float.class),
    RESETVIOLATION(Check.class),
    RESETIOLATION_ALLCHECKS,
    DISABLECHECK(Check.class),
    ENABLECHECK(Check.class),
    WHITELIST(Check.class, long.class);

    private final Class[] classes;

    TriggerAction(Class... classes){
        this.classes = classes;
    }

    public Class[] getClasses() {
        return classes;
    }
}
