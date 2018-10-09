package me.david.timbernocheat.api.hook.verfier;

import me.david.timbernocheat.api.hook.LoadVerifier;
import org.bukkit.Bukkit;

/**
 * Checks if an Plugin is loaded
 */
public class PluginLoadVerifier implements LoadVerifier {

    private String name;

    public PluginLoadVerifier(String name) {
        this.name = name;
    }

    @Override
    public boolean verify(double version) {
        return Bukkit.getPluginManager().isPluginEnabled(name);
    }

}
