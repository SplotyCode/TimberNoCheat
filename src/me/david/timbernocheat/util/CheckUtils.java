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

public final class CheckUtils {

    public static boolean frostWalkers(final Player player) {
        final ItemStack boots = player.getInventory().getBoots();
        return ServerWorldUtil.getMinecraftVersionInt() >= 19 && boots.containsEnchantment(Enchantment.getByName("FROST_WALKER"));
    }

    public static boolean onGround(final Player player){
        AABBBox playerBox = Api.getNms().getBoundingBox(player).expand(0, 0.15, 0);
        for(int x = player.getLocation().getBlockX()-1; x<player.getLocation().getBlockX()+3; x++)
            for(int z = player.getLocation().getBlockZ()-1; z<player.getLocation().getBlockZ()+3; z++)
                for(double y = 0.1;y<1.1;y+=0.1) {
                    Location loc = new Location(player.getWorld(), x, player  .getLocation().getY() - y, z);
                    Block block = loc.getBlock();
                    if(block.getType().isSolid() && playerBox.intersectsWith(Api.getNms().getBoundingBox(block)))
                        return true;
                }
        return false;
    }

    //TODO not 1.9 save becouse of this flyinh chestplate thingy
    //TODO add sneaking (Height: 1.65 Blocks Width: 0.6 Blocks)
    public static boolean onGround(final Location location){
        AABBBox playerBox = new AABBBox(location.getX()-0.3, location.getY(), location.getZ()-0.3, location.getX()+0.3, location.getY()+1.8, location.getZ()+0.3);
        for(int x = location.getBlockX()-1; x<location.getBlockX()+3; x++)
            for(int z = location.getBlockZ()-1; z<location.getBlockZ()+3; z++)
                for(double y = 0.1;y<1.1;y+=0.1) {
                    Location loc = new Location(location.getWorld(), x, location.getY() - y, z);
                    Block block = loc.getBlock();
                    if(block.getType().isSolid() && playerBox.intersectsWith(Api.getNms().getBoundingBox(block)))
                        return true;
                }
        return false;
    }

    public static boolean doesColidateWithMaterial(Material material, Player player){
        AABBBox playerBox = Api.getNms().getBoundingBox(player).expand(0, 0.15, 0);
        for(int x = player.getLocation().getBlockX()-1; x<player.getLocation().getBlockX()+3; x++)
            for(int z = player.getLocation().getBlockZ()-1; z<player.getLocation().getBlockZ()+3; z++)
                for(double y = 0.1;y<1.1;y+=0.1) {
                    Location loc = new Location(player.getWorld(), x, player  .getLocation().getY() - y, z);
                    Block block = loc.getBlock();
                    if(block.getType() == material && playerBox.intersectsWith(Api.getNms().getBoundingBox(block)))
                        return true;
                }
        return false;
    }

    public static int getKnockbag(Entity entity){
        if(!(entity instanceof HumanEntity)) return 0;
        ItemStack is = ((HumanEntity) entity).getItemInHand();
        return (is == null?0:is.getEnchantmentLevel(Enchantment.KNOCKBACK));
    }
}
