package me.david.TimberNoCheat.checkes.movement;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class FastLadder extends Check {

    public FastLadder(){
        super("", Category.MOVEMENT);
    }

    @EventHandler
    public void onWorldchange(PlayerChangedWorldEvent e){
        if (!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer())) {
            return;
        }
        TimberNoCheat.checkmanager.getPlayerdata(e.getPlayer()).setLastfastladderlongZ(-1);
    }
    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if (!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer()) || e.isCancelled() || !e.getTo().getWorld().getName().equals(e.getFrom().getWorld().getName())) {
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(e.getPlayer());
        if((e.getTo().getBlock().getType() == Material.LADDER && e.getFrom().getBlock().getType() == Material.LADDER) || e.getTo().getBlock().getType() == Material.LADDER){
            if(pd.getLastfastladderlongZ() != -1){
                pd.setLastfastladderlongZ(zdis(e.getTo(), e.getFrom()));
                pd.setFastladderlongstart(System.currentTimeMillis());
                pd.setFastladderstart(e.getFrom());
                return;
            }
            pd.setLastfastladderlongZ(pd.getLastfastladderlongZ()+zdis(e.getTo(), e.getFrom()));
            return;
        }
        if(pd.getLastfastladderlongZ() != -1 && pd.getLastfastladderlongZ()*1200<System.currentTimeMillis()-pd.getFastladderlongstart()){
            TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6MODE: §bLONGTIME", " §6BLOCKS: §b" + pd.getLastfastladderlongZ(), " §6NEDEDSECONDS: §b" + ((System.currentTimeMillis()-pd.getFastladderlongstart())/1000), " §6SHOUDNEDEDSECONDS: §b" + pd.getLastfastladderlongZ()*1200);
            e.getPlayer().teleport(pd.getLastspeedloc());
            pd.setLastfastladderlongZ(-1);
        }
        pd.setLastfastladderlongZ(-1);
    }
    public double zdis(final Location loc1, final Location loc2){
        loc1.subtract(0, 0, loc1.getZ());
        loc2.subtract(0, 0, loc2.getZ());
        return loc1.distance(loc2);
    }
}
