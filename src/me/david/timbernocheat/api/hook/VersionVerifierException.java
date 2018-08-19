package me.david.timbernocheat.api.hook;

public class VersionVerifierException extends HookLoadException {

    public VersionVerifierException() {
    }

    public VersionVerifierException(String s) {
        super(s);
    }

    public VersionVerifierException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public VersionVerifierException(Throwable throwable) {
        super(throwable);
    }

    public VersionVerifierException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
