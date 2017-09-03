package me.david.TimberNoCheat.checkes.player;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import me.david.TimberNoCheat.checktools.Velocity;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Inventory extends Check {

    private final boolean sneaking;
    private final long move_delay;
    private final boolean sprint;
    private final boolean block;
    private final boolean sleep;
    private final boolean portalclick;
    private final boolean portalopen;
    private final boolean openinvchat;
    private final boolean openinvhit;
    private final long hitdelay;
    private final long chatdelay;
    public Inventory(){
        super("Inventory", Category.PLAYER);
        sneaking = getBoolean("sneaking");
        move_delay = getLong("move_delay");
        sprint = getBoolean("sprint");
        block = getBoolean("block");
        sleep = getBoolean("sleep");
        portalclick = getBoolean("portalclick");
        portalopen = getBoolean("portalopen");
        openinvchat = getBoolean("openinvchat");
        openinvhit = getBoolean("openinvhit");
        hitdelay = getLong("hitdelay");
        chatdelay = getLong("chatdelay");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void oninventory(InventoryClickEvent e){
        final Player p = (Player) e.getWhoClicked();
        if(!TimberNoCheat.checkmanager.isvalid_create(p) || e.isCancelled()){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        long delay = System.currentTimeMillis() - pd.getLastmove();
        if(delay < move_delay && !Velocity.velocity.containsKey(p.getUniqueId()) && !p.isInsideVehicle() && p.getFallDistance() == 0F && p.getLocation().getWorld().getBlockAt(p.getLocation()).getType() != Material.WATER){
            e.setCancelled(true);
            updatevio(this, p, 1, " §6CHECK: §bMOVE", " §6DELAY: §b" + delay);
        }
        if(p.isSneaking() && sneaking){
            e.setCancelled(true);
            //TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bSNEAKING");
            updatevio(this, p, 1, " §6CHECK: §bSNEAKING");
        }
        if(p.isSprinting() && sprint){
            e.setCancelled(true);
            //TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bSPRINT");
            updatevio(this, p, 1, " §6CHECK: §bSPRINT");
        }
        if(p.isBlocking() && block){
            e.setCancelled(true);
           // TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bBLOCK");
            updatevio(this, p, 1, " §6CHECK: §bBLOCK");
        }
        if(p.isSleeping() && sleep){
            e.setCancelled(true);
          //  TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bSLEEP");
            updatevio(this, p, 1, " §6CHECK: §bSLEEP");
        }
        if(portalclick && e.getCursor() != null && p.getLocation().getBlock().getType() == Material.PORTAL || p.getLocation().add(0, 0, 1).getBlock().getType() == Material.PORTAL){
            e.setCancelled(true);
            updatevio(this, p, 1, " §6CHECK: §bPORTALCLICK", " §6CLOSE: §bTRUE");
            //TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bPORTALCLICK", " §6CLOSE: §bTRUE");
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
        if(portalopen && p.getLocation().getBlock().getType() == Material.PORTAL || p.getLocation().add(0, 0, 1).getBlock().getType() == Material.PORTAL){
            e.setCancelled(true);
            updatevio(this, p, 1, " §6CHECK: §bPORTALOPEN", " §6CLOSE: §bTRUE");
            //TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bPORTALOPEN", " §6CLOSE: §bTRUE");
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
        if(delay < hitdelay){
            e.setCancelled(true);
            updatevio(this, p, 1, " §6CHECK: §bHITDELAY", " §6DELAY: §b" + delay);
            //TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bHITDELAY", " §6DELAY: §b" + delay);
        }
        if(PlayerUtil.hasInventoryOpen(p) && openinvhit){
            e.setCancelled(true);
            updatevio(this, p, 1, " §6CHECK: §bHITINVOPEN");
            //TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bHITNORMAL");
        }
    }
    @EventHandler(priority = EventPriority.LOW)
    public void chat(AsyncPlayerChatEvent e){
        final Player p = e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p) || e.isCancelled()){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        long delay = System.currentTimeMillis()-pd.getLastinv();
        if(delay < chatdelay){
            e.setCancelled(true);
            updatevio(this, p, 1, " §6CHECK: §bCHATDELAY", " §6DELAY: §b" + delay);
            //TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bCHATDELAY", " §6DELAY: §b" + delay);
        }
        if(PlayerUtil.hasInventoryOpen(p) && openinvchat){
            e.setCancelled(true);
            updatevio(this, p, 1, " §6CHECK: §bCHATNORMAL");
            //TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bCHATNORMAL");
        }
    }
}
