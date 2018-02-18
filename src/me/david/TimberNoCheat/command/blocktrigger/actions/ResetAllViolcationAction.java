package me.david.TimberNoCheat.command.blocktrigger.actions;

import me.david.TimberNoCheat.api.TNCAPI;
import me.david.TimberNoCheat.command.blocktrigger.AttributeList;
import me.david.TimberNoCheat.command.blocktrigger.actions.triggerTypes.OnTrigger;
import org.bukkit.entity.Player;

public class ResetAllViolcationAction extends OnTrigger {

    @Override
    protected void execute(AttributeList atributes, boolean state) {
        for(final Player player : getPlayersInRange(atributes.getDoubles().get(0)))
            TNCAPI.clearViolcation(player);
    }
}
