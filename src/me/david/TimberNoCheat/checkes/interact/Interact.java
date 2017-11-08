package me.david.TimberNoCheat.checkes.interact;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.api.utils.BlockUtil;
import me.david.api.utils.player.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Set;

public class Interact extends Check {

    private final boolean visible;
    private final boolean visibleblocks;
    private final boolean liquids;
    private final boolean dead;
    private final boolean itemcursor;
    private final boolean sleep;
    private final boolean block;
    private final boolean openinv;
    private final boolean gost;

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
        gost = getBoolean("gost");
    }

    @EventHandler
    public void onhitinteract(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof Player)){
            return;
        }
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
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
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
            System.out.println(p.getItemOnCursor().getType().name());
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
        if(e.getClickedBlock() == null || p.getTargetBlock((Set<Material>) null, 6) == null || !gost){
            return;
        }

        if(p.getTargetBlock((Set<Material>) null, 6) != e.getClickedBlock() && !BlockUtil.getSurrounding(p.getTargetBlock((Set<Material>) null, 6), false).contains(e.getClickedBlock())){
            e.setCancelled(true);
            updatevio(this, p, 1, " §6CHECK: §bGHOST");
        }
        /*if(e.getClickedBlock() != null && !.contains(e.getClickedBlock())){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bGHOST");
            return;
        }*/

    }
}
