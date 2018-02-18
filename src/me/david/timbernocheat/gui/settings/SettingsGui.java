package me.david.timbernocheat.gui.settings;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Check;
import me.david.timbernocheat.config.Permissions;
import me.david.api.guis.CloseReason;
import me.david.api.guis.standart.ListGui;
import me.david.api.sound.Sound;
import me.david.api.sound.SoundCategory;
import me.david.api.utils.ItemStackUtil;
import me.david.api.utils.StringUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class SettingsGui extends ListGui<Check> {

    public static HashMap<UUID, Check> currentCheck = new HashMap<>();

    public SettingsGui() {
        super(TimberNoCheat.checkmanager.getAllChecks(), "SettingsGui", new Sound("SettingsGui", SoundCategory.INVENOTY_OPEN, org.bukkit.Sound.LEVEL_UP, TimberNoCheat.instance), Permissions.SETTINGS);
    }


    @Override
    protected void itemclick(Check obj, Player p, Inventory inv, ItemStack is, InventoryAction action, ClickType clicktype, int slot) {
        currentCheck.put(p.getUniqueId(), obj);

    }

    @Override
    protected ItemStack getItemStack(Check obj, Player player) {
        boolean enabled = TimberNoCheat.checkmanager.getChecks().contains(obj);
        Material mat = obj.getCategory().getMaterial();
        return ItemStackUtil.createLohre("§6" + obj.getName(), 1, mat, "§6Enabled: §b" + StringUtil.colorbyBool(enabled) + enabled, "§6Category: §b" + obj.getCategory(), "§6Childs: §b" + obj.getChilds().size());
    }

    @Override
    public void close(Player player, CloseReason reason) {
        if(reason != CloseReason.REOPEN && currentCheck.containsKey(player.getUniqueId())) currentCheck.remove(player.getUniqueId());
    }
}
