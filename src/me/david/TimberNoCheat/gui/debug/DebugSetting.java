package me.david.TimberNoCheat.gui.debug;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.config.Permissions;
import me.david.TimberNoCheat.debug.Debuggers;
import me.david.api.FarbCodes;
import me.david.api.guis.CloseReason;
import me.david.api.guis.Gui;
import me.david.api.sound.Sound;
import me.david.api.sound.SoundCategory;
import me.david.api.utils.ItemStackUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DebugSetting extends Gui {

    public static HashMap<UUID, Debuggers> data = new HashMap<>();

    public DebugSetting() {
        super("DebugSetting", Permissions.DEBUGGER, new Sound("DebugGuiOpen", SoundCategory.INVENOTY_OPEN, org.bukkit.Sound.LEVEL_UP, TimberNoCheat.instance));
    }

    @Override
    public Inventory build(Player p) {
        Inventory inv = Bukkit.getServer().createInventory(null, 4*9, "§6Settings");
        Debuggers debugger = data.get(p.getUniqueId());
        for(String setting : debugger.getSettings().keySet())
            inv.addItem(ItemStackUtil.createColoredWool(setting, 1, (debugger.getSetting(p, setting)?FarbCodes.LIME.getId():FarbCodes.RED.getId())));
        return inv;
    }

    @Override
    public void itemclick(Player p, Inventory inv, ItemStack itemstack, InventoryAction inventoryaction, ClickType clicktype, int slot) {
        Debuggers debugger = data.get(p.getUniqueId());
        String name = itemstack.getItemMeta().getDisplayName();
        debugger.setSetting(p, name, !debugger.getSetting(p, name));
        TimberNoCheat.instance.guimanager.reopen(p);
    }

    @Override
    public void close(Player player, CloseReason reason) {
        if(reason != CloseReason.REOPEN) data.remove(player.getUniqueId());
    }
}
