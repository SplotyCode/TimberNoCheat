package me.david.timbernocheat.checkes.chat;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Bots extends Check {

    public Bots() {
        super("AuraBots", Category.CHAT);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        if(!TimberNoCheat.checkmanager.isvalid_create(event.getPlayer()) || event.getMessage().startsWith("/")) return;
        if(TimberNoCheat.checkmanager.getPlayerdata(event.getPlayer()).getGenerals().getLoginLocation().equals(event.getPlayer().getLocation()))
            updateVio(this, event.getPlayer(), 1);
    }

}
