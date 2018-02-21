package me.david.timbernocheat.checkes.chat;

import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import me.david.timbernocheat.TimberNoCheat;
import me.david.api.utils.UrlUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/*
 * Check if a Message contains a Server Address
 */
public class Address extends Check{

    public Address(){
        super("Address", Category.CHAT);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(final AsyncPlayerChatEvent event){
        final Player player = event.getPlayer();
        final String message = event.getMessage();
        if(!TimberNoCheat.checkmanager.isvalid_create(player) || message.startsWith("/")) return;
        if(!UrlUtil.blockURL(message).equals(message)){
            if(updateVio(this, player, 1, " §6MESSAGE: §b" + message))
                event.setCancelled(true);
        }
    }
}
