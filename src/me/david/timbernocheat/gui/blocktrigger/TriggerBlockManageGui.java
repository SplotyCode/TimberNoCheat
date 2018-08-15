package me.david.timbernocheat.gui.blocktrigger;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.command.blocktrigger.AttributeList;
import me.david.timbernocheat.command.blocktrigger.TriggerBlock;
import me.david.timbernocheat.config.Permissions;
import me.david.api.FarbCodes;
import me.david.api.guis.CloseReason;
import me.david.api.guis.standart.NoStaticListGui;
import me.david.api.sound.Sound;
import me.david.api.sound.SoundCategory;
import me.david.api.utils.ItemStackUtil;
import me.david.api.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

//TODO: Maby better Api integration for fix items that you want to add to a guilist???
public class TriggerBlockManageGui extends NoStaticListGui<TriggerBlock> {

    public TriggerBlockManageGui() {
        super("TriggerBlockManageGui", new Sound("TriggerBlockManageOpen", SoundCategory.INVENOTY_OPEN, org.bukkit.Sound.LEVEL_UP, TimberNoCheat.getInstance()), Permissions.BLOCK_TRIGGERS);
    }

    @Override
    protected List<TriggerBlock> getList(Player player) {
        ArrayList<TriggerBlock> list = (ArrayList<TriggerBlock>) TimberNoCheat.getInstance().getTriggerBlockManager().getTriggerBlocks().clone();
        //AddButton
        list.add(new TriggerBlock());
        return list;
    }

    @Override
    protected ItemStack getItemStack(TriggerBlock obj, Player player) {
        if(obj.getAtributes() == null && obj.getAction() == null && obj.getCreator() == null && obj.getLocation() == null){
            return ItemStackUtil.createColoredWool("§aAdd", 1, FarbCodes.LIME.getId());
        }
        return ItemStackUtil.createLohre("§6" + obj.getAction().name(), 1, Material.EMERALD_ORE, "§6Location: §b" + obj.getLocation().getBlockX() + "/" + obj.getLocation().getBlockY() + "/" + obj.getLocation().getBlockZ(), "§6Creator §b" + Bukkit.getOfflinePlayer(obj.getCreator()).getName(), "§6Arguments: §b" + StringUtil.toString(obj.getAtributes().getRealArguments(), ", "), "§cRightclick: Delete");
    }

    @Override
    protected void itemclick(TriggerBlock obj, Player p, Inventory inv, ItemStack is, InventoryAction action, ClickType clicktype, int slot) {
        if(obj.getAtributes() == null && obj.getAction() == null && obj.getCreator() == null && obj.getLocation() == null){
            TimberNoCheat.getInstance().getGuiManager().removeMultiGui(p, false, CloseReason.REMOVE);
            TimberNoCheat.getInstance().getGuiManager().startMultidefaultStage(p, "TriggerBlockMulti");
        }else {
            if(clicktype.isRightClick()){
                TimberNoCheat.getInstance().getTriggerBlockManager().getTriggerBlocks().remove(obj);
                p.sendMessage(TimberNoCheat.getInstance().prefix + "§cOkay, killed!");
            }else if(clicktype.isLeftClick()){
                final Location location = obj.getLocation();
                final AttributeList atributes = obj.getAtributes();
                p.sendMessage(TimberNoCheat.getInstance().prefix + "---[Trigger]---");
                p.sendMessage(TimberNoCheat.getInstance().prefix + "Location: §b" + location.getWorld().getName() + " - " + obj.getLocation().getBlockX() + "/" + obj.getLocation().getBlockY() + "/" + obj.getLocation().getBlockZ());
                p.sendMessage(TimberNoCheat.getInstance().prefix + "Creator: §b" + Bukkit.getOfflinePlayer(obj.getCreator()).getName());
                p.sendMessage(TimberNoCheat.getInstance().prefix + "Action: §b" + obj.getAction().name());
                p.sendMessage(TimberNoCheat.getInstance().prefix + "Attributes: §b" + StringUtil.toString(atributes.getRealArguments(), ", "));
                p.sendMessage(TimberNoCheat.getInstance().prefix + "---[Trigger]---");
            }
        }
    }
}
