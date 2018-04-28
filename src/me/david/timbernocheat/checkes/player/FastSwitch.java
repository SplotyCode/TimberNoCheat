package me.david.timbernocheat.checkes.player;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class FastSwitch extends Check {

    private final long delay;
    public FastSwitch(){
        super("FastSwitch", Category.PLAYER);
        delay = getLong("delay");
    }

    @EventHandler
    public void onswitch(PlayerItemHeldEvent e){
        final Player p = e.getPlayer();
        if(!TimberNoCheat.getCheckManager().isvalid_create(p)){
            return;
        }
        PlayerData pd = TimberNoCheat.getCheckManager().getPlayerdata(p);
        long delay = System.currentTimeMillis()-pd.getLastItemwSitch();
        if(delay < this.delay){
            updateVio(this, p, 1, " §6DELAY: §b" + delay);
            //TimberNoCheat.getCheckManager().notify(this, p, " §6DELAY: §b" + delay);
            //pd.setLastItemwSitch(System.currentTimeMillis()-15000L);
            e.setCancelled(true);
        }
        pd.setLastItemwSitch(System.currentTimeMillis());
    }
}
