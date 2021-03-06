package me.david.timbernocheat.gui.profiler;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.config.Permissions;
import me.david.api.guis.CloseReason;
import me.david.api.guis.Gui;
import me.david.api.utils.ItemStackUtil;
import me.david.api.utils.OLD_Sounds;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiProfiler extends Gui {
    public GuiProfiler() {
        super("GuiProfiler", OLD_Sounds.LEVELUP, Permissions.PROFILER);
    }

    private final ItemStack ALl_SCHEDULER = ItemStackUtil.createLohre("§6All Sheduler", 1, Material.WATCH, "§6List of all Sheduler");
    private final ItemStack SCHEDULER = ItemStackUtil.createLohre("§6Sheduler", 1, Material.WATCH, "§6profile some Sheduler");
    private final ItemStack MOVE = ItemStackUtil.createLohre("§6Move", 1, Material.POTION, "§6Profile All Movement Calculations");
    private final ItemStack EVENT = ItemStackUtil.createLohre("§6Event", 1, Material.COMMAND_MINECART, "§6Event Zeiten überprüfen!");

    @Override
    public Inventory build(Player p) {
        Inventory inv = Bukkit.getServer().createInventory(null, 9, "§6Profiler");
        inv.addItem(ALl_SCHEDULER, SCHEDULER, MOVE, EVENT);
        return inv;
    }

    @Override
    public void itemclick(Player p, Inventory inv, ItemStack itemstack, InventoryAction inventoryaction, ClickType clicktype, int slot) {
        switch (itemstack.getItemMeta().getDisplayName()){
            case "§6All Sheduler":
                TimberNoCheat.getInstance().getGuiManager().removeMultiGui(p, false, CloseReason.REOPEN);
                TimberNoCheat.getInstance().getGuiManager().startMultidefaultStage(p, "ProfilerAllSchedulerMulti");
                break;
            case "§6Move":
                TimberNoCheat.getInstance().getGuiManager().removeMultiGui(p, false, CloseReason.REOPEN);
                TimberNoCheat.getInstance().getGuiManager().startMultidefaultStage(p, "MoveProfilerMulti");
                break;
            case "§6Sheduler":
                TimberNoCheat.getInstance().getGuiManager().removeMultiGui(p, false, CloseReason.REOPEN);
                TimberNoCheat.getInstance().getGuiManager().startMultidefaultStage(p, "ProfilerSchedulerMulti");
                break;
            case "§6Event":
                TimberNoCheat.getInstance().getGuiManager().removeMultiGui(p, false, CloseReason.REOPEN);
                TimberNoCheat.getInstance().getGuiManager().startMultidefaultStage(p, "EventProfilerMulti");
                break;
        }
    }
}
