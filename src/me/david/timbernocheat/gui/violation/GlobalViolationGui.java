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
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GlobalViolationGui extends NoStaticListGui<Player> {


    public GlobalViolationGui() {
        super("PlayerViolationGui", Permissions.VIOLATIONMENU, TimberNoCheat.instance);
    }

    @Override
    protected List<Player> getList(Player player) {
        ArrayList<Player> list = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach((player1) -> {
            if (TNCAPI.getAllViolations(player1) > 0)
                list.add(player1);
        });
        list.sort(Comparator.comparingDouble(TNCAPI::getAllViolations));
        return list;
    }

    Essentials essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");

    @Override
    protected void itemclick(Player obj, Player p, Inventory inv, ItemStack is, InventoryAction action, ClickType clicktype, int slot) {
        if(clicktype.isLeftClick()) {
            PlayerViolationGui.data.put(p.getUniqueId(), obj.getUniqueId());
            TimberNoCheat.instance.guimanager.removeMultiGui(p, false, CloseReason.REMOVE);
            TimberNoCheat.instance.guimanager.startMultidefaultStage(p, "PlayerViolationMulti");
        }else if(clicktype.isRightClick()) {
            if(clicktype.isShiftClick()) {
                if(essentials != null){
                    User user = essentials.getUser(p);
                    if(user.isVanished()){
                        p.sendMessage(TimberNoCheat.instance.prefix + "§cTrotel Du bist schon im Vanish...");
                    }else user.setVanished(true);
                }else p.sendMessage(TimberNoCheat.instance.prefix + "§cEssentials konnte nicht unter dem Namen 'Essentials' gefunden werden...");
            }
            p.teleport(obj);
        }
    }

    @Override
    protected ItemStack getItemStack(Player obj, Player player) {
        return ItemStackUtil.createPlayerHeadLohre("§6" + TNCAPI.getAllViolations(obj), 1, obj.getName(), "§6LeftClick: MoreInfos", "§6Rightclick: Teleport", "§6ShiftRightclick: Teleport+Vanish");
    }
}
