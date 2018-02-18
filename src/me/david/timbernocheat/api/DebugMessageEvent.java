package me.david.timbernocheat.api;

import me.david.timbernocheat.debug.Debuggers;

public class DebugMessageEvent extends TNCCancelEvent {

    private Debuggers debugger;
    private String message;
    private final Object[] DATA;

    public DebugMessageEvent(Debuggers debugger, String message, Object[] data) {
        this.debugger = debugger;
        this.message = message;
        this.DATA = data;
    }

    public Debuggers getDebugger() {
        return debugger;
    }

    public void setDebugger(Debuggers debugger) {
        this.debugger = debugger;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object[] getData() {
        return DATA;
    }
}
