package me.david.timbernocheat.checkes.interact;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import me.david.timbernocheat.checkmanager.PlayerData;
import me.david.timbernocheat.checktools.InteractTool;
import me.david.api.utils.BlockUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;

import java.util.Set;

public class Break extends Check {

    private final boolean nsenable;
    private final double nsvio;
    private final boolean neenable;
    private final double nevio;
    private final boolean tenable;
    private final double tvio;
    private final long tobsidian;
    private final long tnormal;
    private final boolean rayenable;
    private final boolean raycancel;
    private final double rayvio;

    public Break(){
        super("Break", Category.INTERACT);
        nsenable = getBoolean("notstart.enable");
        nsvio = getDouble("nostart.vio");
        neenable = getBoolean("notequls.enable");
        nevio = getDouble("notequls.vio");
        tenable = getBoolean("time.enable");
        tvio = getDouble("time.vio");
        tobsidian = getLong("time.timeobsidian");
        tnormal = getLong("time.timenormal");
        rayenable = getBoolean("raypick.enable");
        raycancel = getBoolean("raypick.cancel");
        rayvio = getDouble("raypick.vio");
    }

    @EventHandler
    public void onbreakstart(BlockDamageEvent e){
        final Player p = e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)) return;
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        pd.setStartbreak(e.getBlock());
        pd.setStartbreaktime(System.currentTimeMillis());
    }
    @EventHandler
    public void onbreak(BlockBreakEvent e){
        final Player p = e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)) return;
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        if(nsenable && pd.getStartbreak() == null){
            e.setCancelled(true);
            updatevio(this, p, nsvio, " §6CHECK: §bNOTSTART");
            return;
        }
        if(neenable && !pd.getStartbreak().getLocation().equals(e.getBlock().getLocation())){
            e.setCancelled(true);
            updatevio(this, p, nevio, " §6CHECK: §bNOTEQULS");
            return;
        }
        if(rayenable && !BlockUtil.HOLLOW_MATERIALS.contains(p.getTargetBlock((Set)null, 5).getType()) && !e.getBlock().getLocation().equals(p.getTargetBlock((Set)null, 5))){
            if(raycancel) e.setCancelled(true);
            updatevio(this, p, rayvio, " §6CHECK: §bWRONG BLOCK");
        }
        if(tenable && (e.getBlock().getType() == Material.OBSIDIAN && System.currentTimeMillis()-pd.getStartbreaktime() > 260000) || (e.getBlock().getType() != Material.OBSIDIAN && System.currentTimeMillis()-pd.getStartbreaktime() > 22000)){
            e.setCancelled(true);
            updatevio(this, p, tvio, " §6CHECK: §bTOSLOW(TIME)");
            return;
        }
        if(tenable && InteractTool.getBreakingDuration(e.getBlock().getType(), p) > System.currentTimeMillis()-pd.getStartbreaktime()){
            e.setCancelled(true);
            updatevio(this, p, tvio, " §6CHECK: §bFAST");
            return;
        }
        pd.setStartbreaktime(0);
        pd.setStartbreak(null);
    }
}
