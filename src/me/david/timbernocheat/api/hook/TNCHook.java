package me.david.timbernocheat.api.hook;

import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.Violation;
import org.bukkit.entity.Player;

public interface TNCHook {

    String getName();

    default String getDisplayName() {
        return getName() + " v" + version();
    }

    double version();

    LoadVerifier versionVerifier();

    /* null = all checks */
    Check check();

    boolean violation(Check check, Player player, double afterVio, double addedVio);

    boolean punishment(Check check, Player player, Violation.ViolationTypes type);

}
