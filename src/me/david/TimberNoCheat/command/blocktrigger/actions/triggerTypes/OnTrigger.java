package me.david.TimberNoCheat.command.blocktrigger.actions.triggerTypes;

import me.david.TimberNoCheat.command.blocktrigger.AttributeList;
import me.david.TimberNoCheat.command.blocktrigger.actions.Action;
import org.bukkit.Location;

public abstract class OnTrigger extends Action {

    @Override
    protected void trigger(Location location, AttributeList atributes, boolean state) {
        this.location = location;
        if(state)
            execute(atributes, state);
    }
}
