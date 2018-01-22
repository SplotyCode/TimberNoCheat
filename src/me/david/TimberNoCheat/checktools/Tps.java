package me.david.TimberNoCheat.checktools;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.config.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;

public class Tps implements Runnable{

    /*
     * Class for Checking current server tps
     */
    public static int tickcount = 0;
    private static long[] ticks = new long[600];
    private boolean lowcpumode = false;

    public static double getTPS() {
        return getTPS(100);
    }

    public static double getTPS(int checklastticks) {
        if (tickcount < checklastticks) return 20;
        int target = (tickcount - 1 - checklastticks) % ticks.length;
        if(target == -1) return 20;
        long elapsed = System.currentTimeMillis() - ticks[target];

        return checklastticks / (elapsed / 1000);
    }

    public void run() {
        ticks[(tickcount % ticks.length)] = System.currentTimeMillis();
        tickcount++;
        //TODO: Stop ProtocolLib listeners and bukkit shedulers
        if(!lowcpumode && getTPS() < 16){
            TimberNoCheat.instance.permissioncache.sendAll(Permissions.NOTITY, "Alle Movement checks wurden wegen der geringen Tps deaktiviert!");
            for(Check check : (ArrayList<Check>)TimberNoCheat.checkmanager.getChecks().clone())
                if(check.getCategory() == Category.MOVEMENT) {
                    HandlerList.unregisterAll(check);
                    check.disablelisteners();
                    check.disabletasks();
                }
            lowcpumode = true;
        }else if(lowcpumode && getTPS() > 17){
            TimberNoCheat.instance.permissioncache.sendAll(Permissions.NOTITY, "Alle Movement checks wurden wieder aktiviert!");
            for(Check check : TimberNoCheat.checkmanager.getChecks())
                if(check.getCategory() == Category.MOVEMENT) {
                    Bukkit.getPluginManager().registerEvents(check, TimberNoCheat.instance);
                    check.registernew();
                    check.starttasks();
                }
            lowcpumode = false;
        }
    }
}
