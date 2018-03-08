package me.david.timbernocheat.gui.debug;

import me.david.api.utils.StringUtil;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.config.Permissions;
import me.david.timbernocheat.debug.Debuggers;
import me.david.api.guis.CloseReason;
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

public class GuiDebug extends ArrayGui<Debuggers> {

    public GuiDebug() {
        super(Debuggers.values(), "§6Debug Stuff", new Sound("DebugGuiOpen", SoundCategory.INVENOTY_OPEN, org.bukkit.Sound.LEVEL_UP, TimberNoCheat.getInstance()), Permissions.DEBUGGER);
    }

    @Override
    protected ItemStack getItemStack(Debuggers obj, Player player) {
        return ItemStackUtil.createLohre(
                "§6Toggle §b" + obj.name(), 1,
                TimberNoCheat.getInstance().getDebugger().isDebugging(player, obj.name())?Material.GLOWSTONE_DUST:Material.REDSTONE,
                "§6External: " + StringUtil.colorbyBool(obj.isExternal()) + (obj.isExternal()?"Ja":"Nein"),
                "§6Dependecy: §b" + obj.getDependecy());
    }

    @Override
    protected void itemclick(Debuggers obj, Player p, Inventory inv, ItemStack is, InventoryAction action, ClickType clicktype, int slot) {
        if(clicktype.isLeftClick()) {
            TimberNoCheat.getInstance().getDebugger().toggleDebugger(p.getUniqueId(), obj.name());
            p.sendMessage(TimberNoCheat.getInstance().prefix + "Der Debugger §b" + obj.name() + "§6 ist nun " + (TimberNoCheat.getInstance().getDebugger().isDebugging(p, obj.name()) ? "§aAktiviert" : "§cDeaktiviert") + "§6!");
            TimberNoCheat.getInstance().getGuimanager().reopen(p);
        }else if(clicktype.isRightClick()){
            TimberNoCheat.getInstance().getGuimanager().removeMultiGui(p, false, CloseReason.REMOVE);
            DebugSetting.data.put(p.getUniqueId(), obj);
            TimberNoCheat.getInstance().getGuimanager().startMultidefaultStage(p, "DebugSettingMulti");
        }
    }
}
