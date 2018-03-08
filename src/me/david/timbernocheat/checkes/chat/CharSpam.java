package me.david.timbernocheat.checkes.chat;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class CharSpam extends Check {

    private final int MINLENGTH;
    private final int PERCENTAGE;
    private final int MAXCHARSPAM;

    public CharSpam() {
        super("CharSpam", Category.CHAT);
        registerChilds(Types.values());
        MINLENGTH = getInt("minlength");
        PERCENTAGE = getChildbyEnum(Types.PERCENTAGE).getInt("percentage");
        MAXCHARSPAM = getChildbyEnum(Types.MAXCHARATERS).getInt("maxcharspam");
    }

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final String message = event.getMessage();
        if (!TimberNoCheat.getCheckManager().isvalid_create(player) || message.startsWith("/")) return;
        int charSpamCount = 0;
        char lastChar = Character.MIN_VALUE;
        for(char c  : message.toCharArray())
            if(c == lastChar) {
                charSpamCount++;
                lastChar = c;
            }
        if(message.length() > MINLENGTH && charSpamCount != 0){
            if(maxCharaters(player, charSpamCount) || percentage(player, charSpamCount, message))
                event.setCancelled(true);
        }
    }

    private boolean maxCharaters(final Player player, int charaters) {
        float violation = charaters - MAXCHARSPAM;
        return charaters >= MAXCHARSPAM && updateVio(getChildbyEnum(Types.MAXCHARATERS), player, violation < 1 ? 1 : violation);
    }

    private boolean percentage(final Player player, int charaters, final String message) {
        if((charaters/message.length())*100 >= PERCENTAGE)
            updateVio(getChildbyEnum(Types.PERCENTAGE), player, ((charaters/message.length())*100)/4);
        return false;
    }

    public enum Types {

        PERCENTAGE,
        MAXCHARATERS

    }
}
