package me.david.timbernocheat.api;

import org.bukkit.command.CommandSender;

/**
 * This events called when Tnc refreshes. You can not cancel this event
 */
public class RefreshEvent extends TNCEvent {

    /* The executor of the Command that causes Tnc to refresh */
    private final CommandSender sender;

    public RefreshEvent(CommandSender sender) {
        this.sender = sender;
    }

    public CommandSender getSender() {
        return sender;
    }

}
