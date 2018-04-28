package me.david.timbernocheat.gui.debug;

import me.david.api.objects.Pair;
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

public class DebugGui extends ArrayGui<Debuggers> {

    public DebugGui() {
        super(Debuggers.values(), "§6Debug Stuff", new Sound("DebugGuiOpen", SoundCategory.INVENOTY_OPEN, org.bukkit.Sound.LEVEL_UP, TimberNoCheat.getInstance()), Permissions.DEBUGGER);
    }

    private long index;

    @Override
    protected ItemStack getItemStack(Debuggers obj, Player player) {
        return ItemStackUtil.createLohre(
                "§6Toggle §b" + obj.name(), 1,
                TimberNoCheat.getInstance().getDebugger().isDebugging(player, obj.name())?Material.GLOWSTONE_DUST:Material.REDSTONE,
                "§6External: " + StringUtil.colorbyBool(obj.isExternal()) + (obj.isExternal()?"Ja":"Nein"),
                ("§b" + obj.getSettings().size() + " §6Options §b" + (obj.isExternal()?obj.getDebugger().getButtonNames().size():0) + " §6Actions"),
                "§6Dependecy: §b" + obj.getDependency().getCategory().name() + "_" + obj.getDependency().displayName(),
                "§6Left-Click => Toggle",
                "§6Right-Click => Settings/Actions",
                "§6Shit-Left-Click => Create Toggle Item",
                "§6Shit-Right-Click => Create No Permission Toggle Item");
    }

    @Override
    protected void itemclick(Debuggers obj, Player player, Inventory inv, ItemStack is, InventoryAction action, ClickType clicktype, int slot) {
        if(clicktype.isShiftClick()){
            ItemStack itemStack = ItemStackUtil.createLohre("§b" + obj.name() + " Loading...", 1, Material.STICK, "§6Creator: §b" + player.getName(), "§6ID: §6b" + index);
            index++;
            player.getInventory().addItem(itemStack);
            TimberNoCheat.getInstance().getListenerManager().getInteractHandler().getDebugItems().put(itemStack, new Pair<>(player, clicktype.isRightClick()));
        }else if(clicktype.isLeftClick()) {
            TimberNoCheat.getInstance().getDebugger().toggleDebugger(player.getUniqueId(), obj.name());
            player.sendMessage(TimberNoCheat.getInstance().prefix + "Der Debugger §b" + obj.name() + "§6 ist nun " + (TimberNoCheat.getInstance().getDebugger().isDebugging(player, obj.name()) ? "§aAktiviert" : "§cDeaktiviert") + "§6!");
            TimberNoCheat.getInstance().getGuimanager().reopen(player);
        }else if(clicktype.isRightClick()){
            TimberNoCheat.getInstance().getGuimanager().removeMultiGui(player, false, CloseReason.REMOVE);
            DebugSetting.data.put(player.getUniqueId(), obj);
            TimberNoCheat.getInstance().getGuimanager().startMultidefaultStage(player, "DebugSettingMulti");
        }
    }
}
