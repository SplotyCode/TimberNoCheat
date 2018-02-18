package me.david.timbernocheat.gui.violation;

import me.david.api.guis.CloseReason;
import me.david.api.guis.standart.NoStaticListGui;
import me.david.api.utils.ItemStackUtil;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Check;
import me.david.timbernocheat.config.Permissions;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class PlayerViolationGui extends NoStaticListGui<Check> {

    public static HashMap<UUID, UUID> data = new HashMap<>();

    public PlayerViolationGui() {
        super("PlayerViolationGui", Permissions.VIOLATIONMENU, TimberNoCheat.instance);
    }

    @Override
    protected List<Check> getList(Player p) {
        final UUID uuid = data.get(p.getUniqueId());
        ArrayList<Check> list = new ArrayList<>();
        for(final Check check : TimberNoCheat.checkmanager.getChecks())
            if(check.getViolation(uuid) > 0) list.add(check);
        list.sort(Comparator.comparingDouble(check -> check.getViolation(uuid)));
        return list;
    }

    @Override
    protected ItemStack getItemStack(Check obj, Player player) {
        return ItemStackUtil.createLohre("§6" + obj.getName(), 1, obj.getCategory().getMaterial(), "§6Violation: §b" + obj.getViolation(player), "§cRightclick: Clear");
    }

    @Override
    protected void itemclick(Check obj, Player p, Inventory inv, ItemStack is, InventoryAction action, ClickType clicktype, int slot) {
        if(clicktype.isRightClick()) obj.resetVio(data.get(p.getUniqueId()));
    }

    @Override
    public void close(Player player, CloseReason reason) {
        data.remove(player.getUniqueId());
    }
}
