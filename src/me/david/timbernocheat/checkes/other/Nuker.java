package me.david.timbernocheat.checkes.other;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
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
    public void onbreak(BlockBreakEvent event){
        final Player player = event.getPlayer();
        if(!CheckManager.getInstance().isvalid_create(player)){
            return;
        }
        PlayerData pd = CheckManager.getInstance().getPlayerdata(player);
        pd.setBlockBreaksLastSec(pd.getBlockBreaksLastSec()+1);
        Bukkit.getScheduler().runTaskLater(TimberNoCheat.getInstance(), () -> pd.setBlockBreaksLastSec(pd.getBlockBreaksLastSec()-1), 20);
        if(pd.getBlockBreaksLastSec() > maxblockpersec){
            //CheckManager.getInstance().notify(this, event.getPlayer(), " §6BLOCKBREAKS: §b" + pd.getBlockBreaksLastSec());
            updateVio(this, player, pd.getBlockBreaksLastSec()-maxblockpersec*2, " §6BLOCKBREAKS: §b" + pd.getBlockBreaksLastSec());
            //pd.setBlockBreaksLastSec(0);
        }

    }
}
