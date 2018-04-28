package me.david.timbernocheat.listener;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.api.RefreshEvent;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class TNCHandler implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onReload(RefreshEvent event){
        for(Check check : CheckManager.getInstance().getChecks()){
            HandlerList.unregisterAll(check);
            check.disableListeners();
            check.disableTasks();
        }
        CheckManager.getInstance().loadChecks();
    }

}
