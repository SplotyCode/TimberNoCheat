package me.david.timbernocheat.checkes.chat;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

public class BlackList extends Check {

    private final List<String> blacklist;

    public BlackList(){
        super("BlackList", Category.CHAT);
        blacklist = getStringList("blacklist");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        if (e.getMessage().startsWith("/")) return;
        final Player p = e.getPlayer();
        if (!TimberNoCheat.checkmanager.isvalid_create(p)) return;
        String key = "";
        for(String black : blacklist)
            if(e.getMessage().contains(black)){
                key = black;
                break;
            }
        if(!key.equals("")){
            e.setCancelled(true);
            updateVio(this, p, 1, " §6WORD: §b" + key, " §6MESSAGE: §b" + e.getMessage());
        }
    }
}
