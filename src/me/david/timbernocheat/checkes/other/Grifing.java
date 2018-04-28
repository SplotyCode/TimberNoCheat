package me.david.timbernocheat.checkes.other;

import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Grifing extends Check {

    public Grifing(){
        super("Grifing", Category.OTHER);
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(!CheckManager.getInstance().isvalid_create(event.getPlayer())){
            return;
        }
        if (event.getItem() == null || event.getItem().getType() == Material.AIR || (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (event.getItem().getType() == Material.EXPLOSIVE_MINECART) {
            event.setCancelled(true);
            updateVio(this, event.getPlayer(), 1, " §6CHECK: §bTntMineCard");
           // CheckManager.getInstance().notify(this, event.getPlayer(), " §6CHECK: §bTntMineCard");
        }
    }
}
