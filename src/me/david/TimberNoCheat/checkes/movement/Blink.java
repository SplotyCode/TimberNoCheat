package me.david.TimberNoCheat.checkes.movement;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Blink extends Check{

    private final double maxrangetp;
    private final double maxrangemove;
    public Blink(){
        super("Blink", Category.MOVEMENT);
        maxrangetp = getDouble("maxrangetp");
        maxrangemove = getDouble("maxrangemove");
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if(!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer())){
            return;
        }
        double dis = e.getTo().distance(e.getFrom());
        if(dis > maxrangemove && maxrangemove != -1){
            e.setCancelled(true);
            updatevio(this, e.getPlayer(), dis-maxrangemove, " §6DISTANCE: §b" + dis, " §6TYPE: §bMOVE");
        }
    }
    @EventHandler
    public void onTeleport(PlayerTeleportEvent e){
        if(!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer())){
            return;
        }
        double dis = e.getTo().distance(e.getFrom());
        if(dis > maxrangemove && maxrangetp != -1){
            e.setCancelled(true);
            updatevio(this, e.getPlayer(), dis-maxrangemove, " §6DISTANCE: §b" + dis, " §6TYPE: §bTELEPORT");
        }
    }
}
