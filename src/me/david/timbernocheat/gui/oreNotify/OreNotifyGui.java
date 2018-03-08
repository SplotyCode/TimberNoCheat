package me.david.timbernocheat.gui.oreNotify;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.command.oreNotify.OreNotifyData;
import me.david.timbernocheat.config.Permissions;
import me.david.api.guis.Gui;
import me.david.api.sound.Sound;
import me.david.api.sound.SoundCategory;
import me.david.api.utils.ItemStackUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class OreNotifyGui extends Gui {
    public OreNotifyGui() {
        super("OreNotifyGui", Permissions.ORE_NOTIFY, new Sound("OreNotify", SoundCategory.INVENOTY_OPEN, org.bukkit.Sound.LEVEL_UP, TimberNoCheat.getInstance()));
    }

    @Override
    public Inventory build(Player p) {
        final OreNotifyData data = TimberNoCheat.getInstance().getOreNotifyManager().getData(p);
        Inventory inv = Bukkit.getServer().createInventory(null, InventoryType.DISPENSER, "§6OreNotify");
        inv.addItem(ItemStackUtil.createColoredWool("§6Notify", 1, data.isActive()));
        inv.addItem(ItemStackUtil.createColoredWool("§bDiamond", 1, data.isDiamond()));
        inv.addItem(ItemStackUtil.createColoredWool("§aEmerald", 1, data.isEmeralds()));
        inv.addItem(ItemStackUtil.createColoredWool("§cRedstone", 1, data.isRedstone()));
        return inv;
    }

    @Override
    public void itemclick(Player p, Inventory inv, ItemStack itemstack, InventoryAction inventoryaction, ClickType clicktype, int slot) {
        OreNotifyData data = TimberNoCheat.getInstance().getOreNotifyManager().getData(p);
        switch (itemstack.getItemMeta().getDisplayName()){
            case "§6Notify":
                data.setActive(!data.isActive());
                break;
            case "§bDiamond":
                data.setDiamond(!data.isDiamond());
                break;
            case "§aEmerald":
                data.setEmeralds(!data.isEmeralds());
                break;
            case "§cRedstone":
                data.setRedstone(!data.isRedstone());
                break;
        }
        TimberNoCheat.getInstance().getGuimanager().reopen(p);
    }
}
