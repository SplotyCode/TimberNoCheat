package me.david.TimberNoCheat.checkes.other;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class Nuker extends Check {

    public Nuker(){
        super("Nuker", Category.OTHER);
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
        if(pd.getBlockbreakslastsec() > TimberNoCheat.instance.settings.other_nuker_maxblockbreakssec){
            TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6BLOCKBREAKS: §b" + pd.getBlockbreakslastsec());
            pd.setBlockbreakslastsec(0);
        }

    }
}
