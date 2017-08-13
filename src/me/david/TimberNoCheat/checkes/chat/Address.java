package me.david.TimberNoCheat.checkes.chat;

import me.david.TimberNoCheat.CheckManager.Category;
import me.david.TimberNoCheat.CheckManager.Check;
import me.david.TimberNoCheat.TimberNoCheat;
import me.david.api.utils.UrlUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Address extends Check{

    public Address(){
        super("Address", Category.CHAT);
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        if(UrlUtil.blockURL(e.getMessage()) != e.getMessage()){
            TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6MESSAGE: §b" + e.getMessage());
            if(TimberNoCheat.instance.settings.chat_address_blockcompletly){
                e.setCancelled(true);
            }
            e.getPlayer().sendMessage(TimberNoCheat.instance.prefix + "§cBitte mache keine Werbung!");
        }
    }
}
