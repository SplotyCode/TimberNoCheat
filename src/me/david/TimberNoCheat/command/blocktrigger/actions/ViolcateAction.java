package me.david.TimberNoCheat.command.blocktrigger.actions;

import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.command.blocktrigger.AttributeList;
import me.david.TimberNoCheat.command.blocktrigger.actions.triggerTypes.OnTrigger;
import org.bukkit.entity.Player;

public class ViolcateAction extends OnTrigger {

    @Override
    protected void execute(AttributeList atributes, boolean state) {
        Check check = atributes.getChecks().get(0);
        float violation = atributes.getFloats().get(0);
        for(final Player player : getPlayersInRange(atributes.getDoubles().get(0))){
            check.updatevio(check, player, violation, "FROM BLOCK");
        }
    }

}
