package me.david.timbernocheat.checkes.interact;

import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.api.utils.BlockUtil;
import me.david.timbernocheat.checkbase.CheckManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakCovered extends Check {

    public BreakCovered() {
        super("BreakCovered", Category.INTERACT);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!CheckManager.getInstance().isvalid_create(event.getPlayer())) return;
        if (isbed(event.getBlock())) {
            if (checkBed(event.getBlock())) {
                event.setCancelled(true);
                updateVio(this, event.getPlayer(), 1, " §6BED: §bTRUE");
            }
        } else if (check(event.getBlock())) {
            event.setCancelled(true);
            updateVio(this, event.getPlayer(), 1, " §6BED: §bFALSE");
        }
    }

    private boolean checkBed(Block block) {
        return getBedBlockFace(block) != null && check(getBedBlockFace(block)) && check(block);
    }

    private Block getBedBlockFace(Block block) {
        int i = 0;
        Block b1 = null;
        for (Block blocks : BlockUtil.getSurrounding(block, false))
            if (isbed(blocks)) {
                i++;
                b1 = blocks;
            }
        if (i != 1 || b1 == null) return null;
        return b1;
    }

    private boolean isbed(Block b) {
        return b.getType() == Material.BED || b.getType() == Material.BED_BLOCK;
    }

    private boolean check(Block b) {
        for (Block blocks : BlockUtil.getSurrounding(b, false))
            if (BlockUtil.TRANSPARENT_MATERIALS.contains(blocks.getType()))
                return false;
        return true;
    }
}