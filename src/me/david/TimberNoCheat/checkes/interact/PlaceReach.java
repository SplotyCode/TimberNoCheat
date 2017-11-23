package me.david.TimberNoCheat.checkes.interact;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.api.utils.player.PlayerUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceReach extends Check {

    private final double distancenormal;
    private final double distancecreative;
    private final double vlmodi;
    public PlaceReach(){
        super("PlaceReach", Category.INTERACT);
        distancenormal = getDouble("distancenormal");
        distancecreative = getDouble("distancecreative");
        vlmodi = getDouble("vlmodi");
    }

    @EventHandler
    public void onBreak(BlockPlaceEvent e){
        final Player p = e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p))
            return;
        final double reach = e.getBlock().getLocation().distance(PlayerUtil.getEyeLocation(p));
        final double maxreach = p.getGameMode() == GameMode.CREATIVE?distancecreative:distancenormal;
        if(reach > maxreach){
            e.setCancelled(true);
            updatevio(this, p, (reach-maxreach)*vlmodi, "§6REACH: §b" + reach);
        }
    }
}
