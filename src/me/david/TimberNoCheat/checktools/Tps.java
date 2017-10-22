package me.david.TimberNoCheat.checktools;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.config.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

public class Tps implements Runnable{

    /*
     * Class for Checking current server tps
     */
    public static int tickcount = 0;
    public static long[] ticks = new long[600];
    private boolean lowcpumode;

    public static double getTPS() {
        return getTPS(100);
    }

    public static double getTPS(int checklastticks) {
        if (tickcount < checklastticks) {
            return 20.0D;
        }
        int target = (tickcount - 1 - checklastticks) % ticks.length;
        long elapsed = System.currentTimeMillis() - ticks[target];

        return checklastticks / (elapsed / 1000.0D);
    }

    public void run() {
        ticks[(tickcount % ticks.length)] = System.currentTimeMillis();
        tickcount++;
        if(!lowcpumode && getTPS() < 16){
            TimberNoCheat.instance.permissioncache.sendAll(Permissions.NOTITY, "Alle Movement checks wurden wegen der geringen Tps deaktiviert!");
            for(Check check : TimberNoCheat.checkmanager.checks)
                if(check.getCategory() == Category.MOVEMENT)
                    HandlerList.unregisterAll(check);
        }else if(lowcpumode && getTPS() > 17){
            TimberNoCheat.instance.permissioncache.sendAll(Permissions.NOTITY, "Alle Movement checks wurden wieder aktiviert!");
            for(Check check : TimberNoCheat.checkmanager.checks)
                if(check.getCategory() == Category.MOVEMENT)
                    Bukkit.getPluginManager().registerEvents(check, TimberNoCheat.instance);
        }
    }
}
