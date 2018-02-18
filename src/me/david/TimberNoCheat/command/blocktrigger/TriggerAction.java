package me.david.TimberNoCheat.command.blocktrigger;

import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.command.blocktrigger.actions.*;

public enum TriggerAction {

    VIOLATE(new ViolcateAction(), Check.class, float.class, double.class),
    RESETVIOLATION(new ResetViolationAction(), Check.class, double.class),
    RESETIOLATION_ALLCHECKS(new ResetAllViolcationAction(), double.class),
    DISABLECHECK(new DisableCheckAction(), Check.class),
    ENABLECHECK(new EnableCheckAction(), Check.class),
    PULSECHECK(new PulseCheckAction(), Check.class),
    WHITELIST(new WhitelistAction(), Check.class, long.class, double.class);

    private final Class[] classes;
    private final Action action;

    TriggerAction(final Action action, Class... classes){
        this.action = action;
        this.classes = classes;
    }

    public Class[] getClasses() {
        return classes;
    }

    public Action getAction() {
        return action;
    }
}
