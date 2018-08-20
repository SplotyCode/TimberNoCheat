package me.david.timbernocheat.command;

import me.david.api.Api;
import me.david.api.commands.CheckBuilder;
import me.david.api.commands.Command;
import me.david.api.commands.checkers.Playercheck;
import me.david.api.commands.checkers.Stringcheck;
import me.david.api.objects.Pair;
import me.david.api.utils.HastebinUtil;
import me.david.api.utils.ServerWorldUtil;
import me.david.api.utils.StringUtil;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.api.RefreshEvent;
import me.david.timbernocheat.api.hook.HookManager;
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

import java.math.BigDecimal;
import java.math.MathContext;
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
                new Object[]{"credits", false, ""},
                new Object[]{"version", false, ""}).
                build(), TimberNoCheat.getInstance().getPrefix(), false, new String[]{"tnc", "ncp", "aac", "spartan", "anticheat", "cheat", "nocheat", "nocheatplus", "advancedanticheat", "ac"});
        setOnlyplayers(false);
    }

    private CommandSender sender;

    @Override
    public void execute(CommandSender sender, org.bukkit.command.Command cmd, String[] args) {
        this.sender = sender;
        Player p = sender instanceof Player?(Player) sender:null;
        if(args.length == 0){
            sendMessage("TimberNoCheat (Version: " + TimberNoCheat.getInstance().getDescription().getVersion() + ")");
            sendMessage("/tnc -help | /tnc version | /tnc credits | /tnc statistics");
            return;
        }
        switch (args[0].toLowerCase()){
            /* Config Reload */
            case "reload":
                long start = System.currentTimeMillis();
                Bukkit.getPluginManager().callEvent(new RefreshEvent(sender));
                sendMessage("Done in " + (System.currentTimeMillis()-start) + " ms!");
                break;
            /* Speed check */
            case "generate":
                if(PatternCheck.generators.contains(p.getUniqueId())){
                    PatternCheck.generators.remove(p.getUniqueId());
                    sendMessage("§cDas Anticheat 'trainiert' sich jetzt nicht mehr an dir!");
                }else {
                    PatternCheck.generators.add(p.getUniqueId());
                    sendMessage("Bewege dich um das Anticheat zu 'trainieren'!");
                    sendMessage("Du darfst jetzt keine Hacks benutzen!");
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
                sendMessage("---[CheckMap]---");
                for(Check check : CheckManager.getInstance().getChecks()){
                    sendMessage(StringUtil.colorbyBool(true) + check.getName());
                    for(Check child : check.getChildes())
                        sendMessage("    ->" + StringUtil.colorbyBool(true) + child.getName());
                    for(Check child : check.getDisabledsChilds())
                        sendMessage("    ->" + StringUtil.colorbyBool(false) + child.getName());

                }
                for(Check check : CheckManager.getInstance().getDisabledChecks()){
                    sendMessage(StringUtil.colorbyBool(false) + check.getName());
                    for(Check child : check.getChildes())
                        sendMessage("    ->" + StringUtil.colorbyBool(true) + child.getName());
                    for(Check child : check.getDisabledsChilds())
                        sendMessage("    ->" + StringUtil.colorbyBool(false) + child.getName());
                }
                for(Check check : Tps.disabledChecks){
                    sendMessage(ChatColor.YELLOW + check.getName());
                    for(Check child : check.getChildes())
                        sendMessage(ChatColor.YELLOW + child.getName());
                }
                sendMessage("---[CheckMap]---");
                break;
            case "settings":
                TimberNoCheat.getInstance().getGuiManager().startMultidefaultStage(p, "SettingsMulti");
                break;
            case "playerdata": {
                final Player target = Bukkit.getPlayer(args[1]);
                if (!CheckManager.getInstance().isvalid_create(target)) {
                    sendMessage("Es gibt keine SpielerDaten für '" + target.getName() + " :(");
                    return;
                }
                PlayerData pd = CheckManager.getInstance().getPlayerdata(target);
                sendMessage("Playerdata for " + pd.getUuid());
                sender.sendMessage(new PrettyPrint(pd, true, TimberNoCheat.getInstance().getPrefix()).prettyPrint(0));
                String hastebin = HastebinUtil.paste(new PrettyPrint(pd, false, "").prettyPrint(0));
                sendMessage("Finished! Now we will make a copy as Hastebin (as this data can get quite confusing in the chat :D ) ");
                sendMessage((hastebin == null ? "§cFehler bei Hochladen" : hastebin));
                break;
            }case "permissionCache":
                sendMessage("---[Cache]---");
                for(Map.Entry<UUID, HashMap<String, Boolean>> cache :  TimberNoCheat.getInstance().permissionCache.getCache().entrySet()){
                    sendMessage(Bukkit.getOfflinePlayer(cache.getKey()).getName());
                    for(Map.Entry<String, Boolean> permission : cache.getValue().entrySet())
                        sendMessage("    -> " + permission.getKey() + " <-> " + permission.getValue());
                }
                sendMessage("---[Cache]---");
                break;
            case "resetcache":
                TimberNoCheat.getInstance().permissionCache.clearAll();
                sendMessage("Fertig!");
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
                else sendMessage("Es gibt keine Spielerdaten für '" + target.getName() + "'!");
                break;
            case "listdebugids":
                sendMessage("---[Debug-Ids]---");
                if(TimberNoCheat.getInstance().getDebugLogManager().getSavedEntries().isEmpty())
                    sendMessage("§cKeine Debug Ids");
                for(Map.Entry<String, Pair<UUID, ArrayList<DebugEntry>>> entry : TimberNoCheat.getInstance().getDebugLogManager().getSavedEntries().entrySet())
                    sendMessage(entry.getKey() + " -> " + Bukkit.getOfflinePlayer(entry.getValue().getOne()).getName());
                sendMessage("---[Debug-Ids]---");
                break;
            case "playerdebugids": {
                sendMessage("---[Debug-Ids]---");
                UUID uuid = Bukkit.getOfflinePlayer(args[1]).getUniqueId();

                for (Map.Entry<String, Pair<UUID, ArrayList<DebugEntry>>> entry : TimberNoCheat.getInstance().getDebugLogManager().getSavedEntries().entrySet())
                    if (entry.getValue().getOne().equals(uuid))
                        sendMessage(entry.getKey() + " -> " + format.format(new Date(entry.getValue().getTwo().get(0).getTime())) + " <-> " + format.format(new Date(entry.getValue().getTwo().get(entry.getValue().getTwo().size() - 1).getTime())));
                sendMessage("---[Debug-Ids]---");
                break;
            }case "debugid":
                Pair<UUID, ArrayList<DebugEntry>> pair = TimberNoCheat.getInstance().getDebugLogManager().getSavedEntries().get(args[1]);
                if (pair == null)
                    sendMessage("§cDie id konnte nicht gefunden werden");
                else {
                    sendMessage("Spieler: " + Bukkit.getOfflinePlayer(pair.getOne()).getName());
                    sendMessage("---[Timeline]---");
                    for (DebugEntry entry : pair.getTwo()) {
                        double delay = entry.getNewVio() - entry.getOldVio();
                        sendMessage(format.format(new Date(entry.getTime())) + " +=+ " +
                                entry.getCheck() + " *=* " +
                                (entry.getExecutions().length == 0 ? "Keine Bestafungen" : StringUtil.toString(entry.getExecutions(), ", ")) + " *=* " +
                                StringUtil.colorbyBool(delay > 0, true) + delay + " §6(New=" + entry.getNewVio() + "Old=" + entry.getOldVio() + ")" +
                                (entry.isCancel() ? " *=* (§cCanceled§6)" : "")
                        );
                    }
                    sendMessage("---[Timeline]---");
                }
                break;
            case "listuntrackedvios":
                UUID uuid = Bukkit.getOfflinePlayer(args[1]).getUniqueId();
                ArrayList<DebugEntry> entries = TimberNoCheat.getInstance().getDebugLogManager().getDebugEntries().get(uuid);
                if(entries == null) sendMessage("§cKeine listuntracked violatrions für den spieler!");
                else {
                    sendMessage("Spieler: " + Bukkit.getOfflinePlayer(uuid).getName());
                    sendMessage("---[Timeline]---");
                    for (DebugEntry entry : entries) {
                        double delay = entry.getNewVio() - entry.getOldVio();
                        sendMessage(format.format(new Date(entry.getTime())) + " +=+ " +
                                entry.getCheck() + " *=* " +
                                (entry.getExecutions() == null || entry.getExecutions().length == 0 ? "Keine Bestafungen" : StringUtil.toString(entry.getExecutions(), ", ")) + " *=* " +
                                StringUtil.colorbyBool(delay > 0, true) + delay + " §6(New=" + entry.getNewVio() + "Old=" + entry.getOldVio() + ")" +
                                (entry.isCancel() ? " *=* (§cCanceled§6)" : "")
                        );
                    }
                    sendMessage("---[Timeline]---");
                }
                break;
            case "credits":
                sendMessage("---[Credits]---");
                sendMessage("LiquidBounce: Testing Client | Hat mir viele bypasses bezeigt");
                sendMessage("LaprigerToast#0882: Ein guter rl Freund | Hat (versucht :D) bypasses zu machen. Danke für alles.");
                sendMessage("frozen#4757: Kennt sich soooo gut mit cheats aus | Hat mir sehr viele Ideen geben");
                sendMessage("Asfold: NoCheatPlus entwickler | Viele anregende koversationen");
                sendMessage("Timber Team: Für die vielen Stunden die ihr in das Testen gesteckt habe <3");
                sendMessage("---[Credits]---");
                break;
            case "statistics":
                sendMessage("---[Statistics]---");
                sendMessage("284 Dateien (15/Aug/2018)");
                sendMessage("977,5 kB reiner Code (15/Aug/2018)");
                sendMessage("19957 Zeilen Code (15/Aug/2018)");
                sendMessage(Bukkit.getOfflinePlayers().length + " Spieler");
                sendMessage(CheckManager.getInstance().getRunnedChecks().divide(new BigDecimal(1000000), MathContext.DECIMAL128).toString() + "mil überprüfte aktionen");
                sendMessage("---[Statistics]---");
                break;
            case "version":
                sendMessage("---[Version]---");
                sendMessage("TimberNoCheat Version: " + TimberNoCheat.getInstance().getDescription().getVersion() + " (" + TimberNoCheat.getInstance().getVersion() + ")");
                sendMessage("Config Version: " + TimberNoCheat.CONFIGURATION_VERSION);
                sendMessage("Server Version: " + Bukkit.getVersion() + " (" + ServerWorldUtil.getMinecraftVersion() + " | " + ServerWorldUtil.getMinecraftVersionInt() + ")");
                sendMessage("Bukkit Version: " + Bukkit.getBukkitVersion());
                sendMessage("Api Version: " + Api.instance.getDescription().getVersion());
                sendMessage("NMS Version: " + Api.getNms().getClass().getName() + " | " + Api.getNms().getClass().getSimpleName());
                sendMessage("Hooks: ");
                HookManager.getInstance().getLoadedHooks().forEach(hook -> sendMessage("    §a" + hook.getDisplayName()));
                HookManager.getInstance().getDisabledHooks().forEach(hook -> sendMessage("    §c" + hook.getDisplayName()));
                sendMessage("Modules: ");
                for(Check check : CheckManager.getInstance().getChecks()){
                    sendMessage("    " + StringUtil.colorbyBool(true) + check.getName());
                    for(Check child : check.getChildes())
                        sendMessage("        ->" + StringUtil.colorbyBool(true) + child.getName());
                    for(Check child : check.getDisabledsChilds())
                        sendMessage("        ->" + StringUtil.colorbyBool(false) + child.getName());

                }
                for(Check check : CheckManager.getInstance().getDisabledChecks()){
                    sendMessage("    " + StringUtil.colorbyBool(false) + check.getName());
                    for(Check child : check.getChildes())
                        sendMessage("        ->" + StringUtil.colorbyBool(true) + child.getName());
                    for(Check child : check.getDisabledsChilds())
                        sendMessage("        ->" + StringUtil.colorbyBool(false) + child.getName());
                }
                for(Check check : Tps.disabledChecks){
                    sendMessage("    " + ChatColor.YELLOW + check.getName());
                    for(Check child : check.getChildes())
                        sendMessage("    " + ChatColor.YELLOW + child.getName());
                }
                sendMessage("---[Version]---");
                break;
        }
    }

    private void sendMessage(String message) {
        sender.sendMessage(TimberNoCheat.getInstance().getPrefix() + message);
    }
}
