package me.david.TimberNoCheat.checkes.player;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class FastSwitch extends Check {

    final long delay;
    public FastSwitch(){
        super("FastSwitch", Category.PLAYER);
        delay = getLong("delay");
    }

    @EventHandler
    public void onswitch(PlayerItemHeldEvent e){
        final Player p = e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        long delay = System.currentTimeMillis()-pd.getLastitemwsitch();
        if(delay < this.delay){
            updatevio(this, p, 1, " §6DELAY: §b" + delay);
            //TimberNoCheat.checkmanager.notify(this, p, " §6DELAY: §b" + delay);
            //pd.setLastitemwsitch(System.currentTimeMillis()-15000L);
            e.setCancelled(true);
        }
        pd.setLastitemwsitch(System.currentTimeMillis());
    }
}
