package me.david.TimberNoCheat.checktools;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.api.commands.CheckBuilder;
import me.david.api.commands.Checker;
import me.david.api.commands.Command;
import me.david.api.commands.checkers.multichoice.Choice;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;

public class TNCCommand extends Command {

    public TNCCommand(){
        super("timbernocheat", new CheckBuilder().addMulti("", "reload").build(), TimberNoCheat.instance.prefix, true, new String[]{"tnc", "ncp", "aac", "spartan", "anticheat", "cheat", "nocheat", "nocheatplus", "advancedanticheat"});
    }

    @Override
    public void execute(Player p, String[] args) {
        switch (args[0].toLowerCase()){
            case "":
                p.sendMessage(TimberNoCheat.instance + "TimberNoCheat (Version: " + TimberNoCheat.instance.getDescription().getVersion() + ")");
                break;
            case "reload":
                for(Check check : TimberNoCheat.checkmanager.checks){
                    HandlerList.unregisterAll(check);
                    check.disablelisteners();
                    check.disabletasks();
                }
                TimberNoCheat.checkmanager.checks.clear();
                TimberNoCheat.checkmanager.loadchecks();
                break;
        }
    }
}
