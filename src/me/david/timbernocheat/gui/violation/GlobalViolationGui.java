package me.david.timbernocheat.gui.violation;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import me.david.api.guis.CloseReason;
import me.david.api.guis.standart.NoStaticListGui;
import me.david.api.utils.ItemStackUtil;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.api.TNCAPI;
import me.david.timbernocheat.config.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class GlobalViolationGui extends NoStaticListGui<OfflinePlayer> {


    public GlobalViolationGui() {
        super("PlayerViolationGui", Permissions.VIOLATIONMENU, TimberNoCheat.instance);
    }

    @Override
    protected List<OfflinePlayer> getList(Player player) {
        ArrayList<OfflinePlayer> list = new ArrayList<>();
        HashMap<UUID, Double> violations = TimberNoCheat.checkmanager.getViolations();
        violations.keySet().forEach((player1) -> list.add(Bukkit.getOfflinePlayer(player1)));
        list.sort(Comparator.comparingDouble(player1 -> violations.get(player1.getUniqueId())));
        return list;
    }

    @Override
    protected void itemclick(OfflinePlayer obj, Player p, Inventory inv, ItemStack is, InventoryAction action, ClickType clicktype, int slot) {
        if(clicktype.isLeftClick()) {
            PlayerViolationGui.data.put(p.getUniqueId(), obj.getUniqueId());
            TimberNoCheat.instance.guimanager.removeMultiGui(p, false, CloseReason.REMOVE);
            TimberNoCheat.instance.guimanager.startMultidefaultStage(p, "PlayerViolationMulti");
        }else if(clicktype.isRightClick()) {
            if(clicktype.isShiftClick()) {
                TimberNoCheat.instance.executeEssentials(p, (user -> {
                    if(user.isVanished()){
                        p.sendMessage(TimberNoCheat.instance.prefix + "§cTrotel Du bist schon im Vanish...");
                    }else user.setVanished(true);
                }));
            }
            Player target = Bukkit.getPlayer(obj.getUniqueId());
            if(target == null)
            p.sendMessage(TimberNoCheat.instance.prefix + "§cDer Spieler ist momentan nicht online!");
            else p.teleport(target);
        }
    }

    @Override
    protected ItemStack getItemStack(OfflinePlayer obj, Player player) {
        return ItemStackUtil.createPlayerHeadLohre("§6" + TNCAPI.getAllViolations(obj.getUniqueId()), 1, obj.getName(), "§6LeftClick: MoreInfos", "§6Rightclick: Teleport", "§6ShiftRightclick: Teleport+Vanish");
    }
}
