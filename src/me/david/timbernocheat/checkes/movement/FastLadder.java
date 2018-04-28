package me.david.timbernocheat.checkes.movement;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.debug.Debuggers;
import me.david.timbernocheat.util.CheckUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class FastLadder extends Check {

    private final long msperblock;
    private final int shortmulti;

    public FastLadder(){
        super("FastLadder", Category.MOVEMENT);
        msperblock = getLong("msperblock");
        shortmulti = getInt("shortmulti");
    }

    @EventHandler (ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event){
        final Player player = event.getPlayer();
        if (!TimberNoCheat.getCheckManager().isvalid_create(player) ||
                !event.getTo().getWorld().getName().equals(event.getFrom().getWorld().getName())) {
            return;
        }

        TimberNoCheat.getInstance().getMoveprofiler().start("FastLadder");
        PlayerData pd = TimberNoCheat.getCheckManager().getPlayerdata(event.getPlayer());
        double yDis = event.getTo().getZ()-event.getFrom().getZ();
        if(yDis <= 0) return;
        if (CheckUtils.doesColidateWithMaterial(Material.LADDER, event.getTo()) && CheckUtils.doesColidateWithMaterial(Material.LADDER, event.getFrom())){
            if(pd.getLastFastLadderLongY() == -1){
                pd.setLastFastLadderLongY(yDis);
                pd.setFastLadderLongStart(System.currentTimeMillis());
                pd.setFastLadderStart(event.getFrom());
                return;
            }
            pd.setLastFastLadderLongY(pd.getLastFastLadderLongY()+yDis);
            TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.FASTLADDER, "[ADD] " + yDis);
            if(pd.getLastFastLadderLongY() > 1.8 && yDis > 0.118)
                updateVio(this, event.getPlayer(), (yDis-0.118)*shortmulti, " §6MODE: §bSHORT");
            return;
        }
        double shoud = pd.getLastFastLadderLongY() * msperblock;
        double does = System.currentTimeMillis() - pd.getFastLadderLongStart();
        if(shoud<0)return;
        if(pd.getLastFastLadderLongY() != -1 && shoud>does){
            TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.FASTLADDER, " shoud=" + shoud + " actual=" + does);
            if (updateVio(this, player, (int)(does-shoud), " §6MODE: §bLONGTIME", " §6BLOCKS: §b" + pd.getLastFastLadderLongY(), " §6NEDEDSECONDS: §b" + (does/1000), " §6SHOUDNEDEDSECONDS: §b" + (shoud/1000)))
                player.teleport(pd.getFastLadderStart());
            pd.setLastFastLadderLongY(-1);
            pd.setFastLadderStart(null);
        }
        TimberNoCheat.getInstance().getMoveprofiler().end();
    }
}
