package me.david.timbernocheat.checkes.player;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
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
    public void onswitch(PlayerItemHeldEvent event){
        final Player player = event.getPlayer();
        if(!CheckManager.getInstance().isvalid_create(player)){
            return;
        }
        PlayerData pd = CheckManager.getInstance().getPlayerdata(player);
        long delay = System.currentTimeMillis()-pd.getLastItemwSitch();
        if(delay < this.delay){
            updateVio(this, player, 1, " §6DELAY: §b" + delay);
            //CheckManager.getInstance().notify(this, player, " §6DELAY: §b" + delay);
            //pd.setLastItemwSitch(System.currentTimeMillis()-15000L);
            event.setCancelled(true);
        }
        pd.setLastItemwSitch(System.currentTimeMillis());
    }
}
