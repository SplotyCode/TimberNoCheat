package me.david.TimberNoCheat.checkes.chat;

import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import me.david.TimberNoCheat.TimberNoCheat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Delay extends Check {

    public final int chats10;
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
        Bukkit.getScheduler().runTaskLaterAsynchronously(TimberNoCheat.instance, new Runnable() {
            @Override
            public void run() {
                pd.setChats10sec(pd.getChats10sec()-1);
            }
        }, 200);
        if(pd.getChats10sec() > chats10){
            updatevio(this, p, 1, " §6MODE: §bSPAM", " §6MESSAGESLAST10SECONDS: §b" + pd.getChats10sec());
            e.setCancelled(true);
        }
        long delay = System.currentTimeMillis() - pd.getLastchat();
        if(e.getMessage().length() >= delayschwelle){
            if(delay <= delaybig){
                updatevio(this, p, 1, " §6MODE: §bBIG", " §6DELAY: §b" + delay, " §6LENGtH: §b" + e.getMessage().length());
                e.setCancelled(true);
            }
        }else{
            if(delay <= delaysmall){
                updatevio(this, p, 1, " §6MODE: §bSMALL", " §6DELAY: §b" + delay, " §6LENGtH: §b" + e.getMessage().length());
                e.setCancelled(true);
            }
        }
        pd.setLastchat(System.currentTimeMillis());
    }
}
