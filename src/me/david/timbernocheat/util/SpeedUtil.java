package me.david.timbernocheat.util;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpeedUtil {

    /* Get the maximum y axis */
    public static double getMaxVertical(Player p, boolean inwater) {
        double speed = 0.5;
        if (p.hasPotionEffect(PotionEffectType.JUMP))
            speed = speed + ((getPotionEffectLevel(p, PotionEffectType.JUMP)) * 0.11);
        if (inwater) speed = 0.3401;
        if (p.getVelocity().getY() > 0) speed = speed + (p.getVelocity().getY());
        return speed;

    }

    /* Return the Level of a Affect */
    public static int getPotionEffectLevel(Player p, PotionEffectType pet) {
        for (PotionEffect pe : p.getActivePotionEffects())
            if (pe.getType().getName().equals(pet.getName()))
                return pe.getAmplifier() + 1;
        return -1;
    }


}
