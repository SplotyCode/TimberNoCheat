package me.david.TimberNoCheat.listener;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.api.RefreshEvent;
import me.david.TimberNoCheat.checkmanager.Check;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class TNCHandler implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onReload(RefreshEvent event){
        for(Check check : TimberNoCheat.checkmanager.checks){
            HandlerList.unregisterAll(check);
            check.disablelisteners();
            check.disabletasks();
        }
        TimberNoCheat.checkmanager.checks.clear();
        TimberNoCheat.checkmanager.loadchecks();
    }
}
