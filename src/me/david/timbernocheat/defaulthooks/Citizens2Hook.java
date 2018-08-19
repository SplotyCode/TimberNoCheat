package me.david.timbernocheat.defaulthooks;

import me.david.timbernocheat.api.hook.LoadVerifier;
import me.david.timbernocheat.api.hook.TNCHook;
import me.david.timbernocheat.api.hook.verfier.PluginLoadVerifier;
import me.david.timbernocheat.api.hook.verfier.VerifierCollection;
import me.david.timbernocheat.checkbase.Check;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.entity.Player;

public class Citizens2Hook implements TNCHook {

    @Override
    public String getName() {
        return "Citizens2 Capability - (Default)";
    }

    @Override
    public double version() {
        return 1.1;
    }

    @Override
    public LoadVerifier versionVerifier() {
        return new VerifierCollection(new PluginLoadVerifier("Citizens"));
    }

    @Override
    public boolean violation(Check check, Player player, double afterVio, double addedVio) {
        return CitizensAPI.getNPCRegistry().isNPC(player);
    }
}
