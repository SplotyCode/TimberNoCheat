package me.david.TimberNoCheat.checkes.interact;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.api.utils.BlockUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;

public class AirPlace extends Check {

    private final boolean diagonals;
    private boolean cancel;
    public AirPlace() {
        super("AirPlace", Category.INTERACT);
        diagonals = getBoolean("diagonals");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlace(BlockPlaceEvent e){
        if(!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer())){
            return;
        }
        if(check(e.getBlockPlaced()) || e.getBlockAgainst() == null){
            if(cancel)e.setCancelled(true);
            updatevio(this, e.getPlayer(), 1);
        }
    }

    private boolean check(Block b) {
        for (Block block : BlockUtil.getSurrounding(b, diagonals))
            if (block != null && block.getType() != Material.AIR)
                return false;
        return true;
    }
}
