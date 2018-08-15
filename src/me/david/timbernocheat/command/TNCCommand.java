package me.david.timbernocheat.command;

import me.david.api.commands.CheckBuilder;
import me.david.api.commands.Command;
import me.david.api.commands.checkers.Playercheck;
import me.david.api.commands.checkers.Stringcheck;
import me.david.api.objects.Pair;
import me.david.api.utils.HastebinUtil;
import me.david.api.utils.StringUtil;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.api.RefreshEvent;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
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
                new Object[]{"permissionCache", false, Permissions.PERMISSION_CACHE},
                new Object[]{"oreNotify", true, Permissions.ORE_NOTIFY},
                new Object[]{"blockTriggers", true, Permissions.BLOCK_TRIGGERS},
                new Object[]{"violations", true, Permissions.VIOLATIONMENU},
                new Object[]{"resetcache", false, Permissions.PERMISSION_CACHE_CLEAR},
                new Object[]{"playerdebugids", false, Permissions.TRACK_DEBUGVIOLATIONS, new Playercheck(false)},
                new Object[]{"listuntrackedvios", false, Permissions.TRACK_DEBUGVIOLATIONS, new Playercheck(false)},
                new Object[]{"debugid", false, Permissions.TRACK_DEBUGVIOLATIONS, new Stringcheck(14)},
                new Object[]{"listdebugids", false, Permissions.TRACK_DEBUGVIOLATIONS},
                new Object[]{"statistics", false, ""},
                new Object[]{"credits", false, ""}).
                build(), TimberNoCheat.getInstance().getPrefix(), false, new String[]{"tnc", "ncp", "aac", "spartan", "anticheat", "cheat", "nocheat", "nocheatplus", "advancedanticheat", "ac"});
        setOnlyplayers(false);
    }

    @Override
    public void execute(CommandSender sender, org.bukkit.command.Command cmd, String[] args) {
        Player p = sender instanceof Player?(Player) sender:null;
        if(args.length == 0){
            sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "TimberNoCheat (Version: " + TimberNoCheat.getInstance().getDescription().getVersion() + ")");
            sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "/tnc -help | /tnc credits | /tnc statistics");
            return;
        }
        switch (args[0].toLowerCase()){
            /* Config Reload */
            case "reload":
                long start = System.currentTimeMillis();
                Bukkit.getPluginManager().callEvent(new RefreshEvent(sender));
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "Done in " + (System.currentTimeMillis()-start) + " ms!");
                break;
            /* Speed check */
            case "generate":
                if(PatternCheck.generators.contains(p.getUniqueId())){
                    PatternCheck.generators.remove(p.getUniqueId());
                    p.sendMessage(TimberNoCheat.getInstance().getPrefix() + "§cDas Anticheat 'trainiert' sich jetzt nicht mehr an dir!");
                }else {
                    PatternCheck.generators.add(p.getUniqueId());
                    p.sendMessage(TimberNoCheat.getInstance().getPrefix() + "Bewege dich um das Anticheat zu 'trainieren'!");
                    p.sendMessage(TimberNoCheat.getInstance().getPrefix() + "Du darfst jetzt keine Hacks benutzen!");
                }
                break;
            case "items":
                TimberNoCheat.getInstance().getGuiManager().startMultidefaultStage(p, "ItemsMulti");
                break;
            case "profiler":
                TimberNoCheat.getInstance().getGuiManager().startMultidefaultStage(p, "ProfilerMulti");
                break;
            case "debugger":
                TimberNoCheat.getInstance().getGuiManager().startMultidefaultStage(p, "DebuggerMulti");
                break;
            case "checkmap":
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "---[CheckMap]---");
                for(Check check : CheckManager.getInstance().getChecks()){
                    sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + StringUtil.colorbyBool(true) + check.getName());
                    for(Check child : check.getChilds())
                        sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "    ->" + StringUtil.colorbyBool(true) + child.getName());
                    for(Check child : check.getDiabledsChilds())
                        sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "    ->" + StringUtil.colorbyBool(false) + child.getName());

                }
                for(Check check : CheckManager.getInstance().getDisabledChecks()){
                    sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + StringUtil.colorbyBool(false) + check.getName());
                    for(Check child : check.getChilds())
                        sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "    ->" + StringUtil.colorbyBool(true) + child.getName());
                    for(Check child : check.getDiabledsChilds())
                        sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "    ->" + StringUtil.colorbyBool(false) + child.getName());
                }
                for(Check check : Tps.disabledChecks){
                    sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + ChatColor.YELLOW + check.getName());
                    for(Check child : check.getChilds())
                        sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + ChatColor.YELLOW + child.getName());
                }
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "---[CheckMap]---");
                break;
            case "settings":
                TimberNoCheat.getInstance().getGuiManager().startMultidefaultStage(p, "SettingsMulti");
                break;
            case "playerdata": {
                final Player target = Bukkit.getPlayer(args[1]);
                if (!CheckManager.getInstance().isvalid_create(target)) {
                    sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "Es gibt keine SpielerDaten für '" + target.getName() + " :(");
                    return;
                }
                PlayerData pd = CheckManager.getInstance().getPlayerdata(target);
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "Playerdata for " + pd.getUuid());
                sender.sendMessage(new PrettyPrint(pd, true, TimberNoCheat.getInstance().getPrefix()).prettyPrint(0));
                String hastebin = HastebinUtil.paste(new PrettyPrint(pd, false, "").prettyPrint(0));
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "Finished! Now we will make a copy as Hastebin (as this data can get quite confusing in the chat :D ) ");
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + (hastebin == null ? "§cFehler bei Hochladen" : hastebin));
                break;
            }case "permissionCache":
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "---[Cache]---");
                for(Map.Entry<UUID, HashMap<String, Boolean>> cache :  TimberNoCheat.getInstance().permissionCache.getCache().entrySet()){
                    sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + Bukkit.getOfflinePlayer(cache.getKey()).getName());
                    for(Map.Entry<String, Boolean> permission : cache.getValue().entrySet())
                        sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "    -> " + permission.getKey() + " <-> " + permission.getValue());
                }
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "---[Cache]---");
                break;
            case "resetcache":
                TimberNoCheat.getInstance().permissionCache.clearAll();
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "Fertig!");
                break;
            case "orenotify":
                TimberNoCheat.getInstance().getGuiManager().startMultidefaultStage(p, "OreNotifyMulti");
                break;
            case "blocktriggers":
                TimberNoCheat.getInstance().getGuiManager().startMultidefaultStage(p, "TriggerBlockManageMulti");
                break;
            case "violations":
                TimberNoCheat.getInstance().getGuiManager().startMultidefaultStage(p, "GlobalViolationMulti");
                break;
            case "invalidate":
                Player target = Bukkit.getPlayer(args[0]);
                PlayerData data = CheckManager.getInstance().getPlayerdata(target);
                if(data != null) CheckManager.getInstance().getPlayerData().remove(data);
                else sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "Es gibt keine Spielerdaten für '" + target.getName() + "'!");
                break;
            case "listdebugids":
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "---[Debug-Ids]---");
                if(TimberNoCheat.getInstance().getDebugLogManager().getSavedEntries().isEmpty())
                    sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "§cKeine Debug Ids");
                for(Map.Entry<String, Pair<UUID, ArrayList<DebugEntry>>> entry : TimberNoCheat.getInstance().getDebugLogManager().getSavedEntries().entrySet())
                    sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + entry.getKey() + " -> " + Bukkit.getOfflinePlayer(entry.getValue().getOne()).getName());
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "---[Debug-Ids]---");
                break;
            case "playerdebugids": {
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "---[Debug-Ids]---");
                UUID uuid = Bukkit.getOfflinePlayer(args[1]).getUniqueId();

                for (Map.Entry<String, Pair<UUID, ArrayList<DebugEntry>>> entry : TimberNoCheat.getInstance().getDebugLogManager().getSavedEntries().entrySet())
                    if (entry.getValue().getOne().equals(uuid))
                        sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + entry.getKey() + " -> " + format.format(new Date(entry.getValue().getTwo().get(0).getTime())) + " <-> " + format.format(new Date(entry.getValue().getTwo().get(entry.getValue().getTwo().size() - 1).getTime())));
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "---[Debug-Ids]---");
                break;
            }case "debugid":
                Pair<UUID, ArrayList<DebugEntry>> pair = TimberNoCheat.getInstance().getDebugLogManager().getSavedEntries().get(args[1]);
                if (pair == null)
                    sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "§cDie id konnte nicht gefunden werden");
                else {
                    sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "Spieler: " + Bukkit.getOfflinePlayer(pair.getOne()).getName());
                    sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "---[Timeline]---");
                    for (DebugEntry entry : pair.getTwo()) {
                        double delay = entry.getNewVio() - entry.getOldVio();
                        sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + format.format(new Date(entry.getTime())) + " +=+ " +
                                entry.getCheck() + " *=* " +
                                (entry.getExecutions().length == 0 ? "Keine Bestafungen" : StringUtil.toString(entry.getExecutions(), ", ")) + " *=* " +
                                StringUtil.colorbyBool(delay > 0, true) + delay + " §6(New=" + entry.getNewVio() + "Old=" + entry.getOldVio() + ")" +
                                (entry.isCancel() ? " *=* (§cCanceled§6)" : "")
                        );
                    }
                    sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "---[Timeline]---");
                }
                break;
            case "listuntrackedvios":
                UUID uuid = Bukkit.getOfflinePlayer(args[1]).getUniqueId();
                ArrayList<DebugEntry> entries = TimberNoCheat.getInstance().getDebugLogManager().getDebugEntries().get(uuid);
                if(entries == null) sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "§cKeine listuntracked violatrions für den spieler!");
                else {
                    sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "Spieler: " + Bukkit.getOfflinePlayer(uuid).getName());
                    sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "---[Timeline]---");
                    for (DebugEntry entry : entries) {
                        double delay = entry.getNewVio() - entry.getOldVio();
                        sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + format.format(new Date(entry.getTime())) + " +=+ " +
                                entry.getCheck() + " *=* " +
                                (entry.getExecutions() == null || entry.getExecutions().length == 0 ? "Keine Bestafungen" : StringUtil.toString(entry.getExecutions(), ", ")) + " *=* " +
                                StringUtil.colorbyBool(delay > 0, true) + delay + " §6(New=" + entry.getNewVio() + "Old=" + entry.getOldVio() + ")" +
                                (entry.isCancel() ? " *=* (§cCanceled§6)" : "")
                        );
                    }
                    sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "---[Timeline]---");
                }
                break;
            case "credits":
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "---[Credits]---");
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "LiquidBounce: Testing Client | Hat mir viele bypasses bezeigt");
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "LaprigerToast#0882: Ein guter rl Freund | Hat (versucht :D) bypasses zu machen. Danke für alles.");
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "frozen#4757: Kennt sich soooo gut mit cheats aus | Hat mir sehr viele Ideen geben");
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "Asfold: NoCheatPlus entwickler | Viele anregende koversationen");
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "Timber Team: Für die vielen Stunden die ihr in das Testen gesteckt habe <3");
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "---[Credits]---");
                break;
            case "statistics":
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "---[Statistics]---");
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "284 Dateien (15/Aug/2018)");
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "977,5 kB reiner Code (15/Aug/2018)");
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "19957 Zeilen Code (15/Aug/2018)");
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + Bukkit.getOfflinePlayers().length + " Spieler");
                sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + "---[Statistics]---");
                break;
        }
    }
}
