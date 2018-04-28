package me.david.timbernocheat.checkes.interact;

import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class RightClickTimer extends Check {

    private final long max_delay;

    public RightClickTimer(){
        super("RightClickTimer", Category.INTERACT);
        max_delay = getLong("delay");
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        final Player p = e.getPlayer();
        if(!CheckManager.getInstance().isvalid_create(p) || (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)){
            return;
        }
        PlayerData pd = CheckManager.getInstance().getPlayerdata(p);
        long delay = System.currentTimeMillis() - pd.getLastRightClick();
        if(delay < max_delay){
            e.setCancelled(true);
            updateVio(this, p, 2);
        }
        pd.setLastRightClick(System.currentTimeMillis());
    }
}
