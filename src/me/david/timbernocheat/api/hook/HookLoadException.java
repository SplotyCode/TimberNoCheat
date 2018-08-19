package me.david.timbernocheat.api.hook;

public class HookLoadException extends RuntimeException {

    public HookLoadException() {
    }

    public HookLoadException(String s) {
        super(s);
    }

    public HookLoadException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public HookLoadException(Throwable throwable) {
        super(throwable);
    }

    public HookLoadException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
