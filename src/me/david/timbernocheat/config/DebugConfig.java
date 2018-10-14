package me.david.timbernocheat.config;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.api.RefreshEvent;
import me.david.api.storage.YamlFile;
import me.david.api.storage.YamlSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.File;

public class DebugConfig implements Listener {

    private boolean discord, antiKick, warnOnSetBacks, notifyAllViolations;
    private File file;

    public DebugConfig(File file, boolean debug) {
        this.file = file;
        if (debug) onReaload(null);
    }

    @EventHandler
    public void onReaload(RefreshEvent event){
        YamlSection section = new YamlFile(file).getYamlSection("debugsettings");
        if (TimberNoCheat.getInstance().isDebug()) {
            discord = section.getBoolean("discord");
            antiKick = section.getBoolean("antikick");
            warnOnSetBacks = section.getBoolean("warnonsetback");
            notifyAllViolations = section.getBoolean("notifyallviolations");
        } else {
            discord = antiKick = warnOnSetBacks = notifyAllViolations = false;
        }

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

    public boolean isNotifyAllViolations() {
        return notifyAllViolations;
    }
}
