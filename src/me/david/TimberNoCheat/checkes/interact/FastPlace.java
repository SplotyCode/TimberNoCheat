package me.david.TimberNoCheat.checkes.interact;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;

public class FastPlace extends Check{

    public FastPlace(){
        super("FastPlace", Category.INTERACT);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlace(BlockPlaceEvent e){
        final Player p = e.getPlayer();
        if(e.isCancelled()){
            return;
        }
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        pd.setBlockplacelastsecond(pd.getBlockplacelastsecond()+1);
        Bukkit.getScheduler().runTaskLater(TimberNoCheat.instance, new Runnable() {
            @Override
            public void run() {
                pd.setBlockplacelastsecond(pd.getBlockplacelastsecond()-1);
            }
        }, 20);
        if(pd.getBlockplacelastsecond() > TimberNoCheat.instance.settings.interact_fasplace_cps){
            e.setCancelled(true);
            pd.setBlockplacelastsecond(0);
            TimberNoCheat.checkmanager.notify(this, p, " §6BLOCKPLACEPERSECOND: §b" + pd.getBlockplacelastsecond());
        }
    }
}
