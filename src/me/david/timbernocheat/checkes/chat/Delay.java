package me.david.timbernocheat.checkes.chat;

import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import me.david.timbernocheat.checkmanager.PlayerData;
import me.david.timbernocheat.TimberNoCheat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Delay extends Check {

    private final int chats10;
    private final long delaysmall;
    private final long delaybig;
    private final int delayschwelle;
    public Delay(){
        super("Delay", Category.CHAT);
        delayschwelle = getInt("schwelle");
        chats10 = getInt("in10seconds");
        delaysmall = getLong("delaysmall");
        delaybig = getLong("delaybig");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e){
        final Player p = e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p) || e.getMessage().startsWith("/")){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        pd.setChats10sec(pd.getChats10sec()+1);
        Bukkit.getScheduler().runTaskLaterAsynchronously(TimberNoCheat.instance, () -> pd.setChats10sec(pd.getChats10sec()-1), 200);
        if(pd.getChats10sec() > chats10){
            updateVio(this, p, 1, " §6MODE: §bSPAM", " §6MESSAGESLAST10SECONDS: §b" + pd.getChats10sec());
            e.setCancelled(true);
        }
        long delay = System.currentTimeMillis() - pd.getLastchat();
        if(e.getMessage().length() >= delayschwelle){
            if(delay <= delaybig){
                updateVio(this, p, 1, " §6MODE: §bBIG", " §6DELAY: §b" + delay, " §6LENGtH: §b" + e.getMessage().length());
                e.setCancelled(true);
            }
        }else{
            if(delay <= delaysmall){
                updateVio(this, p, 1, " §6MODE: §bSMALL", " §6DELAY: §b" + delay, " §6LENGtH: §b" + e.getMessage().length());
                e.setCancelled(true);
            }
        }
        pd.setLastchat(System.currentTimeMillis());
    }
}
