package me.david.TimberNoCheat.checkes.movement;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import me.david.TimberNoCheat.checktools.FalsePositive;
import me.david.TimberNoCheat.checktools.SpeedUtil;
import me.david.TimberNoCheat.checktools.Velocity;
import me.david.api.utils.BlockUtil;
import me.david.api.utils.cordinates.LocationUtil;
import me.david.api.utils.MathUtil;
import me.david.api.utils.player.PlayerUtil;
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
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Speed extends Check {

    private final double nbase;
    private final double nbaseground;
    private final double nbaseice;
    private final double nbaseicesolid;
    private final double nstairs;
    private final double nslabs;
    private final double nsolid;
    private final double nspeed;
    private final double nspeedground;
    private final double nviomodi;
    private final boolean nenable;
    private final boolean ssenable;
    private final double ssviomodi;
    private final boolean sfenable;
    private final int sffood;
    private final double sfvio;
    private final boolean bsenable;
    private final double bsvio;
    private final boolean tenable;
    private final double tvio;
    private final int tchecksize;
    private final double tmax_average;
    private final boolean spenable;
    private final int spmaxsecond;
    private final double spvio;
    private final boolean jenable;
    private final boolean grounddiffen;
    private final double[] grounddiff;
    private final double groundvio;
    private final boolean fgenable;
    private final double fgvio;
    private final boolean uyenable;
    private final double uyvio;
    private final boolean blenable;
    private final double blvio;

    public Speed(){
        super("Speed", Category.MOVEMENT);
        nbase = getDouble("normal.base");
        nbaseground = getDouble("normal.baseground");
        nbaseice = getDouble("normal.baseice");
        nbaseicesolid = getDouble("normal.baseicesolid");
        nstairs = getDouble("normal.stairs");
        nslabs = getDouble("normal.slabs");
        nsolid = getDouble("normal.solid");
        nspeed = getDouble("normal.speed");
        nspeedground = getDouble("normal.speedground");
        nviomodi = getDouble("normal.viomodi");
        nenable = getBoolean("normal.enable");
        ssenable = getBoolean("sneaksprint.enable");
        ssviomodi = getDouble("sneaksprint.viomodi");
        sfenable = getBoolean("sprint.enable");
        sffood = getInt("sprintfood.foodlevel");
        sfvio = getDouble("sprintfood.viomodi");
        bsvio = getDouble("blocksprint.viomodi");
        bsenable = getBoolean("blocksprint.enable");
        tenable = getBoolean("timer.enable");
        tvio = getDouble("timer.viomodi");
        tchecksize = getInt("timer.checksize");
        tmax_average = getDouble("timer.maxaverage");
        spenable = getBoolean("sprintspam.enable");
        spmaxsecond = getInt("sprintspam.maxpersecond");
        spvio = getDouble("sprintspam.viomodi");
        jenable = getBoolean("jump");
        grounddiffen = getBoolean("grounddiff.enable");
        grounddiff = getDoubleArray("grounddiff.whitelist");
        groundvio = getDouble("grounddiff.vio");
        fgenable = getBoolean("fakeground.enable");
        fgvio = getDouble("fakeground.vio");
        uyenable = getBoolean("unusualy.enable");
        uyvio = getDouble("unusualy.vio");
        blenable = getBoolean("blocks.enable");
        blvio = getDouble("blocks.vio");
    }

    @Override
    public void starttasks() {
        /*register(Bukkit.getScheduler().runTaskTimer(TimberNoCheat.instance, new Runnable() {
            @Override
            public void run() {
                for(Player p : TimberNoCheat.checkmanager.tocheck){
                    PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
                    if(PlayerUtil.isOnGround(p))
                        pd.setTicksonground(pd.getTicksonground()+1);
                    else
                        pd.setTicksonground(0);
                    if(p.isInsideVehicle() || pd.getLastspeedloc() == null || p.getAllowFlight() || !pd.getLastspeedloc().getWorld().getName().equals(p.getLocation().getWorld().getName())) {
                        pd.setLastspeedloc(p.getLocation());
                        continue;
                    }
                    if(p.getLocation().getZ() > pd.getLastspeedloc().getZ()+Speed.this.getmodi(p, 0.27) || !PlayerUtil.isOnClimbable(p)){
                        p.teleport(pd.getLastspeedloc(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                        TimberNoCheat.checkmanager.notify(Speed.this, p, " §6MODE: §bSIMPLEHIGHT", " §6HIGHT: §b" + p.getLocation().getZ(), " §6MAXALLOWED: §b" + pd.getLastspeedloc().getZ()+getmodi(p, 0.27));
                    }
                    final double diff = pd.getLastspeedloc().clone().subtract(0, 0, pd.getLastspeedloc().getZ()).distance(p.getLocation().clone().subtract(0, 0, p.getLocation().getZ()));
                    if(diff > getmodi(p, 0.6)){
                        p.teleport(pd.getLastspeedloc(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                        TimberNoCheat.checkmanager.notify(Speed.this, p, " §6MODE: §bSIMPLEXY", " §6XYDIFF: §b" + diff, " §6MAXXYDIFF: §b" + getmodi(p, 0.6));
                    }
                    pd.setLastspeedloc(p.getLocation());
                }
            }
        }, 0, 1););*/
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
        if (!TimberNoCheat.checkmanager.isvalid_create(p) || e.isCancelled() || !to.getWorld().getName().equals(from.getWorld().getName())) return;
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        pd.setLastmove(System.currentTimeMillis());
        if(ssenable && p.isSprinting() && p.isSneaking()){
            e.setCancelled(true);
            updatevio(this, p, ssviomodi, " §6MODE: §bSNEAK&SPRINT");
        }
        if(sfenable && p.isSprinting() && p.getFoodLevel() <= sffood){
            e.setCancelled(true);
            updatevio(this, p, sfvio, "§6MODE: §bSPRINTFOOD");
            //TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6MODE: §bFOODLEVELSPRINT", " §6NEED: §b6", " §6HAS: §b" + p.getFoodLevel());
        }
        if(bsenable && p.isSprinting() && p.isBlocking()){
            e.setCancelled(true);
            updatevio(this, p, bsvio, " §6MODE: §bBLOCKSPRINT");
        }
        if(to.getX() != from.getX() || to.getY() != from.getY() || to.getZ() != from.getZ()){
            pd.setLastrealmove(System.currentTimeMillis());
            if(tenable)check_timer(e, pd);
            if(nenable)check_normal(e);
            if(jenable)check_jumping(e);
            if(grounddiffen) check_grounddiff(e, pd);
            yspeed(e);
        }
        if(fgenable && p.isOnGround() != PlayerUtil.isOnGround(p)) updatevio(this, p, fgvio, " §6MODE: §bFAKEGROUND");
    }

    private void check_normal(PlayerMoveEvent e){
        final Player p = e.getPlayer();
        if(p.getAllowFlight() || p.getVehicle() != null || Velocity.velocity.containsKey(p.getUniqueId())){
            return;
        }
        /*int count = pd.getSpeedticks().getKey();
        long time = pd.getSpeedticks().getValue();
        int TooFastCount = 0;
        if(!pd.isFirstspeedflag()) {*/
        double offsetXZ = LocationUtil.offset(LocationUtil.getHorizontalVector(e.getFrom().toVector()), LocationUtil.getHorizontalVector(e.getTo().toVector()));
        double limitXZ = PlayerUtil.isOnGround(p)?nbaseground:nbase;
        if(PlayerUtil.stairsNear(p.getLocation()))limitXZ += nstairs;
        if(PlayerUtil.slabsNear(p.getLocation()))limitXZ += nslabs;
        final boolean top = PlayerUtil.getEyeLocation(p).clone().add(0, 1, 0).getBlock().getType() != Material.AIR && !BlockUtil.canStandWithin(PlayerUtil.getEyeLocation(p).clone().add(0, 1, 0).getBlock());
        if(top)limitXZ += nsolid;
        if(p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.ICE)
            if(top)limitXZ = nbaseicesolid;
            else limitXZ = nbaseice;
        limitXZ += (p.getWalkSpeed() > 0.2F?p.getWalkSpeed() * 10.0F * 0.33F:0.0F);
        for(PotionEffect effect : p.getActivePotionEffects())
            if(effect.getType().equals(PotionEffectType.SPEED))
                if(PlayerUtil.isOnGround(p)) limitXZ += nspeedground * (effect.getAmplifier() + 1);
                else limitXZ += nspeed * (effect.getAmplifier() + 1);
        if(offsetXZ > limitXZ)
            updatevio(this, p, offsetXZ-limitXZ*nviomodi, " §6MODE: §bNORMAL");
    //}

        /*if(TooFastCount > TimberNoCheat.instance.settings.movement_speed_normal_tofastnewcount) {
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
        pd.setSpeedticksflagt(new AbstractMap.SimpleEntry<Integer, Long>(TooFastCount, System.currentTimeMillis()));*/
    }
    private void check_timer(PlayerMoveEvent e, PlayerData pd){
        pd.getTimerms().add(System.currentTimeMillis()-(pd.getTimerms().size() == 0?0:pd.getTimerms().get(pd.getTimerms().size()-1)));
        if(pd.getTimerms().size() == tchecksize) {
            if(MathUtil.averageLong(pd.getTimerms()) < tmax_average) {
                updatevio(this, e.getPlayer(), tvio, " §6MODE: §bTIMER");
            }
            pd.getTimerms().clear();
        }
        /*if(pd.getTimerflag() > TimberNoCheat.instance.settings.movement_speed_timerflagtomessage){
            pd.setTimerflag(0);
            TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6MODE: TIMER");
        }*/
        //pd.setLasttimer(System.currentTimeMillis());
    }

    private void yspeed(PlayerMoveEvent e){
        double yspeed = LocationUtil.getVerticalVector(e.getFrom().toVector()).subtract(LocationUtil.getVerticalVector(e.getTo().toVector())).length();
        if(uyenable)
            if(((yspeed == 0.25D || (yspeed >= 0.58D && yspeed < 0.581D)) && yspeed > 0.0D || (yspeed > 0.2457D && yspeed < 0.24582D) || (yspeed > 0.329 && yspeed < 0.33)) && !e.getPlayer().getLocation().clone().subtract(0.0D, 0.1, 0.0D).getBlock().getType().equals(Material.SNOW))
                updatevio(this, e.getPlayer(), uyvio, " §6MODE: §bUNUSUAL_Y", " §6SPEED: §b" + yspeed, " §6BLOCK: §b" + e.getPlayer().getLocation().clone().subtract(0, 0.1D, 0).getBlock().getType().name());
        if(blenable)
            for(Block block : BlockUtil.getBlocksAround(e.getPlayer().getLocation(), 1))
                if(block.getType().isSolid() && yspeed >= 0.321 && yspeed < 0.322)
                    updatevio(this, e.getPlayer(), uyvio, " §6MODE: §bBLOCKS", " §6SPEED: §b" + yspeed);
    }

    private void check_grounddiff(PlayerMoveEvent e, PlayerData pd){
        final Player p = e.getPlayer();
        final FalsePositive.FalsePositiveChecks fp = pd.getFalsepositives();
        if(e.getFrom().getYaw() == e.getTo().getYaw() || p.getAllowFlight() || fp.hasVehicle(60) || fp.enderpearl || fp.hasOtherKB(60) || fp.hasTeleport(60) || fp.hasOtherKB(80) || fp.hasExplosion(60) || fp.hasHitorbow(60) || fp.hasPiston(60) || fp.hasRod(55)) return;
        double ongroundDiff = (e.getTo().getY() - e.getFrom().getY());

        if (PlayerUtil.isOnGround(p) && !p.hasPotionEffect(PotionEffectType.JUMP)
                && p.getLocation().add(0, 2, 0).getBlock().getType() == Material.AIR && p.getLocation().add(0, 1, 0).getBlock().getType() == Material.AIR
                && ongroundDiff > 0 && ongroundDiff != 0){
            boolean whitelist = false;
            for(double white : grounddiff) if(white == ongroundDiff) whitelist = true;
            if(whitelist) updatevio(this, p, groundvio, " §6MODE: §bGROUND", " §6DIFF: §b" + ongroundDiff);
        }
    }

    private void check_jumping(PlayerMoveEvent e){
        if(PlayerUtil.isOnClimbable(e.getPlayer()) || e.getPlayer().getAllowFlight())return;
        if(e.getFrom().getY() < e.getTo().getY() && e.getTo().getY()-e.getFrom().getY() < SpeedUtil.getMaxVertical(e.getPlayer(), PlayerUtil.isInLiquid(e.getPlayer()))){
            updatevio(this, e.getPlayer(), tvio, " §6MODE: §bJUMP");
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSprint(PlayerToggleSprintEvent e){
        if (!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer()) || e.isCancelled()) return;
        if(ssenable && e.isSprinting() && e.getPlayer().isSneaking()){
            e.setCancelled(true);
            updatevio(this, e.getPlayer(), ssviomodi, " §6MODE: §bSPRINTSNEAK(2)");
            //TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6MODE: §bSPRINTSNEAK(2)");
        }
        if(sfenable && e.isSprinting() && e.getPlayer().getFoodLevel() <= sffood){
            e.setCancelled(true);
            updatevio(this, e.getPlayer(), ssviomodi, " §6MODE: §bSPRINTFOOD(2)");
            //TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6MODE: §bFOODLEVELSPRINTSTART", " §6NEED: §b6", " §6HAS: §b" + p.getFoodLevel());
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
        Bukkit.getScheduler().runTaskLater(TimberNoCheat.instance, () -> pd.setTogglesneaklastsec(pd.getTogglesneaklastsec()-1), 20);
        if(spenable && pd.getTogglesneaklastsec() > spmaxsecond){
            e.setCancelled(true);
            updatevio(this, e.getPlayer(), spvio, " §6MODE: §bSNEAKSPAM", " §6TOGGLESLASTSEC: §b" + pd.getTogglesneaklastsec());
            //TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6MODE: §bSNEAKSPAM", " §6TOGGLESLASTSEC: §b" + pd.getTogglesneaklastsec());
        }
        if(ssenable && e.isSneaking() && e.getPlayer().isSprinting()){
            e.setCancelled(true);
            updatevio(this, e.getPlayer(), ssviomodi, " §6MODE: §bSNEAKSPRINT(2)");
            //TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6MODE: §bSNEAK");
        }
    }


}
