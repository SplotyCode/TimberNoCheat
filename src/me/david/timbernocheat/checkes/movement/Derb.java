package me.david.timbernocheat.checkes.movement;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class Derb extends Check {

    private final double minpitch;
    private final double maxpitch;
    public Derb(){
        super("Derb", Category.MOVEMENT);
        minpitch = getDouble("minpitch");
        maxpitch = getDouble("maxpitch");
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if(!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer())){
            return;
        }
        TimberNoCheat.instance.getMoveprofiler().start("Derb");
        if((e.getTo().getPitch() > maxpitch && maxpitch != -1) || (e.getTo().getPitch() < minpitch && minpitch != -1)){
            e.setCancelled(true);
            updateVio(this, e.getPlayer(), 1);
        }
        TimberNoCheat.instance.getMoveprofiler().end();
    }
}
