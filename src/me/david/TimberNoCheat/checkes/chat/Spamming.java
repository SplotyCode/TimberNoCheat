package me.david.TimberNoCheat.checkes.chat;

import me.david.TimberNoCheat.CheckManager.Category;
import me.david.TimberNoCheat.CheckManager.Check;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Spamming extends Check{
    public Spamming(){
        super("Spamming", Category.CHAT);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        
    }
}
