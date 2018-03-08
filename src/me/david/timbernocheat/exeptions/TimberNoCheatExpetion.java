package me.david.timbernocheat.exeptions;

public class TimberNoCheatExpetion extends RuntimeException {

    public TimberNoCheatExpetion() {}

    public TimberNoCheatExpetion(String s) {
        super(s);
    }

    public TimberNoCheatExpetion(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TimberNoCheatExpetion(Throwable throwable) {
        super(throwable);
    }

    public TimberNoCheatExpetion(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
