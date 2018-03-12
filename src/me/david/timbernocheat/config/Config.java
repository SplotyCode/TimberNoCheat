package me.david.timbernocheat.config;

import com.google.common.io.ByteStreams;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.storage.YamlFile;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.logging.Level;

public class Config {

    /*
     * Create Config if not exists
     * Check config Version
     * Load Prefix
     */
    public static boolean check(File f, final int version, InputStream in){
        if(!f.exists()){
            TimberNoCheat.getInstance().getLogger().log(Level.INFO, "Creating Config File!");
            f.getParentFile().mkdirs();
            File folder = TimberNoCheat.getInstance().getDataFolder();
            if (!folder.exists())
                folder.mkdir();
            File resourceFile = new File(folder, "config.yml");

            try {
                if (!resourceFile.exists()) {
                    resourceFile.createNewFile();
                    try (OutputStream out = new FileOutputStream(resourceFile)) {
                        ByteStreams.copy(in, out);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        YamlFile yamlFile = TimberNoCheat.getInstance().getConfigFile();
        if(yamlFile.getInt("generel.version") < version){
            TimberNoCheat.getInstance().getLogger().log(Level.WARNING, "Die Config ist auf einer alten Version! Bitte löschen! (ConfigVersionInPluing=" + version + "|ConfigVersionInConfig=" + yamlFile.getInt("generel.version") + ")");
            TimberNoCheat.getInstance().getLogger().log(Level.WARNING, "Plugin wird disabled...");
            return true;
        }
        if(yamlFile.getString("generel.prefix") != null && !yamlFile.getString("generel.prefix").equals("") && !yamlFile.getString("generel.prefix").equals(" "))
            TimberNoCheat.getInstance().prefix = ChatColor.translateAlternateColorCodes('&', yamlFile.getString("generel.prefix"));
        return false;
    }
}
