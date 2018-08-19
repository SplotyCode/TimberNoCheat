package me.david.timbernocheat.api.hook;

import me.david.timbernocheat.checkbase.Check;
import org.bukkit.entity.Player;

public interface TNCHook {

    String getName();

    default String getDisplayName() {
        return getName() + " v" + version();
    }

    double version();

    LoadVerifier versionVerifier();

    boolean violation(Check check, Player player, double afterVio, double addedVio);

}
