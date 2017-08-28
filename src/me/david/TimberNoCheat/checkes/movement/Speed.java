package me.david.TimberNoCheat.checkes.movement;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class Speed extends Check {

    public Speed(){
        super("Speed", Category.MOVEMENT);
    }

    @EventHandler
    public void Update(UpdateEvent event) {
        if (event.getType().equals(UpdateType.TICK)) {
            for(Player p : TimberNoCheat.checkmanager.tocheck){
                PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
                if(pd == null || p.isInsideVehicle() || pd.getLastspeedloc() == null || p.getAllowFlight() || !pd.getLastspeedloc().getWorld().getName().equals(p.getLocation().getWorld().getName()))
                    pd.setLastspeedloc(p.getLocation());
                    continue;
                if(p.getLocation().getZ() > pd.getLastspeedloc().getZ()+getmodi(p, 0.27) || !PLayerUtil.isonclimpabel){
                    p.teleport(pd.getLastspeedloc(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                    TimberNoCheat.checkmanager.notify(this, p, " §6MODE: §bSIMPLEHIGHT", " §6HIGHT: §b" + p.getLocation().getZ(), " §6MAXALLOWED: §b" + pd.getLastspeedloc().getZ()+getmodi(p, 0.27));
                }
                final double diff = pd.getLastspeedloc().clone().subtract(0, 0, pd.getLastspeedloc().getZ()).distance(p.getLocation().clone().subtract(0, 0, p.getLocation().getZ()));
                if(diff > getmodi(p, 0.6)){
                    p.teleport(pd.getLastspeedloc(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                    TimberNoCheat.checkmanager.notify(this, p, " §6MODE: §bSIMPLEXY", " §6XYDIFF: §b" + diff, " §6MAXXYDIFF: §b" + getmodi(p, 0.6));
                }
                pd.setLastspeedloc(p.getLocation());
            }
        }
    }
    private double getmodi(Player p, double modi){
        for(PotionEffect ef : p.getActivePotionEffects()){
            if(ef.getType() == PotionEffectType.SPEED){
                modi += (ef.getAmplifier()+1)*0.2;
            }else if(ef.getType() == PotionEffectType.SLOW){
                modi += (ef.getAmplifier()+1)*0.15;
            }
        }
        if(PlayerUtil.stairsNear(p.getLocation())) {
            modi += 0.45;
        }

        if(PlayerUtil.slabsNear(p.getLocation())) {
            modi += 0.05;
        }
        if(p.isSneaking()){
            modi -= 3.02;
        }
        if(p.isSprinting()){
            modi += 1.28;
        }
        if(p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SOUL_SAND){
            modi /= 2;
        }else if(p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.ICE){
            modi *= 2.8;
        }
        if(p.getLocation().getBlock().getType() == Material.WEB){
            modi *= -85/100;
        }else if(p.getLocation().getBlock().getType() == Material.STATIONARY_WATER || p.getLocation().getBlock().getType() == Material.STATIONARY_LAVA){
            if(p.getEquipment().getBoots() != null && p.getEquipment().getBoots().containsEnchantment(Enchantment.WATER_WORKER)){
                switch (p.getEquipment().getBoots().getEnchantmentLevel(Enchantment.WATER_WORKER)){
                    //33
                    case 1:
                        modi -= 2.0703;
                        break;
                    //66
                    case 2:
                        modi -= 1.0506;
                        break;
                    //3 is 100%
                }
            }else{
                modi -= 3.09;
            }
        }
        return modi;
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
        if(p.isSprinting() && p.getFoodLevel() <= 6){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6MODE: §bFOODLEVELSPRINT", " §6NEED: §b6", " §6HAS: §b" + p.getFoodLevel());
        }
        if(p.isSprinting() && p.isBlocking()){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6MODE: §bBLOCKSPRINT");
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
        if(e.isSprinting() && e.getPlayer().getFoodLevel() <= 6){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6MODE: §bFOODLEVELSPRINTSTART", " §6NEED: §b6", " §6HAS: §b" + p.getFoodLevel());
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
