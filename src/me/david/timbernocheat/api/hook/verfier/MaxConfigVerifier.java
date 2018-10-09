package me.david.timbernocheat.api.hook.verfier;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.api.hook.LoadVerifier;

/**
 * Use this Verifier if your hook needs a maximum config Version
 */
public class MaxConfigVerifier implements LoadVerifier {

    protected int maxConfigVersion;

    public MaxConfigVerifier(int maxConfigVersion) {
        this.maxConfigVersion = maxConfigVersion;
    }

    @Override
    public boolean verify(double version) {
        return TimberNoCheat.CONFIGURATION_VERSION >= maxConfigVersion;
    }
}
