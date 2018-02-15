package me.david.TimberNoCheat.gui.settings;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.config.Permissions;
import me.david.api.guis.Gui;
import me.david.api.sound.Sound;
import me.david.api.sound.SoundCategory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class ViolationGui extends Gui {

    public ViolationGui() {
        super("ViolationGui", Permissions.SETTINGS, new Sound("SettingsGui", SoundCategory.INVENOTY_OPEN, org.bukkit.Sound.LEVEL_UP, TimberNoCheat.instance));
    }

    @Override
    public Inventory build(Player p) {
        Inventory inv = Bukkit.getServer().createInventory(null, InventoryType.DISPENSER, "§6Violations");
        
        return inv;
    }
}
