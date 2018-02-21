package me.david.timbernocheat.checkes.chat;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Caps extends Check {

    private final int MINLENGTH;
    private final int PERCENTAGE;

    public Caps() {
        super("Caps", Category.CHAT);
        MINLENGTH = getInt("minlength");
        PERCENTAGE = getInt("percentage");
    }

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final String message = event.getMessage();
        if (!TimberNoCheat.checkmanager.isvalid_create(player) || message.startsWith("/")) return;
        int[] newMessage;
        if (message.length() >= MINLENGTH) {
            String msgBefore = event.getMessage();
            newMessage = checkCaps(event.getMessage());
            int percentageCaps = percentageCaps(newMessage);
            if (percentageCaps >= PERCENTAGE) {
                String[] parts = event.getMessage().split(" ");
                boolean caps = false;
                for (int i = 0; i < parts.length; i++) {
                    if (!caps) {
                        char firstChar = parts[i].charAt(0);
                        parts[i] = (firstChar + parts[i].toLowerCase().substring(1));
                    }
                    else parts[i] = parts[i].toLowerCase();
                    caps = (!parts[i].endsWith(".")) && (!parts[i].endsWith("!"));
                }
                if (!msgBefore.equals(StringUtils.join(parts, " ")))
                    if(updateVio(this, event.getPlayer(), percentageCaps- PERCENTAGE *1.6))
                        event.setCancelled(true);
            }
        }
    }

    private static int[] checkCaps(String message) {
        int[] editedMsg = new int[message.length()];
        String[] parts = message.split(" ");
        String msg = StringUtils.join(parts, " ");
        for (int i = 0; i < msg.length(); i++)
            if ((Character.isUpperCase(msg.charAt(i))) && (Character.isLetter(msg.charAt(i)))) editedMsg[i] = 1;
            else editedMsg[i] = 0;
        return editedMsg;
    }

    private static int percentageCaps(int[] caps) {
        int sum = 0;
        for (int cap : caps) sum += cap;
        double ratio = sum / caps.length;
        return (int)(100.0D * ratio);
    }
}
