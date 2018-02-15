package me.david.TimberNoCheat.api;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.runnable.Tps;
import me.david.api.anotations.Incompleat;
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
    public static int getPing(Player p){
        return TimberNoCheat.checkmanager.getping(p);
    }
    public static String getPingColor(Player p){
        return TimberNoCheat.checkmanager.getpingcolor(p);
    }

    /*
     * Enables a Specific check!
     * TODO: Add Bukkit Tasks and ProtocolLib Listeners
     */
    @Incompleat
    public static void enablecheck(@NotNull Check c){
        if(c == null){
            TimberNoCheat.instance.getLogger().log(Level.WARNING, "[API] Wrong Api Usage: Check may noy be null!");
            return;
        }
        if(isEnabled(c)){
            TimberNoCheat.instance.getLogger().log(Level.WARNING, "[API] Wrong Api Usage: Check may be disabled inorder to call this function!");
            return;
        }
        TimberNoCheat.checkmanager.register(c);
    }

    /*
     * Disable a Specific check!
     * TODO: Remove Bukkit Tasks and ProtocolLib Listeners
     */
    @Incompleat
    public static void disablecheck(@NotNull Check c){
        if(c == null){
            TimberNoCheat.instance.getLogger().log(Level.WARNING, "[API] Wrong Api Usage: Check may noy be null!");
            return;
        }
        if(!isEnabled(c)){
            TimberNoCheat.instance.getLogger().log(Level.WARNING, "[API] Wrong Api Usage: Check may be enabled inorder to call this function!");
            return;
        }
        TimberNoCheat.checkmanager.unregister(c);
    }

    /* Same Think here*/
    @Incompleat
    public static void disablecheck(@NotNull String s){
        disablecheck(TimberNoCheat.checkmanager.getCheckbyName(s));
    }

    /* Check If an Specific Check is */
    public static boolean isEnabled(@NotNull Check c){
        if(c == null){
            TimberNoCheat.instance.getLogger().log(Level.WARNING, "[API] Wrong Api Usage: Check darf nicht null sein");
            return false;
        }
        return isEnabled(c.getName());
    }

    /* TODO: Do != null check */
    public static boolean isEnabled(@NotNull String c){
        return TimberNoCheat.checkmanager.getCheckbyName(c) != null;
    }

    /* Returns an Array with all Category's */
    public static Category[] getCategorys(){
        return Category.values();
    }

    /* Get an Check by its Name */
    public static Check getCheckbyName(String s){
        return TimberNoCheat.checkmanager.getCheckbyName(s);
    }

    /*
     * Returns the total violation Level
     * TODO: Do != null check
     */
    public static double getAllViolations(@NotNull Player p){
        double vio = 0;
        for(Check c : TimberNoCheat.checkmanager.getChecks())
            if(c.getViolations().containsKey(p))
                vio += c.getViolations().get(p);
        return vio;
    }

    public static void whitelist(final Check check, final Player player, final long time){
        check.whitelist(player, time);
    }
}
