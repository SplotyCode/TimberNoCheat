package me.david.TimberNoCheat.checkes.interact;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.debug.Debuggers;
import me.david.api.utils.cordinates.LocationUtil;
import me.david.api.utils.player.PlayerUtil;
import org.bukkit.GameMode;
import org.bukkit.Location;
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
    public void onPlace(BlockPlaceEvent e){
        final Player p = e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p))
            return;
        Location loc = p.getLocation();
        final double reach = LocationUtil.distance(loc.getX(), loc.getY() + p.getEyeHeight(), loc.getZ(), 0.5 + e.getBlockPlaced().getX(), 0.5 + e.getBlockPlaced().getY(), 0.5 + e.getBlockPlaced().getZ());
        final double maxreach = p.getGameMode() == GameMode.CREATIVE?distancecreative:distancenormal;
        TimberNoCheat.instance.getDebuger().sendDebug(Debuggers.RANGE, "PLACE: MaxReach=" + maxreach + " Reach=" + reach);

        if(reach > maxreach){
            e.setCancelled(true);
            updatevio(this, p, (reach-maxreach)*vlmodi, "§6REACH: §b" + reach);
        }
    }
}
