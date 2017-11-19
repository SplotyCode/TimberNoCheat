package me.david.TimberNoCheat.checkes.interact;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.api.utils.BlockUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;

public class Scaffold extends Check {

    public Scaffold(){
        super("Scaffold", Category.INTERACT);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        final Player p = e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        if((p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR ) && illegalblocks(p.getLocation()).contains(e.getBlockPlaced().getLocation())){
            updatevio(this, p, 1);
            e.setCancelled(true);
        }
    }

    private ArrayList<Location> illegalblocks(Location loc){
        ArrayList<Location> locs = new ArrayList<Location>();
        loc = loc.subtract(0, 1, 0);
        locs.add(loc.clone().add(2, 0, 0));
        locs.add(loc.clone().add(-2, 0, 0));
        locs.add(loc.clone().add(0, 0, 2));
        locs.add(loc.clone().add(0, 0, -2));
        for(Location locc : (ArrayList<Location>)locs.clone()) {
            ArrayList<Block> blocks = BlockUtil.getSurrounding(locc.getBlock(), false);
            blocks.remove(0);
            for(Block b : BlockUtil.getSurrounding(loc.getBlock(), false))
                if(blocks.contains(b))blocks.remove(b);
            for (Block b : blocks)
                if (b != null && b.getType() != Material.AIR)
                    locs.remove(locc);
        }
        return locs;
    }
}