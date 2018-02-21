package me.david.timbernocheat.checkes.chat;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Bots extends Check {

    public Bots() {
        super("ChatBots", Category.CHAT);
    }

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent event){
        final Player player = event.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(player) || event.getMessage().startsWith("/")) return;
        final Location loginLocation = TimberNoCheat.checkmanager.getPlayerdata(player).getGenerals().getLoginLocation();
        if(loginLocation.equals(player.getLocation()))
            if(updateVio(this, player, 1))
                event.setCancelled(true);
    }

}
