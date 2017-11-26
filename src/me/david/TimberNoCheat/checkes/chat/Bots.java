package me.david.TimberNoCheat.checkes.chat;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Bots extends Check {

    public Bots() {
        super("AuraBots", Category.CHAT);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        if(!TimberNoCheat.checkmanager.isvalid_create(event.getPlayer()) || event.getMessage().startsWith("/")){
            return;
        }
        if(TimberNoCheat.checkmanager.getPlayerdata(event.getPlayer()).getLoginlocation().equals(event.getPlayer().getLocation()))
            updatevio(this, event.getPlayer(), 1);
    }
}
