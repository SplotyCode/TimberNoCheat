package me.david.timbernocheat.checkes.other;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Grifing extends Check {

    public Grifing(){
        super("Grifing", Category.OTHER);
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(!TimberNoCheat.getCheckManager().isvalid_create(e.getPlayer())){
            return;
        }
        if (e.getItem() == null || e.getItem().getType() == Material.AIR || (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (e.getItem().getType() == Material.EXPLOSIVE_MINECART) {
            e.setCancelled(true);
            updateVio(this, e.getPlayer(), 1, " §6CHECK: §bTntMineCard");
           // TimberNoCheat.getCheckManager().notify(this, e.getPlayer(), " §6CHECK: §bTntMineCard");
        }
    }
}
