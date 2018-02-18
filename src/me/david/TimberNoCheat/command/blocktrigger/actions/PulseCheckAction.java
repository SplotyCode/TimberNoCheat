package me.david.TimberNoCheat.command.blocktrigger.actions;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.command.blocktrigger.AttributeList;
import me.david.TimberNoCheat.command.blocktrigger.actions.triggerTypes.PulseTrigger;

public class PulseCheckAction extends PulseTrigger {

    @Override
    protected void end(AttributeList atributes) {
        final Check check = atributes.getChecks().get(0);
        TimberNoCheat.checkmanager.unregister(check);
    }

    @Override
    protected void start(AttributeList atributes) {
        final Check check = atributes.getChecks().get(0);
        TimberNoCheat.checkmanager.register(check);
    }
}
