package me.david.timbernocheat.util;

import me.david.api.Api;
import me.david.api.nms.AABBBox;
import me.david.api.utils.ServerWorldUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class CheckUtils {

    public static boolean frostWalkers(final Player player) {
        final ItemStack boots = player.getInventory().getBoots();
        return ServerWorldUtil.getMinecraftVersionInt() >= 190 && boots.containsEnchantment(Enchantment.getByName("FROST_WALKER"));
    }

    public static boolean onGround(final Player player){
        return checkSurroundings(player, CheckMode.FEED, (location, block, playerBox, blockBox) -> {
            return block.getType().isSolid() && playerBox.intersectsWith(blockBox);
        });
    }

    public static boolean headCollidate(final Player player){
        return checkSurroundings(player, CheckMode.HEAD, (location, block, playerBox, blockBox) -> {
            return block.getType().isSolid() && playerBox.intersectsWith(blockBox);
        });
    }

    public static boolean onGround(final Location location){
        return checkSurroundings(location, CheckMode.FEED, (location2, block, playerBox, blockBox) -> {
            return block.getType().isSolid() && playerBox.intersectsWith(blockBox);
        });
    }

    public static boolean headCollidate(final Location location){
        return checkSurroundings(location, CheckMode.HEAD, (location2, block, playerBox, blockBox) -> {
            return block.getType().isSolid() && playerBox.intersectsWith(blockBox);
        });
    }

    public static boolean doesColidateWithMaterial(Material material, Player player){
        return checkSurroundings(player, CheckMode.BODY, (location, block, playerBox, blockBox) -> {
            return block.getType() == material && playerBox.intersectsWith(blockBox);
        });
    }

    public static boolean doesColidateWithMaterial(Material material, Location location){
        return checkSurroundings(location, CheckMode.BODY, (location2, block, playerBox, blockBox) -> {
            return block.getType() == material && playerBox.intersectsWith(blockBox);
        });
    }

    public static int getKnockbag(Entity entity){
        if(!(entity instanceof HumanEntity)) return 0;
        ItemStack is = ((HumanEntity) entity).getItemInHand();
        return (is == null?0:is.getEnchantmentLevel(Enchantment.KNOCKBACK));
    }

    public static boolean checkSurroundings(Player player, CheckMode checkMode, BlockValidator validator) {
        AABBBox playerBox = Api.getNms().getBoundingBox(player).expand(0, 0.15, 0);
        return checkSurroundings(player.getLocation(), playerBox, checkMode, validator);
    }

    public static boolean checkSurroundings(Location location, CheckMode checkMode, BlockValidator validator) {
        AABBBox playerBox = new AABBBox(location.getX()-0.3, location.getY(), location.getZ()-0.3, location.getX()+0.3, location.getY()+1.8, location.getZ()+0.3).expand(0, 0.15, 0);
        return checkSurroundings(location, playerBox, checkMode, validator);
    }

    //TODO not 1.9 save because of this flying chestplate thingy
    //TODO add sneaking (Height: 1.65 Blocks Width: 0.6 Blocks)
    public static boolean checkSurroundings(Location location, AABBBox playerBox, CheckMode checkMode, BlockValidator validator) {
        for(int x = location.getBlockX()-1; x<location.getBlockX()+3; x++)
            for(int z = location.getBlockZ()-1; z<location.getBlockZ()+3; z++) {
                switch (checkMode) {
                    case HEAD:
                        for (double y = 0.1; y < 1.2; y += 0.1) {
                            Location loc = new Location(location.getWorld(), x, location.getY() - y, z);
                            Block block = loc.getBlock();
                            AABBBox blockBox = Api.getNms().getBoundingBox(block);

                            if (validator.check(loc, block, playerBox, blockBox))
                                return true;
                        }
                        break;
                    case BODY:
                        for (int y = location.getBlockY()-1; y<location.getBlockY()+3; y++) {
                            Location loc = new Location(location.getWorld(), x, y, z);
                            Block block = loc.getBlock();
                            AABBBox blockBox = Api.getNms().getBoundingBox(block);

                            if (validator.check(loc, block, playerBox, blockBox))
                                return true;
                        }
                        break;
                    case FEED:
                        for (double y = 0.2;y > -1.1;y-=0.1) {
                            Location loc = new Location(location.getWorld(), x, location.getY() - y, z);
                            Block block = loc.getBlock();
                            AABBBox blockBox = Api.getNms().getBoundingBox(block);

                            if (validator.check(loc, block, playerBox, blockBox))
                                return true;
                        }
                        break;
                }
            }
        return false;
    }

    /* Return the Level of a Affect */
    public static int getPotionEffectLevel(Player p, PotionEffectType pet) {
        for (PotionEffect pe : p.getActivePotionEffects())
            if (pe.getType().getName().equals(pet.getName()))
                return pe.getAmplifier() + 1;
        return 0;
    }

    public enum CheckMode {

        BODY,
        HEAD,
        FEED

    }

    public interface BlockValidator {

        boolean check(Location location, Block block, AABBBox playerBox, AABBBox blockBox);

    }
}
