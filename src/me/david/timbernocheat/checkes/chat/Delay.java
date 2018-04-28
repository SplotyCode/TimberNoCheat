package me.david.timbernocheat.checkes.chat;

import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.TimberNoCheat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Delay extends Check {

    private final int CHATIN10SECS;
    private final long SMALLDELAY;
    private final long BIGDELAY;
    private final int DELAYSCHWELLE;

    public Delay(){
        super("Delay", Category.CHAT);
        DELAYSCHWELLE = getInt("schwelle");
        CHATIN10SECS = getInt("in10seconds");
        SMALLDELAY = getLong("delaysmall");
        BIGDELAY = getLong("delaybig");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(final AsyncPlayerChatEvent event){
        final Player player = event.getPlayer();
        final String message = event.getMessage();

        if(!CheckManager.getInstance().isvalid_create(player) || message.startsWith("/")) return;

        PlayerData pd = CheckManager.getInstance().getPlayerdata(player);


        pd.setChats10sec(pd.getChats10sec()+1);
        Bukkit.getScheduler().runTaskLater(TimberNoCheat.getInstance(), () -> pd.setChats10sec(pd.getChats10sec()-1), 200);
        if(pd.getChats10sec() > CHATIN10SECS){
            updateVio(this, player, 1, " §6MODE: §bSPAM", " §6MESSAGESLAST10SECONDS: §b" + pd.getChats10sec());
            event.setCancelled(true);
        }

        long delay = System.currentTimeMillis() - pd.getLastChat();
        if(message.length() >= DELAYSCHWELLE){
            if(delay <= BIGDELAY){
                if(updateVio(this, player, 1, " §6MODE: §bBIG", " §6DELAY: §b" + delay, " §6LENGtH: §b" + message.length()))
                    event.setCancelled(true);
            }

        }else if(delay <= SMALLDELAY){
            if(updateVio(this, player, 1, " §6MODE: §bSMALL", " §6DELAY: §b" + delay, " §6LENGtH: §b" + message.length()))
                event.setCancelled(true);
        }
        pd.setLastChat(System.currentTimeMillis());
    }
}
