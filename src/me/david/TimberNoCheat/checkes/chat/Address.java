package me.david.TimberNoCheat.checkes.chat;

import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.TimberNoCheat;
import me.david.api.utils.UrlUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Objects;

/*
 * Check if a Message contains a Server Address
 */
public class Address extends Check{

    public final boolean block;

    public Address(){
        super("Address", Category.CHAT);
        block = getBoolean("block");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e){
        if(!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer()) || e.getMessage().startsWith("/")) return;
        if(!UrlUtil.blockURL(e.getMessage()).equals(e.getMessage())){
            updatevio(this, e.getPlayer(), 1, " §6MESSAGE: §b" + e.getMessage());
            if(block) e.setCancelled(true);
        }
    }
}
