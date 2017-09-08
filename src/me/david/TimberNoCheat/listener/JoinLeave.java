package me.david.TimberNoCheat.listener;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checktools.Velocity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeave implements Listener{
    /*
    Permission Cash
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(e.getPlayer().hasPermission("tnc.notify"))
            TimberNoCheat.checkmanager.notify.add(e.getPlayer());
        if(!e.getPlayer().hasPermission("tnc.notcheck") && !TimberNoCheat.checkmanager.tocheck.contains(e.getPlayer()))
            TimberNoCheat.checkmanager.tocheck.add(e.getPlayer());
    }
    /*
    Permission Cash
    Remove PLayers Velocity
     */
    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        if(TimberNoCheat.checkmanager.notify.contains(e.getPlayer())){
            TimberNoCheat.checkmanager.notify.remove(e.getPlayer());
        }
        if(Velocity.velocity.containsKey(e.getPlayer().getUniqueId())){
            Velocity.velocity.remove(e.getPlayer().getUniqueId());
        }
    }
}
