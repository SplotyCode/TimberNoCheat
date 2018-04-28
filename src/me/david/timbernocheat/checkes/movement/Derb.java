package me.david.timbernocheat.checkes.movement;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
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
        if(!CheckManager.getInstance().isvalid_create(e.getPlayer())){
            return;
        }
        TimberNoCheat.getInstance().getMoveprofiler().start("Derb");
        if((e.getTo().getPitch() > maxpitch && maxpitch != -1) || (e.getTo().getPitch() < minpitch && minpitch != -1)){
            e.setCancelled(true);
            updateVio(this, e.getPlayer(), 1);
        }
        TimberNoCheat.getInstance().getMoveprofiler().end();
    }
}
