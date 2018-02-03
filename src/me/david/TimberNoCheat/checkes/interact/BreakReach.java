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
import org.bukkit.event.block.BlockBreakEvent;

public class BreakReach extends Check {

    private final double distancenormal;
    private final double distancecreative;
    private final double vlmodi;
    public BreakReach(){
        super("BreakReach", Category.INTERACT);
        distancenormal = getDouble("distancenormal");
        distancecreative = getDouble("distancecreative");
        vlmodi = getDouble("vlmodi");
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        final Player p = e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)) return;
        final double maxreach = p.getGameMode() == GameMode.CREATIVE?distancecreative:distancenormal;
        Location loc = p.getLocation();
        final double reach = LocationUtil.distance(loc.getX(), loc.getY() + p.getEyeHeight(), loc.getZ(), 0.5 + e.getBlock().getX(), 0.5 + e.getBlock().getY(), 0.5 + e.getBlock().getZ());
        TimberNoCheat.instance.getDebuger().sendDebug(Debuggers.RANGE, "BREAK: MaxReach=" + maxreach + " Reach=" + reach);
        if(reach > maxreach){
            e.setCancelled(true);
            updatevio(this, p, (reach-maxreach)*vlmodi, "§6REACH: §b" + reach);
        }
    }
}
