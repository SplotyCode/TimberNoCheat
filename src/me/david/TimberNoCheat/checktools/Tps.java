package me.david.TimberNoCheat.checktools;

public class Tps implements Runnable{

    public static int tickcount = 0;
    public static long[] ticks = new long[600];

    public static double getTPS() {
        return getTPS(100);
    }

    public static double getTPS(int checklastticks) {
        if (tickcount < checklastticks) {
            return 20.0D;
        }
        int target = (tickcount - 1 - checklastticks) % ticks.length;
        long elapsed = System.currentTimeMillis() - ticks[target];

        return checklastticks / (elapsed / 1000.0D);
    }

    public void run() {
        ticks[(tickcount % ticks.length)] = System.currentTimeMillis();
        tickcount++;
    }
}
