package me.david.timbernocheat.checkes.combat;

import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.util.CheckUtils;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

//TODO
public class Criticals extends Check {

    public Criticals() {
        super("Criticals", Category.COBMAT);
    }

    @EventHandler
    public void onDamage(final EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player))return;
        final Player player = (Player) event.getDamager();
        if(!CheckManager.getInstance().isvalid_create(player)) return;
        if (!CheckUtils.onGround(player) && !player.getAllowFlight() && player.getLocation().getY() % 1 == 0 && player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
            if(updateVio(this, player, 1))
                event.setCancelled(true);
        }

    }

    /*private boolean checkBlockFree(Player player, double diff){
        if(!countTickexsits(player, "blockfree" + diff)){
            return false;
        }
        int a = 40;

    }

    private  boolean ground(Player player, Location location){

    }*/
}
