package me.david.timbernocheat.listener;

import me.david.timbernocheat.TimberNoCheat;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

/*
 * Note: In the ListenerManager only Listeners that would use static variables are linked up
 */
public class ListenerManager {

    private FreezeHandler freezeListener = new FreezeHandler();
    private InteractHandler interactHandler = new InteractHandler();

    public ListenerManager(final TimberNoCheat tnc){
        final PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(freezeListener, tnc);
        pm.registerEvents(interactHandler, tnc);
    }

    public FreezeHandler getFreezeListener() {
        return freezeListener;
    }

    public InteractHandler getInteractHandler() {
        return interactHandler;
    }
}
