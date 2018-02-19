package me.david.timbernocheat.listener;

import me.david.timbernocheat.TimberNoCheat;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

/*
 * Note: In the ListenerManager only Listeners that would use static variables are linked up
 */
public class ListenerManager {

    private FreezeHandler freezeListener = new FreezeHandler();

    public ListenerManager(TimberNoCheat tnc){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(freezeListener, tnc);
    }

    public FreezeHandler getFreezeListener() {
        return freezeListener;
    }
}
