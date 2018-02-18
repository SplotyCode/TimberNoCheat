package me.david.timbernocheat.checktools;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Set;

/* Helping to World with Materials */
public class MaterialHelper {

    public static final ArrayList<Material> a = new ArrayList<Material>();
    public static final ArrayList<Material> b = new ArrayList<Material>();
    public static final ArrayList<Material> c = new ArrayList<Material>();
    public static final ArrayList<Material> d = new ArrayList<Material>();
    public static final ArrayList<Material> CHESTS = new ArrayList<Material>();
    public static final ArrayList<Material> PLATES = new ArrayList<Material>();
    public static final ArrayList<Material> ICE = new ArrayList<Material>();
    public static final ArrayList<Material> GLASSPANE = new ArrayList<Material>();
    public static final ArrayList<Material> SLAPS = new ArrayList<Material>();
    public static final ArrayList<Material> CLIMP = new ArrayList<Material>();
    public static final ArrayList<Material> DOOR = new ArrayList<Material>();
    public static final ArrayList<Material> FALLING = new ArrayList<Material>();
    public static final ArrayList<Material> TRAPDOOR = new ArrayList<Material>();
    public static final ArrayList<Material> LIQUID = new ArrayList<Material>();
    public static final ArrayList<Material> STAIRS = new ArrayList<Material>();
    public static final ArrayList<Material> FENCES = new ArrayList<Material>();

    static {
        FENCES.add(Material.FENCE);
        FENCES.add(Material.SPRUCE_FENCE_GATE);
        FENCES.add(Material.BIRCH_FENCE_GATE);
        FENCES.add(Material.JUNGLE_FENCE_GATE);
        FENCES.add(Material.DARK_OAK_FENCE_GATE);
        FENCES.add(Material.ACACIA_FENCE_GATE);
        FENCES.add(Material.SPRUCE_FENCE);
        FENCES.add(Material.BIRCH_FENCE);
        FENCES.add(Material.JUNGLE_FENCE);
        FENCES.add(Material.DARK_OAK_FENCE);
        FENCES.add(Material.ACACIA_FENCE);
        FENCES.add(Material.IRON_FENCE);
        FENCES.add(Material.NETHER_FENCE);

        STAIRS.add(Material.ACACIA_STAIRS);
        STAIRS.add(Material.BIRCH_WOOD_STAIRS);
        STAIRS.add(Material.BRICK_STAIRS);
        STAIRS.add(Material.COBBLESTONE_STAIRS);
        STAIRS.add(Material.DARK_OAK_STAIRS);
        STAIRS.add(Material.JUNGLE_WOOD_STAIRS);
        STAIRS.add(Material.NETHER_BRICK_STAIRS);
        STAIRS.add(Material.RED_SANDSTONE_STAIRS);
        STAIRS.add(Material.QUARTZ_STAIRS);
        STAIRS.add(Material.SANDSTONE_STAIRS);
        STAIRS.add(Material.SMOOTH_STAIRS);
        STAIRS.add(Material.SPRUCE_WOOD_STAIRS);
        STAIRS.add(Material.WOOD_STAIRS);

        LIQUID.add(Material.STATIONARY_LAVA);
        LIQUID.add(Material.STATIONARY_WATER);

        c.add(Material.COCOA);
        c.add(Material.DRAGON_EGG);
        c.add(Material.ENDER_PORTAL_FRAME);
        c.add(Material.ENCHANTMENT_TABLE);
        c.add(Material.BED_BLOCK);
        c.add(Material.HOPPER);
        c.add(Material.FLOWER_POT);
        c.add(Material.BREWING_STAND);
        c.add(Material.ANVIL);
        c.add(Material.CAULDRON);
        c.add(Material.CARPET);
        c.add(Material.IRON_FENCE);
        c.add(Material.COBBLE_WALL);
        c.add(Material.LADDER);
        c.add(Material.WEB);
        c.add(Material.VINE);
        c.add(Material.WATER_LILY);
        c.add(Material.REDSTONE_COMPARATOR_OFF);
        c.add(Material.REDSTONE_COMPARATOR_ON);
        c.add(Material.DIODE_BLOCK_ON);
        c.add(Material.DIODE_BLOCK_OFF);
        c.add(Material.SKULL);
        c.add(Material.SNOW);
        c.add(Material.PISTON_BASE);
        c.add(Material.PISTON_STICKY_BASE);
        c.add(Material.PISTON_EXTENSION);
        c.add(Material.CACTUS);
        c.add(Material.SOIL);
        c.add(Material.SOUL_SAND);
        c.add(Material.DAYLIGHT_DETECTOR);
        c.add(Material.DAYLIGHT_DETECTOR_INVERTED);
        c.add(Material.CAKE_BLOCK);
        c.add(Material.COCOA);
        c.add(Material.FIRE);
        c.add(Material.LEAVES);
        c.add(Material.LEAVES_2);

        b.add(Material.BED_BLOCK);
        b.add(Material.CARPET);
        b.add(Material.LEAVES);
        b.add(Material.LEAVES_2);
        b.add(Material.VINE);
        b.add(Material.LADDER);
        b.add(Material.HUGE_MUSHROOM_1);
        b.add(Material.HUGE_MUSHROOM_2);
        b.add(Material.TNT);
        b.add(Material.REDSTONE_COMPARATOR_OFF);
        b.add(Material.DIODE_BLOCK_OFF);
        b.add(Material.WATER_LILY);
        b.add(Material.FLOWER_POT);
        b.add(Material.COCOA);
        b.add(Material.NETHERRACK);
        b.add(Material.GLASS);
        b.add(Material.STAINED_GLASS);
        b.add(Material.ICE);
        b.add(Material.PACKED_ICE);
        b.add(Material.SANDSTONE);
        b.add(Material.SANDSTONE_STAIRS);
        b.add(Material.QUARTZ_BLOCK);
        b.add(Material.QUARTZ_STAIRS);
        b.add(Material.DAYLIGHT_DETECTOR);

        a.add(Material.AIR);
        a.add(Material.STONE_BUTTON);
        a.add(Material.WOOD_BUTTON);
        a.add(Material.RED_ROSE);
        a.add(Material.YELLOW_FLOWER);
        a.add(Material.SIGN_POST);
        a.add(Material.WALL_SIGN);
        a.add(Material.BROWN_MUSHROOM);
        a.add(Material.RED_MUSHROOM);
        a.add(Material.TORCH);
        a.add(Material.REDSTONE_TORCH_ON);
        a.add(Material.TRIPWIRE);
        a.add(Material.GOLD_PLATE);
        a.add(Material.IRON_PLATE);
        a.add(Material.STONE_PLATE);
        a.add(Material.WOOD_PLATE);
        a.add(Material.TRIPWIRE_HOOK);
        a.add(Material.REDSTONE_WIRE);
        a.add(Material.RAILS);
        a.add(Material.ACTIVATOR_RAIL);
        a.add(Material.DETECTOR_RAIL);
        a.add(Material.POWERED_RAIL);
        a.add(Material.SEEDS);
        a.add(Material.MELON_SEEDS);
        a.add(Material.PUMPKIN_SEEDS);
        a.add(Material.CROPS);
        a.add(Material.ENDER_PORTAL);
        a.add(Material.PORTAL);
        a.add(Material.PUMPKIN_STEM);
        a.add(Material.MELON_STEM);
        a.add(Material.CARROT);
        a.add(Material.FIRE);
        a.add(Material.NETHER_WARTS);
        a.add(Material.POTATO);
        a.add(Material.LEVER);
        a.add(Material.DOUBLE_PLANT);
        a.add(Material.CARROT);
        a.add(Material.POTATO);
        a.add(Material.NETHER_WARTS);
        a.add(Material.LONG_GRASS);
        a.add(Material.DEAD_BUSH);
        a.add(Material.BEDROCK);
        a.add(Material.SUGAR_CANE_BLOCK);
        a.add(Material.REDSTONE_TORCH_OFF);
        a.add(Material.PISTON_MOVING_PIECE);

        d.add(Material.WORKBENCH);
        d.add(Material.FURNACE);
        d.add(Material.BURNING_FURNACE);
        d.add(Material.ENCHANTMENT_TABLE);
        d.add(Material.HOPPER);
        d.add(Material.BED_BLOCK);
        d.add(Material.JUKEBOX);
        d.add(Material.NOTE_BLOCK);
        d.add(Material.DROPPER);
        d.add(Material.BREWING_STAND);
        d.add(Material.REDSTONE_COMPARATOR_ON);
        d.add(Material.TRIPWIRE_HOOK);
        d.add(Material.ENDER_PORTAL_FRAME);
        d.add(Material.LEVER);
        d.add(Material.ANVIL);
        d.add(Material.WOOD_BUTTON);

        CHESTS.add(Material.CHEST);
        CHESTS.add(Material.TRAPPED_CHEST);
        CHESTS.add(Material.ENDER_CHEST);

        PLATES.add(Material.GOLD_PLATE);
        PLATES.add(Material.IRON_PLATE);
        PLATES.add(Material.STONE_PLATE);
        PLATES.add(Material.WOOD_PLATE);

        ICE.add(Material.ICE);
        ICE.add(Material.PACKED_ICE);

        GLASSPANE.add(Material.THIN_GLASS);
        GLASSPANE.add(Material.STAINED_GLASS_PANE);

        SLAPS.add(Material.STEP);
        SLAPS.add(Material.WOOD_STEP);
        SLAPS.add(Material.STONE_SLAB2);

        CLIMP.add(Material.LADDER);
        CLIMP.add(Material.VINE);

        FALLING.add(Material.SAND);
        FALLING.add(Material.GRAVEL);

        DOOR.add(Material.IRON_DOOR_BLOCK);
        DOOR.add(Material.WOODEN_DOOR);
        DOOR.add(Material.ACACIA_DOOR);
        DOOR.add(Material.BIRCH_DOOR);
        DOOR.add(Material.DARK_OAK_DOOR);
        DOOR.add(Material.JUNGLE_DOOR);
        DOOR.add(Material.SPRUCE_DOOR);

        TRAPDOOR.add(Material.TRAP_DOOR);
        TRAPDOOR.add(Material.IRON_TRAPDOOR);
    }

    //268
    public static boolean slime(Location loc) {
        Material mat = loc.getBlock().getType();
        return (mat == Material.SLIME_BLOCK) || (mat == Material.RED_SANDSTONE) || (mat == Material.RED_SANDSTONE_STAIRS) || (!checkloc(loc)) || GLASSPANE.contains(mat) || (b.contains(mat));
    }


    //284
    public static boolean check(Location loc, boolean paramBoolean) {
        Material mat = loc.getBlock().getType();
        return mat != Material.SLIME_BLOCK && ((!paramBoolean) ||
                checkloc(loc)) &&
                !FENCES.contains(mat) &&
                !STAIRS.contains(mat) &&
                !SLAPS.contains(mat) &&
                !TRAPDOOR.contains(mat) &&
                !DOOR.contains(mat) &&
                !GLASSPANE.contains(mat) &&
                !CHESTS.contains(mat) &&
                !PLATES.contains(mat) &&
                !c.contains(mat);
    }

    //308
    public static boolean checkmat(Location loc) {
        Material mat = loc.getBlock().getType();
        return d.contains(mat) || FENCES.contains(mat) || CLIMP.contains(mat) ||
                CHESTS.contains(mat) || DOOR.contains(mat);
    }

    //317
    public static boolean suround(Location loc) {
        return (CLIMP.contains(loc.getBlock().getType())) && (
                checkloc(loc.clone().add(1.0D, 0.0D, 0.0D)) ||
                        checkloc(loc.clone().add(-1.0D, 0.0D, 0.0D)) ||
                        checkloc(loc.clone().add(0.0D, 0.0D, 1.0D)) ||
                        checkloc(loc.clone().add(0.0D, 0.0D, -1.0D)) ||
                        checkloc(loc.clone().add(1.0D, 0.0D, 1.0D)) ||
                        checkloc(loc.clone().add(-1.0D, 0.0D, -1.0D)) ||
                        checkloc(loc.clone().add(-1.0D, 0.0D, 1.0D)) ||
                        checkloc(loc.clone().add(1.0D, 0.0D, -1.0D)));
    }

    //330
    public static String matname(Block block) {
        return block.getLocation().getBlock().getType().toString().toLowerCase().replaceAll("_", "-");
    }

    //353
    public static Block checkb(Player player, Location loc) {
        if (player.getWorld() != loc.getWorld() || (!checkloc(loc) && !checkmat(loc)) || checkloc(player.getLocation().add(0.0D, 1.0D, 0.0D)))
            return null;
        Block block = loc.getBlock();
        double distance = player.getLocation().distance(loc);
        if (distance >= 0.5D) {
            distance = distance < 1.0D ? Math.round(distance) : distance;
            Block targetBlock = player.getTargetBlock((Set<Material>) null, (int) distance);
            if (targetBlock != null) {
                double dis = targetBlock.getLocation().distance(block.getLocation());
                double d3 = 1.0D;
                if (player.getItemInHand().getType() == Material.MONSTER_EGG) d3 = 2.0D;
                if (check(targetBlock.getLocation(), true) && (dis >= d3) && (
                        targetBlock.getLocation().getBlockX() != block.getLocation().getBlockX() ||
                                targetBlock.getLocation().getBlockY() != block.getLocation().getBlockY() ||
                                targetBlock.getLocation().getBlockZ() != block.getLocation().getBlockZ()))
                    return targetBlock;
            }
        }
        return null;
    }

    //394
    public static boolean check(Player player, double x, double y, double z) {
        Location localLocation = player.getLocation();
        return (!LIQUID.contains(localLocation.clone().add(0.0D, y, 0.0D).getBlock().getType())) &&
                (!LIQUID.contains(localLocation.clone().add(x, y, 0.0D).getBlock().getType())) &&
                (!LIQUID.contains(localLocation.clone().add(-x, y, 0.0D).getBlock().getType())) &&
                (!LIQUID.contains(localLocation.clone().add(0.0D, y, z).getBlock().getType())) &&
                (!LIQUID.contains(localLocation.clone().add(0.0D, y, -z).getBlock().getType())) &&
                (!LIQUID.contains(localLocation.clone().add(x, y, z).getBlock().getType())) &&
                (!LIQUID.contains(localLocation.clone().add(-x, y, -z).getBlock().getType())) &&
                (!LIQUID.contains(localLocation.clone().add(x, y, -z).getBlock().getType())) &&
                (!LIQUID.contains(localLocation.clone().add(-x, y, z).getBlock().getType()));
    }

    //420
    public static boolean checksouroundpos2(Player paramPlayer, double x, double y, double z) {
        Location loc = paramPlayer.getLocation();
        return !checkloc(loc.clone().add(0, y, 0)) &&
                !checkloc(loc.clone().add(x, y, 0)) &&
                !checkloc(loc.clone().add(-x, y, 0)) &&
                !checkloc(loc.clone().add(0, y, z)) &&
                !checkloc(loc.clone().add(0, y, -z)) &&
                !checkloc(loc.clone().add(x, y, z)) &&
                !checkloc(loc.clone().add(-x, y, -z)) &&
                !checkloc(loc.clone().add(x, y, -z)) &&
                !checkloc(loc.clone().add(-x, y, z));
    }

    //435
    public static boolean checksouroundpos3(Player player, double x, double y, double z) {
        Location loc = player.getLocation();
        return check(loc.clone().add(0.0D, y, 0.0D), false) &&
                check(loc.clone().add(x, y, 0.0D), false) &&
                check(loc.clone().add(-x, y, 0.0D), false) &&
                check(loc.clone().add(0.0D, y, z), false) &&
                check(loc.clone().add(0.0D, y, -z), false) &&
                check(loc.clone().add(x, y, z), false) &&
                check(loc.clone().add(-x, y, -z), false) &&
                check(loc.clone().add(x, y, -z), false) &&
                check(loc.clone().add(-x, y, z), false);
    }

    //450
    public static boolean doornear(Player player, double x, double y, double z) {
        Location loc = player.getLocation();
        return DOOR.contains(loc.clone().add(0.0D, y, 0.0D).getBlock().getType()) ||
                DOOR.contains(loc.clone().add(x, y, 0.0D).getBlock().getType()) ||
                DOOR.contains(loc.clone().add(-x, y, 0.0D).getBlock().getType()) ||
                DOOR.contains(loc.clone().add(0.0D, y, z).getBlock().getType()) ||
                DOOR.contains(loc.clone().add(0.0D, y, -z).getBlock().getType()) ||
                DOOR.contains(loc.clone().add(x, y, z).getBlock().getType()) ||
                DOOR.contains(loc.clone().add(-x, y, -z).getBlock().getType()) ||
                DOOR.contains(loc.clone().add(x, y, -z).getBlock().getType()) ||
                DOOR.contains(loc.clone().add(-x, y, z).getBlock().getType());
    }

    //463
   /* public static boolean ��(
    Player paramPlayer, boolean paramBoolean, double paramDouble1, double paramDouble2, double paramDouble3)

    {
        Location localLocation = paramPlayer.getLocation();
        paramPlayer = paramBoolean ? paramPlayer : null;
        return (��(paramPlayer, localLocation.clone().add(0.0D, paramDouble2, 0.0D)))||
        (��(paramPlayer, localLocation.clone().add(paramDouble1, paramDouble2, 0.0D)))||
        (��(paramPlayer, localLocation.clone().add(-paramDouble1, paramDouble2, 0.0D)))||
        (��(paramPlayer, localLocation.clone().add(0.0D, paramDouble2, paramDouble3)))||
        (��(paramPlayer, localLocation.clone().add(0.0D, paramDouble2, -paramDouble3)))||
        (��(paramPlayer, localLocation.clone().add(paramDouble1, paramDouble2, paramDouble3)))||
        (��(paramPlayer, localLocation.clone().add(-paramDouble1, paramDouble2, -paramDouble3)))||
        (��(paramPlayer, localLocation.clone().add(paramDouble1, paramDouble2, -paramDouble3)))||
        (��(paramPlayer, localLocation.clone().add(-paramDouble1, paramDouble2, paramDouble3)));
    }*/

    //477
    //public static boolean ��(Player paramPlayer, LocationparamLocation){
    //    return ��.��(paramPlayer, paramLocation);
    //}

    //480
    public static boolean checkloc(Location loc) {
        Material mat = loc.getBlock().getType();
        return (mat != Material.ARMOR_STAND) && (mat != Material.STANDING_BANNER) && (mat != Material.WALL_BANNER) && (!FENCES.contains(mat)) && (!loc.getBlock().isLiquid()) && (!a.contains(mat));
    }

    //498
    public static boolean IsMatSouround(Location loc, Material mat, double dd) {
        return (loc.getBlock().getType() == mat) ||
                loc.clone().add(0.0D, 0.0D, -dd).getBlock().getType() == mat ||
                (loc.clone().add(0.0D, 0.0D, dd)).getBlock().getType() == mat ||
                loc.clone().add(-dd, 0.0D, 0.0D).getBlock().getType() == mat ||
                loc.clone().add(dd, 0.0D, 0.0D).getBlock().getType() == mat ||
                loc.clone().add(dd, 0.0D, dd).getBlock().getType() == mat ||
                loc.clone().add(-dd, 0.0D, -dd).getBlock().getType() == mat ||
                loc.clone().add(-dd, 0.0D, dd).getBlock().getType() == mat ||
                loc.clone().add(dd, 0.0D, -dd).getBlock().getType() == mat;
    }

    //510
    public static boolean IsMatSourundAll(Location loc, Material mat, double dd) {
        return loc.getBlock().getType() == mat &&
                loc.clone().add(0.0D, 0.0D, -dd).getBlock().getType() == mat &&
                loc.clone().add(0.0D, 0.0D, dd).getBlock().getType() == mat &&
                loc.clone().add(-dd, 0.0D, 0.0D).getBlock().getType() == mat &&
                loc.clone().add(dd, 0.0D, 0.0D).getBlock().getType() == mat &&
                loc.clone().add(dd, 0.0D, dd).getBlock().getType() == mat &&
                loc.clone().add(-dd, 0.0D, -dd).getBlock().getType() == mat &&
                loc.clone().add(-dd, 0.0D, dd).getBlock().getType() == mat &&
                loc.clone().add(dd, 0.0D, -dd).getBlock().getType() == mat;
    }

    //523
    public static boolean slime(Player pl, int blocks) {
        for (int i = 0; i <= blocks; i++) {
            Location loc = pl.getLocation().add(0.0D, -i, 0.0D);
            Material mat = loc.getBlock().getType();
            boolean j = mat == Material.SLIME_BLOCK;
            boolean k = (checkloc(loc))&&
            (mat != Material.CARPET) && (mat != Material.SNOW) && (!SLAPS.contains(loc.getBlock().getType()))&&
            (!STAIRS.contains(loc.getBlock().getType()))&&(!other(loc))&&(!CLIMP.contains(loc.getBlock().getType()));
            if (k && !j) return false;
            if (j) return true;
        }
        return false;
    }

    //544
    public static boolean bed(Player player, int blocks) {
        for (int i = 0; i <= blocks; i++) {
            Location localLocation = player.getLocation().add(0.0D, -i, 0.0D);
            boolean bed = localLocation.getBlock().getType() == Material.BED_BLOCK;
            if (checkloc(localLocation)&&!bed) return false;
            if (bed) return true;
        }
        return false;
    }

    //561
    public static boolean isLiquid(Location loc) {
        return loc != null && !loc.getBlock().isLiquid();
    }

    public static boolean other(Location loc) {
        Material mat = loc.getBlock().getType();
        return GLASSPANE.contains(mat) ||
        DOOR.contains(mat) ||
        TRAPDOOR.contains(mat) ||
        CHESTS.contains(mat) ||
        FENCES.contains(mat) ||
        (mat == Material.IRON_FENCE) || (mat == Material.ANVIL) || (mat == Material.CACTUS) || (mat == Material.CAKE_BLOCK) || (mat == Material.COCOA) || (mat == Material.COBBLE_WALL) || (mat == Material.SKULL) || (mat == Material.FLOWER_POT) || (mat == Material.DRAGON_EGG);
    }

}
