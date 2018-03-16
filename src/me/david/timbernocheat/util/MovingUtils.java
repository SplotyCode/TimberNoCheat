package me.david.timbernocheat.util;

import me.david.timbernocheat.checktools.MaterialHelper;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public final class MovingUtils {

    public static double groundDistance(Player player){
        Location location = player.getLocation().clone();
        while (!location.getBlock().getType().isSolid() && !MaterialHelper.LIQUID.contains(location.getBlock().getType()))
            location.subtract(0, 0.1, 0);
        return player.getLocation().getY()-location.getY();
    }
}
