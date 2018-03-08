package me.david.timbernocheat.debug;

import me.david.timbernocheat.TimberNoCheat;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Set;
import java.util.function.Consumer;

/**
 * There are two kinds of Debuggers
 * - The 'Normal' one
 * - The External one (this)
 * Normal Debuggers often have a Dependency
 * External Debuggers are own classes
 * while Normal debuggers mostly print out the stuff that the Check currently does!
 * You can find all External Debuggers in debug/debuggers
 * The Normal Debuggers are Spread out over the hole Project
 * You can get a List of ALL debuggers in the Debuggers class.
 */
public class ExternalDebugger implements Listener {

    private HashMap<String, Consumer<Player>> buttons = new HashMap<>();

    protected void send(Debuggers debugger, String msg, Object... data){
        TimberNoCheat.instance.getDebugger().sendDebug(debugger, msg, data);
    }

    /**
     * Handels an Button click
     * @param player The Player Who clicked on the Button
     * @param itemStack The Itemstack the Player Clicked on
     * @return If we found a mehtod to the given ItemStack
     */
    public final boolean handleButtonClick(Player player, ItemStack itemStack){
        if(!itemStack.hasItemMeta() || !itemStack.getItemMeta().hasDisplayName())return false;
        Consumer<Player> methode = buttons.get(itemStack.getItemMeta().getDisplayName());
        if(methode != null) {
            methode.accept(player);
            return true;
        }
        return false;
    }

    /**
     * Gives you all Names from the buttons of the Debugger
     * @return All Names from the buttons of the Debugger
     */
    public final Set<String> getButtonNames(){
        return buttons.keySet();
    }

}
