package me.david.TimberNoCheat.command.blocktrigger.actions;

import me.david.TimberNoCheat.command.blocktrigger.AttributeList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class Action {

    protected Location location;

    protected abstract void execute(final AttributeList atributes, final boolean state);

    public void trigger(final Location location, final AttributeList atributes, final boolean state){
        this.location = location;
        execute(atributes, state);
    }

    public ArrayList<Player> getPlayersInRange(final double range){
        ArrayList<Player> list = new ArrayList<>();
        for(Player player : Bukkit.getOnlinePlayers())
            if(player.getLocation().distance(location) <= range)
                list.add(player);
        return list;
    }

}
