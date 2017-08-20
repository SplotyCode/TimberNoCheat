package me.david.TimberNoCheat.checkes.interact;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEvent;

public class NoSwing extends Check{

    public NoSwing(){
        super("NoSwing", Category.INTERACT);
    }

    public void onInventoryMoveItemEvent(final InventoryMoveItemEvent event) {
        System.out.println(event.getSource().getHolder() + " " + event.getDestination().getHolder());
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent e){
        if(!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer())){
            return;
        }
        if(e.isCancelled()){
            return;
        }
        if(TimberNoCheat.instance.settings.interact_noswing_delayticks == -1){
            return;
        }
        if(e.getAction() != Action.LEFT_CLICK_AIR && e.getAction() != Action.LEFT_CLICK_BLOCK){
            return;
        }
        TimberNoCheat.checkmanager.getPlayerdata(e.getPlayer()).setShoudswing(true);
        Bukkit.getScheduler().runTaskLater(TimberNoCheat.instance, new Runnable() {
            @Override
            public void run() {
                if(TimberNoCheat.checkmanager.getPlayerdata(e.getPlayer()).isShoudswing()){
                    TimberNoCheat.checkmanager.notify(NoSwing.this, e.getPlayer());
                }
            }
        }, TimberNoCheat.instance.settings.interact_noswing_delayticks);
    }
    @EventHandler
    public void onSwing(PlayerAnimationEvent e){
        System.out.println("a");
        if(!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer())){
            return;
        }
        if(e.getAnimationType() != PlayerAnimationType.ARM_SWING){
            return;
        }
        TimberNoCheat.checkmanager.getPlayerdata(e.getPlayer()).setShoudswing(false);
    }
}
