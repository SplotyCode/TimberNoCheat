package me.david.TimberNoCheat.checkes.chat;

import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import me.david.TimberNoCheat.TimberNoCheat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Delay extends Check{

    public Delay(){
        super("Delay", Category.CHAT);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e){
        if(e.getMessage().startsWith("/")){
            return;
        }
        final Player p = e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        long delay = System.currentTimeMillis() - pd.getLastchat();
        if(e.getMessage().length() >= TimberNoCheat.instance.settings.chat_delay_schwelle){
            if(delay <= TimberNoCheat.instance.settings.chat_delay_big){
                p.sendMessage(TimberNoCheat.instance.prefix + "§cBitte schreibe langsamger!");
                TimberNoCheat.checkmanager.notify(this, p, " §6DELAY: §b" + delay);
                e.setCancelled(true);
                return;
            }
        }else{
            if(delay <= TimberNoCheat.instance.settings.chat_delay_small){
                p.sendMessage(TimberNoCheat.instance.prefix + "§cBitte schreibe langsamger!");
                TimberNoCheat.checkmanager.notify(this, p, " §6DELAY: §b" + delay, " §6LENGtH: §b" + e.getMessage().length());
                e.setCancelled(true);
                return;
            }
        }
        pd.setLastchat(System.currentTimeMillis());
    }
}
