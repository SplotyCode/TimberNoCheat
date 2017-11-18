package me.david.TimberNoCheat.checkes.interact;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import me.david.TimberNoCheat.checktools.InteractTool;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;

public class Break extends Check {

    private final boolean nsenable;
    private final double nsvio;
    private final boolean neenable;
    private final double nevio;
    private final boolean tenable;
    private final double tvio;
    private final long tobsidian;
    private final long tnormal;
    private final boolean csenable;
    private final long csvio;

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
        csenable = getBoolean("creativesword.enable");
        csvio = getLong("creativesword.vio");
    }

    @EventHandler
    public void onbreakstart(BlockDamageEvent e){
        final Player p = e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        pd.setStartbreak(e.getBlock());
        pd.setStartbreaktime(System.currentTimeMillis());
    }
    @EventHandler
    public void onbreak(BlockBreakEvent e){
        final Player p = e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        InteractTool.Tool hand = p.getItemInHand() == null?null:InteractTool.getToolbyMaterial(p.getItemInHand().getType());
        if(csenable && p.getGameMode() == GameMode.CREATIVE && hand != null && hand.getToolType() == InteractTool.ToolType.SWORD){
            e.setCancelled(true);
            updatevio(this, p, csvio, " §6CHECK: §bCREATIVESWORD");
        }
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
