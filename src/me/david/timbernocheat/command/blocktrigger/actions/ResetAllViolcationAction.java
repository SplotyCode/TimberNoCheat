package me.david.timbernocheat.command.blocktrigger.actions;

import me.david.timbernocheat.api.TNCAPI;
import me.david.timbernocheat.command.blocktrigger.AttributeList;
import me.david.timbernocheat.command.blocktrigger.actions.triggerTypes.OnTrigger;
import org.bukkit.entity.Player;

public class ResetAllViolcationAction extends OnTrigger {

    @Override
    protected void execute(AttributeList atributes, boolean state) {
        for(final Player player : getPlayersInRange(atributes.getDoubles().get(0)))
            TNCAPI.clearViolcation(player);
    }
}
