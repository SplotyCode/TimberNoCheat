package me.david.timbernocheat.runnable;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.debug.Scheduler;
import org.bukkit.scheduler.BukkitRunnable;

public class TimberScheduler extends BukkitRunnable {

    private ExceptionRunnable runnable;
    private final String name;
    private final Scheduler scheduler;

    public TimberScheduler(String name, ExceptionRunnable runnable){
        this.name = name;
        this.runnable = runnable;
        scheduler = null;
    }

    public TimberScheduler(Scheduler scheduler, ExceptionRunnable runnable){
        this.scheduler = scheduler;
        this.runnable = runnable;
        name = null;
    }

    @Override
    public final void run() {
        String realName = scheduler == null?"Unknown: " + name:scheduler.getName();
        TimberNoCheat.getInstance().getSchedulerProfiler().start(realName);
        try {
            runnable.run();
        } catch (Exception e) {
            TimberNoCheat.getInstance().getSchedulerProfiler().handleError(e, realName);
        }
        TimberNoCheat.getInstance().getSchedulerProfiler().end();
    }

    public ExceptionRunnable getRunnable() {
        return runnable;
    }

    public String getRealName(){
        return scheduler == null?"Unknown: " + name:scheduler.getName();
    }

    public TimberScheduler runTaskLater(long delay) {
        super.runTaskLater(TimberNoCheat.getInstance(), delay);
        return this;
    }

    public TimberScheduler runNextTick(){
        super.runTask(TimberNoCheat.getInstance());
        return this;
    }

    public TimberScheduler startTimer(long delay){
        super.runTaskTimer(TimberNoCheat.getInstance(), 1, delay);
        return this;
    }
}
