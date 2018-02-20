package me.david.timbernocheat.debug;

import me.david.timbernocheat.TimberNoCheat;
import org.bukkit.Bukkit;

import java.util.HashMap;

public class SchedulerProfiler {

    private boolean measuring;
    private String currentSection;
    private long startSection;
    private HashMap<String, Long> times = new HashMap<>();;
    private HashMap<String, Long> lastExeptions = new HashMap<>();;
    private boolean running;

    private final long EXPETION_DELAY = 1000*90;

    public void start(String section){
        if(running) {
            currentSection = section;
            measuring = true;
            startSection = System.currentTimeMillis();
        }
    }

    public void end() {
        if (running && measuring) {
            measuring = false;
            long delay = times.getOrDefault(currentSection, 0L);
            times.put(currentSection, delay + (System.currentTimeMillis() - startSection));
        }
    }

    public void handleError(Throwable throwable, String realName){
        Long time = lastExeptions.get(realName);
        boolean cooldown = !(time == null || System.currentTimeMillis()-time < EXPETION_DELAY);
        if(!cooldown){
            System.err.println("Whoops an Error accurs in an TimberNoCheat Scheduler! Scheduler Name: '" + realName + "'");
            System.err.println("The error is now for " + EXPETION_DELAY + " under Cooldown!");
            lastExeptions.put(realName, System.currentTimeMillis());
            throwable.printStackTrace();
            TimberNoCheat.instance.getDebugger().sendDebug(Debuggers.SCHEDULEREXEPTION, "Scheduler " + realName + " throws Exception!", "Deactivate Cooldown");
        }
        TimberNoCheat.instance.getDebugger().sendDebugNotSetting(Debuggers.SCHEDULEREXEPTION, "Scheduler " + realName + " throws Exception!(spam)", "Deactivate Cooldown");

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
