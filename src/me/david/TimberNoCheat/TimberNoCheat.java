package me.david.TimberNoCheat;

import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.CheckManager;
import me.david.TimberNoCheat.checktools.TNCCommand;
import me.david.TimberNoCheat.checktools.Tps;
import me.david.TimberNoCheat.checktools.Velocity;
import me.david.TimberNoCheat.config.Config;
import me.david.TimberNoCheat.listener.JoinLeave;
import me.david.api.ApiPlugin;

import java.io.File;
import java.util.logging.Level;

public class TimberNoCheat extends ApiPlugin {

    public static TimberNoCheat instance;
    public static CheckManager checkmanager;
    public String prefix = "§7[§9T§cN§eC§7] §6";
    public final File config = new File(getDataFolder() + "/config.yml");
    //public old_Settings settings;
    private boolean crash = false;

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
        //settings = new old_Settings();
        checkmanager = new CheckManager();
        registerListener(new JoinLeave(), new Velocity(this));
        registerCommands(new TNCCommand());
        new Velocity(this);
        log(false, "[TimberNoCheat] Es wurden " + checkmanager.checks.size() + " module geladen mit vielen unterchecks!");
    }

    /*
     * Disable Checks
     * Remove Protocolllib Listener
     */
    @Override
    public void pluginDisable() {
        if (crash) return;
        protocolmanager.removePacketListeners(this);
        for (Check c : checkmanager.checks) c.disable();
    }
}
