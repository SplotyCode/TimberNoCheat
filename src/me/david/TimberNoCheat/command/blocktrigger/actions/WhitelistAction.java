package me.david.TimberNoCheat.command.blocktrigger.actions;

import me.david.TimberNoCheat.api.TNCAPI;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.command.blocktrigger.AttributeList;
import me.david.TimberNoCheat.command.blocktrigger.actions.triggerTypes.OnTrigger;
import org.bukkit.entity.Player;

public class WhitelistAction extends OnTrigger {

    @Override
    protected void execute(AttributeList atributes, boolean state) {
        final Check check = atributes.getChecks().get(0);
        final long time = atributes.getLongs().get(0);
        for(final Player player : getPlayersInRange(atributes.getDoubles().get(0))){
            TNCAPI.whitelist(check, player, time);
        }
    }
}
