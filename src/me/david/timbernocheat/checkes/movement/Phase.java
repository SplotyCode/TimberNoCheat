package me.david.timbernocheat.checkes.movement;

import me.david.api.Api;
import me.david.api.nms.AABBBox;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import me.david.api.utils.BlockUtil;
import me.david.timbernocheat.checkmanager.PlayerData;
import me.david.timbernocheat.checktools.MaterialHelper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.material.Gate;

public class Phase extends Check {

    public Phase(){
        super("Phase", Category.MOVEMENT);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(player)) return;
        TimberNoCheat.instance.getMoveprofiler().start("Phase");
        AABBBox playerBox = Api.getNms().getBoundingBox(player);
        int blocks = 0;
        for(Block block : BlockUtil.getBlocksAround(event.getTo(), 3)){
            AABBBox boundingBox = Api.getNms().getBoundingBox(block);
            if(MaterialHelper.GATES.contains(block.getType()) && ((Gate) block).isOpen())continue;
            if(playerBox.intersectsWith(boundingBox)) blocks++;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(player);
        if(blocks == 0) pd.setLastPhaseOkay(event.getTo());
        if(updateVio(this, player, blocks*1.2)) player.teleport(pd.getLastPhaseOkay());
        TimberNoCheat.instance.getMoveprofiler().end();
    }
}