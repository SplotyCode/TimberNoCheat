package me.david.timbernocheat.checkes.movement;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class NoFall extends Check {

    public NoFall(){
        super("NoFall", Category.MOVEMENT);
    }

    @EventHandler
    public void onMode(PlayerMoveEvent e){
        final Player p = e.getPlayer();
        if (!TimberNoCheat.getCheckManager().isvalid_create(p) || e.isCancelled()) {
            return;
        }
        TimberNoCheat.getInstance().getMoveprofiler().start("NoFall");
        int blocks = 0;
        boolean found = false;
        while(!found){
           if(p.getLocation().clone().subtract(0, blocks, 0).getBlock().getType() != Material.AIR)
               found = true;
           else blocks++;
        }
        if(p.getFallDistance() < blocks && blocks<2){
            updateVio(this, p, blocks-p.getFallDistance());
            e.setCancelled(true);
        }
        TimberNoCheat.getInstance().getMoveprofiler().end();
    }
}
