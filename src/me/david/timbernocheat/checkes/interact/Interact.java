package me.david.timbernocheat.checkes.interact;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
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
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        if(e.getEntity() instanceof Player && !p.canSee((Player) e.getEntity()) && visible){
            updatevio(this, p, 1, " §6CHECK: §bVISIBLE");
            e.setCancelled(true);
        }
        if(!p.hasLineOfSight(e.getEntity()) && visibleblocks){
            updatevio(this, p, 1, " §6CHECK: §bVISIBLEBLOCKS");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        final Player p = e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)) return;
        if(e.getClickedBlock() != null && e.getClickedBlock().isLiquid() && liquids){
            e.setCancelled(true);
            updatevio(this, p, 1, " §6CHECK: §bLIQUIDS");
        }
        if(p.isDead() && dead){
            e.setCancelled(true);
            updatevio(this, p, 1, " §6CHECK: §bDEAD");
        }
        if(e.getAction() == Action.PHYSICAL){
            return;
        }
        if(p.getItemOnCursor().getType() != Material.AIR && itemcursor){
            //System.out.println(p.getItemOnCursor().getType().name());
            e.setCancelled(true);
            updatevio(this, p, 1, " §6CHECK: §bITEMCURSOR");
        }
        if(p.isSleeping() && sleep){
            e.setCancelled(true);
            updatevio(this, p, 1, " §6CHECK: §bSLEEP");
        }
        if(p.isBlocking() && block){
            e.setCancelled(true);
            updatevio(this, p, 1, " §6CHECK: §bBLOCK");
        }
        if(PlayerUtil.hasInventoryOpen(p) && openinv){
            e.setCancelled(true);
            //System.out.println(p.getOpenInventory().getType());
            updatevio(this, p, 1, " §6CHECK: §bOPENINV");
        }
        if(raycast){
            if(e.getClickedBlock() != null) {
                Location pLoc = p.getLocation();
                Location bLoc = e.getClickedBlock().getLocation();
                RayTraceResult rayTrace = Api.instance.nms.rayTrace(new Vector(pLoc.getX(), pLoc.getY() + p.getEyeHeight(), pLoc.getZ()), new Vector(bLoc.getX(), bLoc.getY(), bLoc.getZ()), false, true, false, p.getWorld());
                if(rayTrace.getType() != RayTraceResult.Type.BLOCK){
                    updatevio(this, p, 1, " §6CHECK: §bRAYTRACE");
                }

                //TODO: Validate e.getBlockFace() or the player facing(by yaw and pitch) with the facing from raytrace?!
                TimberNoCheat.instance.getDebuger().sendDebug(Debuggers.RAY_TRACE, " calc=" + rayTrace.getFaing().name() + " packet=" + e.getBlockFace().name());

            }
        }
        if(e.getClickedBlock() == null || !ghost)
            return;
        if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) && ((MaterialHelper.checkmat(e.getClickedBlock().getLocation())) || (p.getInventory().getItemInHand().getType() == Material.MONSTER_EGG))) {
            if (MaterialHelper.checkb(p, e.getClickedBlock().getLocation()) != null) {
                updatevio(this, p, 1, " §6CHECK: §bGHOST");
                e.setCancelled(true);
            }
        }
        //if(p.getTargetBlock((Set<Material>) null, 6) != e.getClickedBlock() && !BlockUtil.getSurrounding(p.getTargetBlock((Set<Material>) null, 6), false).contains(e.getClickedBlock())){
        //    e.setCancelled(true);
        //    updatevio(this, p, 1, " §6CHECK: §bGHOST");
        //}
        /*if(e.getClickedBlock() != null && !.contains(e.getClickedBlock())){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bGHOST");
            return;
        }*/

    }

    //TODO:
    private BlockFace toBlockFace(float pitch, float yaw){
        return BlockFace.DOWN;
    }
}
