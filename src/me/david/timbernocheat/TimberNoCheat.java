package me.david.timbernocheat;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import me.david.api.anotations.NotNull;
import me.david.timbernocheat.api.ApiNotLoadedExeption;
import me.david.timbernocheat.api.TNCApi;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checktools.*;
import me.david.timbernocheat.command.TNCCommand;
import me.david.timbernocheat.command.blocktrigger.TriggerBlockManager;
import me.david.timbernocheat.command.oreNotify.OreNotifyManager;
import me.david.timbernocheat.config.Permissions;
import me.david.timbernocheat.debug.Debugger;
import me.david.timbernocheat.debug.SchedulerProfiler;
import me.david.timbernocheat.debug.obj.DebugPermissionCache;
import me.david.timbernocheat.gui.GuiLoader;
import me.david.timbernocheat.listener.*;
import me.david.timbernocheat.debug.MoveProfiler;
import me.david.timbernocheat.record.RecordManager;
import me.david.timbernocheat.runnable.Tps;
import me.david.timbernocheat.runnable.Velocity;
import me.david.api.ApiPlugin;
import me.david.timbernocheat.startup.StageHelper;
import me.david.timbernocheat.startup.StartState;
import me.david.timbernocheat.startup.StartUpHelper;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.logging.Level;

public class TimberNoCheat extends ApiPlugin {

    public static final int CONFIGURATION_VERSION = 201;

    private static TimberNoCheat instance;

    /* Handles Checks, PlayerData and the Violation Message Output */
    private static CheckManager checkManager;

    /* The Location of the TNC Config File normally plugins/TimberNoCheat/config.yml */
    private final File config = new File(getDataFolder() + "/config.yml");
    private final File speedPatterns = new File(getDataFolder() + "/speed_pattern.yml");
    private final File triggerBlocks = new File(getDataFolder() + "/triggerBlocks.yml");

    /* Does the plugin stops Because of a crash for example old config or capability problem with ProtocolLib */
    private boolean crash = false;
    /* Handles Records and Replays */
    private RecordManager recordManager;
    /* Should we clear the PlayerData when the Player Logs out */
    private boolean clearPlayerData = true;

    private ListenerManager listenerManager;

    /* Debug stuff */
    private MoveProfiler moveprofiler;
    private Debugger debugger;
    private SchedulerProfiler schedulerProfiler;


    private OreNotifyManager oreNotifyManager;
    private TriggerBlockManager triggerBlockManager;

    private Essentials essentials;

    private StageHelper stageHelper = new StageHelper();


    /* Default Prefix normally this prefix gets overridden from the config */
    @Override
    public void pluginLoad() {
        prefix = "§7[§9T§cN§eC§7] §6";
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
        StartUpHelper startHelper = new StartUpHelper(() -> {
            crash = true;
            setEnabled(false);
        });
        startHelper.loadProtocolLib();
        startHelper.loadConfiguration();
        clearPlayerData = YamlConfiguration.loadConfiguration(config).getBoolean("clearPlayerData");

        setStartState(StartState.START_OTHER);
        /* would work but we want our special debug permission cache*/ //startpermissionchache(true, -1, true);
        permissioncache = new DebugPermissionCache(true, -1, true);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Tps(), 100, 1);

        setStartState(StartState.START_DEBUGGING);
        moveprofiler = new MoveProfiler();
        debugger = new Debugger();
        schedulerProfiler = new SchedulerProfiler();

        essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        if(essentials == null) log(false, "§cEssentials konnte nicht unter dem Namen 'Essentials' gefunden werden... Ein paar Features werden nicht funktionieren...");

        checkManager = new CheckManager();
        recordManager = new RecordManager(config);
        triggerBlockManager = new TriggerBlockManager(this, triggerBlocks);
        oreNotifyManager = new OreNotifyManager();

        setStartState(StartState.START_LISTENER_AND_COMMANDS);
        listenerManager = new ListenerManager(this);
        registerListener(new JoinLeave(), new Velocity(this), new FalsePositive(), new TNCHandler(), new General(), new ChatHandler(), new OreNotify());
        registerCommands(new TNCCommand()/*, new TestCommand()*/);

        setStartState(StartState.START_GUIS);
        new GuiLoader(this);

        setStartState(StartState.RUNNING);
        log(false, "Es wurden " + checkManager.getChecks().size() + " module geladen mit vielen unterchecks!");
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
            getLogger().info("Plugin has dialled because of an planed error!");
            return;
        }
        getProtocolmanager().removePacketListeners(this);
        for (Check c : checkManager.getChecks()) c.disable();
        setStartState(StartState.STOP_RECORDINGS);
        recordManager.stopAll();
        setStartState(StartState.STOPPED);
    }

    /*
     * Function is used to call Player with Notify Permission and the Conole
     * Permission check is with the standard permissionCache from my API
     * TODO: Add multiple modes for console log(example error, warning, info etc.)
     */
    public void notify(String message){
        permissioncache.sendAll(Permissions.NOTITY, prefix + message);
        getLogger().info(message);
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

    public void executeEssentials(final Player player, Consumer<User> runable){
        if(essentials == null) player.sendMessage(TimberNoCheat.getInstance().prefix + "Es gab ein Fehler dar ein Plugin nicht gefunden/abgestürtzt ist... Du kannst diesen Fehler gerne melden!");
        else runable.accept(essentials.getUser(player));
    }

    public File getConfigFile() {
        return config;
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

    public static void log(Level level, String message){
        TimberNoCheat.getInstance().getLogger().log(level, message);
    }
    public static CheckManager getCheckManager() {
        return checkManager;
    }

    public File getSpeedPatterns() {
        return speedPatterns;
    }
}
