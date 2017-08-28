package me.david.TimberNoCheat.checkes.player;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import me.david.TimberNoCheat.checktools.Velocity;
import me.david.api.utils.PlayerUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Inventory extends Check {

    public Inventory(){
        super("Inventory", Category.PLAYER);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void oninventory(InventoryClickEvent e){
        final Player p = (Player) e.getWhoClicked();
        if(!TimberNoCheat.checkmanager.isvalid_create(p) || e.isCancelled()){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        long delay = System.currentTimeMillis() - pd.getLastmove();
        if(delay < TimberNoCheat.instance.settings.player_inv_delay_inmilis && !Velocity.velocity.containsKey(p.getUniqueId()) && !p.isInsideVehicle() && p.getFallDistance() == 0F && p.getLocation().getWorld().getBlockAt(p.getLocation()).getType() != Material.WATER){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bMOVE", " §6DELAY: §b" + delay);
        }
        if(p.isSneaking() && TimberNoCheat.instance.settings.player_inv_sneak){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bSNEAKING");
        }
        if(p.isSprinting() && TimberNoCheat.instance.settings.player_inv_sprint){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bSPRINT");
        }
        if(p.isBlocking() && TimberNoCheat.instance.settings.player_inv_block){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bBLOCK");
        }
        if(p.isSleeping() && TimberNoCheat.instance.settings.player_inv_sleep){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bSLEEP");
        }
        if(e.getCursor() != null && p.getLocation().getBlock().getType() == Material.PORTAL || p.getLocation().add(0, 0, 1).getBlock().getType() == Material.PORTAL){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bPORTALCLICK", " §6CLOSE: §bTRUE");
            p.closeInventory();
        }
        pd.setLastinv(System.currentTimeMillis());
    }
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e){
        final Player p = (Player) e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p) || e.isCancelled()){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        if(p.getLocation().getBlock().getType() == Material.PORTAL || p.getLocation().add(0, 0, 1).getBlock().getType() == Material.PORTAL){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bPORTALOPEN", " §6CLOSE: §bTRUE");
            p.closeInventory();
        }
    }
    @EventHandler
    public void hit(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof Player)){
            return;
        }
        final Player p = (Player) e.getDamager();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        long delay = System.currentTimeMillis()-pd.getLastinv();
        if(delay < TimberNoCheat.instance.settings.player_inv_hitdelayinmili){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bHITDELAY", " §6DELAY: §b" + delay);
        }
        if(PlayerUtil.hasInventoryOpen(p) && TimberNoCheat.instance.settings.player_inv_openinvhit){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bHITNORMAL");
        }
    }
    @EventHandler(priority = EventPriority.LOW)
    public void chat(AsyncPlayerChatEvent e){
        final Player p = (Player) e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p) || e.isCancelled()){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        long delay = System.currentTimeMillis()-pd.getLastinv();
        if(delay < TimberNoCheat.instance.settings.player_inv_invchatdelay){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bCHATDELAY", " §6DELAY: §b" + delay);
        }
        if(PlayerUtil.hasInventoryOpen(p) && TimberNoCheat.instance.settings.player_inv_openinvchat){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bCHATNORMAL");
        }
    }
}
