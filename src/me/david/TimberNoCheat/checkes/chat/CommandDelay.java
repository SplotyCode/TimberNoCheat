package me.david.TimberNoCheat.checkes.chat;

import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import me.david.TimberNoCheat.TimberNoCheat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandDelay extends Check{

    public int commands10;
    private long delay;
    public CommandDelay(){
        super("CommandDelay", Category.CHAT);
        commands10 = getInt("in10seconds");
        delay = getLong("delay");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent e){
        final Player p = (Player) e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        pd.setCommands10sec(pd.getCommands10sec()+1);
        Bukkit.getScheduler().runTaskLaterAsynchronously(TimberNoCheat.instance, new Runnable() {
            @Override
            public void run() {
                pd.setCommands10sec(pd.getCommands10sec()-1);
            }
        }, 200);
        long delay = System.currentTimeMillis() - pd.getLastcommand();
        if(delay < this.delay){
            updatevio(this, p, 1, " §6MODE: §bDELAY", " §6DELAY: §b" + delay);
            e.setCancelled(true);
        }
        if(pd.getCommands10sec() > commands10){
            updatevio(this, p, 1, " §6MODE: §bSPAM", " §6COMMANDSLASTSECOND: §b" + pd.getCommands10sec());
            e.setCancelled(true);
        }
        pd.setLastcommand(System.currentTimeMillis());
    }
}
