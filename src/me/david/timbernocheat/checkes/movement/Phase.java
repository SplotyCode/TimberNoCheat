package me.david.timbernocheat.checkes.movement;

import me.david.api.Api;
import me.david.api.nms.AABBBox;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.api.utils.BlockUtil;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.checktools.MaterialHelper;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.material.Gate;

public class Phase extends Check {

    //private final float MAX_DIFF;
    private final float BLOCK_XZ, BLOCK_Y, PLAYER_Y, PLAYER_ZX;

    public Phase(){
        super("Phase", Category.MOVEMENT);
      //  MAX_DIFF = getFloat("maxdiff");
        BLOCK_XZ = getFloat("blockxz");
        BLOCK_Y = getFloat("blocky");
        PLAYER_ZX = getFloat("playerxz");
        PLAYER_Y = getFloat("playery");
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if(!TimberNoCheat.getCheckManager().isvalid_create(player)) return;
        TimberNoCheat.getInstance().getMoveprofiler().start("Phase");
        AABBBox playerBox = Api.getNms().getBoundingBox(player);
        playerBox = playerBox.expand(PLAYER_ZX, PLAYER_Y, PLAYER_ZX);
        boolean diff = false;
        for(Block block : BlockUtil.getBlocksAround(event.getTo(), 3)){
            if(block == null || !block.getType().isSolid())continue;
            AABBBox boundingBox = Api.getNms().getBoundingBox(block);
            boundingBox = boundingBox.expand(BLOCK_XZ, BLOCK_Y, BLOCK_XZ);
            if(boundingBox == null)continue;
            if(MaterialHelper.GATES.contains(block.getType()) && ((Gate) block).isOpen())continue;
            if(playerBox.intersectsWith(boundingBox)) diff = true;
            break;
        }
        PlayerData pd = TimberNoCheat.getCheckManager().getPlayerdata(player);
        if(!diff) pd.setLastPhaseOkay(event.getTo());
        else
            if(updateVio(this, player, 1.2, " §6Diff: §b" + diff   )) {
                if(pd.getLastPhaseOkay() == null)
                    event.setCancelled(true);
                else {
                    player.teleport(pd.getLastPhaseOkay());
                    pd.setLastPhaseOkay(null);
                }
            }
        TimberNoCheat.getInstance().getMoveprofiler().end();
    }

    //TODO: currently not working
    private double diff(AABBBox first, AABBBox second){
        double diff = 0;
        double[] values = new double[]{
                first.maxX-second.minX,
                second.maxX-first.minX,
                first.maxY-second.minY,
                second.maxY-first.minY,
                first.maxZ-second.minZ,
                second.maxZ-first.minZ,
        };
        for(double val : values)
            if(val > 0)diff+=val;
        return diff;
    }
}