package me.david.TimberNoCheat.checkes.movement;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class Derb extends Check{

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
        //System.out.println(e.getTo().getPitch());
        if((e.getTo().getPitch() > maxpitch && maxpitch != -1) || (e.getTo().getPitch() < minpitch && minpitch != -1)){
            //TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6PITCH: §b" + e.getTo().getPitch());
            e.setCancelled(true);
            updatevio(this, e.getPlayer(), 1);
        }
        TimberNoCheat.instance.getMoveprofiler().end();
    }
}
