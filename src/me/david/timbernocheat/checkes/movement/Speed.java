package me.david.timbernocheat.checkes.movement;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import me.david.timbernocheat.checkmanager.PlayerData;
import me.david.timbernocheat.checktools.FalsePositive;
import me.david.timbernocheat.util.SpeedUtil;
import me.david.timbernocheat.runnable.Velocity;
import me.david.timbernocheat.debug.Debuggers;
import me.david.api.utils.BlockUtil;
import me.david.api.utils.JsonFileUtil;
import me.david.api.utils.cordinates.LocationUtil;
import me.david.api.utils.MathUtil;
import me.david.api.utils.player.PlayerUtil;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private final boolean pattern;
    private final float patternmulti;
    private final double patternlatency;
    private final boolean patterncancel;
    private final List<String> disabledpatterns;

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
        pattern = getBoolean("pattern.enable");
        patternmulti = (float) getDouble("pattern.viomulti");
        disabledpatterns = getStringList("pattern.disabledpatterns");
        patternlatency = getDouble("pattern.latency");
        patterncancel = getBoolean("pattern.cancel");
        loadpatterns();
    }

    private ArrayList<SpeedPattern> patterns = new ArrayList<SpeedPattern>();
    public static ArrayList<UUID> generators = new ArrayList<>();

    private void loadpatterns(){
        try {
            File file = TimberNoCheat.instance.speedpatterns;
            if(!file.exists()){
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedReader br = new BufferedReader(new FileReader(file));
            if (br.readLine() == null) {
                br.close();
                return;
            }
            br.close();
            JsonReader reader = new JsonReader(new FileReader(file));
            patterns = new Gson().fromJson(reader, new TypeToken<ArrayList<SpeedPattern>>() {}.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void savepatterns(){
        JsonFileUtil.saveJSON(TimberNoCheat.instance.speedpatterns, new TypeToken<ArrayList<SpeedPattern>>() {}.getType(), patterns);
    }

    @Override
    public void starttasks() {
        register(Bukkit.getScheduler().runTaskTimer(TimberNoCheat.instance, new Runnable() {
            @Override
            public void run() {
                for(Player p : Bukkit.getOnlinePlayers()){
                    if(!TimberNoCheat.checkmanager.isvalid_create(p) || p.getAllowFlight())continue;
                    PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
                    if(pd.getLastticklocation() != null)     {
                        FalsePositive.FalsePositiveChecks fp = pd.getFalsepositives();
                        if (p.isSleeping() || fp.hasVehicle(40) || fp.hasExplosion(60) || fp.hasPiston(50) || fp.hasTeleport(80) || fp.hasWorld(120) || fp.hasHitorbow(40) || fp.worldboarder(p) || fp.hasRod(60) || fp.hasOtherKB(50) || fp.hasSlime(120) || fp.hasBed(80) || fp.hasChest(20)) continue;
                        SpeedPattern optimalpattern = generateSpeedPattern(p, pd);
                        boolean found = false;
                        for (SpeedPattern pattern : patterns)
                            if (pattern.equalsnospeed(optimalpattern)) {
                                optimalpattern = pattern;
                                found = true;
                                break;
                            }
                        double xzDiff = LocationUtil.getHorizontalDistance(pd.getLastticklocation(), p.getLocation());
                        double yDiffUp = p.getLocation().getY() - pd.getLastticklocation().getY();
                        double yDiffdown = pd.getLastticklocation().getY() - p.getLocation().getY();
                        if (!found) {
                            if(generators.contains(p.getUniqueId())){
                                optimalpattern.verticaldown = (float) yDiffdown;
                                optimalpattern.verticalup = (float) yDiffUp;
                                optimalpattern.horizontal = (float) xzDiff;
                                patterns.add(optimalpattern);
                                savepatterns();
                                p.sendMessage(TimberNoCheat.instance.prefix + "[SPEED-PATTERN] Neue Pattern '" + optimalpattern.name + "' erstellt!");
                            }else p.sendMessage(TimberNoCheat.instance.prefix + " [DEBUG] [SPEED-PATTERN] Es konnte keine Pattern gefunden werden!");
                            continue;
                        }
                        if(disabledpatterns.contains(optimalpattern.name)) continue;
                        double toomuch = 0;
                        int toomushper = 0;
                        TimberNoCheat.instance.getDebuger().sendDebug(Debuggers.PATTERN_SPEED, "CAPTURED: xz=" + xzDiff + " yUp=" + yDiffUp + " yDown=" + yDiffdown);
                        TimberNoCheat.instance.getDebuger().sendDebug(Debuggers.PATTERN_SPEED, "PATTERN: xz=" + optimalpattern.horizontal + " yUP=" + optimalpattern.verticalup + " yDown=" + optimalpattern.verticaldown);
                        if (optimalpattern.verticaldown < yDiffdown) {
                            if(generators.contains(p.getUniqueId())){
                                p.sendMessage(TimberNoCheat.instance.prefix + "[SPEED-PATTERN] '" + optimalpattern.name + "' updatet yDiffdown to '" + yDiffdown + "'!");
                                optimalpattern.verticaldown = (float) yDiffdown;
                                savepatterns();
                                continue;
                            }
                            toomuch += optimalpattern.verticaldown - yDiffdown;
                            toomushper += optimalpattern.verticaldown/yDiffdown;
                        }
                        if (optimalpattern.verticalup < yDiffUp) {
                            if(generators.contains(p.getUniqueId())){
                                p.sendMessage(TimberNoCheat.instance.prefix + "[SPEED-PATTERN] '" + optimalpattern.name + "' updatet yDiffup to '" + yDiffdown + "'!");
                                optimalpattern.verticalup = (float) yDiffUp;
                                savepatterns();
                                continue;
                            }
                            toomuch += optimalpattern.verticalup - yDiffUp;
                            toomushper +=  optimalpattern.verticalup/yDiffUp;
                        }
                        if (optimalpattern.horizontal < xzDiff) {
                            if(generators.contains(p.getUniqueId())){
                                p.sendMessage(TimberNoCheat.instance.prefix + "[SPEED-PATTERN] '" + optimalpattern.name + "' updatet xzDiff to '" + yDiffdown + "'!");
                                optimalpattern.horizontal = (float) xzDiff;
                                savepatterns();
                                continue;
                            }
                            toomuch += optimalpattern.horizontal - xzDiff;
                            toomushper += optimalpattern.horizontal/xzDiff;
                        }
                        toomushper *= 100;
                        if (toomushper >= patternlatency) {
                            updatevio(Speed.this, p, toomushper / 2, "§6MODE: §bPATTERN", "§6PERCENTAGE: §b" + toomushper, "§6DISTANCE: §b" + toomuch);
                            if(patterncancel) p.teleport(pd.getLastticklocation());
                        }
                    }
                    pd.setLastticklocation(p.getLocation());
                }
            }
        }, 0, 1).getTaskId());
    }

    private SpeedPattern generateSpeedPattern(Player player, PlayerData pd){
        FalsePositive.FalsePositiveChecks fp = pd.getFalsepositives();
        Material under = player.getLocation().subtract(0, 1, 0).getBlock().getType();
        return new SpeedPattern("P-" + patterns.size(), SpeedUtil.getPotionEffectLevel(player, PotionEffectType.SPEED), SpeedUtil.getPotionEffectLevel(player, PotionEffectType.JUMP), SpeedUtil.getPotionEffectLevel(player, PotionEffectType.SLOW), 0F, 0F, 0F, player.isInsideVehicle(), fp.hasLiquid(25), under == Material.ICE, player.isBlocking(), player.isSprinting(), player.isSneaking(), PlayerUtil.isInWeb(player), PlayerUtil.isOnLadder(player), PlayerUtil.slabsNear(player.getLocation()), PlayerUtil.stairsNear(player.getLocation()), player.getLocation().add(0, 2, 0).getBlock().getType() != Material.AIR, System.currentTimeMillis()-pd.getLastongroundtime()<5, under == Material.SOUL_SAND);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMove(PlayerMoveEvent e){
        final Player p = e.getPlayer();
        final Location to = e.getTo();
        final Location from = e.getFrom();
        if (!TimberNoCheat.checkmanager.isvalid_create(p) || e.isCancelled() || !to.getWorld().getName().equals(from.getWorld().getName())) return;
        TimberNoCheat.instance.getMoveprofiler().start("Speed");
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        if(PlayerUtil.isOnGround(p)) pd.setLastongroundtime(System.currentTimeMillis());
        if(ssenable && p.isSprinting() && p.isSneaking()){
            e.setCancelled(true);
            updatevio(this, p, ssviomodi, " §6MODE: §bSNEAK&SPRINT");
        }
        if(sfenable && p.isSprinting() && p.getFoodLevel() <= sffood){
            e.setCancelled(true);
            updatevio(this, p, sfvio, "§6MODE: §bSPRINTFOOD");
        }
        if(bsenable && p.isSprinting() && p.isBlocking()){
            e.setCancelled(true);
            updatevio(this, p, bsvio, " §6MODE: §bBLOCKSPRINT");
        }
        if(to.getX() != from.getX() || to.getY() != from.getY() || to.getZ() != from.getZ()){
            if(tenable)check_timer(e, pd);
            if(nenable)check_normal(e);
            if(jenable)check_jumping(e);
            if(grounddiffen) check_grounddiff(e, pd);
            yspeed(e);
        }
        TimberNoCheat.instance.getMoveprofiler().end();
    }

    private void check_normal(PlayerMoveEvent e) {
        final Player p = e.getPlayer();
        if (p.getAllowFlight() || p.getVehicle() != null || Velocity.velocity.containsKey(p.getUniqueId())) {
            return;
        }
        double offsetXZ = LocationUtil.offset(LocationUtil.getHorizontalVector(e.getFrom().toVector()), LocationUtil.getHorizontalVector(e.getTo().toVector()));
        double limitXZ = PlayerUtil.isOnGround(p) ? nbaseground : nbase;
        if (PlayerUtil.stairsNear(p.getLocation())) limitXZ += nstairs;
        if (PlayerUtil.slabsNear(p.getLocation())) limitXZ += nslabs;
        final boolean top = PlayerUtil.getEyeLocation(p).clone().add(0, 1, 0).getBlock().getType() != Material.AIR && !BlockUtil.canStandWithin(PlayerUtil.getEyeLocation(p).clone().add(0, 1, 0).getBlock());
        if (top) limitXZ += nsolid;
        if (p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.ICE)
            if (top) limitXZ = nbaseicesolid;
            else limitXZ = nbaseice;
        limitXZ += (p.getWalkSpeed() > 0.2F ? p.getWalkSpeed() * 10.0F * 0.33F : 0.0F);
        for (PotionEffect effect : p.getActivePotionEffects())
            if (effect.getType().equals(PotionEffectType.SPEED))
                limitXZ += (PlayerUtil.isOnGround(p)?nspeedground:nspeed) * (effect.getAmplifier() + 1);
        TimberNoCheat.instance.getDebuger().sendDebug(Debuggers.PATTERN_SPEED, " max=" + limitXZ + " player=" + offsetXZ);
        if(offsetXZ > limitXZ)
            updatevio(this, p, offsetXZ-limitXZ*nviomodi, " §6MODE: §bNORMAL");
    }

    private void check_timer(PlayerMoveEvent e, PlayerData pd){
        pd.getTimerms().add(System.currentTimeMillis()-(pd.getTimerms().size() == 0?0:pd.getTimerms().get(pd.getTimerms().size()-1)));
        if(pd.getTimerms().size() == tchecksize) {
            if(MathUtil.averageLong(pd.getTimerms()) < tmax_average) {
                updatevio(this, e.getPlayer(), tvio, " §6MODE: §bTIMER");
            }
            pd.getTimerms().clear();
        }
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
            //if(whitelist) updatevio(this, p, groundvio, " §6MODE: §bGROUND", " §6DIFF: §b" + ongroundDiff);
        }
    }

    private void check_jumping(PlayerMoveEvent e){
        if(PlayerUtil.isOnClimbable(e.getPlayer()) || e.getPlayer().getAllowFlight())return;
        if(e.getFrom().getY() < e.getTo().getY() && e.getTo().getY()-e.getFrom().getY() > SpeedUtil.getMaxVertical(e.getPlayer(), PlayerUtil.isInLiquid(e.getPlayer()))){
            updatevio(this, e.getPlayer(), tvio, " §6MODE: §bJUMP");
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSprint(PlayerToggleSprintEvent e){
        if (!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer()) || e.isCancelled()) return;
        if(ssenable && e.isSprinting() && e.getPlayer().isSneaking()){
            e.setCancelled(true);
            updatevio(this, e.getPlayer(), ssviomodi, " §6MODE: §bSPRINTSNEAK(2)");
        }
        if(sfenable && e.isSprinting() && e.getPlayer().getFoodLevel() <= sffood){
            e.setCancelled(true);
            updatevio(this, e.getPlayer(), ssviomodi, " §6MODE: §bSPRINTFOOD(2)");
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

    class SpeedPattern {

        private String name;
        private int speed, jumpboost, slowness;
        private float verticaldown, verticalup, horizontal;
        private boolean vehicle, liquid, ice, block, sprint, sneak, web, ladder, slabs, stairs, blockover, wasonground, soulsand;

        public SpeedPattern(String name, int speed, int jumpboost, int slowness, float verticaldown, float verticalup, float horizontal, boolean vehicle, boolean liquid, boolean ice, boolean block, boolean sprint, boolean sneak, boolean web, boolean ladder, boolean slabs, boolean stairs, boolean blockover, boolean wasonground, boolean soulsand) {
            this.name = name;
            this.speed = speed;
            this.jumpboost = jumpboost;
            this.slowness = slowness;
            this.verticaldown = verticaldown;
            this.verticalup = verticalup;
            this.horizontal = horizontal;
            this.vehicle = vehicle;
            this.liquid = liquid;
            this.ice = ice;
            this.block = block;
            this.sprint = sprint;
            this.sneak = sneak;
            this.web = web;
            this.ladder = ladder;
            this.slabs = slabs;
            this.stairs = stairs;
            this.blockover = blockover;
            this.wasonground = wasonground;
            this.soulsand = soulsand;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SpeedPattern that = (SpeedPattern) o;

            if (!name.equals(that.name)) return false;
            if (speed != that.speed) return false;
            if (jumpboost != that.jumpboost) return false;
            if (slowness != that.slowness) return false;
            if (Float.compare(that.verticaldown, verticaldown) != 0) return false;
            if (Float.compare(that.verticalup, verticalup) != 0) return false;
            if (Float.compare(that.horizontal, horizontal) != 0) return false;
            if (vehicle != that.vehicle) return false;
            if (liquid != that.liquid) return false;
            if (ice != that.ice) return false;
            if (block != that.block) return false;
            if (sprint != that.sprint) return false;
            if (sneak != that.sneak) return false;
            if (web != that.web) return false;
            if (ladder != that.ladder) return false;
            if (slabs != that.slabs) return false;
            if (stairs != that.stairs) return false;
            if (blockover != that.blockover) return false;
            if (wasonground != that.wasonground) return false;
            return soulsand == that.soulsand;
        }

        public boolean equalsnospeed(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SpeedPattern that = (SpeedPattern) o;

            if (speed != that.speed) return false;
            if (jumpboost != that.jumpboost) return false;
            if (slowness != that.slowness) return false;
            if (vehicle != that.vehicle) return false;
            if (liquid != that.liquid) return false;
            if (ice != that.ice) return false;
            if (block != that.block) return false;
            if (sprint != that.sprint) return false;
            if (sneak != that.sneak) return false;
            if (web != that.web) return false;
            if (ladder != that.ladder) return false;
            if (slabs != that.slabs) return false;
            if (stairs != that.stairs) return false;
            if (blockover != that.blockover) return false;
            if (wasonground != that.wasonground) return false;
            return soulsand == that.soulsand;
        }

        @Override
        public int hashCode() {
            int result = speed;
            result = 31 * result + jumpboost;
            result = 31 * result + slowness;
            result = 31 * result + (verticalup != +0.0f ? Float.floatToIntBits(verticalup) : 0);
            result = 31 * result + (verticaldown != +0.0f ? Float.floatToIntBits(verticaldown) : 0);
            result = 31 * result + (horizontal != +0.0f ? Float.floatToIntBits(horizontal) : 0);
            result = 31 * result + (vehicle ? 1 : 0);
            result = 31 * result + (liquid ? 1 : 0);
            result = 31 * result + (ice ? 1 : 0);
            result = 31 * result + (block ? 1 : 0);
            result = 31 * result + (sprint ? 1 : 0);
            result = 31 * result + (sneak ? 1 : 0);
            result = 31 * result + (web ? 1 : 0);
            result = 31 * result + (ladder ? 1 : 0);
            result = 31 * result + (slabs ? 1 : 0);
            result = 31 * result + (stairs ? 1 : 0);
            result = 31 * result + (blockover ? 1 : 0);
            result = 31 * result + (wasonground ? 1 : 0);
            result = 31 * result + (soulsand ? 1 : 0);
            return result;
        }
    }
}
