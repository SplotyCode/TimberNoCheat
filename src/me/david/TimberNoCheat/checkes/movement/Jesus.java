package me.david.TimberNoCheat.checkes.movement;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import me.david.TimberNoCheat.runnable.Velocity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.NumberConversions;

public class Jesus extends Check {

    final double sensitivity;
    public Jesus(){
        super("Jesus", Category.MOVEMENT);
        sensitivity = getDouble("sensitivity");
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onMove(PlayerMoveEvent e) {
        final Player p = e.getPlayer();
        if (!TimberNoCheat.checkmanager.isvalid_create(p) || e.isCancelled())
            return;
        TimberNoCheat.instance.getMoveprofiler().start("Jesus");
        if(p.getAllowFlight() || p.getVehicle() != null || !p.getNearbyEntities(1,1,1).isEmpty() || Velocity.velocity.containsKey(p.getUniqueId())){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        long ms = System.currentTimeMillis() - pd.getJesus();
        if(cantStandAtWater(p.getWorld().getBlockAt(p.getLocation())) && isHoveringOverWater(p.getLocation()) && !isFullyInWater(p.getLocation())){
            //TimberNoCheat.checkmanager.notify(this, p, " §6DELAY: §b" + ms);
            updatevio(this, p, ms/sensitivity, " §6DEBUGDATA: §b" + ms);
            e.setCancelled(true);
            pd.setJesus(System.currentTimeMillis());
        }
        TimberNoCheat.instance.getMoveprofiler().end();
    }
    public static boolean cantStandAtWater(Block block) {
        Block otherBlock = block.getRelative(BlockFace.DOWN);
        boolean isHover = block.getType() == Material.AIR;
        boolean n = otherBlock.getRelative(BlockFace.NORTH).getType() == Material.WATER || otherBlock.getRelative(BlockFace.NORTH).getType() == Material.STATIONARY_WATER;
        boolean s = otherBlock.getRelative(BlockFace.SOUTH).getType() == Material.WATER || otherBlock.getRelative(BlockFace.SOUTH).getType() == Material.STATIONARY_WATER;
        boolean e = otherBlock.getRelative(BlockFace.EAST).getType() == Material.WATER || otherBlock.getRelative(BlockFace.EAST).getType() == Material.STATIONARY_WATER;
        boolean w = otherBlock.getRelative(BlockFace.WEST).getType() == Material.WATER || otherBlock.getRelative(BlockFace.WEST).getType() == Material.STATIONARY_WATER;
        boolean ne = otherBlock.getRelative(BlockFace.NORTH_EAST).getType() == Material.WATER || otherBlock.getRelative(BlockFace.NORTH_EAST).getType() == Material.STATIONARY_WATER;
        boolean nw = otherBlock.getRelative(BlockFace.NORTH_WEST).getType() == Material.WATER || otherBlock.getRelative(BlockFace.NORTH_WEST).getType() == Material.STATIONARY_WATER;
        boolean se = otherBlock.getRelative(BlockFace.SOUTH_EAST).getType() == Material.WATER || otherBlock.getRelative(BlockFace.NORTH).getType() == Material.STATIONARY_WATER;
        boolean sw = otherBlock.getRelative(BlockFace.SOUTH_WEST).getType() == Material.WATER || otherBlock.getRelative(BlockFace.SOUTH_WEST).getType() == Material.STATIONARY_WATER;
        return n && s && e && w && ne && nw && se && sw && isHover;
    }
    public static double fixXAxis(double x) {
        double touchedX = x;
        double rem = x - (double)Math.round(x) + 0.01D;
        if(rem < 0.3D) {
            touchedX = (double)(NumberConversions.floor(x) - 1);
        }

        return touchedX;
    }
    public static boolean isFullyInWater(Location player) {
        double touchedX = fixXAxis(player.getX());
        return (new Location(player.getWorld(), touchedX, player.getY(), (double)player.getBlockZ())).getBlock().isLiquid() && (new Location(player.getWorld(), touchedX, (double)Math.round(player.getY()), (double)player.getBlockZ())).getBlock().isLiquid();
    }
    public static boolean isHoveringOverWater(Location player) {
        for(int i = player.getBlockY(); i > player.getBlockY() - 25; --i) {
            Block newloc = (new Location(player.getWorld(), player.getBlockX(), i, player.getBlockZ())).getBlock();
            if(newloc.getType() != Material.AIR) {
                return newloc.isLiquid();
            }
        }
        return false;
    }

}
