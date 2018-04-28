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
        rayvio = getDouble("raypick.vio");
    }

    @EventHandler
    public void onBreakStart(final BlockDamageEvent event){
        final Player player = event.getPlayer();
        if(!CheckManager.getInstance().isvalid_create(player)) return;
        PlayerData pd = CheckManager.getInstance().getPlayerdata(player);

        pd.setStartBreak(event.getBlock());
        pd.setStartBreakTime(System.currentTimeMillis());
    }

    @EventHandler
    public void onBreak(final BlockBreakEvent event){
        final Player player = event.getPlayer();
        if(!CheckManager.getInstance().isvalid_create(player)) return;
        PlayerData pd = CheckManager.getInstance().getPlayerdata(player);
        final Material mat = event.getBlock().getType();

        if(nsenable && pd.getStartBreak() == null){
            event.setCancelled(true);
            if (updateVio(this, player, nsvio, " §6CHECK: §bNOTSTART"))
                event.setCancelled(true);
        }

        if(neenable && !pd.getStartBreak().getLocation().equals(event.getBlock().getLocation())){
            event.setCancelled(true);
            if (updateVio(this, player, nevio, " §6CHECK: §bNOTEQULS"))
                event.setCancelled(true);
        }

        if(rayenable && !BlockUtil.HOLLOW_MATERIALS.contains(player.getTargetBlock((Set)null, 5).getType()) && !event.getBlock().getLocation().equals(player.getTargetBlock((Set)null, 5))){
            if (updateVio(this, player, rayvio, " §6CHECK: §bWRONG BLOCK"))
                event.setCancelled(true);
        }

        long delay = System.currentTimeMillis()-pd.getStartBreakTime();
        if (tenable &&
                (mat == Material.OBSIDIAN && delay > tobsidian) ||
                (mat != Material.OBSIDIAN && delay > tnormal)) {
            if (updateVio(this, player, tvio, " §6CHECK: §bTOSLOW(TIME)"))
                event.setCancelled(true);
        }

        pd.setStartBreakTime(0);
        pd.setStartBreak(null);
    }
}
