package me.david.timbernocheat.checkes.interact;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;

public class FastPlace extends Check {

    private final int persecond;
    public FastPlace(){
        super("FastPlace", Category.INTERACT);
        persecond = getInt("persecond");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlace(BlockPlaceEvent event){
        final Player player = event.getPlayer();
        if(event.isCancelled() || !CheckManager.getInstance().isvalid_create(player)) return;
        PlayerData pd = CheckManager.getInstance().getPlayerdata(player);
        pd.setBlockPlacesLastSecond(pd.getBlockPlacesLastSecond()+1);
        Bukkit.getScheduler().runTaskLater(TimberNoCheat.getInstance(), () -> pd.setBlockPlacesLastSecond(pd.getBlockPlacesLastSecond()-1), 20);
        if(pd.getBlockPlacesLastSecond() > persecond){
            event.setCancelled(true);
            pd.setBlockPlacesLastSecond(0);
            updateVio(this, player, pd.getBlockPlacesLastSecond()-persecond, " §6BLOCKPLACEPERSECOND: §b" + pd.getBlockPlacesLastSecond());
        }
    }
}
