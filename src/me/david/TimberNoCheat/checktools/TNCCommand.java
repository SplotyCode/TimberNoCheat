package me.david.TimberNoCheat.checktools;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.api.commands.Checker;
import me.david.api.commands.Command;
import me.david.api.commands.checkers.multichoice.Choice;
import me.david.api.commands.checkers.multichoice.MultiChoices;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.ArrayList;
import java.util.Arrays;

public class TNCCommand extends Command{

    public TNCCommand(){
        super("timbernocheat", new ArrayList<Checker>(), TimberNoCheat.instance.prefix, true, new String[]{"tnc", "ncp", "aac", "spartan", "anticheat", "cheat"});
        //new ArrayList<Checker>(Arrays.asList(new MultiChoices(new ArrayList<Choice>(Arrays.asList(new Choice(""))))))
    }

    @Override
    public void execute(Player p, String[] args) {
        p.sendMessage(TimberNoCheat.instance + "TimberNoCheat (Version: " + TimberNoCheat.instance.getDescription().getVersion() + ")");
    }
}
