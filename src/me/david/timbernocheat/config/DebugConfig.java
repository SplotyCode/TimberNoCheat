package me.david.timbernocheat.config;

import me.david.timbernocheat.api.RefreshEvent;
import me.david.timbernocheat.storage.YamlFile;
import me.david.timbernocheat.storage.YamlSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.File;

public class DebugConfig implements Listener {

    private boolean discord, antiKick, warnOnSetBacks;
    private File file;

    public DebugConfig(File file, boolean debug) {
        this.file = file;
        if(debug) onReaload(null);
    }

    @EventHandler
    public void onReaload(RefreshEvent event){
        YamlSection section = new YamlFile(file).getYamlSection("debugsettings");
        discord = section.getBoolean("discord");
        antiKick = section.getBoolean("antikick");
        warnOnSetBacks = section.getBoolean("warnonsetback");
    }

    public boolean isDiscord() {
        return discord;
    }

    public boolean isAntiKick() {
        return antiKick;
    }

    public boolean isWarnOnSetBacks() {
        return warnOnSetBacks;
    }
}
