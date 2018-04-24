package me.david.timbernocheat.listener;

import javafx.util.Pair;
import me.david.api.events.anvil.AnvilEvent;
import me.david.api.objects.listener.PlayerListener;
import me.david.api.utils.InteractUtil;
import me.david.api.utils.StringUtil;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.command.internal.BoolUserRequest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class InteractHandler implements Listener {

    private HashMap<ItemStack, Pair<Player, Boolean>> debugItems = new HashMap<>();

    @EventHandler(ignoreCancelled = true)
    public void onRename(final AnvilEvent event){
        /*
         * TODO: Make debug items not renamable
         * Requires to finish the api Anvil Event
        */
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInteract(final PlayerInteractEvent event){
        final Player player = event.getPlayer();
        final ItemStack itemStack = event.getItem();
        final Pair<Player, Boolean> options = getDebugItem(itemStack);
        if(itemStack == null || !InteractUtil.isRightClick(event) || options == null) return;
        TimberNoCheat.getInstance().getDebugger().toggleDebugger(player.getUniqueId(), itemStack.getItemMeta().getDisplayName().split(" ")[0].substring(2));
        updateItems();
    }

    private Pair<Player, Boolean> getDebugItem(ItemStack itemStack){
        for(ItemStack is : debugItems.keySet())
            if(is.isSimilar(itemStack))
                return debugItems.get(is);
        return null;
    }


    @EventHandler
    public void onPickUp(final PlayerPickupItemEvent event){
        final Player player = event.getPlayer();
        final ItemStack itemStack = event.getItem().getItemStack();
        final Pair<Player, Boolean> options = getDebugItem(itemStack);
        if(options != null){
            Player owner = options.getKey();
            if(owner.getEntityId() != player.getEntityId()){
                event.setCancelled(true);
                BoolUserRequest pickupRequest = new BoolUserRequest(player.getName() + " versucht dein Debug Item aufzusammeln darf er das?",
                        (player1, value) -> {
                            if(value)
                                player.getInventory().addItem(itemStack);
                        }
                );
                pickupRequest.display(owner);
                event.getItem().remove();
            }
        }
    }

    public void updateItems(){
        for(Map.Entry<ItemStack, Pair<Player, Boolean>> debugItem : debugItems.entrySet()){
            ItemMeta itemMeta = debugItem.getKey().getItemMeta();
            String displayName = itemMeta.getDisplayName();
            String[] split = displayName.split(" ");
            boolean toogled = TimberNoCheat.getInstance().getDebugger().isDebugging(debugItem.getValue().getKey(), split[0].substring(2));
            displayName = split[0] + " §7[" + StringUtil.colorbyBool(toogled) + (toogled?"An":"Aus") + "§7]";
            itemMeta.setDisplayName(displayName);
            debugItem.getKey().setItemMeta(itemMeta);
            debugItem.getValue().getKey().updateInventory();
        }
    }

    public HashMap<ItemStack, Pair<Player, Boolean>> getDebugItems() {
        return debugItems;
    }
}
