package me.david.TimberNoCheat.gui.blocktrigger;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.command.blocktrigger.TriggerAction;
import me.david.TimberNoCheat.config.Permissions;
import me.david.api.guis.standart.ArrayGui;
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

import java.util.ArrayList;
import java.util.Arrays;

public class TriggerBlockGui extends ArrayGui<TriggerAction> {

    public TriggerBlockGui() {
        super(TriggerAction.values(), "TriggerBlockGui", new Sound("TriggerBlockOpen", SoundCategory.INVENOTY_OPEN, org.bukkit.Sound.LEVEL_UP, TimberNoCheat.instance), Permissions.BLOCK_TRIGGERS);
    }

    @Override
    protected void itemclick(TriggerAction obj, Player p, Inventory inv, ItemStack is, InventoryAction action, ClickType clicktype, int slot) {
        TimberNoCheat.instance.getTriggerBlockManager().getPending().put(p.getUniqueId(), obj);
        p.sendMessage(TimberNoCheat.instance.prefix + "Super du kannst nun ein Paar argumente übergeben!");
        p.sendMessage(TimberNoCheat.instance.prefix + "Seperiert werden diese mit einem ',");
        ArrayList<String> args = new ArrayList<>();
        Arrays.stream(obj.getClasses()).forEach((aClass -> args.add(aClass.getSimpleName())));
        p.sendMessage(TimberNoCheat.instance.prefix + "Deine Argument Typen sehen so aus: " + StringUtil.toString(args, ", "));
    }

    @Override
    protected ItemStack getItemStack(TriggerAction obj, Player player) {
        return ItemStackUtil.createbasic("§6" + obj.name(), 1, Material.WATCH);
    }
}
