package me.david.timbernocheat.checkes.interact;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import me.david.api.utils.BlockUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakCovered extends Check {

    public BreakCovered() {
        super("BreakCovered", Category.INTERACT);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer())) return;
        if (isbed(e.getBlock())) {
            if (checkbed(e.getBlock())) {
                e.setCancelled(true);
                updatevio(this, e.getPlayer(), 1, " §6BED: §bTRUE");
            }
        } else if (check(e.getBlock())) {
            e.setCancelled(true);
            updatevio(this, e.getPlayer(), 1, " §6BED: §bFALSE");
        }
    }

    private boolean checkbed(Block b) {
        return getbedblockface(b) != null && check(getbedblockface(b)) && check(b);
    }

    private Block getbedblockface(Block b) {
        int i = 0;
        Block b1 = null;
        for (Block blocks : BlockUtil.getSurrounding(b, false))
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

    public boolean check(Block b) {
        for (Block blocks : BlockUtil.getSurrounding(b, false))
            if (BlockUtil.TRANSPARENT_MATERIALS.contains(blocks.getType()))
                return false;
        return true;
    }
}