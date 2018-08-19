package me.david.timbernocheat.api.hook.verfier;

import me.david.timbernocheat.api.hook.LoadVerifier;

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
