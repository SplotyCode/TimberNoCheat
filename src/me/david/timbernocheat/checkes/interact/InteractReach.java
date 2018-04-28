package me.david.timbernocheat.checkes.interact;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.debug.Debuggers;
import me.david.api.utils.cordinates.LocationUtil;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractReach extends Check {

    private final double distancenormal;
    private final double distancecreative;
    private final double vlmodi;

    public InteractReach(){
        super("InteractReach", Category.INTERACT);
        distancenormal = getDouble("distancenormal");
        distancecreative = getDouble("distancecreative");
        vlmodi = getDouble("vlmodi");
    }

    @EventHandler
    public void oninteract(PlayerInteractEvent event){
        final Player player = event.getPlayer();
        if(!CheckManager.getInstance().isvalid_create(player) || event.getClickedBlock() == null) return;
        Location loc = player.getLocation();
        final double reach = LocationUtil.distance(loc.getX(), loc.getY() + player.getEyeHeight(), loc.getZ(), 0.5 + event.getClickedBlock().getX(), 0.5 + event.getClickedBlock().getY(), 0.5 + event.getClickedBlock().getZ());
        final double maxreach = player.getGameMode() == GameMode.CREATIVE?distancecreative:distancenormal;
        TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.RANGE, "INTERACT: MaxReach=" + maxreach + " Reach=" + reach);

        if(reach > maxreach){
            event.setCancelled(true);
            updateVio(this, player, (reach-maxreach)*vlmodi, "§6REACH: §b" + reach);
        }
    }
}
