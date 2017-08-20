package me.david.TimberNoCheat;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.google.common.io.ByteStreams;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.CheckManager;
import me.david.TimberNoCheat.checktools.TNCCommand;
import me.david.TimberNoCheat.checktools.Velocity;
import me.david.TimberNoCheat.config.Settings;
import me.david.TimberNoCheat.checktools.Tps;
import me.david.TimberNoCheat.listener.JoinLeave;
import me.david.api.commands.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.logging.Level;

public class TimberNoCheat extends JavaPlugin{

    public static TimberNoCheat instance;
    public static CheckManager checkmanager;
    public String prefix = "§7[§9T§cN§eC§7] §6";
    public final File config = new File(getDataFolder() + "/config.yml");
    public final int configversion = 8;
    public int curconfigversion = -1;
    public Settings settings;
    public ProtocolManager protocolmanager;
    public boolean crash = false;


    @Override
    public void onEnable() {
        protocolmanager = ProtocolLibrary.getProtocolManager();
        if (this.protocolmanager == null) {
            getLogger().log(Level.WARNING, "ProtocollLib konnte nicht gefunden wurde!");
            crash = true;
            setEnabled(false);
            return;
        }
        if(!config.exists()){
            getLogger().log(Level.INFO, "Creating Config File!");
            config.getParentFile().mkdirs();
            File folder = this.getDataFolder();
            if (!folder.exists())
                folder.mkdir();
            File resourceFile = new File(folder, "config.yml");

            try {
                if (!resourceFile.exists()) {
                    resourceFile.createNewFile();
                    try (InputStream in = this.getResource("me/david/TimberNoCheat/config/config.yml");
                         OutputStream out = new FileOutputStream(resourceFile)) {
                        ByteStreams.copy(in, out);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Tps(), 100, 1);
        instance = this;
        settings = new Settings();
        if(curconfigversion < configversion){
            getLogger().log(Level.WARNING, "Die Config ist auf einer alten Version! Bitte löschen!");
            getLogger().log(Level.WARNING, "Plugin wird disabled...");
            crash = true;
            setEnabled(false);
            return;
        }
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
