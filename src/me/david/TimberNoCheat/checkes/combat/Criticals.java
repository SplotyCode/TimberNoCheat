package me.david.TimberNoCheat.checkes.combat;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.api.utils.player.PlayerUtil;
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
        final Player p = (Player) event.getDamager();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        if (!PlayerUtil.isOnGround(p) && !p.getAllowFlight() && p.getLocation().getY() % 1 == 0 && p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
            updatevio(this, p, 1);
        }
    }
}
