package me.david.timbernocheat.checkes.player;

import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.PlayerData;
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
        if (!CheckManager.getInstance().isvalid_create(e.getPlayer())) {
            return;
        }
        PlayerData pd = CheckManager.getInstance().getPlayerdata(e.getPlayer());
        long delay = System.currentTimeMillis()-pd.getLastDead();
        //message nicht vergessen :D wenn neie config
        if(mdelay > delay){
            pd.setLastDead(System.currentTimeMillis()-15000L);
            updateVio(this, e.getPlayer(), delay-mdelay, " §6DELAY: §b" + delay, " §6MAXDELAY: §b" + mdelay);
            //CheckManager.getInstance().notify(this, e.getPlayer(), " §6DELAY: §b" + delay, " §6MAXDELAY: §b" + mdelay);
        }
    }
    @EventHandler
    public void onDead(PlayerDeathEvent e){
        if (!CheckManager.getInstance().isvalid_create(e.getEntity())) {
            return;
        }
        CheckManager.getInstance().getPlayerdata(e.getEntity()).setLastDead(System.currentTimeMillis());
    }
}
