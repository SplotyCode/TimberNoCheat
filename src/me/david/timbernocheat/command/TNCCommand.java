package me.david.timbernocheat.command;

import javafx.util.Pair;
import me.david.api.commands.CheckBuilder;
import me.david.api.commands.Command;
import me.david.api.commands.checkers.Playercheck;
import me.david.api.commands.checkers.Stringcheck;
import me.david.api.utils.HastebinUtil;
import me.david.api.utils.StringUtil;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.api.RefreshEvent;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.checkes.movement.speed.PatternCheck;
import me.david.timbernocheat.config.Permissions;
import me.david.timbernocheat.debug.log.DebugEntry;
import me.david.timbernocheat.runnable.Tps;
import me.david.timbernocheat.util.PrettyPrint;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.*;

public class TNCCommand extends Command {

    private SimpleDateFormat format = new SimpleDateFormat("kk:mm:ss | dd/MM/yy");


    public TNCCommand(){
        super("timbernocheat", new CheckBuilder().addMultiPlayerPermissionChecker(
                new Object[]{"", false, ""},
                new Object[]{"reload", false, Permissions.RELOAD},
                new Object[]{"generate", true, Permissions.GENERATE},
                new Object[]{"items", true, Permissions.ITEMS},
                new Object[]{"profiler", true, Permissions.PROFILER},
                new Object[]{"debugger", true, Permissions.DEBUGGER},
                new Object[]{"checkmap", false, Permissions.CHECKMAP},
                new Object[]{"settings", true, Permissions.SETTINGS},
                new Object[]{"playerdata", false, Permissions.PLAYER_DATA, new Playercheck(true)},
                new Object[]{"invalidate", false, Permissions.PLAYER_DAYA_INVALIDATE, new Playercheck(true)},
                new Object[]{"permissioncache", false, Permissions.PERMISSION_CACHE},
                new Object[]{"oreNotify", true, Permissions.ORE_NOTIFY},
                new Object[]{"blockTriggers", true, Permissions.BLOCK_TRIGGERS},
                new Object[]{"violations", true, Permissions.VIOLATIONMENU},
                new Object[]{"resetcache", false, Permissions.PERMISSION_CACHE_CLEAR},
                new Object[]{"playerdebugids", false, Permissions.TRACK_DEBUGVIOLATIONS, new Playercheck(false)},
                new Object[]{"listuntrackedvios", false, Permissions.TRACK_DEBUGVIOLATIONS, new Playercheck(false)},
                new Object[]{"debugid", false, Permissions.TRACK_DEBUGVIOLATIONS, new Stringcheck(14)},
                new Object[]{"listdebugids", false, Permissions.TRACK_DEBUGVIOLATIONS}).
                build(), TimberNoCheat.getInstance().prefix, false, new String[]{"tnc", "ncp", "aac", "spartan", "anticheat", "cheat", "nocheat", "nocheatplus", "advancedanticheat", "ac"});
        setOnlyplayers(false);
    }

    @Override
    public void execute(CommandSender sender, org.bukkit.command.Command cmd, String[] args) {
        Player p = sender instanceof Player?(Player) sender:null;
        if(args.length == 0){
            p.sendMessage(TimberNoCheat.getInstance().prefix + "TimberNoCheat (Version: " + TimberNoCheat.getInstance().getDescription().getVersion() + ")");
            return;
        }
        switch (args[0].toLowerCase()){
            /* Config Reload */
            case "reload":
                long start = System.currentTimeMillis();
                Bukkit.getPluginManager().callEvent(new RefreshEvent(sender));
                sender.sendMessage(TimberNoCheat.getInstance().prefix + "Done in " + (System.currentTimeMillis()-start) + " ms!");
                break;
            /* Speed check */
            case "generate":
                if(PatternCheck.generators.contains(p.getUniqueId())){
                    PatternCheck.generators.remove(p.getUniqueId());
                    p.sendMessage(TimberNoCheat.getInstance().prefix + "§cDas Anticheat 'trainiert' sich jetzt nicht mehr an dir!");
                }else {
                    PatternCheck.generators.add(p.getUniqueId());
                    p.sendMessage(TimberNoCheat.getInstance().prefix + "Bewege dich um das Anticheat zu 'trainieren'!");
                    p.sendMessage(TimberNoCheat.getInstance().prefix + "Du darfst jetzt keine Hacks benutzen!");
                }
                break;
            case "items":
                TimberNoCheat.getInstance().getGuimanager().startMultidefaultStage(p, "ItemsMulti");
                break;
            case "profiler":
                TimberNoCheat.getInstance().getGuimanager().startMultidefaultStage(p, "ProfilerMulti");
                break;
            case "debugger":
                TimberNoCheat.getInstance().getGuimanager().startMultidefaultStage(p, "DebuggerMulti");
                break;
            case "checkmap":
                sender.sendMessage(TimberNoCheat.getInstance().prefix + "---[CheckMap]---");
                for(Check check : TimberNoCheat.getCheckManager().getChecks()){
                    sender.sendMessage(TimberNoCheat.getInstance().prefix + StringUtil.colorbyBool(true) + check.getName());
                    for(Check child : check.getChilds())
                        sender.sendMessage(TimberNoCheat.getInstance().prefix + "    ->" + StringUtil.colorbyBool(true) + child.getName());
                    for(Check child : check.getDiabledsChilds())
                        sender.sendMessage(TimberNoCheat.getInstance().prefix + "    ->" + StringUtil.colorbyBool(false) + child.getName());

                }
                for(Check check : TimberNoCheat.getCheckManager().getDisabledChecks()){
                    sender.sendMessage(TimberNoCheat.getInstance().prefix + StringUtil.colorbyBool(false) + check.getName());
                    for(Check child : check.getChilds())
                        sender.sendMessage(TimberNoCheat.getInstance().prefix + "    ->" + StringUtil.colorbyBool(true) + child.getName());
                    for(Check child : check.getDiabledsChilds())
                        sender.sendMessage(TimberNoCheat.getInstance().prefix + "    ->" + StringUtil.colorbyBool(false) + child.getName());
                }
                for(Check check : Tps.disabledChecks){
                    sender.sendMessage(TimberNoCheat.getInstance().prefix + ChatColor.YELLOW + check.getName());
                    for(Check child : check.getChilds())
                        sender.sendMessage(TimberNoCheat.getInstance().prefix + ChatColor.YELLOW + child.getName());
                }
                sender.sendMessage(TimberNoCheat.getInstance().prefix + "---[CheckMap]---");
                break;
            case "settings":
                TimberNoCheat.getInstance().getGuimanager().startMultidefaultStage(p, "SettingsMulti");
                break;
            case "playerdata": {
                final Player target = Bukkit.getPlayer(args[1]);
                if (!TimberNoCheat.getCheckManager().isvalid_create(target)) {
                    sender.sendMessage(TimberNoCheat.getInstance().prefix + "Es gibt keine SpielerDaten für '" + target.getName() + " :(");
                    return;
                }
                PlayerData pd = TimberNoCheat.getCheckManager().getPlayerdata(target);
                sender.sendMessage(TimberNoCheat.getInstance().prefix + "Playerdata for " + pd.getUuid());
                sender.sendMessage(new PrettyPrint(pd, true, TimberNoCheat.getInstance().prefix).prettyPrint(0));
                String hastebin = HastebinUtil.paste(new PrettyPrint(pd, false, "").prettyPrint(0));
                sender.sendMessage(TimberNoCheat.getInstance().prefix + "Finished! Now we will make a copy as Hastebin (as this data can get quite confusing in the chat :D ) ");
                sender.sendMessage(TimberNoCheat.getInstance().prefix + (hastebin == null ? "§cFehler bei Hochladen" : hastebin));
                break;
            }case "permissioncache":
                sender.sendMessage(TimberNoCheat.getInstance().prefix + "---[Cache]---");
                for(Map.Entry<UUID, HashMap<String, Boolean>> cache :  TimberNoCheat.getInstance().permissioncache.getCache().entrySet()){
                    sender.sendMessage(TimberNoCheat.getInstance().prefix + Bukkit.getOfflinePlayer(cache.getKey()).getName());
                    for(Map.Entry<String, Boolean> permission : cache.getValue().entrySet())
                        sender.sendMessage(TimberNoCheat.getInstance().prefix + "    -> " + permission.getKey() + " <-> " + permission.getValue());
                }
                sender.sendMessage(TimberNoCheat.getInstance().prefix + "---[Cache]---");
                break;
            case "resetcache":
                TimberNoCheat.getInstance().permissioncache.clearAll();
                sender.sendMessage(TimberNoCheat.getInstance().prefix + "Fertig!");
                break;
            case "orenotify":
                TimberNoCheat.getInstance().getGuimanager().startMultidefaultStage(p, "OreNotifyMulti");
                break;
            case "blocktriggers":
                TimberNoCheat.getInstance().getGuimanager().startMultidefaultStage(p, "TriggerBlockManageMulti");
                break;
            case "violations":
                TimberNoCheat.getInstance().getGuimanager().startMultidefaultStage(p, "GlobalViolationMulti");
                break;
            case "invalidate":
                Player target = Bukkit.getPlayer(args[0]);
                PlayerData data = TimberNoCheat.getCheckManager().getPlayerdata(target);
                if(data != null) TimberNoCheat.getCheckManager().getPlayerData().remove(data);
                else sender.sendMessage(TimberNoCheat.getInstance().prefix + "Es gibt keine Spielerdaten für '" + target.getName() + "'!");
                break;
            case "listdebugids":
                sender.sendMessage(TimberNoCheat.getInstance().prefix + "---[Debug-Ids]---");
                if(TimberNoCheat.getInstance().getDebugLogManager().getSavedEntries().isEmpty())
                    sender.sendMessage(TimberNoCheat.getInstance().prefix + "§cKeine Debug Ids");
                for(Map.Entry<String, Pair<UUID, ArrayList<DebugEntry>>> entry : TimberNoCheat.getInstance().getDebugLogManager().getSavedEntries().entrySet())
                    sender.sendMessage(TimberNoCheat.getInstance().prefix + entry.getKey() + " -> " + Bukkit.getOfflinePlayer(entry.getValue().getKey()).getName());
                sender.sendMessage(TimberNoCheat.getInstance().prefix + "---[Debug-Ids]---");
                break;
            case "playerdebugids": {
                sender.sendMessage(TimberNoCheat.getInstance().prefix + "---[Debug-Ids]---");
                UUID uuid = Bukkit.getOfflinePlayer(args[1]).getUniqueId();

                for (Map.Entry<String, Pair<UUID, ArrayList<DebugEntry>>> entry : TimberNoCheat.getInstance().getDebugLogManager().getSavedEntries().entrySet())
                    if (entry.getValue().getKey().equals(uuid))
                        sender.sendMessage(TimberNoCheat.getInstance().prefix + entry.getKey() + " -> " + format.format(new Date(entry.getValue().getValue().get(0).getTime())) + " <-> " + format.format(new Date(entry.getValue().getValue().get(entry.getValue().getValue().size() - 1).getTime())));
                sender.sendMessage(TimberNoCheat.getInstance().prefix + "---[Debug-Ids]---");
                break;
            }case "debugid":
                Pair<UUID, ArrayList<DebugEntry>> pair = TimberNoCheat.getInstance().getDebugLogManager().getSavedEntries().get(args[1]);
                if (pair == null)
                    sender.sendMessage(TimberNoCheat.getInstance().prefix + "§cDie id konnte nicht gefunden werden");
                else {
                    sender.sendMessage(TimberNoCheat.getInstance().prefix + "Spieler: " + Bukkit.getOfflinePlayer(pair.getKey()).getName());
                    sender.sendMessage(TimberNoCheat.getInstance().prefix + "---[Timeline]---");
                    for (DebugEntry entry : pair.getValue()) {
                        double delay = entry.getNewVio() - entry.getOldVio();
                        sender.sendMessage(TimberNoCheat.getInstance().prefix + format.format(new Date(entry.getTime())) + " +=+ " +
                                entry.getCheck() + " *=* " +
                                (entry.getExecutions().length == 0 ? "Keine Bestafungen" : StringUtil.toString(entry.getExecutions(), ", ")) + " *=* " +
                                StringUtil.colorbyBool(delay > 0, true) + delay + " §6(New=" + entry.getNewVio() + "Old=" + entry.getOldVio() + ")" +
                                (entry.isCancel() ? " *=* (§cCanceled§6)" : "")
                        );
                    }
                    sender.sendMessage(TimberNoCheat.getInstance().prefix + "---[Timeline]---");
                }
                break;
            case "listuntrackedvios":
                UUID uuid = Bukkit.getOfflinePlayer(args[1]).getUniqueId();
                ArrayList<DebugEntry> entries = TimberNoCheat.getInstance().getDebugLogManager().getDebugEntries().get(uuid);
                if(entries == null) sender.sendMessage(TimberNoCheat.getInstance().prefix + "§cKeine listuntracked violatrions für den spieler!");
                else {
                    sender.sendMessage(TimberNoCheat.getInstance().prefix + "Spieler: " + Bukkit.getOfflinePlayer(uuid).getName());
                    sender.sendMessage(TimberNoCheat.getInstance().prefix + "---[Timeline]---");
                    for (DebugEntry entry : entries) {
                        double delay = entry.getNewVio() - entry.getOldVio();
                        sender.sendMessage(TimberNoCheat.getInstance().prefix + format.format(new Date(entry.getTime())) + " +=+ " +
                                entry.getCheck() + " *=* " +
                                (entry.getExecutions() == null || entry.getExecutions().length == 0 ? "Keine Bestafungen" : StringUtil.toString(entry.getExecutions(), ", ")) + " *=* " +
                                StringUtil.colorbyBool(delay > 0, true) + delay + " §6(New=" + entry.getNewVio() + "Old=" + entry.getOldVio() + ")" +
                                (entry.isCancel() ? " *=* (§cCanceled§6)" : "")
                        );
                    }
                    sender.sendMessage(TimberNoCheat.getInstance().prefix + "---[Timeline]---");
                }
                break;
        }
    }
}
