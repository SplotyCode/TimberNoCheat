package me.david.TimberNoCheat.checkes.movement;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class Derb extends Check{

    public Derb(){
        super("Derb", Category.MOVEMENT);
    }
    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if(!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer())){
            return;
        }
        if((e.getTo().getPitch() > TimberNoCheat.instance.settings.movement_derb_maxpitch && TimberNoCheat.instance.settings.movement_derb_maxpitch != -1) || (e.getTo().getPitch() < TimberNoCheat.instance.settings.movement_derb_minpitch && TimberNoCheat.instance.settings.movement_derb_minpitch != -1)){
            TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6PITCH: §b" + e.getTo().getPitch());
            e.setCancelled(true);
            TimberNoCheat.checkmanager.execute(TimberNoCheat.instance.settings.movement_derb_cmd, e.getPlayer().getName());
        }
    }
}
