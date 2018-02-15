package me.david.TimberNoCheat.debug.debuggers;

import me.david.TimberNoCheat.checktools.MaterialHelper;
import me.david.TimberNoCheat.debug.Debuggers;
import me.david.TimberNoCheat.debug.ExternalDebuger;
import me.david.api.Api;
import me.david.api.nms.AABBBox;
import me.david.api.utils.player.PlayerUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class OnGround extends ExternalDebuger {

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        final Player player = event.getPlayer();
        boolean bool1 = PlayerUtil.isOnGround(player);
        double dis = (player.getPlayer().getLocation().getY() - player.getPlayer().getLocation().getBlockY());
        boolean bool2 = dis % getRelativeBlockHeight(getGroundMaterial(player.getLocation())) == 0 || dis <= 0.002;
        boolean bool3 = variant3(player);
        send(Debuggers.ONGROUND, "1=" + bool1 + " 2=" + bool2 + " 3=" + bool3, bool1, bool2, bool3);
    }

    private boolean variant3(Player player){
        boolean onGround = false;
        AABBBox playerBox = Api.instance.nms.getBoundingBox(player).expand(0, 0.15, 0);
        for(int x = player.getLocation().getBlockX()-1; x<player.getLocation().getBlockX()+3; x++)
            for(int z = player.getLocation().getBlockZ()-1; z<player.getLocation().getBlockZ()+3; z++)
                for(double y = 0.1;y<1.1;y+=0.1) {
                    Location loc = new Location(player.getWorld(), x, player  .getLocation().getY() - y, z);
                    Block block = loc.getBlock();
                    if(block.getType() != Material.AIR && playerBox.intersectsWith(Api.instance.nms.getBoundingBox(block)))
                        onGround = true;
                }
        return onGround;
    }

    private Material getGroundMaterial(Location los){
        while (!los.getBlock().getType().isSolid() && !MaterialHelper.LIQUID.contains(los.getBlock().getType())) los.subtract(0, 0.1, 0);
        return los.getBlock().getType();
    }

    private double getRelativeBlockHeight(Material material) {
        switch (material) {
            case ACACIA_FENCE:
            case ACACIA_FENCE_GATE:
            case BIRCH_FENCE:
            case BIRCH_FENCE_GATE:
            case DARK_OAK_FENCE:
            case DARK_OAK_FENCE_GATE:
            case FENCE:
            case FENCE_GATE:
            case IRON_FENCE:
            case JUNGLE_FENCE:
            case JUNGLE_FENCE_GATE:
            case NETHER_FENCE:
            case SPRUCE_FENCE:
            case SPRUCE_FENCE_GATE:
            case COBBLE_WALL:
                return 0.5;
            case SOIL:
            case CACTUS:
                return 0.9375;
            case SOUL_SAND:
            case CHEST:
            case ENDER_CHEST:
            case TRAPPED_CHEST:
                return 0.875;
            case ENCHANTMENT_TABLE:
                return 0.75;
            case BED_BLOCK:
                return 0.5625;
            case SKULL:
                return 0.25;
            case WATER_LILY:
                return 0.09375;
            default:
                return 0.0625;
        }
    }
}
