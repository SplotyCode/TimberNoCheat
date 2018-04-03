package me.david.timbernocheat.checktools;

import me.david.timbernocheat.util.CheckUtils;
import me.david.timbernocheat.util.PotionUtil;
import me.david.api.utils.BlockUtil;
import me.david.api.utils.player.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

/* Mostly for Calculating block breaking times */
public class InteractTool {

    private static final double LIQUID_HEIGHT_LOWERED = 80000002;
    private static final int maxBlocks = 4096;
    public static final ArrayList<Tool> tools = new ArrayList<Tool>();
    public static final Block[] blocks = new Block[maxBlocks];
    public static final Tool noTool = new Tool(null, ToolType.NONE, ToolMaterial.NONE);
    public static final long[] instantTimes = secToMs(0);
    public static final long[] leafTimes = secToMs(0.3);
    public static final long[] glassTimes = secToMs(0.45);
    public static final long[] gravelTimes = secToMs(0.9, 0.45, 0.25, 0.15, 0.15, 0.1);
    public static final long[] railsTimes = secToMs(1.05, 0.55, 0.3, 0.2, 0.15, 0.1);
    public static final long[] woodTimes = secToMs(3, 1.5, 0.75, 0.5, 0.4, 0.25);
    public static final Block instantType = new Block(noTool, 0, instantTimes);
    public static final Block glassType = new Block(noTool, 0.3f, glassTimes, 2f);
    public static final long indestructible = Long.MAX_VALUE;
    private static final long[] indestructibleTimes = new long[]{indestructible, indestructible, indestructible, indestructible, indestructible, indestructible};
    public static final Block gravelType = new Block(getToolbyMaterial(Material.WOOD_SPADE), 0.6f, gravelTimes);
    public static final Block stoneType = new Block(getToolbyMaterial(Material.WOOD_PICKAXE), 1.5f);
    public static final Block woodType = new Block(getToolbyMaterial(Material.WOOD_AXE), 2, woodTimes);
    public static final Block brickType = new Block(getToolbyMaterial(Material.WOOD_PICKAXE), 2);
    public static final Block coalType = new Block(getToolbyMaterial(Material.WOOD_PICKAXE), 3);
    public static final Block goldBlockType = new Block(getToolbyMaterial(Material.WOOD_PICKAXE), 3, secToMs(15, 7.5, 3.75, 0.7, 0.55, 1.2));
    public static final Block ironBlockType = new Block(getToolbyMaterial(Material.WOOD_PICKAXE), 5, secToMs(25, 12.5, 2.0, 1.25, 0.95, 2.0));
    public static final Block diamondBlockType = new Block(getToolbyMaterial(Material.WOOD_PICKAXE), 5, secToMs(25, 12.5, 6.0, 1.25, 0.95, 2.0));
    public static final Block hugeMushroomType = new Block(getToolbyMaterial(Material.WOOD_AXE), 0.2f, secToMs(0.3, 0.15, 0.1, 0.05, 0.05, 0.05));
    public static final Block leafType = new Block(noTool, 0.2f, leafTimes);
    public static final Block sandType = new Block(getToolbyMaterial(Material.WOOD_SPADE), 0.5f, secToMs(0.75, 0.4, 0.2, 0.15, 0.1, 0.1));
    public static final Block leverType = new Block(noTool, 0.5f, secToMs(0.75));
    public static final Block sandStoneType = new Block(getToolbyMaterial(Material.WOOD_PICKAXE), 0.8f);
    public static final Block chestType = new Block(getToolbyMaterial(Material.WOOD_AXE), 2.5f, secToMs(3.75, 1.9, 0.95, 0.65, 0.5, 0.35));
    public static final Block woodDoorType = new Block(getToolbyMaterial(Material.WOOD_AXE), 3.0f, secToMs(4.5, 2.25, 1.15, 0.75, 0.6, 0.4));
    public static final Block dispenserType = new Block(getToolbyMaterial(Material.WOOD_PICKAXE), 3.5f);
    public static final Block ironDoorType = new Block(getToolbyMaterial(Material.WOOD_PICKAXE), 5);
    public static final Block indestructibleType = new Block(noTool, -1f, indestructibleTimes);

    static {
        tools.add(new Tool(Material.WOOD_AXE, ToolType.AXE, ToolMaterial.WOOD));
        tools.add(new Tool(Material.WOOD_PICKAXE, ToolType.PICKAXE, ToolMaterial.WOOD));
        tools.add(new Tool(Material.WOOD_SWORD, ToolType.SWORD, ToolMaterial.WOOD));
        tools.add(new Tool(Material.WOOD_HOE, ToolType.HOE, ToolMaterial.WOOD));
        tools.add(new Tool(Material.WOOD_SPADE, ToolType.SHOVEL, ToolMaterial.WOOD));

        tools.add(new Tool(Material.IRON_AXE, ToolType.AXE, ToolMaterial.IRON));
        tools.add(new Tool(Material.IRON_PICKAXE, ToolType.PICKAXE, ToolMaterial.IRON));
        tools.add(new Tool(Material.IRON_SWORD, ToolType.SWORD, ToolMaterial.IRON));
        tools.add(new Tool(Material.IRON_HOE, ToolType.HOE, ToolMaterial.IRON));
        tools.add(new Tool(Material.IRON_SPADE, ToolType.SHOVEL, ToolMaterial.IRON));

        tools.add(new Tool(Material.GOLD_AXE, ToolType.AXE, ToolMaterial.GOLD));
        tools.add(new Tool(Material.GOLD_PICKAXE, ToolType.PICKAXE, ToolMaterial.GOLD));
        tools.add(new Tool(Material.GOLD_SWORD, ToolType.SWORD, ToolMaterial.GOLD));
        tools.add(new Tool(Material.GOLD_HOE, ToolType.HOE, ToolMaterial.GOLD));
        tools.add(new Tool(Material.GOLD_SPADE, ToolType.SHOVEL, ToolMaterial.GOLD));

        tools.add(new Tool(Material.DIAMOND_AXE, ToolType.AXE, ToolMaterial.DIAMOND));
        tools.add(new Tool(Material.DIAMOND_PICKAXE, ToolType.PICKAXE, ToolMaterial.DIAMOND));
        tools.add(new Tool(Material.DIAMOND_SWORD, ToolType.SWORD, ToolMaterial.DIAMOND));
        tools.add(new Tool(Material.DIAMOND_HOE, ToolType.HOE, ToolMaterial.DIAMOND));
        tools.add(new Tool(Material.DIAMOND_SPADE, ToolType.SHOVEL, ToolMaterial.DIAMOND));

        tools.add(new Tool(Material.SHEARS, ToolType.SHEARS, ToolMaterial.NONE));

        for (final Material mat : BlockUtil.INSTANT_BREAK) {
            blocks[mat.getId()] = instantType;
        }
        for (Material mat : new Material[]{
                Material.LEAVES, Material.BED_BLOCK}) {
            blocks[mat.getId()] = leafType;
        }
        for (Material mat : new Material[]{
                Material.HUGE_MUSHROOM_1, Material.HUGE_MUSHROOM_2,
                Material.VINE, Material.COCOA}) {
            blocks[mat.getId()] = hugeMushroomType;
        }

        blocks[Material.SNOW.getId()] = new Block(getToolbyMaterial(Material.WOOD_SPADE), 0.1f, secToMs(0.5, 0.1, 0.05, 0.05, 0.05, 0.05));
        blocks[Material.SNOW_BLOCK.getId()] = new Block(getToolbyMaterial(Material.WOOD_SPADE), 0.1f, secToMs(1, 0.15, 0.1, 0.05, 0.05, 0.05));
        for (Material mat : new Material[]{
                Material.REDSTONE_LAMP_ON, Material.REDSTONE_LAMP_OFF,
                Material.GLOWSTONE, Material.GLASS,
        }) {
            blocks[mat.getId()] = glassType;
        }
        blocks[Material.THIN_GLASS.getId()] = glassType;
        blocks[Material.NETHERRACK.getId()] = new Block(getToolbyMaterial(Material.WOOD_PICKAXE), 0.4f, secToMs(2, 0.3, 0.15, 0.1, 0.1, 0.05));
        blocks[Material.LADDER.getId()] = new Block(noTool, 0.4f, secToMs(0.6), 2.5f);
        blocks[Material.CACTUS.getId()] = new Block(noTool, 0.4f, secToMs(0.6));
        blocks[Material.WOOD_PLATE.getId()] = new Block(getToolbyMaterial(Material.WOOD_AXE), 0.5f, secToMs(0.75, 0.4, 0.2, 0.15, 0.1, 0.1));
        blocks[Material.STONE_PLATE.getId()] = new Block(getToolbyMaterial(Material.WOOD_PICKAXE), 0.5f, secToMs(2.5, 0.4, 0.2, 0.15, 0.1, 0.07));
        blocks[Material.SAND.getId()] = sandType;
        blocks[Material.SOUL_SAND.getId()] = sandType;
        for (Material mat : new Material[]{Material.LEVER, Material.PISTON_BASE,
                Material.PISTON_EXTENSION, Material.PISTON_STICKY_BASE,
                Material.STONE_BUTTON, Material.PISTON_MOVING_PIECE}) {
            blocks[mat.getId()] = leverType;
        }
        //		blocks[Material.ICE.getId()] = new Block(woodPickaxe, 0.5f, secToMs(2.5, 0.4, 0.2, 0.15, 0.1, 0.1));
        blocks[Material.ICE.getId()] = new Block(getToolbyMaterial(Material.WOOD_PICKAXE), 0.5f, secToMs(0.7, 0.35, 0.18, 0.12, 0.09, 0.06));
        blocks[Material.DIRT.getId()] = sandType;
        blocks[Material.CAKE_BLOCK.getId()] = leverType;
        blocks[Material.BREWING_STAND.getId()] = new Block(getToolbyMaterial(Material.WOOD_PICKAXE), 0.5f, secToMs(2.5, 0.4, 0.2, 0.15, 0.1, 0.1));
        blocks[Material.SPONGE.getId()] = new Block(noTool, 0.6f, secToMs(0.9));
        for (Material mat : new Material[]{
                Material.MYCEL, Material.GRAVEL, Material.GRASS, Material.SOIL,
                Material.CLAY,
        }) {
            blocks[mat.getId()] = gravelType;
        }
        for (Material mat : new Material[]{
                Material.RAILS, Material.POWERED_RAIL, Material.DETECTOR_RAIL,
        }) {
            blocks[mat.getId()] = new Block(getToolbyMaterial(Material.WOOD_PICKAXE), 0.7f, railsTimes);
        }
        blocks[Material.MONSTER_EGGS.getId()] = new Block(noTool, 0.75f, secToMs(1.15));
        blocks[Material.WOOL.getId()] = new Block(noTool, 0.8f, secToMs(1.2), 3f);
        blocks[Material.SANDSTONE.getId()] = sandStoneType;
        blocks[Material.SANDSTONE_STAIRS.getId()] = sandStoneType;
        for (Material mat : new Material[]{
                Material.STONE, Material.SMOOTH_BRICK, Material.SMOOTH_STAIRS,
        }) {
            blocks[mat.getId()] = stoneType;
        }
        blocks[Material.NOTE_BLOCK.getId()] = new Block(getToolbyMaterial(Material.WOOD_AXE), 0.8f, secToMs(1.2, 0.6, 0.3, 0.2, 0.15, 0.1));
        final Block pumpkinType = new Block(getToolbyMaterial(Material.WOOD_AXE), 1, secToMs(1.5, 0.75, 0.4, 0.25, 0.2, 0.15));
        blocks[Material.WALL_SIGN.getId()] = pumpkinType;
        blocks[Material.SIGN_POST.getId()] = pumpkinType;
        blocks[Material.PUMPKIN.getId()] = pumpkinType;
        blocks[Material.JACK_O_LANTERN.getId()] = pumpkinType;
        blocks[Material.MELON_BLOCK.getId()] = new Block(noTool, 1, secToMs(1.45), 3);
        blocks[Material.BOOKSHELF.getId()] = new Block(getToolbyMaterial(Material.WOOD_AXE), 1.5f, secToMs(2.25, 1.15, 0.6, 0.4, 0.3, 0.2));
        for (Material mat : new Material[]{
                Material.WOOD_STAIRS, Material.WOOD, Material.WOOD_STEP, Material.LOG,
                Material.FENCE, Material.FENCE_GATE, Material.JUKEBOX,
                Material.JUNGLE_WOOD_STAIRS, Material.SPRUCE_WOOD_STAIRS,
                Material.BIRCH_WOOD_STAIRS,
                Material.WOOD_DOUBLE_STEP,
        }) {
            blocks[mat.getId()] = woodType;
        }
        for (Material mat : new Material[]{
                Material.COBBLESTONE_STAIRS, Material.COBBLESTONE,
                Material.NETHER_BRICK, Material.NETHER_BRICK_STAIRS, Material.NETHER_FENCE,
                Material.CAULDRON, Material.BRICK, Material.BRICK_STAIRS,
                Material.MOSSY_COBBLESTONE, Material.BRICK, Material.BRICK_STAIRS,
                Material.STEP, Material.DOUBLE_STEP, // ?

        }) {
            blocks[mat.getId()] = brickType;
        }
        blocks[Material.WORKBENCH.getId()] = chestType;
        blocks[Material.CHEST.getId()] = chestType;
        blocks[Material.WOODEN_DOOR.getId()] = woodDoorType;
        blocks[Material.TRAP_DOOR.getId()] = woodDoorType;
        for (Material mat : new Material[]{
                Material.ENDER_STONE, Material.COAL_ORE,

        }) {
            blocks[mat.getId()] = coalType;
        }
        blocks[Material.DRAGON_EGG.getId()] = new Block(noTool, 3f, secToMs(4.5));
    }
    public enum ToolType {
        NONE,
        SWORD,
        SHOVEL,
        SHEARS,
        AXE,
        PICKAXE,
        HOE
    }
    public static Block getBlock(final Material m){
        if (m.getId() < 0 || m.getId() >= blocks.length || blocks[m.getId()] == null) {
            return instantType;
        } else {
            return blocks[m.getId()];
        }
    }
    public enum ToolMaterial {
        NONE(0, 1f),
        WOOD(1, 2f),
        STONE(2, 4f),
        IRON(3, 6f),
        DIAMOND(4, 8f),
        GOLD(5, 12f);


        private final int index;
        private final float breakMultiplier;

        ToolMaterial(int index, float breakMultiplier) {
            this.index = index;
            this.breakMultiplier = breakMultiplier;
        }

        public static ToolMaterial getById(final int id) {
            for (ToolMaterial material : values()) {
                if (material.index == id) {
                    return material;
                }
            }
            return null;
        }

        public float getBreakMultiplier() {
            return breakMultiplier;
        }

        public int getIndex() {
            return index;
        }
    }

    public static Tool getToolbyMaterial(Material m) {
        for (Tool cm : tools)
            if (cm.getMaterial() == m)
                return cm;
        return new Tool(m, ToolType.NONE, ToolMaterial.NONE);
    }

    public static class Tool {
        private Material material;
        private ToolType toolType;
        private ToolMaterial toolMaterial;

        public Tool(Material material, ToolType toolType, ToolMaterial toolMaterial) {
            this.material = material;
            this.toolType = toolType;
            this.toolMaterial = toolMaterial;
        }

        public Material getMaterial() {
            return material;
        }

        public void setMaterial(Material material) {
            this.material = material;
        }

        public ToolType getToolType() {
            return toolType;
        }

        public void setToolType(ToolType toolType) {
            this.toolType = toolType;
        }

        public ToolMaterial getToolMaterial() {
            return toolMaterial;
        }

        public void setToolMaterial(ToolMaterial toolMaterial) {
            this.toolMaterial = toolMaterial;
        }
    }

    public static class Block {
        private final Tool tool;
        private final long[] breakingTimes;
        private final float hardness;
        private final float efficiencyMod;

        public Block(Tool tool, float hardness) {
            this(tool, hardness, 1);
        }

        public Block(Tool tool, float hardness, float efficiencyMod) {
            this.tool = tool;
            this.hardness = hardness;
            breakingTimes = new long[6];
            for (int i = 0; i < 6; i++) {
                final float multiplier;
                if (tool.getMaterial() == null) {
                    multiplier = 1f;
                } else if (i < tool.getToolMaterial().index) {
                    multiplier = 1f;
                } else {
                    multiplier = ToolMaterial.getById(i).breakMultiplier * 3.33f;
                }
                breakingTimes[i] = (long) (1000f * 5f * hardness / multiplier);
            }
            this.efficiencyMod = efficiencyMod;
        }

        public Block(Tool tool, float hardness, long[] breakingTimes) {
            this(tool, hardness, breakingTimes, 1f);
        }

        public Block(Tool tool, float hardness, long[] breakingTimes, float efficiencyMod) {
            this.tool = tool;
            this.breakingTimes = breakingTimes;
            this.hardness = hardness;
            this.efficiencyMod = efficiencyMod;
        }
    }

    public static long getBreakingDuration(final Material m, final Player p) {
        boolean ground = CheckUtils.onGround(p);
        boolean water = PlayerUtil.getEyeLocation(p).getBlock().isLiquid();
        boolean aquaAffinity = p.getEquipment().getHelmet() != null && p.getEquipment().getHelmet().containsEnchantment(Enchantment.WATER_WORKER);
        int haste = PotionUtil.getPotionEffectAmplifier(p, PotionEffectType.FAST_DIGGING) + 1;
        if (p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) {
            return getBreakingDuration(m, getBlock(m), noTool, ground, water, aquaAffinity, 0);
        }
        int efficiency = p.getItemInHand().containsEnchantment(Enchantment.DIG_SPEED) ? p.getItemInHand().getEnchantmentLevel(Enchantment.DIG_SPEED) : 0;
        return getBreakingDuration(m, getBlock(m), getToolbyMaterial(p.getItemInHand().getType()), ground, water, aquaAffinity, efficiency, haste);
    }

    public static long getBreakingDuration(final Material mat, final Block Block, final Tool toolProps, final boolean onGround, final boolean inWater, boolean aquaAffinity, int efficiency, int haste) {
        final long dur = getBreakingDuration(mat, Block, toolProps, onGround, inWater, aquaAffinity, efficiency);
        return haste > 0 ? (long) (Math.pow(0.8, haste) * dur) : dur;
    }

    public static long getBreakingDuration(final Material mat, final Block block, final Tool tool, final boolean onGround, final boolean inWater, boolean aquaAffinity, int efficiency) {
        boolean isValidTool = isValidTool(mat, block, tool, efficiency);
        if (efficiency > 0) {
            if (BlockUtil.LEAVES.contains(mat) || block == glassType) {
                return efficiency == 1?100:0;
            } else if (block == chestType) {
                return (long) ((double) block.breakingTimes[0] / 5f / efficiency);
            }
        }
        long duration;
        if (isValidTool) {
            duration = block.breakingTimes[tool.getToolMaterial().index];
            if (efficiency > 0) {
                duration = (long) (duration / block.efficiencyMod);
            }
        } else {
            duration = block.breakingTimes[0];
            if (tool.toolType == ToolType.SWORD) {
                duration = (long) ((float) duration / 1.5f);
            }
        }
        if (tool.toolType == ToolType.SHEARS) {
            if (mat == Material.WEB) {
                duration = 400;
                isValidTool = true;
            } else if (mat == Material.WOOL) {
                duration = 240;
                isValidTool = true;
            } else if (BlockUtil.LEAVES.contains(mat)) {
                duration = 20;
                isValidTool = true;
            } else if (mat == Material.VINE) {
                duration = 300;
                isValidTool = true;
            }
        } else if (mat == Material.VINE && tool.getToolType() == ToolType.AXE) {
            isValidTool = true;
            duration = tool.getToolMaterial() == ToolMaterial.WOOD || tool.getToolMaterial() == ToolMaterial.STONE?100:0;
        }

        if (isValidTool || block.tool.toolType == ToolType.NONE) {
            float mult = 1f;
            if (inWater && !aquaAffinity) {
                mult *= 4f;
            }
            if (!onGround) {
                mult *= 4f;
            }
            duration = (long) (mult * duration);
            if (efficiency > 0) {
                if (mat == Material.WOODEN_DOOR && tool.getToolType() != ToolType.AXE) {
                    switch (efficiency) {
                        case 1:
                            return (long) (mult * 1500);
                        case 2:
                            return (long) (mult * 750);
                        case 3:
                            return (long) (mult * 450);
                        case 4:
                            return (long) (mult * 250);
                        case 5:
                            return (long) (mult * 150);
                    }
                }
                for (int i = 0; i < efficiency; i++) {
                    duration /= 1.33;
                }
                if (tool.getToolMaterial() == ToolMaterial.WOOD) {
                    if (tool.getToolType() == ToolType.PICKAXE && (block == ironDoorType || block == dispenserType)) {
                        if (block == dispenserType) {
                            duration = (long) (duration / 1.5 - (efficiency - 1) * 60);
                        } else {
                            duration = (long) (duration / 1.5 - (efficiency - 1) * 100);
                        }
                    } else if (mat == Material.LOG) {
                        duration -= efficiency >= 4 ? 250 : 400;
                    } else if (block.tool.toolType == tool.getToolType()) {
                        duration -= 250;
                    } else {
                        duration -= efficiency * 30;
                    }

                } else if (tool.getToolMaterial() == ToolMaterial.STONE) {
                    if (mat == Material.LOG) {
                        duration -= 100;
                    }
                }
            }
        }
        if (efficiency > 0 && !isValidTool && mat == Material.MELON_BLOCK) {
            duration = Math.min(duration, 450 / (long) Math.pow(2, efficiency - 1));
        }
        return Math.max(0, duration);
    }

    public static long[] secToMs(final double s1, final double s2, final double s3,
                                 final double s4, final double s5, final double s6) {
        return new long[]{(long) (s1 * 1000d), (long) (s2 * 1000d), (long) (s3 * 1000d), (long) (s4 * 1000d), (long) (s5 * 1000d), (long) (s6 * 1000d)};
    }

    public static long[] secToMs(final double s1) {
        final long v = (long) (s1 * 1000d);
        return new long[]{v, v, v, v, v, v};
    }
    public static boolean isValidTool(final Material mat, final Block blockProps,
                                      final Tool toolProps, final int efficiency) {
        boolean isValidTool = blockProps.tool.toolType == toolProps.toolType;

        if (!isValidTool && efficiency > 0) {
            if (mat == Material.SNOW) {
                return toolProps.toolType == ToolType.SHOVEL;
            }
            if (mat == Material.WOOL) {
                return true;
            }
            if (mat == Material.WOODEN_DOOR) {
                return true;
            }
            if (blockProps.hardness <= 2 && (blockProps.tool.toolType == ToolType.AXE || blockProps.tool.toolType == ToolType.SHOVEL || blockProps.hardness < 0.8 && mat != Material.NETHERRACK && mat != Material.SNOW_BLOCK && mat != Material.STONE_PLATE)) {
                return true;
            }
        }
        return isValidTool;
    }

}
