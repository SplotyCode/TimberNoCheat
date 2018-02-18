package me.david.timbernocheat.listener;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.api.RefreshEvent;
import me.david.timbernocheat.checkmanager.Check;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class TNCHandler implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onReload(RefreshEvent event){
        for(Check check : TimberNoCheat.checkmanager.getChecks()){
            HandlerList.unregisterAll(check);
            check.disableListeners();
            check.disableTasks();
        }
        TimberNoCheat.checkmanager.loadchecks();
    }

}
