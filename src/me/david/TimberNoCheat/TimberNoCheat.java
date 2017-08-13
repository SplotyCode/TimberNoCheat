package me.david.TimberNoCheat;

import me.david.TimberNoCheat.CheckManager.CheckManager;
import me.david.TimberNoCheat.CheckManager.Settings;
import me.david.TimberNoCheat.checktools.Tps;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class TimberNoCheat extends JavaPlugin{

    public static TimberNoCheat instance;
    public static CheckManager checkmanager;
    public String prefix = "§7[§9T§cN§eC§7] §6";
    public File config = new File(getDataFolder() + "/config.yml");
    public Settings settings;

    @Override
    public void onEnable() {
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Tps(), 100, 1);
        instance = this;
        settings = new Settings();
        checkmanager = new CheckManager();
    }
}
