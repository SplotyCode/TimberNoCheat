package me.david.TimberNoCheat;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.CheckManager;
import me.david.TimberNoCheat.checktools.TNCCommand;
import me.david.TimberNoCheat.checktools.Velocity;
import me.david.TimberNoCheat.config.Config;
import me.david.TimberNoCheat.config.old_Settings;
import me.david.TimberNoCheat.checktools.Tps;
import me.david.TimberNoCheat.listener.JoinLeave;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.logging.Level;

public class TimberNoCheat extends JavaPlugin{

    public static TimberNoCheat instance;
    public static CheckManager checkmanager;
    public String prefix = "§7[§9T§cN§eC§7] §6";
    public final File config = new File(getDataFolder() + "/config.yml");
    public old_Settings settings;
    public ProtocolManager protocolmanager;
    public boolean crash = false;

    @Override
    public void onEnable() {
        protocolmanager = ProtocolLibrary.getProtocolManager();
        instance = this;
        if (this.protocolmanager == null) {
            getLogger().log(Level.WARNING, "ProtocollLib konnte nicht gefunden wurde!");
            crash = true;
            setEnabled(false);
            return;
        }
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Tps(), 100, 1);
        if(Config.check(config, 201, this.getResource("me/david/TimberNoCheat/config/config.yml"))){
            crash = true;
            setEnabled(false);
            return;
        }
        settings = new old_Settings();
        checkmanager = new CheckManager();
        getServer().getPluginManager().registerEvents(new JoinLeave(), this);
        getServer().getPluginManager().registerEvents(new Velocity(), this);
        CommandManager.commands.add(new TNCCommand());
        getLogger().log(Level.INFO, "Es wurden " + checkmanager.checks.size() + " module geladen mit vielen unterchecks!");
    }

    @Override
    public void onDisable() {
        if(crash){
            return;
        }
        TimberNoCheat.instance.protocolmanager.removePacketListeners(this);
        for(Check c : checkmanager.checks){
            c.disable();
        }
    }
}
