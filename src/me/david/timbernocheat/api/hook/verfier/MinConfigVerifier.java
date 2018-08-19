package me.david.timbernocheat.api.hook.verfier;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.api.hook.LoadVerifier;

public class MinConfigVerifier implements LoadVerifier {

    protected int minConfigVersion;

    public MinConfigVerifier(int minConfigVersion) {
        this.minConfigVersion = minConfigVersion;
    }

    @Override
    public boolean verify(double version) {
        return TimberNoCheat.CONFIGURATION_VERSION <= minConfigVersion;
    }
}
