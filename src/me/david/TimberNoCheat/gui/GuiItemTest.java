package me.david.TimberNoCheat.gui;

import me.david.TimberNoCheat.config.Permissions;
import me.david.api.Potions;
import me.david.api.guis.Gui;
import me.david.api.utils.ItemStackUtil;
import me.david.api.utils.OLD_Sounds;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiItemTest extends Gui {

    public GuiItemTest() {
        super("GuiItemTest", OLD_Sounds.LEVELUP, Permissions.ITEMS_TEST);
    }

    @Override
    public Inventory build(Player p) {
        Inventory inv = Bukkit.getServer().createInventory(null, 9*4, "§6Test");
        inv.addItem(toItems(Material.SLIME_BLOCK, Material.WATER_BUCKET, Material.LAVA_BUCKET, Material.WATER_LILY, Material.LADDER, Material.RED_SANDSTONE_STAIRS, Material.STONE_SLAB2, Material.TRAP_DOOR, Material.IRON_TRAPDOOR, Material.ICE, Material.WEB, Material.FLOWER_POT_ITEM, Material.SOUL_SAND, Material.STONE));
        inv.addItem(ItemStackUtil.createPotion(Potions.SPEED, 1, "Speed"));
        inv.addItem(ItemStackUtil.createPotion(Potions.SPEED2, 2, "Speed2"));
        inv.addItem(ItemStackUtil.createPotion(Potions.SLOWNESS, 1, "Slow"));
        inv.addItem(ItemStackUtil.createPotion(Potions.FASTDIG, 1, "FastDig"));
        inv.addItem(ItemStackUtil.createPotion(Potions.FASTDIG2, 2, "FastDig2"));
        return inv;
    }

    private ItemStack[] toItems(Material... materials){
        ItemStack[] array = new ItemStack[materials.length];
        int i = 0;
        for(Material mat : materials){
            array[i] = new ItemStack(mat);
            i++;
        }
        return array;
    }

    @Override
    public void itemclick(Player p, Inventory inv, ItemStack itemstack, InventoryAction inventoryaction, ClickType clicktype, int slot) {
        p.getInventory().addItem(itemstack);
    }
}
