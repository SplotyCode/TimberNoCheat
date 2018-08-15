package me.david.timbernocheat.command.internal;

import me.david.api.objects.listener.ListenerHandler;
import me.david.api.objects.listener.PlayerListener;
import me.david.timbernocheat.TimberNoCheat;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class UserRequest<T> extends ListenerHandler<PlayerListener<T>> {

    private String message;

    public UserRequest(String message){
        this.message = message;
    }

    public UserRequest(String message, ArrayList<PlayerListener<T>> list){
        super(list);
        this.message = message;
    }

    @SafeVarargs
    public UserRequest(String message, PlayerListener<T>... list){
        super(list);
        this.message = message;
    }

    public void display(final Player player){
        TextComponent message = new TextComponent(TimberNoCheat.getInstance().getPrefix() + getMessage() + " ");
        TextComponent options = new TextComponent();
        displayOption(options);
        message.addExtra(options);
        player.spigot().sendMessage(message);
    }

    abstract void displayOption(TextComponent text);


    public String getMessage() {
        return message;
    }
}
