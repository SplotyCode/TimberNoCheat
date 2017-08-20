package me.david.TimberNoCheat.checkes.player;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class FastSwitch extends Check {

    public FastSwitch(){
        super("FastSwitch", Category.PLAYER);
    }

    @EventHandler
    public void onswitch(PlayerItemHeldEvent e){
        final Player p = e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        if(TimberNoCheat.checkmanager.getping(p) >= TimberNoCheat.instance.settings.player_fastswitch_maxping){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        long delay = System.currentTimeMillis()-pd.getLastitemwsitch();
        if(delay < TimberNoCheat.instance.settings.player_fastswitch_delaymili){
            TimberNoCheat.checkmanager.notify(this, p, " §6DELAY: §b" + delay);
            pd.setLastitemwsitch(System.currentTimeMillis()-15000L);
            return;
        }
        pd.setLastitemwsitch(System.currentTimeMillis());
    }
}
