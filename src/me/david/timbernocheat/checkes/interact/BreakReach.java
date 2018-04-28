package me.david.timbernocheat.checkes.interact;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.debug.Debuggers;
import me.david.api.utils.player.PlayerUtil;
import org.bukkit.GameMode;
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
    public void onBreak(BlockBreakEvent event){
        final Player player = event.getPlayer();
        if(!CheckManager.getInstance().isvalid_create(player)) return;
        final double reach = event.getBlock().getLocation().distance(PlayerUtil.getEyeLocation(player));
        final double maxreach = player.getGameMode() == GameMode.CREATIVE?distancecreative:distancenormal;
        TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.RANGE, "BREAK: MaxReach=" + maxreach + " Reach=" + reach);
        if(reach > maxreach){
            event.setCancelled(true);
            updateVio(this, player, (reach-maxreach)*vlmodi, "§6REACH: §b" + reach);
        }
    }
}
