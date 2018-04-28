package me.david.timbernocheat.command.blocktrigger.actions;

import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.command.blocktrigger.AttributeList;
import me.david.timbernocheat.command.blocktrigger.actions.triggerTypes.PulseTrigger;

public class PulseCheckAction extends PulseTrigger {

    @Override
    protected void end(AttributeList atributes) {
        final Check check = atributes.getChecks().get(0);
        CheckManager.getInstance().unregister(check);
    }

    @Override
    protected void start(AttributeList atributes) {
        final Check check = atributes.getChecks().get(0);
        CheckManager.getInstance().register(check);
    }
}
