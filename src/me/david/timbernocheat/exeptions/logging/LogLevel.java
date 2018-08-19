package me.david.timbernocheat.exeptions.logging;

import java.util.logging.Level;

public enum LogLevel {

    ERROR(Level.WARNING),
    WARN(Level.WARNING),
    INFO(Level.INFO),
    DEBUG_LAYER_1(Level.FINE),
    DEBUG_LAYER_2(Level.FINER),
    DEBUG_LAYER_3(Level.FINEST);

    private Level level;

    LogLevel(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }
}
