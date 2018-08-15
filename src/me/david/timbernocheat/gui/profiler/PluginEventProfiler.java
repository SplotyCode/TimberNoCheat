package me.david.timbernocheat.gui.profiler;

import me.david.api.guis.CloseReason;
import me.david.api.guis.standart.NoStaticListGui;
import me.david.api.utils.ItemStackUtil;
import me.david.api.utils.StringUtil;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.config.Permissions;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.TimedRegisteredListener;

import java.util.ArrayList;
import java.util.List;

public class PluginEventProfiler extends NoStaticListGui<TimedRegisteredListener> {

    public PluginEventProfiler() {
        super("PluginEventProfiler", Permissions.PROFILER_EVENT, TimberNoCheat.getInstance());
    }

    @Override
    protected List<TimedRegisteredListener> getList(Player player) {
        Plugin plugin = EventProfiler.session.get(player.getUniqueId());
        ArrayList<TimedRegisteredListener> list = getValidListener(plugin);
        list.add(null);
        return list;
    }

    public static ArrayList<TimedRegisteredListener> getValidListener(Plugin plugin){
        ArrayList<TimedRegisteredListener> list = new ArrayList<>();
        ArrayList<RegisteredListener> handlers = HandlerList.getRegisteredListeners(plugin);
        for(RegisteredListener listener : handlers)
            if(listener instanceof TimedRegisteredListener)
                list.add((TimedRegisteredListener) listener);
        return list;
    }

    @Override
    public void close(Player player, CloseReason reason) {
        if(reason != CloseReason.REOPEN)
            EventProfiler.session.remove(player.getUniqueId());
    }

    @Override
    protected ItemStack getItemStack(TimedRegisteredListener obj, Player player) {
        if(obj == null) return ItemStackUtil.createLohre("§6Reset All", 1, Material.REDSTONE_BLOCK);
        else return ItemStackUtil.createLohre("§6" + obj.getEventClass().getSimpleName(), 1, Material.LEVER, "§6Calls: " + obj.getCount(), "§6Time: " + obj.getTotalTime(), "§6Multi: " + StringUtil.bool(obj.hasMultiple(), true), "§cLeftClick->Reset Timings");
    }

    @Override
    protected void itemclick(TimedRegisteredListener obj, Player p, Inventory inv, ItemStack is, InventoryAction action, ClickType clicktype, int slot) {
        if(obj == null){
            for(TimedRegisteredListener listener : getValidListener(EventProfiler.session.get(p.getUniqueId())))
                listener.reset();
        }else obj.reset();
        TimberNoCheat.getInstance().getGuiManager().reopen(p);
    }
}
