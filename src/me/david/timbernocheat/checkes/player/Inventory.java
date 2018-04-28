package me.david.timbernocheat.checkes.player;

import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.checktools.FalsePositive;
import me.david.api.utils.player.PlayerUtil;
import org.bukkit.Achievement;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
    private final boolean achivement;
    private final boolean achivementportal;
    private final boolean itemcursor;

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
        achivement = getBoolean("achivement");
        achivementportal = getBoolean("achivement_portal");
        itemcursor = getBoolean("itemcursor");
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        final Player p = event.getPlayer();
        if(!CheckManager.getInstance().isvalid_create(p))return;
        PlayerData pd = CheckManager.getInstance().getPlayerdata(p);
        if(itemcursor && p.getItemOnCursor().getType() != Material.AIR) updateVio(this, p, 1, " §6CHECK: §bCURSOR");
        long delay = System.currentTimeMillis() - pd.getLastAchivementOpenInv();
        FalsePositive.FalsePositiveChecks fp = pd.getFalsePositives();
        if(delay < 550 && !fp.jumpboost(p) && !fp.enderpearl && !fp.hasVehicle(60) && !fp.hasSlime(80) && !fp.hasPiston(60) && !fp.hasLiquid(60) && !fp.hasHitorbow(80) && !fp.hasRod(60) && !fp.hasExplosion(80) && !fp.hasExplosion(120) && !fp.hasOtherKB(60) && !fp.hasTeleport(60) && !fp.hasWorld(35) && p.getFallDistance() == 0F) updateVio(this, p, (delay-550)/2, " §6CHECK: §bACHIVEMENT");
    }

    @EventHandler
    public void onAchivement(PlayerAchievementAwardedEvent event){
        final Player p = event.getPlayer();
        if((achivement || achivementportal) && event.getAchievement() == Achievement.OPEN_INVENTORY && CheckManager.getInstance().isvalid_create(p)){
            event.setCancelled(true);
            if(achivementportal && p.getLocation().getBlock().getType() == Material.PORTAL || p.getLocation().add(0, p.getEyeHeight(), 0).getBlock().getType() == Material.PORTAL)
                updateVio(this, p, 1, " §6CHECK: §bPORTAL_ACHIVEMENT");
            if(achivement) CheckManager.getInstance().getPlayerdata(p).setLastAchivementOpenInv(System.currentTimeMillis());
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        if((achivement || achivementportal) && CheckManager.getInstance().isvalid_create(event.getPlayer()) && event.getPlayer().hasAchievement(Achievement.OPEN_INVENTORY)) event.getPlayer().removeAchievement(Achievement.OPEN_INVENTORY);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventory(InventoryClickEvent e){
        final Player p = (Player) e.getWhoClicked();
        if(!CheckManager.getInstance().isvalid_create(p) || e.isCancelled()){
            return;
        }
        PlayerData pd = CheckManager.getInstance().getPlayerdata(p);
        long delay = System.currentTimeMillis() - pd.getGenerals().getLastRealMove();
        FalsePositive.FalsePositiveChecks fp = pd.getFalsePositives();
        if(delay < move_delay && !fp.jumpboost(p) && !fp.enderpearl && !fp.hasVehicle(60) && !fp.hasSlime(80) && !fp.hasPiston(60) && !fp.hasLiquid(60) && !fp.hasHitorbow(80) && !fp.hasRod(60) && !fp.hasExplosion(80) && !fp.hasExplosion(120) && !fp.hasOtherKB(60) && !fp.hasTeleport(60) && !fp.hasWorld(35) && p.getFallDistance() == 0F){
            e.setCancelled(true);
            updateVio(this, p, 1, " §6CHECK: §bMOVE", " §6DELAY: §b" + delay);
        }
        if(p.isSneaking() && sneaking){
            e.setCancelled(true);
            updateVio(this, p, 1, " §6CHECK: §bSNEAKING");
        }
        if(p.isSprinting() && sprint){
            e.setCancelled(true);
            updateVio(this, p, 1, " §6CHECK: §bSPRINT");
        }
        if(p.isBlocking() && block){
            e.setCancelled(true);
            updateVio(this, p, 1, " §6CHECK: §bBLOCK");
        }
        if(p.isSleeping() && sleep){
            e.setCancelled(true);
            updateVio(this, p, 1, " §6CHECK: §bSLEEP");
        }
        if(portalclick && e.getCursor() != null && p.getLocation().getBlock().getType() == Material.PORTAL || p.getLocation().add(0, p.getEyeHeight(), 0).getBlock().getType() == Material.PORTAL){
            e.setCancelled(true);
            updateVio(this, p, 1, " §6CHECK: §bPORTALCLICK", " §6CLOSE: §bTRUE");
            p.closeInventory();
        }
    }
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e){
        final Player p = (Player) e.getPlayer();
        if(!CheckManager.getInstance().isvalid_create(p) || e.isCancelled()){
            return;
        }
        PlayerData pd = CheckManager.getInstance().getPlayerdata(p);
        if(portalopen && p.getLocation().getBlock().getType() == Material.PORTAL || p.getLocation().add(0, 0, 1).getBlock().getType() == Material.PORTAL){
            e.setCancelled(true);
            updateVio(this, p, 1, " §6CHECK: §bPORTALOPEN", " §6CLOSE: §bTRUE");
            //CheckManager.getInstance().notify(this, p, " §6CHECK: §bPORTALOPEN", " §6CLOSE: §bTRUE");
            p.closeInventory();
        }
    }
    @EventHandler
    public void hit(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof Player)){
            return;
        }
        final Player p = (Player) e.getDamager();
        if(!CheckManager.getInstance().isvalid_create(p)){
            return;
        }
        PlayerData pd = CheckManager.getInstance().getPlayerdata(p);
        long delay = System.currentTimeMillis()-pd.getGenerals().getLastItemClick();
        if(delay < hitdelay){
            e.setCancelled(true);
            updateVio(this, p, 1, " §6CHECK: §bHITDELAY", " §6DELAY: §b" + delay);
        }
        if(PlayerUtil.hasInventoryOpen(p) && openinvhit){
            e.setCancelled(true);
            updateVio(this, p, 1, " §6CHECK: §bHITINVOPEN");
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void chat(AsyncPlayerChatEvent e){
        final Player p = e.getPlayer();
        if(!CheckManager.getInstance().isvalid_create(p) || e.isCancelled()){
            return;
        }
        PlayerData pd = CheckManager.getInstance().getPlayerdata(p);
        long delay = System.currentTimeMillis()-pd.getGenerals().getLastItemClick();
        if(delay < chatdelay){
            e.setCancelled(true);
            updateVio(this, p, 1, " §6CHECK: §bCHATDELAY", " §6DELAY: §b" + delay);
        }
        if(PlayerUtil.hasInventoryOpen(p) && openinvchat){
            e.setCancelled(true);
            updateVio(this, p, 1, " §6CHECK: §bCHATNORMAL");
        }
    }
}
