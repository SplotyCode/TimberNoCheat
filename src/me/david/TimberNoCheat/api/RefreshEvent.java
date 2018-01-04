package me.david.TimberNoCheat.api;

import org.bukkit.command.CommandSender;

public class RefreshEvent extends TNCEvent {

    private CommandSender sender;

    public RefreshEvent(CommandSender sender) {
        this.sender = sender;
    }

    public CommandSender getSender() {
        return sender;
    }

    public void setSender(CommandSender sender) {
        this.sender = sender;
    }
}
