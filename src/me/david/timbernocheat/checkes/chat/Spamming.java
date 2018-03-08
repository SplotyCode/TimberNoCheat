package me.david.timbernocheat.checkes.chat;

import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.TimberNoCheat;
import me.david.api.utils.ArrayCollectUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class Spamming extends Check {
    private final boolean IGNORECASEWHITELIST;
    private final boolean ignorecasewhitelist;
    private final List<String> whitelist;
    private final int toshort;

    public Spamming(){
        super("Spamming", Category.CHAT);
        IGNORECASEWHITELIST = getBoolean("ignorecase");
        ignorecasewhitelist = getBoolean("whitelist_ignorecase");
        whitelist = getStringList("whitelist");
        toshort = getInt("toshort");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event){
        final Player player = event.getPlayer();
        String message = event.getMessage();
        if(!TimberNoCheat.getCheckManager().isvalid_create(player) || event.getMessage().startsWith("/"))
            return;
        PlayerData pd = TimberNoCheat.getCheckManager().getPlayerdata(player);
        if(message.length() < toshort) return;
        if(ignorecasewhitelist && ArrayCollectUtil.containsignorecase(whitelist, message)){
            return;
        }

        if(!ignorecasewhitelist && whitelist.contains(message))
            return;
        if(!IGNORECASEWHITELIST && pd.getGenerals().getMessages().contains(message)){
            if(updateVio(this, player, 1))
                event.setCancelled(true);
            return;
        }
        if(IGNORECASEWHITELIST && ArrayCollectUtil.containsignorecase(pd.getGenerals().getMessages(), message)){
            if(updateVio(this, player, 1))
                event.setCancelled(true);
        }
    }
}
