package me.david.timbernocheat.api;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import me.david.timbernocheat.runnable.Tps;
import me.david.api.anotations.NotNull;
import org.bukkit.entity.Player;

import java.util.logging.Level;

/* The Main Api Class for other plugins that want to communicate with TNC */
public final class TNCAPI {

    /* Get Server Tps in a specific time or with colour*/
    public static double getTPS() {
        return Tps.getTPS();
    }
    public static double getTPS(int ticks) {
        return Tps.getTPS(ticks);
    }
    public static String getTPSColor(){
        return TimberNoCheat.checkmanager.gettpscolor();
    }

    /*
     * Get the Player Ping (Method 2 with colour)
     * The player may manipulate this function with a PingSpoof
     */
    public static int getPing(Player player){
        return TimberNoCheat.checkmanager.getping(player);
    }
    public static String getPingColor(Player player){
        return TimberNoCheat.checkmanager.getpingcolor(player);
    }

    /*
     * Enables a Specific check!
     */
    public static void enablecheck(@NotNull Check check){
        if(check == null){
            TimberNoCheat.instance.getLogger().log(Level.WARNING, "[API] Wrong Api Usage: Check may noy be null!");
            return;
        }
        if(isEnabled(check)){
            TimberNoCheat.instance.getLogger().log(Level.WARNING, "[API] Wrong Api Usage: Check may be disabled inorder to call this function!");
            return;
        }
        TimberNoCheat.checkmanager.register(check);
    }

    /*
     * Disable a Specific check!
     */
    public static void disablecheck(@NotNull Check check){
        if(check == null){
            TimberNoCheat.instance.getLogger().log(Level.WARNING, "[API] Wrong Api Usage: Check may noy be null!");
            return;
        }
        if(!isEnabled(check)){
            TimberNoCheat.instance.getLogger().log(Level.WARNING, "[API] Wrong Api Usage: Check may be enabled inorder to call this function!");
            return;
        }
        TimberNoCheat.checkmanager.unregister(check);
    }

    public static void disablecheck(@NotNull String name){
        disablecheck(TimberNoCheat.checkmanager.getCheckbyString(name));
    }

    /* Check If an Specific Check is */
    public static boolean isEnabled(@NotNull Check check){
        if(check == null){
            TimberNoCheat.instance.getLogger().log(Level.WARNING, "[API] Wrong Api Usage: Check darf nicht null sein");
            return false;
        }
        return isEnabled(check.getName());
    }

    public static boolean isEnabled(@NotNull String name){
        assert name != null:"Check Name might not be null";
        return TimberNoCheat.checkmanager.getCheckbyString(name) != null;
    }

    /* Returns an Array with all Category's */
    public static Category[] getCategorys(){
        return Category.values();
    }

    /* Get an Check by its Name */
    public static Check getCheckbyName(String name){
        return TimberNoCheat.checkmanager.getCheckbyString(name);
    }

    /*
     * Returns the total violation Level
     */
    public static double getAllViolations(@NotNull Player player){
        assert player != null:"Check Name might not be null";
        double vio = 0;
        for(Check c : TimberNoCheat.checkmanager.getChecks())
            if(c.getViolations().containsKey(player))
                vio += c.getViolations().get(player);
        return vio;
    }

    public static void whitelist(final Check check, final Player player, final long time){
        check.whitelist(player, time);
    }

    public static void clearViolcation(final Player player){
        for(Check c : TimberNoCheat.checkmanager.getChecks()){
            c.resetVio(player);
            for(Check child : c.getChilds())
                child.resetVio(player);
        }
    }
}
