package me.david.timbernocheat.command.internal;

import me.david.api.objects.listener.PlayerListener;
import me.david.timbernocheat.command.TNCInternal;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;

public class MultiSelectUserRequest extends UserRequest<String> {

    private String[] options;
    private long id;

    public MultiSelectUserRequest(String message, String... options) {
        super(message);
        this.options = options;
        register();
    }

    public MultiSelectUserRequest(String message, ArrayList<PlayerListener<String>> list, String... options) {
        super(message, list);
        this.options = options;
        register();
    }

    public MultiSelectUserRequest(String message, String[] options, PlayerListener<String>... list) {
        super(message, list);
        this.options = options;
        register();
    }

    private void register(){
        id = TNCInternal.getInstance().addHandler((player, value) -> {
            getListeners().forEach((listener -> listener.onEvent(player, value)));
        });
    }

    @Override
    void displayOption(TextComponent text) {
        for(String option : options){
            TextComponent component = new TextComponent("§7[§b" + option + "§7] ");
            component.setBold(true);
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "tncinternal " + id + " " + option));
            text.addExtra(component);
        }
    }
}
