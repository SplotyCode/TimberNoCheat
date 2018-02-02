package me.david.TimberNoCheat.gui;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.config.Permissions;
import me.david.TimberNoCheat.debug.Debuggers;
import me.david.api.guis.standart.ArrayGui;
import me.david.api.sound.Sound;
import me.david.api.sound.SoundCategory;
import me.david.api.utils.ItemStackUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DebugGui extends ArrayGui<Debuggers> {

    public DebugGui() {
        super(Debuggers.values(), "§6Debug Stuff", new Sound("DebugGuiOpen", SoundCategory.INVENOTY_OPEN, org.bukkit.Sound.LEVEL_UP, TimberNoCheat.instance), Permissions.DEBUGGER);
    }

    @Override
    protected ItemStack getItemStack(Debuggers obj, Player player) {
        return ItemStackUtil.createbasic("§6Toggle §b" + obj.name(), 1, TimberNoCheat.instance.getDebuger().isDebugging(player, obj.name())?Material.REDSTONE:Material.GLOWSTONE_DUST);
    }

    @Override
    protected void itemclick(Debuggers obj, Player p, Inventory inv, ItemStack is, InventoryAction action, ClickType clicktype, int slot) {
        TimberNoCheat.instance.getDebuger().toggleDebugger(p.getUniqueId(), obj.name());
    }
}
