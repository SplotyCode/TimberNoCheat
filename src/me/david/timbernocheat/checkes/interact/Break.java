package me.david.timbernocheat.checkes.interact;

import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.PlayerData;
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
    public void onBreakStart(BlockDamageEvent e){
        final Player p = e.getPlayer();
        if(!CheckManager.getInstance().isvalid_create(p)) return;
        PlayerData pd = CheckManager.getInstance().getPlayerdata(p);
        pd.setStartBreak(e.getBlock());
        pd.setStartBreakTime(System.currentTimeMillis());
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        final Player p = e.getPlayer();
        if(!CheckManager.getInstance().isvalid_create(p)) return;
        PlayerData pd = CheckManager.getInstance().getPlayerdata(p);
        if(nsenable && pd.getStartBreak() == null){
            e.setCancelled(true);
            updateVio(this, p, nsvio, " §6CHECK: §bNOTSTART");
            return;
        }
        if(neenable && !pd.getStartBreak().getLocation().equals(e.getBlock().getLocation())){
            e.setCancelled(true);
            updateVio(this, p, nevio, " §6CHECK: §bNOTEQULS");
            return;
        }
        if(rayenable && !BlockUtil.HOLLOW_MATERIALS.contains(p.getTargetBlock((Set)null, 5).getType()) && !e.getBlock().getLocation().equals(p.getTargetBlock((Set)null, 5))){
            if(raycancel) e.setCancelled(true);
            updateVio(this, p, rayvio, " §6CHECK: §bWRONG BLOCK");
        }
        if(tenable && (e.getBlock().getType() == Material.OBSIDIAN && System.currentTimeMillis()-pd.getStartBreakTime() > 260000) || (e.getBlock().getType() != Material.OBSIDIAN && System.currentTimeMillis()-pd.getStartBreakTime() > 22000)){
            e.setCancelled(true);
            updateVio(this, p, tvio, " §6CHECK: §bTOSLOW(TIME)");
            return;
        }
        if(tenable && InteractTool.getBreakingDuration(e.getBlock().getType(), p) > System.currentTimeMillis()-pd.getStartBreakTime()){
            e.setCancelled(true);
            updateVio(this, p, tvio, " §6CHECK: §bFAST");
            return;
        }
        pd.setStartBreakTime(0);
        pd.setStartBreak(null);
    }
}
