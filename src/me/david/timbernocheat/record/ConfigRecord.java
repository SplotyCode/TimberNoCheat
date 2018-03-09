package me.david.timbernocheat.record;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.storage.YamlFile;
import me.david.timbernocheat.storage.YamlSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/*
 * Configuration about Recording
 */
public class ConfigRecord {

    private final YamlSection configSection;

    private boolean enable;
    private int minvio;
    private int maxleanght;

    ConfigRecord(){
        configSection = TimberNoCheat.getInstance().getConfigFile().getYamlSection("generel.record");
        refresh();
    }

    public void refresh(){
        enable = configSection.getBoolean("enable");
        minvio = configSection.getInt("minvio");
        maxleanght = configSection.getInt("maxleanght");
    }

    public YamlSection getConfigSection() {
        return configSection;
    }

    public boolean isEnable() {
        return enable;
    }

    public int getMinvio() {
        return minvio;
    }

    public int getMaxleanght() {
        return maxleanght;
    }
}
