package me.david.timbernocheat.checkes.chat;

import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import me.david.timbernocheat.checkmanager.PlayerData;
import me.david.timbernocheat.TimberNoCheat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandDelay extends Check {

    private final int commands10;
    private final long delay;
    public CommandDelay(){
        super("CommandDelay", Category.CHAT);
        commands10 = getInt("in10seconds");
        delay = getLong("delay");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent e){
        final Player p = e.getPlayer();
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
            updateVio(this, p, 1, " §6MODE: §bDELAY", " §6DELAY: §b" + delay);
            e.setCancelled(true);
        }
        if(pd.getCommands10sec() > commands10){
            updateVio(this, p, 1, " §6MODE: §bSPAM", " §6COMMANDSLASTSECOND: §b" + pd.getCommands10sec());
            e.setCancelled(true);
        }
        pd.setLastcommand(System.currentTimeMillis());
    }
}