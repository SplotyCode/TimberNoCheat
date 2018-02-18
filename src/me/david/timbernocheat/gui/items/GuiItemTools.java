package me.david.timbernocheat.gui.items;

import me.david.timbernocheat.config.Permissions;
import me.david.api.guis.Gui;
import me.david.api.utils.OLD_Sounds;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiItemTools extends Gui {

    public GuiItemTools() {
        super("GuiItemTools", OLD_Sounds.LEVELUP, Permissions.ITEMS_TOOLS);
    }

    @Override
    public Inventory build(Player p) {
        Inventory inv = Bukkit.getServer().createInventory(null, 9, "§6Tools");
        return inv;
    }

    @Override
    public void itemclick(Player p, Inventory inv, ItemStack itemstack, InventoryAction inventoryaction, ClickType clicktype, int slot) {
        p.getInventory().addItem(itemstack);
    }
}
