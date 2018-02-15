package me.david.TimberNoCheat.gui.settings;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.config.Permissions;
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
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CheckGui extends Gui {
    public CheckGui() {
        super("CheckGui", Permissions.SETTINGS, new Sound("SettingsGui", SoundCategory.INVENOTY_OPEN, org.bukkit.Sound.LEVEL_UP, TimberNoCheat.instance));
    }

    @Override
    public Inventory build(Player p) {
        Check check = SettingsGui.currentCheck.get(p.getUniqueId());
        Inventory inv = Bukkit.getServer().createInventory(null, InventoryType.DISPENSER, "§6" + check.getName());
        boolean enabled = TimberNoCheat.checkmanager.getChecks().contains(check);
        inv.addItem(ItemStackUtil.createbasic((enabled?"§An":"§cAus"), 1, (enabled? Material.REDSTONE_TORCH_ON:Material.REDSTONE_TORCH_OFF)));
        inv.addItem(ItemStackUtil.createbasic("§7Custom Settings", 1, Material.STONE_PICKAXE));
        inv.addItem(ItemStackUtil.createbasic("§bViolations", 1, Material.COMMAND));
        inv.addItem(ItemStackUtil.createbasic("§6Childs", 1, Material.COMMAND_MINECART));
        return inv;
    }

    @Override
    public void itemclick(Player p, Inventory inv, ItemStack itemstack, InventoryAction inventoryaction, ClickType clicktype, int slot) {
        Check check = SettingsGui.currentCheck.get(p.getUniqueId());
        switch (itemstack.getType()){
            case REDSTONE_TORCH_ON:
                check.setSetting("enable", false);
                TimberNoCheat.instance.guimanager.removeMultiGui(p, false, CloseReason.REMOVE);
                break;
            case REDSTONE_TORCH_OFF:
                check.setSetting("enable", true);
                TimberNoCheat.instance.guimanager.removeMultiGui(p, false, CloseReason.REMOVE);
                break;
            case STONE_PICKAXE:
                TimberNoCheat.instance.guimanager.removeMultiGui(p, false, CloseReason.REOPEN);
                TimberNoCheat.instance.guimanager.startMultidefaultStage(p, "CustomSettingMulti");
                break;
        }
    }

    @Override
    public void close(Player player, CloseReason reason) {
        if(reason != CloseReason.REOPEN)
            Bukkit.getScheduler().runTaskLater(TimberNoCheat.instance, () -> TimberNoCheat.instance.guimanager.startMultidefaultStage(player, "ReloadMulti"), 1);
    }
}
