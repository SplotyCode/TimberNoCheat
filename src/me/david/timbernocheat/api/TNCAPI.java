package me.david.timbernocheat.api;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.runnable.Tps;
import me.david.api.anotations.NotNull;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.logging.Level;

/**
 * The Main Api Class for other plugins that want to communicate with TNC
 * This class is just for Backwards Capability
 * @see TNCApi
 */
@Deprecated
public final class TNCAPI {

    /* Get Server Tps in a specific time or with colour*/
    @Deprecated public static double getTPS() {
        return Tps.getTPS();
    }
    @Deprecated public static double getTPS(int ticks) {
        return Tps.getTPS(ticks);
    }
    @Deprecated public static String getTPSColor(){
        return CheckManager.getInstance().getTpsColor();
    }

    /*
     * Get the Player Ping (Method 2 with colour)
     * The player may manipulate this function with a PingSpoof
     */
    @Deprecated public static int getPing(Player player){
        return CheckManager.getInstance().getping(player);
    }
    @Deprecated public static String getPingColor(Player player){
        return CheckManager.getInstance().getPingColor(player);
    }

    /*
     * Enables a Specific check!
     */
    @Deprecated
    public static void enablecheck(@NotNull Check check){
        if(check == null){
            TimberNoCheat.getInstance().getLogger().log(Level.WARNING, "[API] Wrong Api Usage: Check may noy be null!");
            return;
        }
        if(isEnabled(check)){
            TimberNoCheat.getInstance().getLogger().log(Level.WARNING, "[API] Wrong Api Usage: Check may be disabled inorder to call this function!");
            return;
        }
        CheckManager.getInstance().register(check);
    }

    /*
     * Disable a Specific check!
     */
    @Deprecated
    public static void disablecheck(@NotNull Check check){
        if(check == null){
            TimberNoCheat.getInstance().getLogger().log(Level.WARNING, "[API] Wrong Api Usage: Check may noy be null!");
            return;
        }
        if(!isEnabled(check)){
            TimberNoCheat.getInstance().getLogger().log(Level.WARNING, "[API] Wrong Api Usage: Check may be enabled inorder to call this function!");
            return;
        }
        CheckManager.getInstance().unregister(check);
    }

    @Deprecated
    public static void disablecheck(@NotNull String name){
        disablecheck(CheckManager.getInstance().getCheckByString(name));
    }

    /* Check If an Specific Check is */
    @Deprecated
    public static boolean isEnabled(@NotNull Check check){
        if(check == null){
            TimberNoCheat.getInstance().getLogger().log(Level.WARNING, "[API] Wrong Api Usage: Check darf nicht null sein");
            return false;
        }
        return isEnabled(check.getName());
    }

    @Deprecated
    public static boolean isEnabled(@NotNull String name){
        assert name != null:"Check Name might not be null";
        return CheckManager.getInstance().getCheckByString(name) != null;
    }

    /* Returns an Array with all Category's */
    @Deprecated
    public static Category[] getCategorys(){
        return Category.values();
    }

    /* Get an Check by its Name */
    @Deprecated
    public static Check getCheckbyName(String name){
        return CheckManager.getInstance().getCheckByString(name);
    }


    @Deprecated
    public static double getAllViolations(@NotNull Player player){
        return getAllViolations(player.getUniqueId());
    }

    /*
     * Returns the total violation Level
     */
    @Deprecated
    public static double getAllViolations(@NotNull UUID uuid){
        assert uuid != null:"Check Name might not be null";
        double vio = 0;
        for(Check c : CheckManager.getInstance().getChecks())
            if(c.getViolations().containsKey(uuid))
                vio += c.getViolations().get(uuid);
        return vio;
    }

    @Deprecated
    public static void whitelist(final Check check, final Player player, final long time){
        check.whitelist(player, time);
    }

    @Deprecated
    public static void clearViolcation(final Player player){
        for(Check c : CheckManager.getInstance().getChecks()){
            c.resetVio(player);
            for(Check child : c.getChildes())
                child.resetVio(player);
        }
    }
}
