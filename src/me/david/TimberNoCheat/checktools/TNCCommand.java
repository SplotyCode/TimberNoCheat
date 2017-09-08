package me.david.TimberNoCheat.checktools;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.api.commands.Checker;
import me.david.api.commands.Command;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class TNCCommand extends Command {

    public TNCCommand(){
        super("timbernocheat", new ArrayList<Checker>(), TimberNoCheat.instance.prefix, true, new String[]{"tnc", "ncp", "aac", "spartan", "anticheat", "cheat"});
        //new ArrayList<Checker>(Arrays.asList(new MultiChoices(new ArrayList<Choice>(Arrays.asList(new Choice(""))))))
    }

    @Override
    public void execute(Player p, String[] args) {
        p.sendMessage(TimberNoCheat.instance + "TimberNoCheat (Version: " + TimberNoCheat.instance.getDescription().getVersion() + ")");
    }
}
