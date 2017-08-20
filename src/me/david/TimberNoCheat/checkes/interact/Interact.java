package me.david.TimberNoCheat.checkes.interact;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.api.utils.BlockUtil;
import me.david.api.utils.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Set;

public class Interact extends Check {

    public Interact(){
        super("Interact", Category.INTERACT);
    }

    @EventHandler
    public void onhitinteract(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof Player) || !(e.getEntity() instanceof Player)){
            return;
        }
        final Player p = (Player)e.getDamager();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        if(!p.canSee((Player) e.getEntity())){
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bVISIBLE");
        }
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        final Player p = e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        if(e.getClickedBlock() != null && e.getClickedBlock().isLiquid() && TimberNoCheat.instance.settings.interact_water){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bLIQUIDS");
        }
        if(p.isDead() && TimberNoCheat.instance.settings.interact_keepalive){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bDEAD");
        }
        if(e.getAction() == Action.PHYSICAL){
            return;
        }
        if(p.isSleeping() && TimberNoCheat.instance.settings.interact_sleep){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bSLEEP");
        }
        if(p.isBlocking() && TimberNoCheat.instance.settings.interact_block){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bBLOCK");
        }
        if(PlayerUtil.hasInventoryOpen(p) && TimberNoCheat.instance.settings.interact_openinv){
            e.setCancelled(true);
            System.out.println(p.getOpenInventory().getType());
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bOPENINV");
        }
        if(e.getClickedBlock() == null || p.getTargetBlock((Set<Material>) null, 6) == null || !TimberNoCheat.instance.settings.interact_ghosthand){
            return;
        }

        if(p.getTargetBlock((Set<Material>) null, 6) != e.getClickedBlock() && !BlockUtil.getSurrounding(p.getTargetBlock((Set<Material>) null, 6), false).contains(e.getClickedBlock())){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bGHOST");
        }
        /*if(e.getClickedBlock() != null && !.contains(e.getClickedBlock())){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bGHOST");
            return;
        }*/

    }
}
