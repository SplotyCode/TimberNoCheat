package me.david.timbernocheat.checkes.interact;

import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.api.utils.BlockUtil;
import me.david.timbernocheat.checkbase.CheckManager;
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
    public void onPlace(final BlockPlaceEvent event){
        final Player player = event.getPlayer();
        if(!CheckManager.getInstance().isvalid_create(player)){
            return;
        }
        if((player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR ) && illegalBlocks(player.getLocation()).contains(event.getBlockPlaced().getLocation())){
            updateVio(this, player, 1);
            event.setCancelled(true);
        }
    }

    private ArrayList<Location> illegalBlocks(Location loc){
        ArrayList<Location> locs = new ArrayList<>();
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