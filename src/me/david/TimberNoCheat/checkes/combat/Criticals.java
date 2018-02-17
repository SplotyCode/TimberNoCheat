package me.david.TimberNoCheat.checkes.combat;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.api.utils.player.PlayerUtil;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Criticals extends Check {
    public Criticals() {
        super("Criticals", Category.COBMAT);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player))return;
        final Player player = (Player) event.getDamager();
        if(!TimberNoCheat.checkmanager.isvalid_create(player)) return;
        if (!PlayerUtil.isOnGround(player) && !player.getAllowFlight() && player.getLocation().getY() % 1 == 0 && player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
            updatevio(this, player, 1);
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
