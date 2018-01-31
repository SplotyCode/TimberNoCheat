package me.david.TimberNoCheat;

import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.CheckManager;
import me.david.TimberNoCheat.checktools.*;
import me.david.TimberNoCheat.config.Config;
import me.david.TimberNoCheat.config.Permissions;
import me.david.TimberNoCheat.gui.GuiLoader;
import me.david.TimberNoCheat.listener.JoinLeave;
import me.david.TimberNoCheat.listener.TNCHandler;
import me.david.TimberNoCheat.profiler.MoveProfiler;
import me.david.TimberNoCheat.record.RecordManager;
import me.david.api.ApiPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.logging.Level;

public class TimberNoCheat extends ApiPlugin {

    public static TimberNoCheat instance;
    /* Handles Checks, PlayerData and the Violation Message Output */
    public static CheckManager checkmanager;
    /* The Location of the TNC Config File normally plugins/TimberNoCheat/config.yml */
    public final File config = new File(getDataFolder() + "/config.yml");
    public final File speedpatterns = new File(getDataFolder() + "/speed_pattern.yml");
    /* Does the plugin stops Because of a crash for example old config or capability problem with ProtocolLib */
    private boolean crash = false;
    /* Handles Records and Replays */
    private RecordManager recordManager;
    /* Should we clear the PlayerData when the Player Logs out */
    private boolean clearPlayerData = true;

    private MoveProfiler moveprofiler;


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
    public void pluginEnable() {
        instance = this;
        if (!startprotocollib()) {
            getLogger().log(Level.WARNING, "ProtocollLib konnte nicht gefunden wurde!");
            crash = true;
            setEnabled(false);
            return;
        }
        startpermissionchache(true, -1, true);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Tps(), 100, 1);
        if (Config.check(config, 201, this.getResource("me/david/TimberNoCheat/config/config.yml"))) {
            crash = true;
            setEnabled(false);
            return;
        }
        moveprofiler = new MoveProfiler();
        //settings = new old_Settings();
        checkmanager = new CheckManager();
        recordManager = new RecordManager(config);
        registerListener(new JoinLeave(), new Velocity(this), new FalsePositive(), new TNCHandler(), new General());
        registerCommands(new TNCCommand(), new TestCommand());
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
}
