package me.david.TimberNoCheat.config;

import com.google.common.io.ByteStreams;
import me.david.TimberNoCheat.TimberNoCheat;
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
            TimberNoCheat.instance.getLogger().log(Level.INFO, "Creating Config File!");
            f.getParentFile().mkdirs();
            File folder = TimberNoCheat.instance.getDataFolder();
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
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(f);
        if(yml.getInt("generel.version") < version){
            TimberNoCheat.instance.getLogger().log(Level.WARNING, "Die Config ist auf einer alten Version! Bitte löschen!");
            TimberNoCheat.instance.getLogger().log(Level.WARNING, "Plugin wird disabled...");
            return true;
        }
        if(yml.getString("generel.prefix") != null && !yml.getString("generel.prefix").equals("") && !yml.getString("generel.prefix").equals(" "))
            TimberNoCheat.instance.prefix = ChatColor.translateAlternateColorCodes('&', yml.getString("generel.prefix"));
        return false;
    }
}
