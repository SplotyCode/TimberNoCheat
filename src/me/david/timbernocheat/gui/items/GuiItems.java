package me.david.timbernocheat.gui.items;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.config.Permissions;
import me.david.api.guis.CloseReason;
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

public class GuiItems extends Gui {


    private final ItemStack TOOLS = ItemStackUtil.createLohre("§6Tools", 1, Material.STONE_SPADE, "§6Anticheat Tools");
    private final ItemStack TEST_ITEMS = ItemStackUtil.createLohre("§6TestItems", 1, Material.POTION, "§6Anticheat Test Items");

    public GuiItems() {
        super("GuiItems", OLD_Sounds.LEVELUP, Permissions.ITEMS);
    }

    @Override
    public Inventory build(Player p) {
        Inventory inv = Bukkit.getServer().createInventory(null, 9, "§6Items");
        inv.addItem(TOOLS, TEST_ITEMS);
        return inv;
    }

    @Override
    public void itemclick(Player p, Inventory inv, ItemStack itemstack, InventoryAction inventoryaction, ClickType clicktype, int slot) {
        switch (itemstack.getType()){
            case STONE_SPADE:
                TimberNoCheat.getInstance().getGuimanager().removeMultiGui(p, false, CloseReason.REOPEN);
                TimberNoCheat.getInstance().getGuimanager().startMultidefaultStage(p, "ItemsToolMulti");
                break;
            case POTION:
                TimberNoCheat.getInstance().getGuimanager().removeMultiGui(p, false, CloseReason.REOPEN);
                TimberNoCheat.getInstance().getGuimanager().startMultidefaultStage(p, "ItemsTestMulti");
                break;
        }
    }
}
