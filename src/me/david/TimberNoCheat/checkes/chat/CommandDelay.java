package me.david.TimberNoCheat.checkes.chat;

import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import me.david.TimberNoCheat.TimberNoCheat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandDelay extends Check{

    public CommandDelay(){
        super("CommandDelay", Category.CHAT);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent e){
        final Player p = (Player) e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        long delay = System.currentTimeMillis() - pd.getLastcommand();
        if(delay < TimberNoCheat.instance.settings.chat_commanddelay_delay){
            TimberNoCheat.checkmanager.notify(this, p, " §6DELAY: §b" + delay);
            p.sendMessage(TimberNoCheat.instance.prefix + "§cBitte warte ein wenig bis zum nägsten command!");
            e.setCancelled(true);
            return;
        }
        pd.setLastcommand(System.currentTimeMillis());
    }
}
