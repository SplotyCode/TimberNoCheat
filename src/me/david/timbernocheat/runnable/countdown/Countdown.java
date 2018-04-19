package me.david.timbernocheat.runnable.countdown;

public class Countdown {

    private Runnable finsished;
    private Runnable update;
    private int ticks = 0;

    public Countdown(Runnable finsished, int ticks) {
        this.finsished = finsished;
        this.ticks = ticks;
        update = () -> {
            this.ticks--;
            if(this.ticks == 0)
                finsished.run();
        };
    }

    public Runnable getFinsished() {
        return finsished;
    }

    public void setFinsished(Runnable finsished) {
        this.finsished = finsished;
    }

    public Runnable getUpdate() {
        return update;
    }

    public void setUpdate(Runnable update) {
        this.update = update;
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }
}
