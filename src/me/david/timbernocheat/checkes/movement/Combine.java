package me.david.timbernocheat.checkes.movement;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
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
    public void onSprint(PlayerToggleSprintEvent e){
        if (!TimberNoCheat.getCheckManager().isvalid_create(e.getPlayer())) return;
        if(e.isSprinting() && e.getPlayer().isSneaking()){
            e.setCancelled(true);
            updateVio(this, e.getPlayer(), 1, " §6MODE: §bSPRINTSNEAK(2)");
        }
        if(e.isSprinting() && e.getPlayer().getFoodLevel() <= 7){
            e.setCancelled(true);
            updateVio(this, e.getPlayer(), 1, " §6MODE: §bSPRINTFOOD(2)");
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onSneak(PlayerToggleSneakEvent event){
        if (!TimberNoCheat.getCheckManager().isvalid_create(event.getPlayer())) {
            return;
        }
        if((event.getPlayer().isSneaking() && event.isSneaking()) || (!event.getPlayer().isSneaking() && !event.isSneaking())){
            return;
        }
        PlayerData pd = TimberNoCheat.getCheckManager().getPlayerdata(event.getPlayer());
        pd.setTogglesneaklastsec(pd.getTogglesneaklastsec()+1);
        Bukkit.getScheduler().runTaskLater(TimberNoCheat.getInstance(), () -> pd.setTogglesneaklastsec(pd.getTogglesneaklastsec()-1), 20);
        if(pd.getTogglesneaklastsec() > maxSneakToggles){
            event.setCancelled(true);
            updateVio(this, event.getPlayer(), 1, " §6MODE: §bSNEAKSPAM", " §6TOGGLESLASTSEC: §b" + pd.getTogglesneaklastsec());
        }
        if(event.isSneaking() && event.getPlayer().isSprinting()){
            event.setCancelled(true);
            updateVio(this, event.getPlayer(), 1, " §6MODE: §bSNEAKSPRINT(2)");
        }
    }
}
