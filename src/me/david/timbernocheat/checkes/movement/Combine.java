package me.david.timbernocheat.checkes.movement;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

public class Combine extends Check {

    private int maxSneakToggles;

    public Combine() {
        super("Combine", Category.MOVEMENT);
        maxSneakToggles = getInt("maxsneaktoggles");
    }

    @EventHandler
    public void onMove(final PlayerMoveEvent event){
        if (!CheckManager.getInstance().isvalid_create(event.getPlayer())) {
            return;
        }
        final Player player = event.getPlayer();
        if(player.isSprinting() && player.isSneaking()){
            event.setCancelled(true);
            updateVio(this, player, 1, " §6MODE: §bSNEAK&SPRINT");
        }
        if(player.isSprinting() && player.getFoodLevel() <= 7){
            event.setCancelled(true);
            updateVio(this, player, 1, "§6MODE: §bSPRINTFOOD");
        }
        if(player.isSprinting() && player.isBlocking()){
            event.setCancelled(true);
            updateVio(this, player, 1, " §6MODE: §bBLOCKSPRINT");
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onSprint(PlayerToggleSprintEvent event){
        if (!CheckManager.getInstance().isvalid_create(event.getPlayer())) return;
        if(event.isSprinting() && event.getPlayer().isSneaking()){
            event.setCancelled(true);
            updateVio(this, event.getPlayer(), 1, " §6MODE: §bSPRINTSNEAK(2)");
        }
        if(event.isSprinting() && event.getPlayer().getFoodLevel() <= 7){
            event.setCancelled(true);
            updateVio(this, event.getPlayer(), 1, " §6MODE: §bSPRINTFOOD(2)");
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onSneak(PlayerToggleSneakEvent event){
        if (!CheckManager.getInstance().isvalid_create(event.getPlayer())) {
            return;
        }
        if((event.getPlayer().isSneaking() && event.isSneaking()) || (!event.getPlayer().isSneaking() && !event.isSneaking())){
            return;
        }
        PlayerData pd = CheckManager.getInstance().getPlayerdata(event.getPlayer());
        pd.setToggleSneakLastSec(pd.getToggleSneakLastSec()+1);
        Bukkit.getScheduler().runTaskLater(TimberNoCheat.getInstance(), () -> pd.setToggleSneakLastSec(pd.getToggleSneakLastSec()-1), 20);
        if(pd.getToggleSneakLastSec() > maxSneakToggles){
            event.setCancelled(true);
            updateVio(this, event.getPlayer(), 1, " §6MODE: §bSNEAKSPAM", " §6TOGGLESLASTSEC: §b" + pd.getToggleSneakLastSec());
        }
        if(event.isSneaking() && event.getPlayer().isSprinting()){
            event.setCancelled(true);
            updateVio(this, event.getPlayer(), 1, " §6MODE: §bSNEAKSPRINT(2)");
        }
    }
}
