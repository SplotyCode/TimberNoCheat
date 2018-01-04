package me.david.TimberNoCheat.api;

import org.bukkit.event.Cancellable;

public class TNCCancelEvent extends TNCEvent implements Cancellable {

    private boolean cancelled;

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
