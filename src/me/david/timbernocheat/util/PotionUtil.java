package me.david.timbernocheat.util;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class PotionUtil {

    /* Get the PotionEffectAmplifier from a Player ant the PotionEffectType */
    public static int getPotionEffectAmplifier(final Player p, final PotionEffectType type) {
        if (!p.hasPotionEffect(type)) {
            return -1;
        }
        int max = -1;
        for (final PotionEffect e : p.getActivePotionEffects()){
            if (e.getType().equals(type)){
                max = Math.max(max, e.getAmplifier());
            }
        }
        return max;
    }
}
