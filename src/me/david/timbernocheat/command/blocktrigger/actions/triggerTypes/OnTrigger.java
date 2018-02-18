package me.david.timbernocheat.command.blocktrigger.actions.triggerTypes;

import me.david.timbernocheat.command.blocktrigger.AttributeList;
import me.david.timbernocheat.command.blocktrigger.actions.Action;
import org.bukkit.Location;

public abstract class OnTrigger extends Action {

    @Override
    public void trigger(Location location, AttributeList atributes, boolean state) {
        this.location = location;
        if(state)
            execute(atributes, state);
    }
}
