package me.david.timbernocheat.checkes.movement;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import me.david.timbernocheat.checkmanager.PlayerData;
import me.david.timbernocheat.debug.Debuggers;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class FastLadder extends Check {

    private final long msperblock;
    private final double cancel_vl;
    private final int shortmulti;

    public FastLadder(){
        super("FastLadder", Category.MOVEMENT);
        msperblock = getLong("msperblock");
        cancel_vl = getDouble("cancel_vl");
        shortmulti = getInt("shortmulti");
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if (!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer()) || e.isCancelled() || !e.getTo().getWorld().getName().equals(e.getFrom().getWorld().getName())) {
            return;
        }
        TimberNoCheat.instance.getMoveprofiler().start("FastLadder");
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(e.getPlayer());
        double zdis = e.getTo().getZ()-e.getFrom().getZ();
        if(zdis <= 0)return;
        if((e.getTo().getBlock().getType() == Material.LADDER && e.getFrom().getBlock().getType() == Material.LADDER) || e.getTo().getBlock().getType() == Material.LADDER){
            if(pd.getLastfastladderlongZ() == -1){
                pd.setLastfastladderlongZ(zdis);
                pd.setFastladderlongstart(System.currentTimeMillis());
                pd.setFastladderstart(e.getFrom());
                return;
            }
            pd.setLastfastladderlongZ(pd.getLastfastladderlongZ()+zdis);
            TimberNoCheat.instance.getDebuger().sendDebug(Debuggers.FASTLADDER, "[ADD] " + zdis);
            if(pd.getLastfastladderlongZ() > 1.8 && zdis > 0.118)
                updateVio(this, e.getPlayer(), (zdis-0.118)*shortmulti, " §6MODE: §bSHORT");
            return;
        }
        double shoud = pd.getLastfastladderlongZ()*msperblock;
        double does = System.currentTimeMillis()-pd.getFastladderlongstart();
        if(shoud<0)return;
        if(pd.getLastfastladderlongZ() != -1 && shoud>does){
            TimberNoCheat.instance.getDebuger().sendDebug(Debuggers.FASTLADDER, " shoud=" + shoud + " actual=" + does);
            updateVio(this, e.getPlayer(), (int)(does-shoud), " §6MODE: §bLONGTIME", " §6BLOCKS: §b" + pd.getLastfastladderlongZ(), " §6NEDEDSECONDS: §b" + (does/1000), " §6SHOUDNEDEDSECONDS: §b" + (shoud/1000));
            e.getPlayer().teleport(pd.getFastladderstart());
            pd.setLastfastladderlongZ(-1);
            pd.setFastladderstart(null);
        }
        if(getViolations().containsKey(e.getPlayer()) && getViolations().get(e.getPlayer()) >= cancel_vl && pd.getFastladderstart() != null)e.getPlayer().teleport(pd.getLastspeedloc());
        pd.setLastfastladderlongZ(-1);
        TimberNoCheat.instance.getMoveprofiler().end();
    }
}
