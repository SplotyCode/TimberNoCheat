package me.david.timbernocheat;

import me.david.timbernocheat.checkmanager.Check;
import me.david.timbernocheat.checkmanager.CheckManager;
import me.david.timbernocheat.checktools.*;
import me.david.timbernocheat.command.TNCCommand;
import me.david.timbernocheat.command.blocktrigger.TriggerBlockManager;
import me.david.timbernocheat.command.oreNotify.OreNotifyManager;
import me.david.timbernocheat.config.Config;
import me.david.timbernocheat.config.Permissions;
import me.david.timbernocheat.debug.Debuger;
import me.david.timbernocheat.debug.obj.DebugPermissionCache;
import me.david.timbernocheat.gui.GuiLoader;
import me.david.timbernocheat.listener.ChatHandler;
import me.david.timbernocheat.listener.JoinLeave;
import me.david.timbernocheat.listener.OreNotify;
import me.david.timbernocheat.listener.TNCHandler;
import me.david.timbernocheat.debug.MoveProfiler;
import me.david.timbernocheat.record.RecordManager;
import me.david.timbernocheat.runnable.Tps;
import me.david.timbernocheat.runnable.Velocity;
import me.david.api.ApiPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class TimberNoCheat extends ApiPlugin {

    public static TimberNoCheat instance;

    /* Handles Checks, PlayerData and the Violation Message Output */
    public static CheckManager checkmanager;

    /* The Location of the TNC Config File normally plugins/TimberNoCheat/config.yml */
    public final File config = new File(getDataFolder() + "/config.yml");
    public final File speedpatterns = new File(getDataFolder() + "/speed_pattern.yml");
    private final File triggerBlocks = new File(getDataFolder() + "/triggerBlocks.yml");

    /* Does the plugin stops Because of a crash for example old config or capability problem with ProtocolLib */
    private boolean crash = false;
    /* Handles Records and Replays */
    private RecordManager recordManager;
    /* Should we clear the PlayerData when the Player Logs out */
    private boolean clearPlayerData = true;

    /* Debug stuff */
    private MoveProfiler moveprofiler;
    private Debuger debuger;

    private OreNotifyManager oreNotifyManager;
    private TriggerBlockManager triggerBlockManager;


    /* Default Prefix normaly this prefix gets overridden from the config */
    @Override
    public void pluginLoad() {
        prefix = "§7[§9T§cN§eC§7] §6";
    }

    /*
     * Init ProtocollLib
     * Starting TPS and Velocity Scheduler
     * Check and load Config
     * Register Commands and Listener
     * Enable Checks
     */
    @Override
    public void pluginEnable() throws IOException {
        instance = this;
        if (!startprotocollib()) {
            getLogger().log(Level.WARNING, "ProtocollLib konnte nicht gefunden wurde!");
            crash = true;
            setEnabled(false);
            return;
        }
        /* would work but we want our special debug permission cache*/ //startpermissionchache(true, -1, true);
        permissioncache = new DebugPermissionCache(true, -1, true);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Tps(), 100, 1);
        if (Config.check(config, 201, this.getResource("me/david/timbernocheat/config/config.yml"))) {
            crash = true;
            setEnabled(false);
            return;
        }
        moveprofiler = new MoveProfiler();
        debuger = new Debuger();

        //settings = new old_Settings();
        checkmanager = new CheckManager();
        recordManager = new RecordManager(config);
        triggerBlockManager = new TriggerBlockManager(this, triggerBlocks);
        oreNotifyManager = new OreNotifyManager();
        registerListener(new JoinLeave(), new Velocity(this), new FalsePositive(), new TNCHandler(), new General(), new ChatHandler(), new OreNotify());
        registerCommands(new TNCCommand()/*, new TestCommand()*/);
        clearPlayerData = YamlConfiguration.loadConfiguration(config).getBoolean("clearPlayerData");
        log(false, "Es wurden " + checkmanager.getChecks().size() + " module geladen mit vielen unterchecks!");
        new GuiLoader(this);
    }

    /*
     * Disable Checks
     * Remove Protocolllib Listener
     * Stop Recordings
     */
    @Override
    public void pluginDisable() {
        if (crash) return;
        protocolmanager.removePacketListeners(this);
        for (Check c : checkmanager.getChecks()) c.disable();
        recordManager.stopAll();
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

    public Debuger getDebuger() {
        return debuger;
    }

    public OreNotifyManager getOreNotifyManager() {
        return oreNotifyManager;
    }

    public TriggerBlockManager getTriggerBlockManager() {
        return triggerBlockManager;
    }
}