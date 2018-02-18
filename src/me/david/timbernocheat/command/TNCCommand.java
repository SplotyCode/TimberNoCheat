package me.david.timbernocheat.command;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.api.RefreshEvent;
import me.david.timbernocheat.checkes.movement.Speed;
import me.david.timbernocheat.checkmanager.Check;
import me.david.timbernocheat.checkmanager.PlayerData;
import me.david.timbernocheat.config.Permissions;
import me.david.api.commands.CheckBuilder;
import me.david.api.commands.Command;
import me.david.api.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.*;

public class TNCCommand extends Command {

    public TNCCommand(){
        super("timbernocheat", new CheckBuilder().addMultiPlayerPermission(
                new Object[]{"", false, ""},
                new Object[]{"reload", false, Permissions.RELOAD},
                new Object[]{"generate", true, Permissions.GENERATE},
                new Object[]{"items", true, Permissions.ITEMS},
                new Object[]{"profiler", true, Permissions.PROFILER},
                new Object[]{"debugger", true, Permissions.DEBUGGER},
                new Object[]{"checkmap", false, Permissions.CHECKMAP},
                new Object[]{"settings", true, Permissions.SETTINGS},
                new Object[]{"playerdata", false, Permissions.PLAYER_DATA},
                new Object[]{"permissioncache", false, Permissions.PERMISSION_CACHE},
                new Object[]{"oreNotify", true, Permissions.ORE_NOTIFY},
                new Object[]{"blockTriggers", true, Permissions.BLOCK_TRIGGERS},
                new Object[]{"resetcache", false, Permissions.PERMISSION_CACHE_CLEAR}).
                build(), TimberNoCheat.instance.prefix, false, new String[]{"tnc", "ncp", "aac", "spartan", "anticheat", "cheat", "nocheat", "nocheatplus", "advancedanticheat", "ac"});
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
            case "playerdata":
                if (!TimberNoCheat.checkmanager.isvalid_create(p)) {
                    p.sendMessage(TimberNoCheat.instance.prefix + "Es gibt keine SpielerDaten für dich :(");
                    return;
                }
                PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
                for(Field field : pd.getClass().getDeclaredFields()){
                    try {
                        p.sendMessage(field.getName() + "[" + field.getType().getSimpleName() + "]" + field.get(pd));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "permissioncache":
                p.sendMessage(TimberNoCheat.instance.prefix + "---[Cache]---");
                for(Map.Entry<UUID, HashMap<String, Boolean>> cache :  TimberNoCheat.instance.permissioncache.getCache().entrySet()){
                    p.sendMessage(TimberNoCheat.instance.prefix + Bukkit.getOfflinePlayer(cache.getKey()).getName());
                    for(Map.Entry<String, Boolean> permission : cache.getValue().entrySet())
                        p.sendMessage(TimberNoCheat.instance.prefix + "    -> " + permission.getKey() + " <-> " + permission.getValue());
                }
                p.sendMessage(TimberNoCheat.instance.prefix + "---[Cache]---");
                break;
            case "resetcache":
                TimberNoCheat.instance.permissioncache.clearAll();
                p.sendMessage(TimberNoCheat.instance.prefix + "Fertig!");
                break;
            case "oreNotify":
                TimberNoCheat.instance.guimanager.startMultidefaultStage(p, "OreNotifyMulti");
                break;
            case "blockTriggers":
                TimberNoCheat.instance.guimanager.startMultidefaultStage(p, "TriggerBlockManageMulti");
                break;
        }
    }
}
