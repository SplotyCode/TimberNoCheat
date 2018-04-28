package me.david.timbernocheat.listener;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.runnable.Velocity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeave implements Listener{

    /* Removes Players Velocity And clears the PlayerData/Permissioncache */
    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        final Player player = event.getPlayer();
        if(Velocity.velocity.containsKey(player.getUniqueId()))
            Velocity.velocity.remove(player.getUniqueId());
        PlayerData data = CheckManager.getInstance().getPlayerdata(player);
        if(TimberNoCheat.getInstance().isClearPlayerData() && data != null)
            CheckManager.getInstance().getPlayerData().remove(data);

        //Normally the cache shut automatically clear itself this is not the case...
        TimberNoCheat.getInstance().permissioncache.clearAll();
    }
}
