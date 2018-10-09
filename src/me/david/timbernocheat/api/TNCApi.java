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

/**
 * The Main Api Class for other plugins that want to communicate with TNC
 */
public enum TNCApi {

    INSTANCE;

    /**
     * Get the average Server Tick Per Seconds as a double for the last 100 ticks
     * @return current Tick per Seconds
     */
    public double getTPS() {
        return Tps.getTPS();
    }

    /**
     * Get the average Server tps of a specific time
     * @param ticks how many ticks should we consider
     * @return the Tick per Seconds
     */
    public double getTPS(int ticks) {
        return Tps.getTPS(ticks);
    }

    /**
     * Server Ticks per Second with Color
     * @return getTPS() with a Bukkit Color
     */
    public String getTPSColor(){
        checkIfReady();
        return CheckManager.getInstance().getTpsColor();
    }

    /**
     * Get the Player Ping (Method 2 with colour)
     * The player may manipulate this function with a PingSpoof
     */
    public int getPing(Player player) {
        checkIfReady();
        return CheckManager.getInstance().getping(player);
    }

    /**
     * Gets the Player ping with a Bukkit Color
     * @param player the player that you want to ping
     * @return
     */
    public String getPingColor(Player player) {
        checkIfReady();
        return CheckManager.getInstance().getPingColor(player);
    }

    /**
     * Enables a Specific check!
     * @param check The Check you want to enable
     */
    public void enableCheck(@NotNull Check check){
        checkIfReady();
        if (check == null) {
            TimberNoCheat.getInstance().getLogger().log(Level.WARNING, "[API] Wrong Api Usage: Check may noy be null!");
            return;
        }
        if (isEnabled(check)) {
            TimberNoCheat.getInstance().getLogger().log(Level.WARNING, "[API] Wrong Api Usage: Check may be disabled inorder to call this function!");
            return;
        }
        CheckManager.getInstance().register(check);
    }

    /**
     * Disable a Specific check!
     * @param check The Check you want to enable
     */
    public void disableCheck(@NotNull Check check) {
        checkIfReady();
        if (check == null) {
            TimberNoCheat.getInstance().getLogger().log(Level.WARNING, "[API] Wrong Api Usage: Check may noy be null!");
            return;
        }
        if (!isEnabled(check)) {
            TimberNoCheat.getInstance().getLogger().log(Level.WARNING, "[API] Wrong Api Usage: Check may be enabled inorder to call this function!");
            return;
        }
        CheckManager.getInstance().unregister(check);
    }

    /**
     * Disable a Specific check!
     * @param name The name of the Check you want to enable
     */
    public void disableCheck(@NotNull String name) {
        checkIfReady();
        disableCheck(CheckManager.getInstance().getCheckByString(name));
    }

    /**
     * Check if an Check is enabled
     * @param check The Check you want to know if it is
     */
    public boolean isEnabled(@NotNull Check check) {
        checkIfReady();
        if (check == null) {
            TimberNoCheat.getInstance().getLogger().log(Level.WARNING, "[API] Wrong Api Usage: Check darf nicht null sein");
            return false;
        }
        return isEnabled(check.getName());
    }

    /**
     * Check if an Check is enabled
     * @param name The name if the check you want to know if it is
     */
    public boolean isEnabled(@NotNull String name) {
        checkIfReady();
        assert name != null:"Check Name might not be null";
        return CheckManager.getInstance().getCheckByString(name) != null;
    }

    /**
     * @return an Array with all Category's
     */
    public Category[] getCategories() {
        checkIfReady();
        return Category.values();
    }

    /**
     * Get an Check by its Name
     * @param name the name of the Check
     * @return the check
     */
    public Check getCheckByName(String name) {
        checkIfReady();
        return CheckManager.getInstance().getCheckByString(name);
    }


    /**
     * Returns the total violation Level of an Player
     * @param uuid the uuid of the player
     * @return the total violation level
     */
    public double getAllViolations(@NotNull UUID uuid) {
        checkIfReady();
        assert uuid != null:"Check Name might not be null";
        double vio = 0;
        for(Check c : CheckManager.getInstance().getChecks())
            if(c.getViolations().containsKey(uuid))
                vio += c.getViolations().get(uuid);
        return vio;
    }

    /**
     * Returns the total violation Level of an Player
     * @param player the player of what you want to get the total violation level
     * @return the total violation level
     */
    public double getAllViolations(@NotNull Player player) {
        checkIfReady();
        return getAllViolations(player.getUniqueId());
    }

    /**
     * Whitelist a Player for a check for a specific amount of Time(in Milliseconds)
     * TimberNoCheat will cancel all Violations for the Player in this check
     * There will not be an ViolationUpdateEvent when the Player is whitelisted
     * @param check The check that should be whitelisted
     * @param player The player that you want to whitelist
     * @param time how long do you want to whitelist a player? (in milliseconds)
     */
    public void whitelist(final Check check, final Player player, final long time){
        checkIfReady();
        check.whitelist(player, time);
    }

    /**
     * Clears all Violations for all Checks from a Player
     * @param player the player of what you want to clear the violation
     */
    public void clearViolation(final Player player) {
        checkIfReady();
        for (Check c : CheckManager.getInstance().getChecks()) {
            c.resetVio(player);
            for (Check child : c.getChildes())
                child.resetVio(player);
        }
    }

    protected void checkIfReady() {
        if(TimberNoCheat.getInstance().getStartState() != StartState.RUNNING) throw new AdiNotLoadedException();
    }
}
