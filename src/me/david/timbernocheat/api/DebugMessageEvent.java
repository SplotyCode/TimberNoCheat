package me.david.timbernocheat.api;

import me.david.timbernocheat.debug.Debuggers;

/**
 * Called when a Debugger sends a message
 */
public class DebugMessageEvent extends TNCCancelEvent {

    private Debuggers debugger;
    private String message;
    private final Object[] data;

    public DebugMessageEvent(Debuggers debugger, String message, Object[] data) {
        this.debugger = debugger;
        this.message = message;
        this.data = data;
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
        return data;
    }

}
