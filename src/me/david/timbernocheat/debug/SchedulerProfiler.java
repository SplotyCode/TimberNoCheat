package me.david.timbernocheat.debug;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.discord.DiscordManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.util.HashMap;

public class SchedulerProfiler {

    private boolean measuring;
    private String currentSection;
    private long startSection;
    private HashMap<String, Long> times = new HashMap<>();
    private HashMap<String, Integer> calls = new HashMap<>();
    private HashMap<String, Long> lastExeptions = new HashMap<>();;
    private boolean running;

    private final long EXCEPTION_DELAY = TimberNoCheat.getInstance().isDebug()?0:   1000*90;

    public void start(String section){
        if(running) {
            currentSection = section;
            measuring = true;
            startSection = System.currentTimeMillis();
        }
        calls.put(section, calls.getOrDefault(section, 0)+1);
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
        boolean cooldown = !(time == null || System.currentTimeMillis()-time < EXCEPTION_DELAY);
        if(!cooldown){
            String str = "Whoops an Error accurs in an TimberNoCheat Scheduler! Scheduler Name: '" + realName + "\n" + "The error is now for " + EXCEPTION_DELAY + " under Cooldown!";
            System.err.println(str);
            lastExeptions.put(realName, System.currentTimeMillis());
            throwable.printStackTrace();
            TimberNoCheat.getInstance().getDiscordManager().sendError(str, throwable, DiscordManager.ErrorType.SCHEDULER, new MessageEmbed.Field("Cooldown", System.currentTimeMillis()-time + "", true));
            TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.SCHEDULEREXEPTION, "Scheduler " + realName + " throws Exception!", "Deactivate Cooldown");
        }
        TimberNoCheat.getInstance().getDebugger().sendDebugNotSetting(Debuggers.SCHEDULEREXEPTION, "Scheduler " + realName + " throws Exception!(spam)", "Deactivate Cooldown");

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

    public HashMap<String, Integer> getCalls() {
        return calls;
    }
}
