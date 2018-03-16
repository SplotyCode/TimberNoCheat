package me.david.timbernocheat.checkes.interact;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.PlayerData;
import net.minecraft.server.v1_8_R3.PacketPlayInArmAnimation;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
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
        if(!TimberNoCheat.getCheckManager().isvalid_create(e.getPlayer()) || e.isCancelled() || checkdelay == -1 || e.getAction() != Action.LEFT_CLICK_AIR && e.getAction() != Action.LEFT_CLICK_BLOCK) return;

        PlayerData pd = TimberNoCheat.getCheckManager().getPlayerdata(e.getPlayer());
        if(pd.isArmSwung()) pd.setArmSwung(false);
        else if(updateVio(this, e.getPlayer(), 1)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onSwing(PlayerAnimationEvent e){
        //System.out.println("a");
        if(!TimberNoCheat.getCheckManager().isvalid_create(e.getPlayer()) || e.getAnimationType() != PlayerAnimationType.ARM_SWING) {
            return;
        }
        TimberNoCheat.getCheckManager().getPlayerdata(e.getPlayer()).setArmSwung(true);
    }
}
