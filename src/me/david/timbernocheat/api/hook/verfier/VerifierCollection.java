package me.david.timbernocheat.api.hook.verfier;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.api.hook.LoadVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VerifierCollection extends ArrayList<LoadVerifier> implements LoadVerifier {

    public VerifierCollection(List<LoadVerifier> verifiers) {
        super(verifiers);
    }

    public VerifierCollection(LoadVerifier... verifiers) {
        super(Arrays.asList(verifiers));
    }

    @Override
    public boolean verify(double version) {
        return stream().noneMatch(verifier -> verifier.verify(TimberNoCheat.getInstance().getVersion()));
    }
}
