package me.david.timbernocheat.api;

import me.david.api.anotations.NotNull;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.runnable.Tps;
import me.david.timbernocheat.startup.StartState;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.logging.Level;

/* The Main Api Class for other plugins that want to communicate with TNC */
public enum TNCApi {

    INSTANCE;

    /* Get Server Tps in a specific time or with colour*/
    public  double getTPS() {
        return Tps.getTPS();
    }
    public  double getTPS(int ticks) {
        return Tps.getTPS(ticks);
    }
    public  String getTPSColor(){
        checkIfReady();
        return CheckManager.getInstance().getTpsColor();
    }

    /*
     * Get the Player Ping (Method 2 with colour)
     * The player may manipulate this function with a PingSpoof
     */
    public  int getPing(Player player){
        checkIfReady();
        return CheckManager.getInstance().getping(player);
    }
    public  String getPingColor(Player player){
        checkIfReady();
        return CheckManager.getInstance().getPingColor(player);
    }

    /*
     * Enables a Specific check!
     */
    public  void enableCheck(@NotNull Check check){
        checkIfReady();
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
    public  void disableCheck(@NotNull Check check){
        checkIfReady();
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

    public  void disableCheck(@NotNull String name){
        checkIfReady();
        disableCheck(CheckManager.getInstance().getCheckByString(name));
    }

    /* Check If an Specific Check is */
    public  boolean isEnabled(@NotNull Check check){
        checkIfReady();
        if(check == null){
            TimberNoCheat.getInstance().getLogger().log(Level.WARNING, "[API] Wrong Api Usage: Check darf nicht null sein");
            return false;
        }
        return isEnabled(check.getName());
    }

    public  boolean isEnabled(@NotNull String name){
        checkIfReady();
        assert name != null:"Check Name might not be null";
        return CheckManager.getInstance().getCheckByString(name) != null;
    }

    /* Returns an Array with all Category's */
    public  Category[] getCategories(){
        checkIfReady();
        return Category.values();
    }

    /* Get an Check by its Name */
    public  Check getCheckByName(String name){
        checkIfReady();
        return CheckManager.getInstance().getCheckByString(name);
    }


    /*
     * Returns the total violation Level
     */
    public  double getAllViolations(@NotNull UUID uuid){
        checkIfReady();
        assert uuid != null:"Check Name might not be null";
        double vio = 0;
        for(Check c : CheckManager.getInstance().getChecks())
            if(c.getViolations().containsKey(uuid))
                vio += c.getViolations().get(uuid);
        return vio;
    }

    public  double getAllViolations(@NotNull Player player){
        checkIfReady();
        return getAllViolations(player.getUniqueId());
    }

    /*
     * Whitelist a Player for a check for a specific amount of Time(in Milliseconds)
     * TimberNoCheat will cancel all Violations for the Player in this check
     * There will not be an ViolationUpdateEvent when the Player is whitelisted
     */
    public  void whitelist(final Check check, final Player player, final long time){
        checkIfReady();
        check.whitelist(player, time);
    }

    /*
     * Clears all Violations for all Checks from a Player
     */
    public void clearViolation(final Player player){
        checkIfReady();
        for(Check c : CheckManager.getInstance().getChecks()){
            c.resetVio(player);
            for(Check child : c.getChilds())
                child.resetVio(player);
        }
    }

    protected void checkIfReady(){
        if(TimberNoCheat.getInstance().getStartState() != StartState.RUNNING) throw new ApiNotLoadedExeption();
    }
}
