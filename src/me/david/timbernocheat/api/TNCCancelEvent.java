package me.david.timbernocheat.api;

import org.bukkit.event.Cancellable;

/**
 * An Base-Event
 * Normal TNC Events can extend from This when they can be canceled
 */
public class TNCCancelEvent extends TNCEvent implements Cancellable {

    private boolean cancelled;

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
