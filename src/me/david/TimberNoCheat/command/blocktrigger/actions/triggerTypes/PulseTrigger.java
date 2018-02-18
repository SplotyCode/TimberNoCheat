package me.david.TimberNoCheat.command.blocktrigger.actions.triggerTypes;

import me.david.TimberNoCheat.command.blocktrigger.AttributeList;
import me.david.TimberNoCheat.command.blocktrigger.actions.Action;
import org.bukkit.Location;

public abstract class PulseTrigger extends Action {

    //ignore
    @Override protected void execute(AttributeList atributes, boolean state) {}

    @Override
    protected void trigger(Location location, AttributeList atributes, boolean state) {
        this.location = location;
        if(state) start(atributes);
        else end(atributes);
    }

    protected abstract void end(AttributeList atributes);
    protected abstract void start(AttributeList atributes);

}
