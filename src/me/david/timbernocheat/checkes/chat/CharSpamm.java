package me.david.timbernocheat.checkes.chat;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
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
        int i = 0;
        char lastchar = Character.MIN_VALUE;
        for(char c  : event.getMessage().toCharArray())
            if(c == lastchar) i++;
        if(event.getMessage().length() > minlen && i != 0){
            if(i >= maxcharspam) updateVio(this, event.getPlayer(), i-maxcharspam < 1?1:i-maxcharspam, " §6MODE: §bMAXSPAMCHARS");
            if((i/event.getMessage().length())*100 >= per) updateVio(this, event.getPlayer(), ((i/event.getMessage().length())*100)/4, " §6MODE: §bPERCENTAGE");
        }
    }
}
