package me.david.TimberNoCheat.checktools;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpeedUtil {

    public static double getMaxVertical(Player p, boolean inwater) {
        double speed = 0.5;
        if (p.hasPotionEffect(PotionEffectType.JUMP))
            speed = speed + ((getPotionEffectLevel(p, PotionEffectType.JUMP)) * 0.11);
        if (inwater) speed = 0.3401;
        if (p.getVelocity().getY() > 0) speed = speed + (p.getVelocity().getY());
        return speed;

    }

    private static int getPotionEffectLevel(Player p, PotionEffectType pet) {
        for (PotionEffect pe : p.getActivePotionEffects())
            if (pe.getType().getName().equals(pet.getName()))
                return pe.getAmplifier() + 1;
        return 0;
    }

    private double getMaxHight(Player p) {
        double d;
        if (p.hasPotionEffect(PotionEffectType.JUMP)) {
            int level = getPotionEffectLevel(p, PotionEffectType.JUMP);
            switch (level){
                case 1:
                    d = 1.9;
                    break;
                case 2:
                    d = 2.7;
                    break;
                case 3:
                    d = 3.36;
                    break;
                case 4:
                    d = 4.22;
                    break;
                case 5:
                    d = 5.16;
                    break;
                default:
                    d = (level) + 1;
                    break;
            }
            d = d + 1.35;
        } else d = 1.35;
        return d;

    }
}
