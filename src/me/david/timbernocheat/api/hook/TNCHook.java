package me.david.timbernocheat.api.hook;

import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.Violation;
import org.bukkit.entity.Player;

/**
 * Hooks can be used to Fix compatibility issues with TNC
 * @see me.david.timbernocheat.defaulthooks.Citizens2Hook
 */
public interface TNCHook {

    /**
     * The name of the Hook
     */
    String getName();

    default String getDisplayName() {
        return getName() + " v" + version();
    }

    /**
     * The current Version of an Hook
     */
    double version();

    /**
     * What does the hook need to start?
     * @return the LoadVerifier that Verifies the StartUp
     */
    LoadVerifier versionVerifier();

    /**
     * The Check for violation and punishment events
     * null = all checks
     * @return The Check for violation and punishment events  null = all checks
     */
    Check check();

    /**
     * Triggers on an Violation event
     * @param check the check that triggered the Violation
     * @param player the player that triggered the violation
     * @param afterVio the violation that the player has after the violation
     * @param addedVio the amount of violation that has been added to the violation level
     * @return should we cancel the violation
     */
    boolean violation(Check check, Player player, double afterVio, double addedVio);

    /**
     * Triggers on an Player Punishment
     * @param check the Check that caused that Punishment
     * @param player the Player that caused the Punishment
     * @param type the type of the punishment
     * @return should we cancel the punishment
     */
    boolean punishment(Check check, Player player, Violation.ViolationTypes type);

}
