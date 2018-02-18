package me.david.timbernocheat.checkes.player;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import me.david.timbernocheat.checkmanager.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class Respawn extends Check {

    private final long mdelay;
    public Respawn(){
        super("Respawn", Category.PLAYER);
        mdelay = getLong("delay");
    }

    @EventHandler
    public void onRespwan(PlayerRespawnEvent e){
        if (!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer())) {
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(e.getPlayer());
        long delay = System.currentTimeMillis()-pd.getLastdead();
        //message nicht vergessen :D wenn neie config
        if(mdelay > delay){
            pd.setLastdead(System.currentTimeMillis()-15000L);
            updatevio(this, e.getPlayer(), delay-mdelay, " §6DELAY: §b" + delay, " §6MAXDELAY: §b" + mdelay);
            //TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6DELAY: §b" + delay, " §6MAXDELAY: §b" + mdelay);
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
