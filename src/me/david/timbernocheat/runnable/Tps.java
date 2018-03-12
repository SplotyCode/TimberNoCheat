package me.david.timbernocheat.runnable;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.config.Permissions;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.util.ArrayList;

public class Tps implements Runnable{

    /*
     * Class for Checking current server tps
     */
    public static int tickCount = 0;
    private static long[] ticks = new long[600];
    private static boolean lowTpsMode = false;
    public static ArrayList<Check> disabledChecks = new ArrayList<>();

    public static double getTPS() {
        return getTPS(100);
    }

    public static double getTPS(int checkTimeRadius) {
        if (tickCount < checkTimeRadius) return 20;
        int target = (tickCount - 1 - checkTimeRadius) % ticks.length;
        if(target == -1) return 20;
        long elapsed = System.currentTimeMillis() - ticks[target];

        return checkTimeRadius / (elapsed / 1000);
    }

    public void run() {
        ticks[(tickCount % ticks.length)] = System.currentTimeMillis();
        tickCount++;
        if(!lowTpsMode && getTPS() < 16){
            TimberNoCheat.getInstance().permissioncache.sendAll(Permissions.NOTITY, "Alle Movement checks wurden wegen der geringen Tps deaktiviert!");
            for(Check check : (ArrayList<Check>)TimberNoCheat.getCheckManager().getChecks().clone())
                if(check.getCategory() == Category.MOVEMENT) {
                    TimberNoCheat.getCheckManager().unregister(check);
                    disabledChecks.add(check);
                }
            lowTpsMode = true;
            TimberNoCheat.getInstance().getDiscordManager().sendWarning("LowTps mode wurde aktiviert!" + new MessageEmbed.Field("Tps", getTPS()+"", true));
        }else if(lowTpsMode && getTPS() > 17){
            TimberNoCheat.getInstance().permissioncache.sendAll(Permissions.NOTITY, "Alle Movement checks wurden wieder aktiviert!");
            for(Check check : disabledChecks)
                TimberNoCheat.getCheckManager().register(check);
            disabledChecks.clear();
            lowTpsMode = false;
            TimberNoCheat.getInstance().getDiscordManager().sendWarning("LowTps mode wurde deaktiviert!", new MessageEmbed.Field("Tps", getTPS()+"", true));
        }
    }
}
