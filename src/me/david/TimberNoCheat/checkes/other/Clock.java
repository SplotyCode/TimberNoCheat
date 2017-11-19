package me.david.TimberNoCheat.checkes.other;

import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockRedstoneEvent;

import java.util.HashMap;

public class Clock extends Check {

    private HashMap<Location, Data> values = new HashMap<Location, Data>();

    private int maxtoggles;
    private boolean notify;
    private boolean cancel;
    private HashMap<Location, Long> blacklist = new HashMap<Location, Long>();

    public Clock() {
        super("Clock", Category.OTHER);
        maxtoggles = getInt("maxtoggles");
        notify = getBoolean("notify");
        cancel = getBoolean("cancel");
    }

    @EventHandler
    public void onRedstone(BlockRedstoneEvent event){
        final Location loc = event.getBlock().getLocation();
        if(blacklist.containsKey(loc)){
            if(System.currentTimeMillis()-blacklist.get(loc) > 100000){
                blacklist.remove(loc);
            }
            event.setNewCurrent(0);
        }
        if(!values.containsKey(loc)) values.put(loc, new Data());
        if(System.currentTimeMillis()-values.get(loc).start > 10000){
            values.remove(loc);
            return;
        }
        values.get(loc).toggles++;
        if(values.get(loc).toggles == maxtoggles){
            if(cancel) blacklist.put(loc, System.currentTimeMillis());
            /* TODO: ADD NOFIFY */if(notify);
        }

    }

    private class Data {
        private long start;
        private int toggles;

        private Data(){
            this.start = System.currentTimeMillis();
            this.toggles = 0;
        }
    }


}
