package me.david.TimberNoCheat.checkes.chat;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class BlackList extends Check{
    public BlackList(){
        super("BlackList", Category.CHAT);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        if (e.getMessage().startsWith("/")) {
            return;
        }
        final Player p = e.getPlayer();
        if (!TimberNoCheat.checkmanager.isvalid_create(p)) {
            return;
        }
        String key = "";
        for(String black : TimberNoCheat.instance.settings.chat_blacklist){
            if(e.getMessage().contains(black)){
                key = black;
                break;
            }
        }
        if(key != ""){
            e.setCancelled(true);
            e.getPlayer().sendMessage(TimberNoCheat.instance.prefix + "§cDeine Nachicht enthält schimpfwörter!");
            TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6WORD: §b" + key, " §6MESSAGE: §b" + e.getMessage());
        }
    }
}
