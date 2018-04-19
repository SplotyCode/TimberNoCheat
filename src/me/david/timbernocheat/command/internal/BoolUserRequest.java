package me.david.timbernocheat.command.internal;

import me.david.api.objects.listener.PlayerListener;
import me.david.timbernocheat.command.TNCInternal;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;

public class BoolUserRequest extends UserRequest<Boolean> {

    private long id;

    public BoolUserRequest(String message) {
        super(message);
        register();
    }

    private void register(){
        id = TNCInternal.getInstance().addHandler((player, value) -> {
            boolean bool = Integer.valueOf(value)==1;
            getListeners().forEach((listener -> listener.onEvent(player, bool)));
        });
    }

    public BoolUserRequest(String message, ArrayList<PlayerListener<Boolean>> list) {
        super(message, list);
        register();
    }

    public BoolUserRequest(String message, PlayerListener<Boolean>... list) {
        super(message, list);
        register();
    }

    @Override
    void displayOption(TextComponent text) {
        TextComponent yes = new TextComponent("§7[§aYes§7]");
        yes.setBold(true);
        yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "tncinternal " + id + " 1"));

        TextComponent no = new TextComponent("§7[§cNo§7]");
        no.setBold(true);
        no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "tncinternal " + id + " 0"));

        text.addExtra(yes);
        text.addExtra(new TextComponent(" §6or "));
        text.addExtra(no);
    }
}
