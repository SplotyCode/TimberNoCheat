package me.david.TimberNoCheat.command.blocktrigger.actions;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.command.blocktrigger.AttributeList;
import me.david.TimberNoCheat.command.blocktrigger.actions.triggerTypes.OnTrigger;

public class DisableCheckAction extends OnTrigger {

    @Override
    protected void execute(AttributeList atributes, boolean state) {
        final Check check = atributes.getChecks().get(0);
        TimberNoCheat.checkmanager.unregister(check);
    }
}
