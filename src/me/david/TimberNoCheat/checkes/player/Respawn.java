package me.david.TimberNoCheat.checkes.player;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class Respawn extends Check {

    public Respawn(){
        super("Respawn", Category.PLAYER);
    }

    @EventHandler
    public void onRespwan(PlayerRespawnEvent e){
        if (!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer())) {
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(e.getPlayer());
        long delay = System.currentTimeMillis()-pd.getLastdead();
        //message nicht vergessen :D wenn neie config
        if(850 > delay){
            pd.setLastdead(System.currentTimeMillis()-15000L);
            TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6DELAY: §b" + delay, " §6MAXDELAY: §b850");
        }
    }
    @EventHandler
    public void onDead(PlayerDeathEvent e){
        if (!TimberNoCheat.checkmanager.isvalid_create(e.getEntity())) {
            return;
        }
        TimberNoCheat.checkmanager.getPlayerdata(e.getEntity()).setLastdead(System.currentTimeMillis());
    }
}
