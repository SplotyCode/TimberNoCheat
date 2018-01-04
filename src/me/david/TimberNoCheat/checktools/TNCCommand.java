package me.david.TimberNoCheat.checktools;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.api.RefreshEvent;
import me.david.TimberNoCheat.checkes.movement.Speed;
import me.david.api.commands.CheckBuilder;
import me.david.api.commands.Command;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TNCCommand extends Command {

    public TNCCommand(){
        super("timbernocheat", new CheckBuilder().addMulti("", "reload", "generate").build(), TimberNoCheat.instance.prefix, true, new String[]{"tnc", "ncp", "aac", "spartan", "anticheat", "cheat", "nocheat", "nocheatplus", "advancedanticheat"});
    }

    @Override
    public void execute(Player p, String[] args) {
        switch (args[0].toLowerCase()){
            case "":
                p.sendMessage(TimberNoCheat.instance + "TimberNoCheat (Version: " + TimberNoCheat.instance.getDescription().getVersion() + ")");
                break;
            case "reload":
                Bukkit.getPluginManager().callEvent(new RefreshEvent(p));
                p.sendMessage(TimberNoCheat.instance.prefix + "Done!");
                break;
            case "generate":
                if(Speed.generators.contains(p.getUniqueId())){
                    Speed.generators.remove(p.getUniqueId());
                    p.sendMessage(TimberNoCheat.instance.prefix + "§cDas Anticheat 'trainiert' sich jetzt nicht mehr an dir!");
                }else {
                    Speed.generators.add(p.getUniqueId());
                    p.sendMessage(TimberNoCheat.instance.prefix + "Bewege dich um das Anticheat zu 'trainieren'!");
                    p.sendMessage(TimberNoCheat.instance.prefix + "Du darfst jetzt keine Hacks benutzen!");
                }
                break;
        }
    }
}
