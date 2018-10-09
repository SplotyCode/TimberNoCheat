package me.david.timbernocheat.api.hook.verfier;

import me.david.timbernocheat.api.hook.LoadVerifier;

/**
 * Use this Verifier if your hook needs a maximum TimberNoCheat Version
 */
public class MaxVersionVerifier implements LoadVerifier {

    protected double maxVersion;

    public MaxVersionVerifier(double maxVersion) {
        this.maxVersion = maxVersion;
    }

    @Override
    public boolean verify(double version) {
        return version <= maxVersion;
    }

}
