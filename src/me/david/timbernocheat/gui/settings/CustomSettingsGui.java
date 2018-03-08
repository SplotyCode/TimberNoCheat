package me.david.timbernocheat.gui.settings;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.config.Permissions;
import me.david.api.guis.CloseReason;
import me.david.api.guis.standart.NoStaticListGui;
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

import java.util.*;

public class CustomSettingsGui extends NoStaticListGui<String> {

    public static HashMap<UUID, String> players = new HashMap<>();

    public CustomSettingsGui() {
        super("CustomSettingsGui", new Sound("SettingsGui", SoundCategory.INVENOTY_OPEN, org.bukkit.Sound.LEVEL_UP, TimberNoCheat.getInstance()), Permissions.SETTINGS);
    }

    @Override
    protected List<String> getList(Player player) {
        return SettingsGui.currentCheck.get(player.getUniqueId()).getCustomSettings();
    }

    @Override
    protected ItemStack getItemStack(String obj, Player player) {
        Check check = SettingsGui.currentCheck.get(player.getUniqueId());
        return ItemStackUtil.createLohre(obj, 1, Material.LADDER, "§6Node: §b" + obj, "§6Value: §b" + check.getYml().get(obj));
    }

    @Override
    protected void itemclick(String obj, Player p, Inventory inv, ItemStack is, InventoryAction action, ClickType clicktype, int slot) {
        players.put(p.getUniqueId(), obj);
        TimberNoCheat.getInstance().getGuimanager().removeMultiGui(p, false, CloseReason.REMOVE);
        p.sendMessage(TimberNoCheat.getInstance().prefix + "Du kannst den neuen Wert von '" + obj + "' in den Chat eingeben");
    }

    @Override
    public void close(Player player, CloseReason reason) {
        if(reason != CloseReason.REMOVE)
            Bukkit.getScheduler().runTaskLater(TimberNoCheat.getInstance(), () -> TimberNoCheat.getInstance().getGuimanager().startMultidefaultStage(player, "ReloadMulti"), 1);
    }
}
