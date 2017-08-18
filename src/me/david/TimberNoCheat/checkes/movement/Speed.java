package me.david.TimberNoCheat.checkes.movement;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import me.david.TimberNoCheat.checktools.Velocity;
import me.david.api.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class Speed extends Check {

    public Speed(){
        super("Speed", Category.MOVEMENT);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMove(PlayerMoveEvent e){
        final Player p = e.getPlayer();
        final Location to = e.getTo();
        final Location from = e.getFrom();
        if (!TimberNoCheat.checkmanager.isvalid_create(p) || e.isCancelled()) {
            return;
        }
        if(!to.getWorld().getName().equals(from.getWorld().getName())){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        pd.setLastmove(System.currentTimeMillis());
        if(p.isSprinting() && p.isSneaking()){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6MODE: §bSNEAK&SPRINT");
        }
        if(to.getX() != from.getX() || to.getY() != from.getY() || to.getZ() != from.getZ()){
            pd.setLastrealmove(System.currentTimeMillis());
            check_timer(e, pd);
            check_normal(e, pd);
        }
    }
    public void check_normal(PlayerMoveEvent e, PlayerData pd){
        final Player p = e.getPlayer();
        if(p.getAllowFlight() || p.getVehicle() != null || Velocity.velocity.containsKey(p.getUniqueId())){
            return;
        }
        int count = pd.getSpeedticks().getKey();
        long time = pd.getSpeedticks().getValue();
        int TooFastCount = 0;
        if(!pd.isFirstspeedflag()) {
            double OffsetXZ = LocationUtil.offset(LocationUtil.getHorizontalVector(e.getFrom().toVector()), LocationUtil.getHorizontalVector(e.getTo().toVector()));
            double LimitXZ = PlayerUtil.isOnGround(p)?TimberNoCheat.instance.settings.movement_speed_normal_normalground:TimberNoCheat.instance.settings.movement_speed_normal_normal;
            if(PlayerUtil.stairsNear(p.getLocation())) {
                LimitXZ += TimberNoCheat.instance.settings.movement_speed_normal_modistairs;
            }

            if(PlayerUtil.slabsNear(p.getLocation())) {
                LimitXZ += TimberNoCheat.instance.settings.movement_speed_normal_modislabs;
            }

            Block b = PlayerUtil.getEyeLocation(p).clone().add(0, 1, 0).getBlock();
            if(b.getType() != Material.AIR && !BlockUtil.canStandWithin(b)) {
                LimitXZ += TimberNoCheat.instance.settings.movement_speed_normal_modisolid;
            }

            if(p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.ICE) {
                if(b.getType() != Material.AIR && !BlockUtil.canStandWithin(b)) {
                    LimitXZ = TimberNoCheat.instance.settings.movement_speed_normal_modiicesolic;
                } else {
                    LimitXZ = TimberNoCheat.instance.settings.movement_speed_normal_modiice;
                }
            }

            float speed = p.getWalkSpeed();
            LimitXZ += (speed > 0.2F?speed * 10.0F * 0.33F:0.0F);
            for(PotionEffect effect : p.getActivePotionEffects()){
                if(effect.getType().equals(PotionEffectType.SPEED)) {
                    if(PlayerUtil.isOnGround(p)) {
                        LimitXZ += TimberNoCheat.instance.settings.movement_speed_normal_modispeedground * (effect.getAmplifier() + 1);
                    } else {
                        LimitXZ += TimberNoCheat.instance.settings.movement_speed_normal_modispeed * (effect.getAmplifier() + 1);
                    }
                }
            }

            if(OffsetXZ > LimitXZ && !DateTimeUtil.elapsed(pd.getSpeedticksflagt().getValue(), TimberNoCheat.instance.settings.movement_speed_normal_elapsedtoretflag)) {
                TooFastCount = pd.getSpeedticksflagt().getKey() + 1;
            } else {
                TooFastCount = 0;
            }
        }

        if(TooFastCount > TimberNoCheat.instance.settings.movement_speed_normal_tofastnewcount) {
            TooFastCount = 0;
            count++;
        }

        if(!pd.isFirstspeed() && DateTimeUtil.elapsed(time, TimberNoCheat.instance.settings.movement_speed_normal_elapsedtoret)) {
            count = 0;
            time = System.currentTimeMillis();
        }

        if(count >= TimberNoCheat.instance.settings.movement_speed_normal_flagcountertoflag) {
            count = 0;
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6MODE: §bNORMAL");
        }

        pd.setFirstspeedflag(false);
        pd.setFirstspeed(false);
        pd.setSpeedticks(new AbstractMap.SimpleEntry<Integer, Long>(count, time));
        pd.setSpeedticksflagt(new AbstractMap.SimpleEntry<Integer, Long>(TooFastCount, System.currentTimeMillis()));
    }
    public void check_timer(PlayerMoveEvent e, PlayerData pd){
        long delay = System.currentTimeMillis() - pd.getLasttimer();
        pd.getTimerms().add(delay);
        if(pd.getTimerms().size() == TimberNoCheat.instance.settings.movement_speed_timercheck) {
            Long average = MathUtil.averageLong(pd.getTimerms());
            if(average < TimberNoCheat.instance.settings.movement_speed_timeraverage_inmilis) {
                pd.setTimerflag(pd.getTimerflag()+1);
            }else {
                pd.setTimerflag(0);
            }
            pd.getTimerms().clear();
        }
        if(pd.getTimerflag() > TimberNoCheat.instance.settings.movement_speed_timerflagtomessage){
            pd.setTimerflag(0);
            TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6MODE: TIMER");
        }
        pd.setLasttimer(System.currentTimeMillis());
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onSprint(PlayerToggleSprintEvent e){
        if (!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer()) || e.isCancelled()) {
            return;
        }
        if(e.isSprinting() && e.getPlayer().isSneaking()){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6MODE: §bSPRINT");
        }
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onSneak(PlayerToggleSneakEvent e){
        if (!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer()) || e.isCancelled()) {
            return;
        }
        if((e.getPlayer().isSneaking() && e.isSneaking()) || (!e.getPlayer().isSneaking() && !e.isSneaking())){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(e.getPlayer());
        pd.setTogglesneaklastsec(pd.getTogglesneaklastsec()+1);
        Bukkit.getScheduler().runTaskLater(TimberNoCheat.instance, new Runnable() {
            @Override
            public void run() {
                pd.setTogglesneaklastsec(pd.getTogglesneaklastsec()-1);
            }
        }, 20);
        if(pd.getTogglesneaklastsec() > TimberNoCheat.instance.settings.movement_speed_togglesnekinsec){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6MODE: §bSNEAKSPAM", " §6TOGGLESLASTSEC: §b" + pd.getTogglesneaklastsec());
        }
        if(e.isSneaking() && e.getPlayer().isSprinting()){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6MODE: §bSNEAK");
        }
    }
}
