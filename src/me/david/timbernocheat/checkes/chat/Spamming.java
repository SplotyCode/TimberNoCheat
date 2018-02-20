package me.david.timbernocheat.checkes.chat;

import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import me.david.timbernocheat.checkmanager.PlayerData;
import me.david.timbernocheat.TimberNoCheat;
import me.david.api.utils.ArrayCollectUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

public class Spamming extends Check {
    private final boolean ignorecase;
    private final boolean ignorecasewhitelist;
    private final List<String> whitelist;
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
            updateVio(this, e.getPlayer(), 1);
        }
    }
}
