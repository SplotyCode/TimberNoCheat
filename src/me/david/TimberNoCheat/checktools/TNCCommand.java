package me.david.TimberNoCheat.checktools;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.api.RefreshEvent;
import me.david.TimberNoCheat.checkes.movement.Speed;
import me.david.TimberNoCheat.config.Permissions;
import me.david.api.commands.CheckBuilder;
import me.david.api.commands.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TNCCommand extends Command {

    public TNCCommand(){
        super("timbernocheat", new CheckBuilder().addMultiPlayerPermission(new Object[]{"", false, ""}, new Object[]{"reload", false, Permissions.RELOAD}, new Object[]{"generate", true, Permissions.GENERATE}, new Object[]{"items", true, Permissions.ITEMS}, new Object[]{"profiler", true, Permissions.PROFILER}).build(), TimberNoCheat.instance.prefix, false, new String[]{"tnc", "ncp", "aac", "spartan", "anticheat", "cheat", "nocheat", "nocheatplus", "advancedanticheat", "ac"});
        setOnlyplayers(true);
    }

    @Override
    public void execute(CommandSender sender, org.bukkit.command.Command cmd, String[] args) {
        Player p = (Player) sender;
        if(args.length == 0){
            p.sendMessage(TimberNoCheat.instance.prefix + "TimberNoCheat (Version: " + TimberNoCheat.instance.getDescription().getVersion() + ")");
            return;
        }
        switch (args[0].toLowerCase()){
            /* Config Reload */
            case "reload":
                Bukkit.getPluginManager().callEvent(new RefreshEvent(p));
                p.sendMessage(TimberNoCheat.instance.prefix + "Done!");
                break;
            /* Speed check */
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
            case "items":
                TimberNoCheat.instance.guimanager.startMultidefaultStage(p, "ItemsMulti");
                break;
            case "profiler":
                TimberNoCheat.instance.guimanager.startMultidefaultStage(p, "ProfilerMulti");
                break;
        }
    }
}
