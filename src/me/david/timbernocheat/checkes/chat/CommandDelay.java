package me.david.timbernocheat.checkes.chat;

import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.TimberNoCheat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandDelay extends Check {

    private final int COMMANDSIN10SECS;
    private final long DELAY;

    public CommandDelay(){
        super("CommandDelay", Category.CHAT);
        COMMANDSIN10SECS = getInt("in10seconds");
        DELAY = getLong("delay");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(final PlayerCommandPreprocessEvent e){
        final Player p = e.getPlayer();
        if(!TimberNoCheat.getCheckManager().isvalid_create(p)) return;
        PlayerData pd = TimberNoCheat.getCheckManager().getPlayerdata(p);
        pd.setCommands10sec(pd.getCommands10sec()+1);
        Bukkit.getScheduler().runTaskLaterAsynchronously(TimberNoCheat.getInstance(), () -> pd.setCommands10sec(pd.getCommands10sec()-1), 200);
        long delay = System.currentTimeMillis() - pd.getLastCommand();
        if(delay < this.DELAY){
            if(updateVio(this, p, 1, " §6MODE: §bDELAY", " §6DELAY: §b" + delay))
                e.setCancelled(true);
        }
        if(pd.getCommands10sec() > COMMANDSIN10SECS){
            if(updateVio(this, p, 1, " §6MODE: §bSPAM", " §6COMMANDSLASTSECOND: §b" + pd.getCommands10sec()))
                e.setCancelled(true);
        }
        pd.setLastCommand(System.currentTimeMillis());
    }
}
