package me.david.timbernocheat.checkes.interact;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
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
    public void onPlace(BlockPlaceEvent e){
        final Player p = e.getPlayer();
        if(e.isCancelled() || !TimberNoCheat.getCheckManager().isvalid_create(p)) return;
        PlayerData pd = TimberNoCheat.getCheckManager().getPlayerdata(p);
        pd.setBlockplacelastsecond(pd.getBlockplacelastsecond()+1);
        Bukkit.getScheduler().runTaskLater(TimberNoCheat.getInstance(), () -> pd.setBlockplacelastsecond(pd.getBlockplacelastsecond()-1), 20);
        if(pd.getBlockplacelastsecond() > persecond){
            e.setCancelled(true);
            pd.setBlockplacelastsecond(0);
            updateVio(this, p, pd.getBlockplacelastsecond()-persecond, " §6BLOCKPLACEPERSECOND: §b" + pd.getBlockplacelastsecond());
        }
    }
}
