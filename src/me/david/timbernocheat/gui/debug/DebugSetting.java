package me.david.timbernocheat.gui.debug;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.config.Permissions;
import me.david.timbernocheat.debug.Debuggers;
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
import java.util.UUID;

public class DebugSetting extends Gui {

    public static HashMap<UUID, Debuggers> data = new HashMap<>();

    public DebugSetting() {
        super("DebugSetting", Permissions.DEBUGGER, new Sound("DebugGuiOpen", SoundCategory.INVENOTY_OPEN, org.bukkit.Sound.LEVEL_UP, TimberNoCheat.getInstance()));
    }

    @Override
    public Inventory build(Player player) {
        Inventory inv = Bukkit.getServer().createInventory(null, 4*9, "§6Settings");
        Debuggers debugger = data.get(player.getUniqueId());
        if(debugger.isExternal())
            for(String name : debugger.getDebugger().getButtonNames())
                inv.addItem(ItemStackUtil.createbasic(name, 1, Material.WOOD_BUTTON));
        for(String setting : debugger.getSettings().keySet())
            inv.addItem(ItemStackUtil.createColoredWool(setting, 1, (debugger.getSetting(player, setting)?FarbCodes.LIME.getId():FarbCodes.RED.getId())));
        return inv;
    }

    @Override
    public void itemclick(Player player, Inventory inv, ItemStack itemstack, InventoryAction inventoryaction, ClickType clicktype, int slot) {
        Debuggers debugger = data.get(player.getUniqueId());
        String name = itemstack.getItemMeta().getDisplayName();
        boolean handeled = false;
        if(debugger.isExternal())
            handeled = debugger.getDebugger().handleButtonClick(player, itemstack);
        if(!handeled) debugger.setSetting(player, name, !debugger.getSetting(player, name));
        TimberNoCheat.getInstance().getGuimanager().reopen(player);
    }

    @Override
    public void close(Player player, CloseReason reason) {
        if(reason != CloseReason.REOPEN) data.remove(player.getUniqueId());
    }
}
