package me.david.timbernocheat.checkbase;

import me.david.api.objects.Pair;
import me.david.timbernocheat.checkes.movement.fly.FlyData;
import me.david.timbernocheat.checktools.AsyncGeneral;
import me.david.timbernocheat.checktools.FalsePositive;
import me.david.timbernocheat.checktools.General;
import me.david.timbernocheat.util.LimitedMap;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;

/*
 * All Variables for PlayerData mostly for checks
 * Also Link to FalsePositive/Generals/AsyncGenerals Tool
 */
public class PlayerData {

    private final UUID uuid;

    /* Chat */
    private long lastChat;
    private long lastCommand;

    /* NBT Packet Flood */private long lastNBTPacket;

    /* FightSpeed */
    private int interactsLastSecond;
    private int hitsLastSecond;
    private ArrayList<Long> fightSpeed;

    /* FastPlace */ private int blockPlacesLastSecond;
    /* NoSwing */ private boolean armSwung;

    /* ServerCrasher*/
    private int itemSwungInOneSecond;
    private int itemSwitchesInOneSecond;

    /* FastBow */private long lastBowShot;
    /* FastEat */private long lastEat;

    /* Regen */
    private long lastRegen;
    private long lastRegenPeaceful;
    private long lastRegenMagic;

    /* Killaura (multi) */
    private int lastHitEntity;
    private long lastHitMutli;
    private HashMap<Integer, Long> hittetEntitys;

    /* MorePackets */
    private long lastPacket;
    private boolean morePacketBlacklist;
    private boolean morePacketBlacklist2;
    private Map.Entry<Integer, Long> morepackets;

    /* Speed */
    private int toggleSneakLastSec;
    private Pair<Location, Location> lastPattern;

    /* BadPackets */private LimitedMap<Integer, Pair<Double, Integer>> movePackets;
    /* Nuker */private int blockBreaksLastSec;
    /* FastSwitch */private long lastItemSwitch;

    private float lastPitch;
    private float lastYaw;
    private long lastTeleport = 0;
    private boolean teleportUsed = true;

    /* ChestStealer */
    private long lastChestStealer;
    private ArrayList<Long> chestStealerCon;

    /* Speed */
    private Location lastSpeedLoc;
    private Location lastFlagLoc;

    /* FastLadder */
    private double lastFastLadderLongY;
    private long fastLadderLongStart;
    private Location fastLadderStart;

    /* FastRespawn */ private long lastDead;

    private int commands10sec;
    private int chats10sec;

    private Block startBreak;
    private long startBreakTime;

    private int ticksOnGround;

    private long lastRightClick;
    private double stepJump;
    private int stepJump2;
    private Location lastStep;

    private HashMap<Long, Boolean> accuracy;
    /* Glide */ private long glide;
    /* Step */ private double newStep;

    /* VehicleMove*/
    private double vehicleY;
    private int vehicleDiff;

    private long lastAchivementOpenInv;
    private float hitBoxYaw;
    private Player lastAttaked;
    /* Killaura(Bot) */ private EntityPlayer bot;

    /* Killaura(hit miss radio) */
    private int packetHit;
    private int packetSwing;

    private Location aimBotLoc;
    private double aimBotDiff;
    private Location lastTickLocation;
    private long lastOnGroundTime;

    /* GodMode Variables */
    private int godLastDamageTick;
    private int godLastNoDamageTicks;
    private int godAcc;
    private double godHealth;
    private int godHealthTick;

    /* Rotate */ private int snappyRotate;

    /* Criticals */ private double critHeight = -1;

    /* Phase */ private Location lastPhaseOkay;
    /* Fly */ private FlyData flyData;

    /* ZeroDelay */
    private Location zeroDelayLocation;
    private long zeroDelayTime;
    private int zeroDelayBlocked;

    /* MotionLoop */
    private double lastYMotion;
    private int motionLoopRepeat;
    private Location firstRepeat;

    /* Jesus */
    private int waterJumpingTicks;
    private Location jumpStart;
    private double jesusJumpLenght;

    /* LowHop */
    private double lowHopDiff;
    private boolean lowHopInvalid;


    private FalsePositive.FalsePositiveChecks falsePositives;
    private General.GeneralValues generals;
    private AsyncGeneral.AsyncGeneralValues asyncGenerals;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.lastChat = System.currentTimeMillis()-15000L;
        this.lastCommand = System.currentTimeMillis()-15000L;
        this.lastNBTPacket = System.currentTimeMillis()-15000L;
        this.interactsLastSecond = 0;
        this.hitsLastSecond = 0;
        this.blockPlacesLastSecond = 0;
        this.armSwung = false;
        this.itemSwungInOneSecond = 0;
        this.itemSwitchesInOneSecond = 0;
        this.lastBowShot = System.currentTimeMillis()-15000L;
        this.lastEat = System.currentTimeMillis()-15000L;
        this.lastHitEntity = 0;
        this.lastHitMutli = System.currentTimeMillis()-15000L;
        this.morePacketBlacklist = false;
        this.lastPacket = 0;
        this.morepackets = new AbstractMap.SimpleEntry<>(0, System.currentTimeMillis());
        this.toggleSneakLastSec = 0;
        this.morePacketBlacklist2 = false;
        this.movePackets = new LimitedMap<>(450);
        this.blockBreaksLastSec = 0;
        this.lastItemSwitch = System.currentTimeMillis()-15000L;
        this.lastYaw = 1000;
        this.lastPitch = 1000;
        this.lastChestStealer = System.currentTimeMillis()-15000L;
        this.chestStealerCon = new ArrayList<>();
        this.lastSpeedLoc = null;
        this.lastFastLadderLongY = -1;
        this.fastLadderLongStart = System.currentTimeMillis()-15000L;
        this.fastLadderStart = null;
        this.lastDead = System.currentTimeMillis()-15000L;
        this.chats10sec = 0;
        this.commands10sec = 0;
        this.startBreak = null;
        this.startBreakTime = 0;
        this.ticksOnGround = 0;
        this.lastRightClick = System.currentTimeMillis()-15000L;
        this.falsePositives = new FalsePositive.FalsePositiveChecks();
        this.stepJump = 0;
        this.stepJump2 = -1;
        this.lastStep = null;
        this.accuracy = new HashMap<>();
        this.glide = -1;
        this.vehicleDiff = -1;
        this.vehicleY = -1;
        this.lastAchivementOpenInv = System.currentTimeMillis()-15000L;
        this.hitBoxYaw = 0;
        this.lastAttaked = null;
        this.bot = null;
        this.packetHit = 0;
        this.packetSwing = 0;
        this.fightSpeed = new ArrayList<>();
        this.aimBotLoc = null;
        this.aimBotDiff = -1;
        this.lastTickLocation = null;
        this.lastOnGroundTime = System.currentTimeMillis()-15000L;
        this.godAcc = 0;
        this.godLastDamageTick = 0;
        this.godLastNoDamageTicks = 0;
        this.godHealth = 0;
        this.godHealthTick = 0;
        generals = new General.GeneralValues();
        asyncGenerals = new AsyncGeneral.AsyncGeneralValues();
        newStep = 0;
        hittetEntitys = new HashMap<>();
        lastRegen = -1;
        lastRegenMagic = -1;
        lastRegenPeaceful = -1;
        snappyRotate = -1;
        critHeight = -1;
        lastPhaseOkay = null;
        flyData = new FlyData();
        lastFlagLoc = null;
        lastPattern = null;
        zeroDelayLocation = null;
        zeroDelayTime = System.currentTimeMillis()-15000L;
        zeroDelayBlocked = 0;
        firstRepeat = null;
        motionLoopRepeat = 0;
        lastYMotion = Double.MIN_VALUE;
        lowHopDiff = 0;
        lowHopInvalid = false;
    }

    /**
     * Getter for falsePositives
     *
     * @return value of falsePositives
     */
    public FalsePositive.FalsePositiveChecks getFalsePositives() {
        return falsePositives;
    }
    public General.GeneralValues getGenerals() {
        return generals;
    }
    public AsyncGeneral.AsyncGeneralValues getAsyncGenerals() {
        return asyncGenerals;
    }



    public long getLastTeleport() {
        return lastTeleport;
    }

    public void setLastTeleport(long lastTeleport) {
        this.lastTeleport = lastTeleport;
    }

    public boolean isTeleportUsed() {
        return teleportUsed;
    }

    public void setTeleportUsed(boolean teleportUsed) {
        this.teleportUsed = teleportUsed;
    }

    public Pair<Location, Location> getLastPattern() {
        return lastPattern;
    }

    public void setLastPattern(Pair<Location, Location> lastPattern) {
        this.lastPattern = lastPattern;
    }

    public Location getLastFlagLoc() {
        return lastFlagLoc;
    }

    public void setLastFlagLoc(Location lastFlagLoc) {
        this.lastFlagLoc = lastFlagLoc;
    }

    public double getNewStep() {
        return newStep;
    }

    public void setNewStep(double newStep) {
        this.newStep = newStep;
    }

    public double getGodHealth() {
        return godHealth;
    }

    public void setGodHealth(double godHealth) {
        this.godHealth = godHealth;
    }

    public int getGodhealthtick() {
        return godHealthTick;
    }

    public void setGodhealthtick(int godhealthtick) {
        this.godHealthTick = godhealthtick;
    }

    public int getGodLastDamageTick() {
        return godLastDamageTick;
    }

    public void setGodLastDamageTick(int godLastDamageTick) {
        this.godLastDamageTick = godLastDamageTick;
    }

    public int getGodLastNoDamageTicks() {
        return godLastNoDamageTicks;
    }

    public void setGodLastNoDamageTicks(int godLastNoDamageTicks) {
        this.godLastNoDamageTicks = godLastNoDamageTicks;
    }

    public int getGodAcc() {
        return godAcc;
    }

    public Location getLastPhaseOkay() {
        return lastPhaseOkay;
    }

    public void setLastPhaseOkay(Location lastPhaseOkay) {
        this.lastPhaseOkay = lastPhaseOkay;
    }

    public void setGodAcc(int godAcc) {
        this.godAcc = godAcc;
    }

    public long getLastOnGroundTime() {
        return lastOnGroundTime;
    }

    public void setLastOnGroundTime(long lastOnGroundTime) {
        this.lastOnGroundTime = lastOnGroundTime;
    }

    public Location getLastTickLocation() {
        return lastTickLocation;
    }

    public void setLastTickLocation(Location lastTickLocation) {
        this.lastTickLocation = lastTickLocation;
    }

    public double getLastYMotion() {
        return lastYMotion;
    }

    public void setLastYMotion(double lastYMotion) {
        this.lastYMotion = lastYMotion;
    }

    public int getMotionLoopRepeat() {
        return motionLoopRepeat;
    }

    public void setMotionLoopRepeat(int motionLoopRepeat) {
        this.motionLoopRepeat = motionLoopRepeat;
    }

    public Location getFirstRepeat() {
        return firstRepeat;
    }

    public void setFirstRepeat(Location firstRepeat) {
        this.firstRepeat = firstRepeat;
    }

    public Location getAimBotLoc() {
        return aimBotLoc;
    }

    public void setAimBotLoc(Location aimBotLoc) {
        this.aimBotLoc = aimBotLoc;
    }

    public double getAimBotDiff() {
        return aimBotDiff;
    }

    public void setAimBotDiff(double aimBotDiff) {
        this.aimBotDiff = aimBotDiff;
    }

    public ArrayList<Long> getFightSpeed() {
        return fightSpeed;
    }

    public int getPacketHit() {
        return packetHit;
    }

    public void setPacketHit(int packetHit) {
        this.packetHit = packetHit;
    }

    public int getPacketSwing() {
        return packetSwing;
    }

    public void setPacketSwing(int packetSwing) {
        this.packetSwing = packetSwing;
    }

    public EntityPlayer getBot() {
        return bot;
    }

    public void setBot(EntityPlayer bot) {
        this.bot = bot;
    }

    public Location getZeroDelayLocation() {
        return zeroDelayLocation;
    }

    public int getZeroDelayBlocked() {
        return zeroDelayBlocked;
    }

    public void setZeroDelayBlocked(int zeroDelayBlocked) {
        this.zeroDelayBlocked = zeroDelayBlocked;
    }

    public void setZeroDelayLocation(Location zeroDelayLocation) {
        this.zeroDelayLocation = zeroDelayLocation;
    }

    public long getZeroDelayTime() {
        return zeroDelayTime;
    }

    public void setZeroDelayTime(long zeroDelayTime) {
        this.zeroDelayTime = zeroDelayTime;
    }

    public Player getLastAttaked() {
        return lastAttaked;
    }

    public void setLastAttaked(Player lastAttaked) {
        this.lastAttaked = lastAttaked;
    }

    public float getHitBoxYaw() {
        return hitBoxYaw;
    }

    public void setHitBoxYaw(float hitBoxYaw) {
        this.hitBoxYaw = hitBoxYaw;
    }

    public long getLastAchivementOpenInv() {
        return lastAchivementOpenInv;
    }

    public void setLastAchivementOpenInv(long lastAchivementOpenInv) {
        this.lastAchivementOpenInv = lastAchivementOpenInv;
    }

    public double getVehicleY() {
        return vehicleY;
    }

    public void setVehicleY(double vehicleY) {
        this.vehicleY = vehicleY;
    }

    public int getVehicleDiff() {
        return vehicleDiff;
    }

    public void setVehicleDiff(int vehicleDiff) {
        this.vehicleDiff = vehicleDiff;
    }

    public long getGlide() {
        return glide;
    }

    public void setGlide(long glide) {
        this.glide = glide;
    }

    public HashMap<Long, Boolean> getAccuracy() {
        return accuracy;
    }

    public Location getLastStep() {
        return lastStep;
    }

    public void setLastStep(Location lastStep) {
        this.lastStep = lastStep;
    }

    public int getStepJump2() {
        return stepJump2;
    }

    public void setStepJump2(int stepJump2) {
        this.stepJump2 = stepJump2;
    }

    public double getStepJump() {
        return stepJump;
    }

    public void setStepJump(double stepJump) {
        this.stepJump = stepJump;
    }

    public long getLastRightClick() {
        return lastRightClick;
    }

    public void setLastRightClick(long lastRightClick) {
        this.lastRightClick = lastRightClick;
    }

    public int getTicksOnGround() {
        return ticksOnGround;
    }

    public void setTicksOnGround(int ticksOnGround) {
        this.ticksOnGround = ticksOnGround;
    }

    public Block getStartBreak() {
        return startBreak;
    }

    public void setStartBreak(Block startBreak) {
        this.startBreak = startBreak;
    }

    public long getStartBreakTime() {
        return startBreakTime;
    }

    public void setStartBreakTime(long startBreakTime) {
        this.startBreakTime = startBreakTime;
    }

    public int getCommands10sec() {
        return commands10sec;
    }

    public void setCommands10sec(int commands10sec) {
        this.commands10sec = commands10sec;
    }

    public int getChats10sec() {
        return chats10sec;
    }

    public void setChats10sec(int chats10sec) {
        this.chats10sec = chats10sec;
    }

    public long getLastDead() {
        return lastDead;
    }

    public void setLastDead(long lastDead) {
        this.lastDead = lastDead;
    }

    public Location getFastLadderStart() {
        return fastLadderStart;
    }

    public void setFastLadderStart(Location fastLadderStart) {
        this.fastLadderStart = fastLadderStart;
    }

    public double getLastFastLadderLongY() {
        return lastFastLadderLongY;
    }

    public void setLastFastLadderLongY(double lastFastLadderLongY) {
        this.lastFastLadderLongY = lastFastLadderLongY;
    }

    public long getFastLadderLongStart() {
        return fastLadderLongStart;
    }

    public void setFastLadderLongStart(long fastLadderLongStart) {
        this.fastLadderLongStart = fastLadderLongStart;
    }

    public Location getLastSpeedLoc() {
        return lastSpeedLoc;
    }

    public void setLastSpeedLoc(Location lastSpeedLoc) {
        this.lastSpeedLoc = lastSpeedLoc;
    }

    public ArrayList<Long> getCheststealercon() {
        return chestStealerCon;
    }

    public void setCheststealercon(ArrayList<Long> cheststealercon) {
        this.chestStealerCon = cheststealercon;
    }

    public long getLastChestStealer() {
        return lastChestStealer;
    }

    public void setLastChestStealer(long lastChestStealer) {
        this.lastChestStealer = lastChestStealer;
    }

    public float getLastPitch() {
        return lastPitch;
    }

    public void setLastPitch(float lastPitch) {
        this.lastPitch = lastPitch;
    }

    public float getLastYaw() {
        return lastYaw;
    }

    public void setLastYaw(float lastYaw) {
        this.lastYaw = lastYaw;
    }

    public long getLastItemwSitch() {
        return lastItemSwitch;
    }

    public void setLastItemwSitch(long lastItemwSitch) {
        this.lastItemSwitch = lastItemwSitch;
    }

    public int getBlockBreaksLastSec() {
        return blockBreaksLastSec;
    }

    public void setBlockBreaksLastSec(int blockBreaksLastSec) {
        this.blockBreaksLastSec = blockBreaksLastSec;
    }

    public boolean isMorePacketBlacklist2() {
        return morePacketBlacklist2;
    }

    public void setMorePacketBlacklist2(boolean morePacketBlacklist2) {
        this.morePacketBlacklist2 = morePacketBlacklist2;
    }

    public int getToggleSneakLastSec() {
        return toggleSneakLastSec;
    }

    public void setToggleSneakLastSec(int toggleSneakLastSec) {
        this.toggleSneakLastSec = toggleSneakLastSec;
    }

    public boolean isMorePacketBlacklist() {
        return morePacketBlacklist;
    }

    public void setMorePacketBlacklist(boolean morePacketBlacklist) {
        this.morePacketBlacklist = morePacketBlacklist;
    }

    public Map.Entry<Integer, Long> getMorepackets() {
        return morepackets;
    }

    public void setMorepackets(Map.Entry<Integer, Long> morepackets) {
        this.morepackets = morepackets;
    }

    public long getLastPacket() {
        return lastPacket;
    }

    public void setLastPacket(long lastPacket) {
        this.lastPacket = lastPacket;
    }

    public int getLastHitEntity() {
        return lastHitEntity;
    }

    public void setLastHitEntity(int lastHitEntity) {
        this.lastHitEntity = lastHitEntity;
    }

    public long getLastHitMutli() {
        return lastHitMutli;
    }

    public void setLastHitMutli(long lastHitMutli) {
        this.lastHitMutli = lastHitMutli;
    }

    public long getLastEat() {
        return lastEat;
    }

    public void setLastEat(long lastEat) {
        this.lastEat = lastEat;
    }

    public long getLastBowShot() {
        return lastBowShot;
    }

    public void setLastBowShot(long lastBowShot) {
        this.lastBowShot = lastBowShot;
    }

    public int getItemSwungInOneSecond() {
        return itemSwungInOneSecond;
    }

    public void setItemSwungInOneSecond(int itemSwungInOneSecond) {
        this.itemSwungInOneSecond = itemSwungInOneSecond;
    }

    public int getItemSwitchesInOneSecond() {
        return itemSwitchesInOneSecond;
    }

    public void setItemSwitchesInOneSecond(int itemSwitchesInOneSecond) {
        this.itemSwitchesInOneSecond = itemSwitchesInOneSecond;
    }

    public boolean isArmSwung() {
        return armSwung;
    }

    public void setArmSwung(boolean armSwung) {
        this.armSwung = armSwung;
    }

    public int getBlockPlacesLastSecond() {
        return blockPlacesLastSecond;
    }

    public void setBlockPlacesLastSecond(int blockPlacesLastSecond) {
        this.blockPlacesLastSecond = blockPlacesLastSecond;
    }

    public int getInteractsLastSecond() {
        return interactsLastSecond;
    }

    public void setInteractsLastSecond(int interactsLastSecond) {
        this.interactsLastSecond = interactsLastSecond;
    }

    public int getHitsLastSecond() {
        return hitsLastSecond;
    }

    public void setHitsLastSecond(int hitsLastSecond) {
        this.hitsLastSecond = hitsLastSecond;
    }

    public UUID getUuid() {
        return uuid;
    }

    public long getLastChat() {
        return lastChat;
    }

    public void setLastChat(long lastChat) {
        this.lastChat = lastChat;
    }

    public long getLastCommand() {
        return lastCommand;
    }

    public void setLastCommand(long lastCommand) {
        this.lastCommand = lastCommand;
    }

    public long getLastNBTPacket() {
        return lastNBTPacket;
    }

    public void setLastNBTPacket(long lastNBTPacket) {
        this.lastNBTPacket = lastNBTPacket;
    }

    public HashMap<Integer, Long> getHittetEntitys() {
        return hittetEntitys;
    }

    public long getLastRegen() {
        return lastRegen;
    }

    public void setLastRegen(long lastRegen) {
        this.lastRegen = lastRegen;
    }

    public long getLastRegenPeaceful() {
        return lastRegenPeaceful;
    }

    public void setLastRegenPeaceful(long lastRegenPeaceful) {
        this.lastRegenPeaceful = lastRegenPeaceful;
    }

    public long getLastRegenMagic() {
        return lastRegenMagic;
    }

    public void setLastRegenMagic(long lastRegenMagic) {
        this.lastRegenMagic = lastRegenMagic;
    }

    public int getSnappyRotate() {
        return snappyRotate;
    }

    public void setSnappyRotate(int snappyRotate) {
        this.snappyRotate = snappyRotate;
    }

    public double getCritHeight() {
        return critHeight;
    }

    public void setCritHeight(double critHeight) {
        this.critHeight = critHeight;
    }

    public FlyData getFlyData() {
        return flyData;
    }

    public void setFlyData(FlyData flyData) {
        this.flyData = flyData;
    }

    public LimitedMap<Integer, Pair<Double, Integer>> getMovePackets() {
        return movePackets;
    }

    public void setMovePackets(LimitedMap<Integer, Pair<Double, Integer>> movePackets) {
        this.movePackets = movePackets;
    }

    public int getWaterJumpingTicks() {
        return waterJumpingTicks;
    }

    public void setWaterJumpingTicks(int waterJumpingTicks) {
        this.waterJumpingTicks = waterJumpingTicks;
    }

    public double getJesusJumpLenght() {
        return jesusJumpLenght;
    }

    public Location getJumpStart() {
        return jumpStart;
    }

    public void setJesusJumpLenght(double jesusJumpLenght) {
        this.jesusJumpLenght = jesusJumpLenght;
    }

    public void setJumpStart(Location jumpStart) {
        this.jumpStart = jumpStart;
    }

    public double getLowHopDiff() {
        return lowHopDiff;
    }

    public void setLowHopDiff(double lowHopDiff) {
        this.lowHopDiff = lowHopDiff;
    }

    public boolean isLowHopInvalid() {
        return lowHopInvalid;
    }

    public void setLowHopInvalid(boolean lowHopInvalid) {
        this.lowHopInvalid = lowHopInvalid;
    }
}
