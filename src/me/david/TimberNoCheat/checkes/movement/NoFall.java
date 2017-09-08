package me.david.TimberNoCheat.checkes.movement;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
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
        if (!TimberNoCheat.checkmanager.isvalid_create(p) || e.isCancelled()) {
            return;
        }
        int blocks = 0;
        boolean found = false;
        while(!found){
           if(p.getLocation().clone().subtract(0, blocks, 0).getBlock().getType() != Material.AIR)
               found = true;
           else blocks++;
        }
        if(p.getFallDistance() < blocks && blocks<2){
            updatevio(this, p, blocks-p.getFallDistance());
            e.setCancelled(true);
        }
    }
}
