package me.david.timbernocheat.gui.profiler;

import me.david.api.guis.CloseReason;
import me.david.api.guis.standart.NoStaticListGui;
import me.david.api.utils.ItemStackUtil;
import me.david.api.utils.StringUtil;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.config.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class EventProfiler extends NoStaticListGui<Plugin> {

    public static HashMap<UUID, Plugin> session = new HashMap<>();

    public EventProfiler() {
        super("EventProfiler", Permissions.PROFILER_EVENT, TimberNoCheat.getInstance());
    }

    @Override
    protected List<Plugin> getList(Player player) {
        return Arrays.asList(Bukkit.getPluginManager().getPlugins());
    }

    @Override
    protected ItemStack getItemStack(Plugin obj, Player player) {
        return ItemStackUtil.createLohre("§6" + obj.getName() + " v" + obj.getDescription().getVersion(),
                1, Material.COMMAND_MINECART,
                "§6Depend: " + StringUtil.toString(obj.getDescription().getDepend(), ", "),
                "§6SoftDepend: " + StringUtil.toString(obj.getDescription().getSoftDepend(), ", "),
                "§6Authors: " + StringUtil.toString(obj.getDescription().getAuthors(), ", "),
                "§6Website: " + obj.getDescription().getWebsite(),
                "§6Description: " + obj.getDescription().getDescription(),
                "§6Listener: " + PluginEventProfiler.getValidListener(obj).size() + "/" + HandlerList.getRegisteredListeners(obj).size());
    }

    @Override
    protected void itemclick(Plugin obj, Player p, Inventory inv, ItemStack is, InventoryAction action, ClickType clicktype, int slot) {
        session.put(p.getUniqueId(), obj);
        TimberNoCheat.getInstance().getGuiManager().removeMultiGui(p, false, CloseReason.REMOVE);
        TimberNoCheat.getInstance().getGuiManager().startMultidefaultStage(p, "PluginEventProfilerMulti");
    }
}
