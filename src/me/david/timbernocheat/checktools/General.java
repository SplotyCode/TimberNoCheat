package me.david.timbernocheat.checktools;

import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.runnable.ExceptionRunnable;
import me.david.timbernocheat.runnable.TimberScheduler;
import me.david.timbernocheat.runnable.Tps;
import me.david.timbernocheat.util.CheckUtils;
import me.david.timbernocheat.util.LimitedList;
import me.david.timbernocheat.util.LimitedMap;
import me.david.timbernocheat.util.MoveingUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class General implements Listener, ExceptionRunnable  {

    public General(){
        new TimberScheduler("General-PlayerData", this).startTimer(1);
    }

    @Override
    public void run() {
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            if(CheckManager.getInstance().isvalid_create(player)) {
                GeneralValues generals = CheckManager.getInstance().getPlayerdata(player).getGenerals();
                if(CheckUtils.onGround(player)) generals.ticksInAir = 0;
                else generals.ticksInAir++;
                if(player.isSprinting()) generals.lastSprintTick = Tps.tickCount;
                generals.lastLocs.put(Tps.tickCount, player.getLocation());
            }
        }
    }

    public static class GeneralValues {

        private ArrayList<String> messages;
        private long lastMove;
        private long lastRealMove;
        private Location lastLocation;
        private Location loginLocation;
        private long lastItemClick;
        private Location lastOnGround;
        private int ticksInAir;
        private int lastSprintTick;

        private LimitedMap<Integer, Location> lastLocs;
        private LimitedMap<Integer, Boolean> vehicleTicks;
        private LimitedMap<Integer, Boolean> liquidTicks;

        private LimitedMap<Integer, Integer> speedTicks;
        private LimitedMap<Integer, Integer> jumpTicks;
        private LimitedMap<Integer, Integer> slowTicks;

        private LimitedMap<Integer, Boolean> sprintTicks;
        private LimitedMap<Integer, Boolean> sneakTicks;
        private LimitedMap<Integer, Boolean> blockTicks;

        private LimitedMap<Integer, Boolean> soulSandTicks;
        private LimitedMap<Integer, Boolean> iceTicks;
        private LimitedMap<Integer, Boolean> webTicks;
        private LimitedMap<Integer, Boolean> ladderTicks;
        private LimitedMap<Integer, Boolean> stairTicks;
        private LimitedMap<Integer, Boolean> slabsTicks;

        private LimitedMap<Integer, Boolean> groundTicks;
        private LimitedMap<Integer, Boolean> topTicks;
        private LimitedMap<Integer, Location> moveEventTicks;
        private LimitedMap<Integer, Location> firstLoc;

        private LimitedList<Integer> movingTicks;

        public GeneralValues(){
            messages = new ArrayList<>();
            lastLocation = null;
            loginLocation = null;
            lastMove = System.currentTimeMillis()-15000L;
            lastRealMove = System.currentTimeMillis()-15000L;
            lastItemClick = System.currentTimeMillis()-15000L;
            lastOnGround = null;
            ticksInAir = 0;
            lastSprintTick = 0;
            lastLocs = new LimitedMap<>(8);
            vehicleTicks = new LimitedMap<>(4);
            speedTicks = new LimitedMap<>(4);
            jumpTicks = new LimitedMap<>(4);
            slowTicks = new LimitedMap<>(4);
            liquidTicks = new LimitedMap<>(4);
            sprintTicks = new LimitedMap<>(4);
            sneakTicks = new LimitedMap<>(4);
            blockTicks = new LimitedMap<>(4);
            soulSandTicks = new LimitedMap<>(4);
            iceTicks = new LimitedMap<>(4);
            webTicks = new LimitedMap<>(4);
            ladderTicks = new LimitedMap<>(4);
            stairTicks = new LimitedMap<>(4);
            slabsTicks = new LimitedMap<>(4);
            groundTicks = new LimitedMap<>(6);
            topTicks = new LimitedMap<>(4);
            moveEventTicks = new LimitedMap<>(6);
            movingTicks = new LimitedList<>(8);
            firstLoc = new LimitedMap<>(6);
        }

        public List<Integer> getMovingTicks() {
            return movingTicks.getValues();
        }

        public boolean sneakTick(int tick){
            return sneakTicks.get(tick);
        }

        public Location firstPos(int tick){
            return firstLoc.get(tick);
        }

        public boolean bloclTick(int tick){
            return blockTicks.get(tick);
        }

        public boolean sprintTick(int tick){
            return sprintTicks.get(tick);
        }

        public int speedLevelTick(int tick){
            return speedTicks.get(tick);
        }

        public int jumpLevelTick(int tick){
            return jumpTicks.get(tick);
        }

        public int slowLevelTick(int tick){
            return slowTicks.get(tick);
        }

        public boolean liquidTick(int tick){
            return liquidTicks.get(tick);
        }

        public boolean vehicleTick(int tick){
            return vehicleTicks.get(tick);
        }

        public boolean iceTick(int tick){
            return iceTicks.get(tick);
        }

        public boolean soulSandTick(int tick){
            return soulSandTicks.get(tick);
        }

        public boolean webTick(int tick){
            return webTicks.get(tick);
        }

        public boolean ladderTick(int tick){
            return ladderTicks.get(tick);
        }

        public boolean stairTick(int tick){
            return stairTicks.get(tick);
        }

        public boolean slabTick(int tick){
            return slabsTicks.get(tick);
        }

        public boolean ground(int tick){
            return groundTicks.get(tick);
        }

        public boolean headCollidate(int tick){
            return topTicks.get(tick);
        }

        public Location moveEventLoc(int tick){
            return moveEventTicks.get(tick);
        }

        public int ticksSinceSprint(){
            return Tps.tickCount-lastSprintTick;
        }

        public Location getLastOnGround() {
            return lastOnGround;
        }

        public ArrayList<String> getMessages() {
            return messages;
        }

        public long getLastMove() {
            return lastMove;
        }

        public long getLastRealMove() {
            return lastRealMove;
        }

        public Location getLastLocation() {
            return lastLocation;
        }

        public Location getLoginLocation() {
            return loginLocation;
        }

        public Location getLastLocations(int tick) {
            return lastLocs.get(tick);
        }

        public long getLastItemClick() {
            return lastItemClick;
        }

        public int getTicksInAir() {
            return ticksInAir;
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void message(AsyncPlayerChatEvent event){
        if(!event.isCancelled() && CheckManager.getInstance().isvalid_create(event.getPlayer()))
            CheckManager.getInstance().getPlayerdata(event.getPlayer()).getGenerals().messages.add(event.getMessage());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event){
        if(CheckManager.getInstance().isvalid_create(event.getPlayer()))
            CheckManager.getInstance().getPlayerdata(event.getPlayer()).getGenerals().loginLocation = event.getPlayer().getLocation();
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event){
        final Player player = event.getPlayer();
        if(CheckManager.getInstance().isvalid_create(player)){
            PlayerData pd = CheckManager.getInstance().getPlayerdata(player);
            GeneralValues gen = pd.getGenerals();

            if(CheckUtils.onGround(event.getTo())){
                gen.lastOnGround = event.getTo();
            }
            gen.lastMove = System.currentTimeMillis();
            Location to = event.getTo();
            Location fr = event.getFrom();

            if(!(to.getX() == fr.getX() && to.getY() == fr.getY() && to.getZ() == to.getZ())) gen.lastRealMove = System.currentTimeMillis();
            gen.lastLocation = to;

            if(gen.vehicleTicks.get(Tps.tickCount) == null) gen.vehicleTicks.put(Tps.tickCount, player.getVehicle() != null);
            else if(player.getVehicle() != null) gen.vehicleTicks.put(Tps.tickCount, true);

            if(gen.speedTicks.get(Tps.tickCount) == null) gen.speedTicks.put(Tps.tickCount, CheckUtils.getPotionEffectLevel(player, PotionEffectType.SPEED));
            else if(CheckUtils.getPotionEffectLevel(player, PotionEffectType.SPEED) > gen.speedTicks.get(Tps.tickCount)) gen.speedTicks.put(Tps.tickCount, CheckUtils.getPotionEffectLevel(player, PotionEffectType.SPEED));

            if(gen.jumpTicks.get(Tps.tickCount) == null) gen.jumpTicks.put(Tps.tickCount, CheckUtils.getPotionEffectLevel(player, PotionEffectType.JUMP));
            else if(CheckUtils.getPotionEffectLevel(player, PotionEffectType.JUMP) > gen.jumpTicks.get(Tps.tickCount)) gen.jumpTicks.put(Tps.tickCount, CheckUtils.getPotionEffectLevel(player, PotionEffectType.JUMP));

            if(gen.slowTicks.get(Tps.tickCount) == null) gen.slowTicks.put(Tps.tickCount, CheckUtils.getPotionEffectLevel(player, PotionEffectType.SLOW));
            else if(CheckUtils.getPotionEffectLevel(player, PotionEffectType.SLOW) > gen.slowTicks.get(Tps.tickCount)) gen.slowTicks.put(Tps.tickCount, CheckUtils.getPotionEffectLevel(player, PotionEffectType.SLOW));

            boolean liquid = liquid(to) || liquid(fr);
            if(gen.liquidTicks.get(Tps.tickCount) == null) gen.vehicleTicks.put(Tps.tickCount, liquid);
            else if(liquid) gen.vehicleTicks.put(Tps.tickCount, true);

            if(gen.sprintTicks.get(Tps.tickCount) == null) gen.sprintTicks.put(Tps.tickCount, player.isSprinting());
            else if(player.isSprinting()) gen.sprintTicks.put(Tps.tickCount, true);

            if(gen.sneakTicks.get(Tps.tickCount) == null) gen.sneakTicks.put(Tps.tickCount, player.isSneaking());
            else if(player.isSneaking()) gen.sneakTicks.put(Tps.tickCount, true);

            if(gen.blockTicks.get(Tps.tickCount) == null) gen.blockTicks.put(Tps.tickCount, player.isBlocking());
            else if(player.isBlocking()) gen.blockTicks.put(Tps.tickCount, true);

            boolean ice = CheckUtils.doesColidateWithMaterial(Material.ICE, to) || CheckUtils.doesColidateWithMaterial(Material.ICE, fr);
            if(gen.iceTicks.get(Tps.tickCount) == null) gen.iceTicks.put(Tps.tickCount, ice);
            else if(ice) gen.iceTicks.put(Tps.tickCount, true);

            boolean soul = CheckUtils.doesColidateWithMaterial(Material.SOUL_SAND, to) || CheckUtils.doesColidateWithMaterial(Material.SOUL_SAND, fr);
            if(gen.soulSandTicks.get(Tps.tickCount) == null) gen.soulSandTicks.put(Tps.tickCount, soul);
            else if(soul) gen.soulSandTicks.put(Tps.tickCount, true);

            boolean web = CheckUtils.doesColidateWithMaterial(Material.WEB, to) || CheckUtils.doesColidateWithMaterial(Material.WEB, fr);
            if(gen.webTicks.get(Tps.tickCount) == null) gen.webTicks.put(Tps.tickCount, web);
            else if(web) gen.webTicks.put(Tps.tickCount, true);

            boolean ladder = CheckUtils.doesColidateWithMaterial(Material.LADDER, to) || CheckUtils.doesColidateWithMaterial(Material.LADDER, fr);
            if(gen.ladderTicks.get(Tps.tickCount) == null) gen.ladderTicks.put(Tps.tickCount, ladder);
            else if(ladder) gen.ladderTicks.put(Tps.tickCount, true);

            boolean stair = MoveingUtils.touchStair(to) || MoveingUtils.touchStair(fr);
            if(gen.stairTicks.get(Tps.tickCount) == null) gen.stairTicks.put(Tps.tickCount, stair);
            else if(stair) gen.stairTicks.put(Tps.tickCount, true);

            boolean slab = MoveingUtils.touchSlabs(to) || MoveingUtils.touchSlabs(fr);
            if(gen.slabsTicks.get(Tps.tickCount) == null) gen.slabsTicks.put(Tps.tickCount, slab);
            else if(slab) gen.slabsTicks.put(Tps.tickCount, true);

            boolean ground = CheckUtils.onGround(to) && CheckUtils.onGround(fr);
            if(gen.groundTicks.get(Tps.tickCount) == null) gen.groundTicks.put(Tps.tickCount, ground);
            else if(ground) gen.groundTicks.put(Tps.tickCount, true);

            boolean head = CheckUtils.headCollidate(to) || CheckUtils.headCollidate(fr);
            if(gen.topTicks.get(Tps.tickCount) == null) gen.topTicks.put(Tps.tickCount, head);
            else if(head) gen.topTicks.put(Tps.tickCount, true);

            if(gen.firstLoc.get(Tps.tickCount) == null) gen.firstLoc.put(Tps.tickCount, to);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onItemClick(final InventoryClickEvent event){
        final Player player = (Player) event.getWhoClicked();
        if(CheckManager.getInstance().isvalid_create(player))
            CheckManager.getInstance().getPlayerdata(player).getGenerals().lastItemClick = System.currentTimeMillis();
    }

    private boolean liquid(Location location){
        for(Material mat : MaterialHelper.LIQUID)
            if(CheckUtils.doesColidateWithMaterial(mat, location))
                return true;
        return false;
    }

}
