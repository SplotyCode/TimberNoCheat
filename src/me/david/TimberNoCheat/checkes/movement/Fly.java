package me.david.TimberNoCheat.checkes.movement;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import me.david.TimberNoCheat.checktools.Velocity;
import me.david.api.utils.BlockUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

public class Fly extends Check {

    public Fly(){
        super("Fly", Category.MOVEMENT);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void Kick(PlayerKickEvent e) {
        if (!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer()) || e.isCancelled()) {
            return;
        }
        if(e.getReason().equals("Flying is not enabled on this server")) {
            TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6CHECK: §bVANILLA_KICK");
        }
    }

    @EventHandler
    public void playermove(PlayerMoveEvent e){
        final Player p = e.getPlayer();
        final Location to = e.getTo();
        final Location from = e.getFrom();
        if (!TimberNoCheat.checkmanager.isvalid_create(p) || e.isCancelled()) {
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        if(inair(p) && !Velocity.velocity.containsKey(p.getUniqueId()) && !p.getAllowFlight() && to.getY() >= from.getY() && p.getActivePotionEffects().stream().noneMatch(potionEffect -> potionEffect.getType() == PotionEffectType.JUMP)){
            pd.setFly_nodown(pd.getFly_nodown()+1);
        }else{
            pd.setFly_nodown(0);
        }
        if(pd.getFly_nodown() >= TimberNoCheat.instance.settings.movement_fly_nodowntonotify){
            TimberNoCheat.checkmanager.notify(this, p);
        }
    }

    public boolean inair(Player p){
        for(Block b : BlockUtil.getBlocksAround(p.getLocation(), 2)){
            if(b.getType() != Material.AIR){
                return false;
            }
        }
        return true;
    }
}
