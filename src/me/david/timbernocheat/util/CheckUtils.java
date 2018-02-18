package me.david.timbernocheat.util;

import me.david.api.utils.ServerWorldUtil;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class CheckUtils {

    public static boolean frostWalkers(final Player player) {
        final ItemStack boots = player.getInventory().getBoots();
        return ServerWorldUtil.getMinecraftVersionInt() >= 19 && boots.containsEnchantment(Enchantment.getByName("FROST_WALKER"));
    }
}
