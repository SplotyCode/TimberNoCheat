package me.david.timbernocheat.util;

import me.david.timbernocheat.checktools.MaterialHelper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public final class MovingUtils {

    public static double groundDistance(Player player){
        Location location = player.getLocation().clone();
        while (!location.getBlock().getType().isSolid() && !MaterialHelper.LIQUID.contains(location.getBlock().getType()))
            location.subtract(0, 0.1, 0);
        return player.getLocation().getY()-location.getY();
    }

    /* Get the maximum y axis */
    public static double getMaxVertical(Player p, boolean inwater) {
        double speed = 0.5;
        if (p.hasPotionEffect(PotionEffectType.JUMP))
            speed = speed + ((CheckUtils.getPotionEffectLevel(p, PotionEffectType.JUMP)) * 0.11);
        if (inwater) speed = 0.3401;
        if (p.getVelocity().getY() > 0) speed = speed + (p.getVelocity().getY());
        return speed;
    }

    public static boolean touchSlabs(Location location){
        for(Material stair : MaterialHelper.SLAPS)
            if(CheckUtils.doesColidateWithMaterial(stair, location))
                return true;
        return false;
    }

    public static boolean touchStair(Location location){
        for(Material stair : MaterialHelper.STAIRS)
            if(CheckUtils.doesColidateWithMaterial(stair, location))
                return true;
        return false;
    }
}
