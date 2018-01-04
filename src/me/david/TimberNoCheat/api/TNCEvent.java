package me.david.TimberNoCheat.api;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TNCEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
