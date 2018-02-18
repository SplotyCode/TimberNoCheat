package me.david.timbernocheat.command.blocktrigger.actions;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Check;
import me.david.timbernocheat.command.blocktrigger.AttributeList;
import me.david.timbernocheat.command.blocktrigger.actions.triggerTypes.OnTrigger;

public class EnableCheckAction extends OnTrigger {

    @Override
    protected void execute(AttributeList atributes, boolean state) {
        final Check check = atributes.getChecks().get(0);
        TimberNoCheat.checkmanager.register(check);
    }
}
