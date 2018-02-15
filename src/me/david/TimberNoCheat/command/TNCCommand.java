package me.david.TimberNoCheat.command;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.api.RefreshEvent;
import me.david.TimberNoCheat.checkes.movement.Speed;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.config.Permissions;
import me.david.api.commands.CheckBuilder;
import me.david.api.commands.Command;
import me.david.api.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class TNCCommand extends Command {

    public TNCCommand(){
        super("timbernocheat", new CheckBuilder().addMultiPlayerPermission(new Object[]{"", false, ""}, new Object[]{"reload", false, Permissions.RELOAD}, new Object[]{"generate", true, Permissions.GENERATE}, new Object[]{"items", true, Permissions.ITEMS}, new Object[]{"profiler", true, Permissions.PROFILER}, new Object[]{"debugger", true, Permissions.DEBUGGER}, new Object[]{"checkmap", false, Permissions.CHECKMAP}, new Object[]{"settings", true, Permissions.SETTINGS}).build(), TimberNoCheat.instance.prefix, false, new String[]{"tnc", "ncp", "aac", "spartan", "anticheat", "cheat", "nocheat", "nocheatplus", "advancedanticheat", "ac"});
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
                long start = System.currentTimeMillis();
                Bukkit.getPluginManager().callEvent(new RefreshEvent(p));
                p.sendMessage(TimberNoCheat.instance.prefix + "Done in " + (System.currentTimeMillis()-start) + " ms!");
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
            case "debugger":
                TimberNoCheat.instance.guimanager.startMultidefaultStage(p, "DebuggerMulti");
                break;
            case "checkmap":
                p.sendMessage(TimberNoCheat.instance.prefix + "---[CheckMap]---");
                for(Check check : TimberNoCheat.checkmanager.getChecks()){
                    p.sendMessage(TimberNoCheat.instance.prefix + StringUtil.colorbyBool(true) + check.getName());
                    for(Check child : check.getChilds())
                        p.sendMessage(TimberNoCheat.instance.prefix + "    ->" + StringUtil.colorbyBool(true) + child.getName());
                    for(Check child : check.getDiabledsChilds())
                        p.sendMessage(TimberNoCheat.instance.prefix + "    ->" + StringUtil.colorbyBool(false) + child.getName());

                }
                for(Check check : TimberNoCheat.checkmanager.getDisabledChecks()){
                    p.sendMessage(TimberNoCheat.instance.prefix + StringUtil.colorbyBool(false) + check.getName());
                    for(Check child : check.getChilds())
                        p.sendMessage(TimberNoCheat.instance.prefix + "    ->" + StringUtil.colorbyBool(true) + child.getName());
                    for(Check child : check.getDiabledsChilds())
                        p.sendMessage(TimberNoCheat.instance.prefix + "    ->" + StringUtil.colorbyBool(false) + child.getName());
                }
                p.sendMessage(TimberNoCheat.instance.prefix + "---[CheckMap]---");
                break;
            case "settings":
                TimberNoCheat.instance.guimanager.startMultidefaultStage(p, "SettingsMulti");
                break;
        }
    }
}
