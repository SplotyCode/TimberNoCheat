package me.david.timbernocheat.listener;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.PlayerData;
import me.david.timbernocheat.runnable.Velocity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeave implements Listener{

    /*Removes Players Velocity And clears the PlayerData */
    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        if(Velocity.velocity.containsKey(e.getPlayer().getUniqueId()))
            Velocity.velocity.remove(e.getPlayer().getUniqueId());
        PlayerData data = TimberNoCheat.checkmanager.getPlayerdata(e.getPlayer());
        if(TimberNoCheat.instance.isClearPlayerData() && data != null)
            TimberNoCheat.checkmanager.getPlayerdata().remove(data);
    }
}