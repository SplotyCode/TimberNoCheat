package me.david.timbernocheat;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import me.david.api.ApiPlugin;
import me.david.api.anotations.Plugin;
import me.david.timbernocheat.api.hook.HookManager;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checktools.AsyncGeneral;
import me.david.timbernocheat.checktools.FalsePositive;
import me.david.timbernocheat.checktools.General;
import me.david.timbernocheat.command.TNCCommand;
import me.david.timbernocheat.command.TNCInternal;
import me.david.timbernocheat.command.blocktrigger.TriggerBlockManager;
import me.david.timbernocheat.command.oreNotify.OreNotifyManager;
import me.david.timbernocheat.config.DebugConfig;
import me.david.timbernocheat.config.Permissions;
import me.david.timbernocheat.debug.Debugger;
import me.david.timbernocheat.debug.Debuggers;
import me.david.timbernocheat.debug.MoveProfiler;
import me.david.timbernocheat.debug.SchedulerProfiler;
import me.david.timbernocheat.debug.log.DebugLogManager;
import me.david.timbernocheat.debug.obj.DebugPermissionCache;
import me.david.timbernocheat.discord.DiscordManager;
import me.david.timbernocheat.event.internal.ShutdownEvent;
import me.david.timbernocheat.exeptions.ExceptionHelper;
import me.david.timbernocheat.exeptions.logging.Identifiers;
import me.david.timbernocheat.exeptions.logging.LogLevel;
import me.david.timbernocheat.gui.GuiLoader;
import me.david.timbernocheat.listener.*;
import me.david.timbernocheat.record.RecordManager;
import me.david.timbernocheat.runnable.Tps;
import me.david.timbernocheat.runnable.Velocity;
import me.david.timbernocheat.runnable.countdown.CountdownManager;
import me.david.timbernocheat.startup.StageHelper;
import me.david.timbernocheat.startup.StartState;
import me.david.timbernocheat.startup.StartUpHelper;
import me.david.api.storage.YamlFile;
import net.dv8tion.jda.core.entities.MessageEmbed;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;

@Plugin(name = "TimberNoCheat", version = "2.8")
public class TimberNoCheat extends ApiPlugin {

    public static final int CONFIGURATION_VERSION = 206;

    private static TimberNoCheat instance;

    /* The Location of the TNC Config File normally plugins/TimberNoCheat/config.yml */
    private final File configFile = new File(getDataFolder(), "config.yml");
    private YamlFile config;

    private final File speedPatterns = new File(getDataFolder(), "speed_pattern.yml");
    private final File triggerBlocks = new File(getDataFolder(), "/triggerBlocks.yml");
    private final File checksRan = new File(getDataFolder(), "/checkcount.txt");

    /* Does the plugin stops Because of a crash for example old config or capability problem with ProtocolLib */
    private boolean crash = false;

    /* Handles Records and Replays */
    private RecordManager recordManager;

    /* Should we clear the PlayerData when the Player Logs out */
    private boolean clearPlayerData = true;

    /* Debug mode aka Verbose */
    private boolean debug = false;
    private boolean forceDebug = new File(getDataFolder(), "DEBUG").exists();
    private DebugConfig debugConfig;

    private ListenerManager listenerManager;
    private CountdownManager countdownManager;

    /* Debug stuff */
    private MoveProfiler moveprofiler;
    private Debugger debugger;
    private SchedulerProfiler schedulerProfiler;
    private DebugLogManager debugLogManager;


    private OreNotifyManager oreNotifyManager;
    private TriggerBlockManager triggerBlockManager;
    private DiscordManager discordManager;

    private Essentials essentials;

    private StageHelper stageHelper = new StageHelper();
    private ExceptionHelper exceptionHelper = new ExceptionHelper();

    /* Runnable's that will be executed after the plugin is enabled */
    private Set<Runnable> onPostLoad = new HashSet<>();

    private boolean loaded = false;


    /* Default Prefix normally this prefix gets overridden from the config */
    @Override
    public void pluginLoad() {
        setPrefix("§7[§9T§cN§eC§7] §6");
    }

    /*
     * Init ProtocolLib
     * Starting TPS and Velocity Scheduler
     * Check and load Config
     * Register Commands and Listener
     * Enable Checks
     */
    @Override
    public void pluginEnable() throws IOException {
        instance = this;
        if (forceDebug) debug = true;
        stageHelper.onPluginStart();
        StartUpHelper startHelper = new StartUpHelper(() -> {
            crash = true;
            setEnabled(false);
        });
        startHelper.loadProtocolLib();
        startHelper.loadConfiguration();
        startHelper.loadOtherFiles();
        clearPlayerData = config.getBoolean("generel.clearPlayerData");
        debug = config.getBoolean("generel.debug");
        if (forceDebug) {
            TimberNoCheat.log(Level.INFO, "Debug Mode was set from " + debug + " to true because DEBUG File exists");
            debug = true;
        }
        debugConfig = new DebugConfig(configFile, debug);

        setStartState(StartState.START_OTHER);
        /* would work but we want our special debug permission cache*/ //startpermissionchache(true, -1, true);
        permissionCache = new DebugPermissionCache(true, -1, true);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Tps(), 100, 1);
        HookManager.getInstance().loadInternals();

        essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        if(essentials == null) log(false, "§cEssentials konnte nicht unter dem Namen 'Essentials' gefunden werden... Ein paar Features werden nicht funktionieren...");

        setStartState(StartState.START_DEBUGGING);
        moveprofiler = new MoveProfiler();
        debugger = new Debugger();
        schedulerProfiler = new SchedulerProfiler();
        debugLogManager = new DebugLogManager();

        setStartState(StartState.START_DISCORD);
        discordManager = new DiscordManager();
        discordManager.sendInfo("Server wird gestartet!");

        setStartState(StartState.START_CHECKS);
        CheckManager.getInstance().enableChecks();

        recordManager = new RecordManager();
        triggerBlockManager = new TriggerBlockManager(this, triggerBlocks);
        oreNotifyManager = new OreNotifyManager();

        setStartState(StartState.START_LISTENER_AND_COMMANDS);
        countdownManager = new CountdownManager();
        listenerManager = new ListenerManager(this);
        registerListener(new JoinLeave(), new Velocity(this), new FalsePositive(), new TNCHandler(), new General(), new ChatHandler(), new OreNotify());
        new AsyncGeneral();
        getCommand("tncinternal").setExecutor(TNCInternal.getInstance());
        registerCommands(new TNCCommand()/*, new TestCommand()*/);

        setStartState(StartState.START_GUIS);
        new GuiLoader(this);

        setStartState(StartState.FINISHING);
        Debuggers.checkDependencies();
        onPostLoad.forEach(Runnable::run);
        onPostLoad.clear();

        stageHelper.validate();
        setStartState(StartState.RUNNING);
        loaded = true;
        discordManager.sendInfo("Plugin wurde gestartet!");
        log(false, "Es wurden " + CheckManager.getInstance().getChecks().size() + " module geladen mit vielen unterchecks!");
    }

    /*
     * Disable Checks
     * Remove ProtocolLib Listener
     * Stop Recordings
     */
    @Override
    public void pluginDisable() {
        setStartState(StartState.STOP);
        if (crash) {
            getLogger().info("Plugin has disabled because of an planed error!");
            return;
        }
        setStartState(StartState.STOP_OTHERS);
        ShutdownEvent event = new ShutdownEvent();
        Bukkit.getPluginManager().callEvent(event);
        if (debugLogManager != null) debugLogManager.onStop(event);
        if (getProtocolManager() != null) getProtocolManager().removePacketListeners(this);
        if (CheckManager.getInstance().getRunnedChecks() != null) {
            try {
                FileUtils.writeStringToFile(checksRan, CheckManager.getInstance().getRunnedChecks().toString());
            } catch (IOException ex) {
                reportException(ex, "Could not write Checks runned to File");
            }
        }
        setStartState(StartState.DISABLE_CHECKS);
        if (CheckManager.getInstance().getChecks() == null) getLogger().log(Level.WARNING, "Fatal Error in the CheckManager it is not possible to Shutdown ANY check!");
        else {
            for (Check c : CheckManager.getInstance().getChecks())
                if (c == null)
                    getLogger().log(Level.WARNING, "Count not Shutdown a check becouse it dont even exsits! We can not tell witch check is that. (Kind of useless message)");
                else c.disable();
        }
        setStartState(StartState.STOP_RECORDINGS);
        if(recordManager == null) getLogger().log(Level.WARNING, "Fatal Error in the RecordManager it is not possible to stop ANY Record!");
        else recordManager.stopAll();
        setStartState(StartState.STOPPED);
        if (discordManager != null) discordManager.sendInfo("Server wurde gestoppt!");
    }

    /*
     * Function is used to call Player with Notify Permission and the Conole
     * Permission check is with the standard permissionCache from my API
     * TODO: Add multiple modes for console log(example error, warning, info etc.)
     */
    public void notify(String message){
        permissionCache.sendAll(Permissions.NOTITY, getPrefix() + message);
        getLogger().info(message);
    }

    public void reportException(Throwable ex, String message){
        exceptionHelper.reportException(ex, message);
    }

    public void reportException(Throwable ex, String message, DiscordManager.ErrorType type, MessageEmbed.Field... fields){
        exceptionHelper.reportException(ex, message, type, fields);
    }

    public void log(String message, LogLevel level, Identifiers identifier) {
        exceptionHelper.log(message, level, identifier);
    }

    public void log(String message, LogLevel level) {
        exceptionHelper.log(message, level);
    }

    public void executeEssentials(final Player player, Consumer<User> runable){
        if(essentials == null) player.sendMessage(TimberNoCheat.getInstance().getPrefix() + "Es gab ein Fehler dar ein Plugin nicht gefunden/abgestürtzt ist... Du kannst diesen Fehler gerne melden!");
        else runable.accept(essentials.getUser(player));
    }

    public double getVersion() {
        return Double.valueOf(getDescription().getVersion());
    }

    public String pluginDisplayName() {
        return getDescription().getName() + " v" + getDescription().getVersion() + " (Config Version: " + CONFIGURATION_VERSION + ")";
    }

    public MoveProfiler getMoveprofiler() {
        return moveprofiler;
    }

    public RecordManager getRecordManager() {
        return recordManager;
    }

    public boolean isClearPlayerData() {
        return clearPlayerData;
    }

    public Debugger getDebugger() {
        return debugger;
    }

    public OreNotifyManager getOreNotifyManager() {
        return oreNotifyManager;
    }

    public TriggerBlockManager getTriggerBlockManager() {
        return triggerBlockManager;
    }

    public ListenerManager getListenerManager() {
        return listenerManager;
    }

    public SchedulerProfiler getSchedulerProfiler() {
        return schedulerProfiler;
    }

    @Override
    public YamlFile getConfig() {
        return config;
    }

    public File getConfigFile() {
        return configFile;
    }

    public static TimberNoCheat getInstance() {
        return instance;
    }

    public void setStartState(StartState startState) {
        stageHelper.changeState(startState);
    }

    public StartState getStartState(){
        return stageHelper.getCurrentStartState();
    }

    public boolean isDebug() {
        return debug;
    }

    public static void log(Level level, String message){
        getInstance().getLogger().log(level, message);
    }

    public static void logWarningDiscord(String consoleMessage, String discordMessage) {
        log(Level.WARNING, consoleMessage);
        if (TimberNoCheat.getInstance().loaded) {
            TimberNoCheat.getInstance().getDiscordManager().sendWarning(discordMessage);
        } else {
            TimberNoCheat.getInstance().getOnPostLoad().add(() -> TimberNoCheat.getInstance().getDiscordManager().sendWarning(discordMessage));
        }
    }

    public File getSpeedPatterns() {
        return speedPatterns;
    }

    public DiscordManager getDiscordManager() {
        return discordManager;
    }

    public void setConfig(YamlFile config) {
        this.config = config;
    }

    public DebugLogManager getDebugLogManager() {
        return debugLogManager;
    }

    public DebugConfig getDebugConfig() {
        return debugConfig;
    }

    public CountdownManager getCountdownManager() {
        return countdownManager;
    }

    public File getChecksRan() {
        return checksRan;
    }

    public Set<Runnable> getOnPostLoad() {
        return onPostLoad;
    }
}
