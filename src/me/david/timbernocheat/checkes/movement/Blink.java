package me.david.timbernocheat.checkes.movement;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Blink extends Check {

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
        TimberNoCheat.instance.getMoveprofiler().start("Blink");
        double dis = e.getTo().distance(e.getFrom());
        if(dis > maxrangemove && maxrangemove != -1){
            e.setCancelled(true);
            updateVio(this, e.getPlayer(), dis-maxrangemove, " §6DISTANCE: §b" + dis, " §6TYPE: §bMOVE");
        }
        TimberNoCheat.instance.getMoveprofiler().end();
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e){
        if(!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer())){
            return;
        }
        double dis = e.getTo().distance(e.getFrom());
        if(dis > maxrangemove && maxrangetp != -1 && e.getCause() == PlayerTeleportEvent.TeleportCause.UNKNOWN){
            e.setCancelled(true);
            updateVio(this, e.getPlayer(), dis-maxrangemove, " §6DISTANCE: §b" + dis, " §6TYPE: §bTELEPORT");
        }
    }
}
