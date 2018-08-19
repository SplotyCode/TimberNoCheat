package me.david.timbernocheat.api.hook.verfier;

import me.david.timbernocheat.api.hook.LoadVerifier;

public class MinVersionVerifier implements LoadVerifier {

    protected double minVersion;

    public MinVersionVerifier(double minVersion) {
        this.minVersion = minVersion;
    }

    @Override
    public boolean verify(double version) {
        return version >= minVersion;
    }
}
