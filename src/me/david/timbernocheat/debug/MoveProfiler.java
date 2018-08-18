package me.david.timbernocheat.debug;

import me.david.timbernocheat.TimberNoCheat;
import org.bukkit.Bukkit;

import java.util.HashMap;

public class MoveProfiler {

    private boolean measuring;
    private String currentSection;
    private long startSection;
    private HashMap<String, Long> times;
    private boolean running;

    public MoveProfiler(){
        times = new HashMap<>();
    }

    public void start(String section){
        if (running) {
            currentSection = section;
            measuring = true;
            startSection = System.currentTimeMillis();
        }
    }

    public void end() {
        if (running && measuring) {
            measuring = false;
            long delay = times.getOrDefault(currentSection, 0L);
            //System.out.println(currentSection + " took " + (System.currentTimeMillis() - startSection));
            times.put(currentSection, delay + (System.currentTimeMillis() - startSection));
        }
    }

    public boolean isMeasuring() {
        return measuring;
    }

    public boolean isRunning() {
        return running;
    }

    public HashMap<String, Long> getTimes() {
        return times;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void reset(){
        times.clear();
    }
}
