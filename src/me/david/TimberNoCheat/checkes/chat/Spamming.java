package me.david.TimberNoCheat.checkes.chat;

import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import me.david.TimberNoCheat.TimberNoCheat;
import me.david.api.utils.ArrayCollectUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Spamming extends Check{
    public Spamming(){
        super("Spamming", Category.CHAT);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e){
        if(e.getMessage().startsWith("/")){
            return;
        }
        if(!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer())){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(e.getPlayer());
        String message = e.getMessage();
        if(message.length() < TimberNoCheat.instance.settings.chat_spamming_toshort){
            return;
        }
        if(TimberNoCheat.instance.settings.chat_spamming_whitelist_ignorecase && ArrayCollectUtil.containsignorecase(TimberNoCheat.instance.settings.chat_spamming_whitelist, message)){
            return;
        }

        if(!TimberNoCheat.instance.settings.chat_spamming_whitelist_ignorecase && TimberNoCheat.instance.settings.chat_spamming_whitelist.contains(message)) {
            return;
        }
        if(!TimberNoCheat.instance.settings.chat_spamming_ignorecase && pd.getMessages().contains(message)){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, e.getPlayer());
            return;
        }
        if(TimberNoCheat.instance.settings.chat_spamming_ignorecase && ArrayCollectUtil.containsignorecase(pd.getMessages(), message)){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, e.getPlayer());
            e.getPlayer().sendMessage(TimberNoCheat.instance.prefix + "§cBitte gebe die wiederhole dich nicht!");
            return;
        }
        pd.getMessages().add(message);
    }
}
