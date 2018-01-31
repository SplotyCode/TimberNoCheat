package me.david.TimberNoCheat.checkes.chat;

import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import me.david.TimberNoCheat.TimberNoCheat;
import me.david.api.utils.ArrayCollectUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;

public class Spamming extends Check {
    private final boolean ignorecase;
    private final boolean ignorecasewhitelist;
    private final ArrayList<String> whitelist;
    private final int toshort;

    public Spamming(){
        super("Spamming", Category.CHAT);
        ignorecase = getBoolean("ignorecase");
        ignorecasewhitelist = getBoolean("whitelist_ignorecase");
        whitelist = getStringList("whitelist");
        toshort = getInt("toshort");
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
        if(message.length() < toshort){
            return;
        }
        if(ignorecasewhitelist && ArrayCollectUtil.containsignorecase(whitelist, message)){
            return;
        }

        if(!ignorecasewhitelist && whitelist.contains(message)) {
            return;
        }
        if(!ignorecase && pd.getGenerals().getMessages().contains(message)){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, e.getPlayer());
            return;
        }
        if(ignorecase && ArrayCollectUtil.containsignorecase(pd.getGenerals().getMessages(), message)){
            e.setCancelled(true);
            updatevio(this, e.getPlayer(), 1);
        }
    }
}
