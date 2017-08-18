package me.david.TimberNoCheat.checkes.movement;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Blink extends Check{

    public Blink(){
        super("Blink", Category.MOVEMENT);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if(!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer())){
            return;
        }
        double dis = e.getTo().distance(e.getFrom());
        if(e.getTo().distance(e.getFrom()) > TimberNoCheat.instance.settings.movement_blink_distance && TimberNoCheat.instance.settings.movement_blink_distance != -1){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6DISTANCE: §b" + dis, " §6TYPE: §bmove");
        }
    }
    @EventHandler
    public void onTeleport(PlayerTeleportEvent e){
        if(!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer())){
            return;
        }
        double dis = e.getTo().distance(e.getFrom());
        if(e.getTo().distance(e.getFrom()) > TimberNoCheat.instance.settings.movement_blink_distance && TimberNoCheat.instance.settings.movement_blink_distance != -1){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6DISTANCE: §b" + dis, " §6TYPE: §bTELEPORT");
        }
    }
}
