package me.david.TimberNoCheat.record;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/*
 * Configuration about Recording
 */
public class ConfigRecord {

    private File file;

    private boolean enable;
    private int minvio;
    private int maxleanght;

    ConfigRecord(File file){
        this.file = file;
        refresh();
    }

    public void refresh(){
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        enable = yml.getBoolean("generel.record.enable");
        minvio = yml.getInt("generel.record.minvio");
        maxleanght = yml.getInt("generel.record.maxleanght");
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
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
