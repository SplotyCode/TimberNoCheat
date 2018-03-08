package me.david.timbernocheat.checkes.chat;

import me.david.api.utils.StringUtil;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class BlackList extends Check {

    private final List<String> BLACKLIST;

    public BlackList(){
        super("BlackList", Category.CHAT);
        BLACKLIST = getStringList("blacklist");
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(final AsyncPlayerChatEvent event) {
        final String message = event.getMessage();
        final Player player = event.getPlayer();
        if (!TimberNoCheat.getCheckManager().isvalid_create(player) || message.startsWith("/")) return;
        String key = "";
        for(String black : BLACKLIST)
            if(message.contains(black)){
                key = black;
                break;
            }
        if(!StringUtil.isEmty(key)){
            if(updateVio(this, player, 1, " §6WORD: §b" + key, " §6MESSAGE: §b" + message))
                event.setCancelled(true);
        }
    }
}
