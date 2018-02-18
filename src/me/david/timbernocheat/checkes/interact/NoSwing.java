package me.david.timbernocheat.checkes.interact;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEvent;

public class NoSwing extends Check {

    private final long checkdelay;
    public NoSwing(){
        super("NoSwing", Category.INTERACT);
        checkdelay = getLong("checkdelay");
    }

    /*@EventHandler
    public void onInventoryMoveItemEvent(final InventoryMoveItemEvent event) {
        //System.out.println(event.getSource().getHolder() + " " + event.getDestination().getHolder());
    }*/

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent e){
        if(!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer()) || e.isCancelled() || checkdelay == -1 || e.getAction() != Action.LEFT_CLICK_AIR && e.getAction() != Action.LEFT_CLICK_BLOCK) return;
        TimberNoCheat.checkmanager.getPlayerdata(e.getPlayer()).setShoudswing(true);
        Bukkit.getScheduler().runTaskLater(TimberNoCheat.instance, new Runnable() {
            @Override
            public void run() {
                if(TimberNoCheat.checkmanager.getPlayerdata(e.getPlayer()).isShoudswing())
                    updatevio(NoSwing.this, e.getPlayer(), 1);
            }
        }, checkdelay);
    }
    @EventHandler
    public void onSwing(PlayerAnimationEvent e){
        //System.out.println("a");
        if(!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer()) || e.getAnimationType() != PlayerAnimationType.ARM_SWING) {
            return;
        }
        TimberNoCheat.checkmanager.getPlayerdata(e.getPlayer()).setShoudswing(false);
    }
}
