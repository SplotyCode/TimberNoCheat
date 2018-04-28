package me.david.timbernocheat.checkes.player;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

public class FreeCam extends Check {

    public FreeCam(){
        super("FreeCam", Category.PLAYER);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        final Player player = event.getPlayer();
        if(!CheckManager.getInstance().isvalid_create(player) || event.isCancelled()){
            return;
        }
        boolean isValid = false;
        final Location scanLocation = event.getClickedBlock().getRelative(event.getBlockFace()).getLocation();
        final double x = scanLocation.getX();
        final double y = scanLocation.getY();
        final double z = scanLocation.getZ();
        for (double sX = x; sX < x + 2.0; sX += 1.0) {
            for (double sY = y; sY < y + 2.0; sY += 1.0) {
                for (double sZ = z; sZ < z + 2.0; sZ += 1.0) {
                    Location relative = new Location(scanLocation.getWorld(), sX, sY, sZ);
                    List<Location> blocks = getrayTrace(player.getLocation(), relative);
                    boolean valid = true;
                    for (Location l : blocks)
                        if (!checksolid(l.getBlock().getType()))
                        valid = false;
                    if (valid)
                    isValid = true;
                }
            }
        }
        if (!isValid && player.getItemInHand().getType() != Material.ENDER_PEARL) {
            event.setCancelled(true);
            updateVio(this, player, 1);
        }
    }
    private List<Location> getrayTrace(Location f, Location t) {
        if (f.getWorld() != t.getWorld() || f.distance(t) > 10.0)
            return new ArrayList<Location>();
        ArrayList<Location> list = new ArrayList<Location>();
        double x = f.getX();
        double y = f.getY() + 1.62;
        double z = f.getZ();
        boolean scanning = true;
        while (scanning) {
            list.add(new Location(f.getWorld(), x, y, z));
            x += (t.getX() - x) / 10.0;
            y += (t.getY() - y) / 10.0;
            z += (t.getZ() - z) / 10.0;
            if (Math.abs(x - t.getX()) >= 0.01 || Math.abs(y - t.getY()) >= 0.01 || Math.abs(z - t.getZ()) >= 0.01) continue;
            scanning = false;
        }
        return list;
    }
    private boolean checksolid(Material m){
        for (int id : new int[]{355, 196, 194, 197, 195, 193, 64, 96, 187, 184, 186, 107, 185, 183, 192, 189, 139, 191, 85, 101, 190, 113, 188, 160, 102, 163, 157, 0, 145, 49, 77, 135, 108, 67, 164, 136, 114, 156, 180, 128, 143, 109, 134, 53, 126, 44, 416, 8, 425, 138, 26, 397, 372, 13, 135, 117, 108, 39, 81, 92, 71, 171, 141, 118, 144, 54, 139, 67, 127, 59, 115, 330, 164, 151, 178, 32, 28, 93, 94, 175, 122, 116, 130, 119, 120, 51, 140, 147, 154, 148, 136, 65, 10, 69, 31, 105, 114, 372, 33, 34, 36, 29, 90, 142, 27, 104, 156, 66, 40, 330, 38, 180, 149, 150, 75, 76, 55, 128, 6, 295, 323, 63, 109, 78, 88, 134, 176, 11, 9, 44, 70, 182, 83, 50, 146, 132, 131, 106, 177, 68, 8, 111, 30, 72, 53, 126, 37}) {
            if (m.getId() == id)
            return true;
        }
        return false;
    }
}
