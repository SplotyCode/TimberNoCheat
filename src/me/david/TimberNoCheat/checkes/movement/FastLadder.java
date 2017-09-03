package me.david.TimberNoCheat.checkes.movement;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class FastLadder extends Check {

    private final long msperblock;
    private final double cancel_vl;
    public FastLadder(){
        super("", Category.MOVEMENT);
        msperblock = getLong("msperblock");
        cancel_vl = getDouble("cancel_vl");
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if (!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer()) || e.isCancelled() || !e.getTo().getWorld().getName().equals(e.getFrom().getWorld().getName())) {
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(e.getPlayer());
        double zdis = e.getTo().getZ()-e.getFrom().getZ();
        if((e.getTo().getBlock().getType() == Material.LADDER && e.getFrom().getBlock().getType() == Material.LADDER) || e.getTo().getBlock().getType() == Material.LADDER){
            if(pd.getLastfastladderlongZ() != -1){
                pd.setLastfastladderlongZ(zdis);
                pd.setFastladderlongstart(System.currentTimeMillis());
                pd.setFastladderstart(e.getFrom());
                return;
            }
            pd.setLastfastladderlongZ(pd.getLastfastladderlongZ()+zdis);
            return;
        }
        double shoud = pd.getLastfastladderlongZ()*msperblock;
        double does = System.currentTimeMillis()-pd.getFastladderlongstart();
        if(pd.getLastfastladderlongZ() != -1 && shoud<does){
            updatevio(this, e.getPlayer(), (int)(does-shoud), " §6MODE: §bLONGTIME", " §6BLOCKS: §b" + pd.getLastfastladderlongZ(), " §6NEDEDSECONDS: §b" + (does/1000), " §6SHOUDNEDEDSECONDS: §b" + (shoud/1000));
            pd.setLastfastladderlongZ(-1);
            pd.setFastladderstart(null);
        }
        if(getViolations().containsKey(e.getPlayer()) && getViolations().get(e.getPlayer()) >= cancel_vl && pd.getFastladderstart() != null)e.getPlayer().teleport(pd.getLastspeedloc());
        pd.setLastfastladderlongZ(-1);
    }
}
