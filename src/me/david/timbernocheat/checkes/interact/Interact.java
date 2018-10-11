package me.david.timbernocheat.checkes.interact;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checktools.MaterialHelper;
import me.david.timbernocheat.debug.Debuggers;
import me.david.api.Api;
import me.david.api.nms.RayTraceResult;
import me.david.api.utils.player.PlayerUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Interact extends Check {

    private final boolean visible;
    private final boolean visibleblocks;
    private final boolean liquids;
    private final boolean dead;
    private final boolean itemcursor;
    private final boolean sleep;
    private final boolean block;
    private final boolean openinv;
    private final boolean ghost;
    private final boolean raycast;

    public Interact(){
        super("Interact", Category.INTERACT);
        visible = getBoolean("visible");
        visibleblocks = getBoolean("visibleblocks");
        liquids = getBoolean("liquids");
        dead = getBoolean("dead");
        itemcursor = getBoolean("itemcursor");
        sleep = getBoolean("sleep");
        block = getBoolean("block");
        openinv = getBoolean("openinv");
        ghost = getBoolean("gost");
        raycast = getBoolean("raycast");
    }

    @EventHandler
    public void onhitinteract(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof Player)) return;
        final Player p = (Player)e.getDamager();
        if(!CheckManager.getInstance().isvalid_create(p)){
            return;
        }
        if(e.getEntity() instanceof Player && !p.canSee((Player) e.getEntity()) && visible){
            updateVio(this, p, 1, " §6CHECK: §bVISIBLE");
            e.setCancelled(true);
        }
        if(!p.hasLineOfSight(e.getEntity()) && visibleblocks){
            updateVio(this, p, 1, " §6CHECK: §bVISIBLEBLOCKS");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        final Player player = event.getPlayer();
        if(!CheckManager.getInstance().isvalid_create(player)) return;
        if(event.getClickedBlock() != null && event.getClickedBlock().isLiquid() && liquids){
            event.setCancelled(true);
            updateVio(this, player, 1, " §6CHECK: §bLIQUIDS");
        }
        if(player.isDead() && dead){
            event.setCancelled(true);
            updateVio(this, player, 1, " §6CHECK: §bDEAD");
        }
        if(event.getAction() == Action.PHYSICAL){
            return;
        }
        if(player.getItemOnCursor().getType() != Material.AIR && itemcursor){
            //System.out.println(player.getItemOnCursor().getType().name());
            event.setCancelled(true);
            updateVio(this, player, 1, " §6CHECK: §bITEMCURSOR");
        }
        if(player.isSleeping() && sleep){
            event.setCancelled(true);
            updateVio(this, player, 1, " §6CHECK: §bSLEEP");
        }
        if(player.isBlocking() && block){
            event.setCancelled(true);
            updateVio(this, player, 1, " §6CHECK: §bBLOCK");
        }
        if(PlayerUtil.hasInventoryOpen(player) && openinv){
            event.setCancelled(true);
            //System.out.println(player.getOpenInventory().getType());
            updateVio(this, player, 1, " §6CHECK: §bOPENINV");
        }
        if(raycast){
            if(event.getClickedBlock() != null) {
                Location pLoc = player.getLocation();
                Location bLoc = event.getClickedBlock().getLocation();
                RayTraceResult rayTrace = Api.getNms().rayTrace(new Vector(pLoc.getX(), pLoc.getY() + player.getEyeHeight(), pLoc.getZ()), new Vector(bLoc.getX(), bLoc.getY(), bLoc.getZ()), false, true, false, player.getWorld());

                if (rayTrace.isValid()) {
                    if (rayTrace.getType() != RayTraceResult.Type.BLOCK) {
                        updateVio(this, player, 0.3, " §6CHECK: §bRAYTRACE");
                    }

                    //TODO: Validate event.getBlockFace() or the player facing(by yaw and pitch) with the facing from raytrace?!
                    TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.RAY_TRACE, " calc=" + rayTrace.getFaceing().name() + " packet=" + event.getBlockFace().name());
                }
            }
        }
        if(event.getClickedBlock() == null || !ghost)
            return;
        if ((event.getAction() == Action.RIGHT_CLICK_BLOCK) && ((MaterialHelper.checkmat(event.getClickedBlock().getLocation())) || (player.getInventory().getItemInHand().getType() == Material.MONSTER_EGG))) {
            if (MaterialHelper.checkb(player, event.getClickedBlock().getLocation()) != null) {
                updateVio(this, player, 1, " §6CHECK: §bGHOST");
                event.setCancelled(true);
            }
        }
        //if(player.getTargetBlock((Set<Material>) null, 6) != event.getClickedBlock() && !BlockUtil.getSurrounding(player.getTargetBlock((Set<Material>) null, 6), false).contains(event.getClickedBlock())){
        //    event.setCancelled(true);
        //    updateVio(this, player, 1, " §6CHECK: §bGHOST");
        //}
        /*if(event.getClickedBlock() != null && !.contains(event.getClickedBlock())){
            event.setCancelled(true);
            CheckManager.getInstance().notify(this, player, " §6CHECK: §bGHOST");
            return;
        }*/

    }

    //TODO:
    private BlockFace toBlockFace(float pitch, float yaw){
        return BlockFace.DOWN;
    }
}
