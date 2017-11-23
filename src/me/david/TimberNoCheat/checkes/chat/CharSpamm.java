package me.david.TimberNoCheat.checkes.chat;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class CharSpamm extends Check {

    private final int minlen;
    private final int per;
    private final int maxcharspam;

    public CharSpamm() {
        super("CharSpamm", Category.CHAT);
        minlen = getInt("minlen");
        per = getInt("per");
        maxcharspam = getInt("maxcharspam");
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (!TimberNoCheat.checkmanager.isvalid_create(event.getPlayer()) || event.getMessage().startsWith("/")) return;
        String message = event.getMessage();
        message = message.replaceAll("(.)(?=\\1\\1+)", "");
        message = message.replaceAll("(..)(?=\\1\\1+)", "");
        message = message.replaceAll("(...)(?=\\1\\1+)", "");
        if(event.getMessage().length() > minlen && message.length() < event.getMessage().length()){
            if(event.getMessage().length()-message.length() >= maxcharspam) updatevio(this, event.getPlayer(), event.getMessage().length()-message.length()-maxcharspam < 1?1:event.getMessage().length()-message.length()-maxcharspam, " §6MODE: §bPERCENTAGE");
            if((message.length()/event.getMessage().length())*100 >= per) updatevio(this, event.getPlayer(), ((message.length()/event.getMessage().length())*100)/4, " §6MODE: §bMAXSPAMCHARS");
        }
    }
}
