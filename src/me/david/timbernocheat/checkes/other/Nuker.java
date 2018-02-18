package me.david.timbernocheat.checkes.other;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import me.david.timbernocheat.checkmanager.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class Nuker extends Check {

    private final int maxblockpersec;
    public Nuker(){
        super("Nuker", Category.OTHER);
        maxblockpersec = getInt("maxblockpersec");
    }

    @EventHandler
    public void onbreak(BlockBreakEvent e){
        final Player p = e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        pd.setBlockbreakslastsec(pd.getBlockbreakslastsec()+1);
        Bukkit.getScheduler().runTaskLater(TimberNoCheat.instance, new Runnable() {
            @Override
            public void run() {
                pd.setBlockbreakslastsec(pd.getBlockbreakslastsec()-1);
            }
        }, 20);
        if(pd.getBlockbreakslastsec() > maxblockpersec){
            //TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6BLOCKBREAKS: §b" + pd.getBlockbreakslastsec());
            updatevio(this, p, pd.getBlockbreakslastsec()-maxblockpersec*2, " §6BLOCKBREAKS: §b" + pd.getBlockbreakslastsec());
            //pd.setBlockbreakslastsec(0);
        }

    }
}
