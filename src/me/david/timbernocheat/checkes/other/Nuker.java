package me.david.timbernocheat.checkes.other;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.PlayerData;
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
        if(!TimberNoCheat.getCheckManager().isvalid_create(p)){
            return;
        }
        PlayerData pd = TimberNoCheat.getCheckManager().getPlayerdata(p);
        pd.setBlockBreaksLastSec(pd.getBlockBreaksLastSec()+1);
        Bukkit.getScheduler().runTaskLater(TimberNoCheat.getInstance(), () -> pd.setBlockBreaksLastSec(pd.getBlockBreaksLastSec()-1), 20);
        if(pd.getBlockBreaksLastSec() > maxblockpersec){
            //TimberNoCheat.getCheckManager().notify(this, e.getPlayer(), " §6BLOCKBREAKS: §b" + pd.getBlockBreaksLastSec());
            updateVio(this, p, pd.getBlockBreaksLastSec()-maxblockpersec*2, " §6BLOCKBREAKS: §b" + pd.getBlockBreaksLastSec());
            //pd.setBlockBreaksLastSec(0);
        }

    }
}
