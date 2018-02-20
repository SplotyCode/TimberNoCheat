package me.david.timbernocheat.debug;

public enum Scheduler {

    KILLAURA("Killaura"),
    PATTERN_SPEED("Speed (Pattern)"),
    MOVE_PROFILER("Move Profiler"),
    SCHEDULER_PROFILER("Scheduler Profiler");
    private final String name;

    Scheduler(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
