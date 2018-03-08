package me.david.timbernocheat.command.blocktrigger.actions;

import me.david.timbernocheat.api.TNCApi;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.command.blocktrigger.AttributeList;
import me.david.timbernocheat.command.blocktrigger.actions.triggerTypes.OnTrigger;
import org.bukkit.entity.Player;

public class WhitelistAction extends OnTrigger {

    @Override
    protected void execute(AttributeList atributes, boolean state) {
        final Check check = atributes.getChecks().get(0);
        final long time = atributes.getLongs().get(0);
        for(final Player player : getPlayersInRange(atributes.getDoubles().get(0))){
            TNCApi.INSTANCE.whitelist(check, player, time);
        }
    }
}
