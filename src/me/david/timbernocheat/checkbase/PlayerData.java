package me.david.timbernocheat.checkbase;

import me.david.api.objects.Pair;
import me.david.timbernocheat.checkes.movement.fly.FlyData;
import me.david.timbernocheat.checktools.AsyncGeneral;
import me.david.timbernocheat.checktools.FalsePositive;
import me.david.timbernocheat.checktools.General;
import me.david.timbernocheat.util.LimitedList;
import me.david.timbernocheat.util.LimitedMap;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;

/*
 * All Variables for PlayerData mostly for checks
 * Also Link to FalsePositive Tool
 */
public class PlayerData {

    private final UUID uuid;

    /* Chat */
    private long lastchat;
    private long lastcommand;

    /* NBT Packet Flood */private long lastnbtpacket;

    /* Fightspeed */
    private int interactslastsecond;
    private int hitslastsecond;
    private ArrayList<Long> fightSpeed;

    /* FastPlace */private int blockplacelastsecond;
    /* NoSwing */private boolean armSwung;

    /* ServerCrasher*/
    private int itemswinginonesecond;
    private int itemshwitchinonesecond;

    /* FastBow */private long lastbowshot;
    /* FastEat */private long lasteat;

    /* Regen */
    private long lastRegen;
    private long lastRegenPeaceful;
    private long lastRegenMagic;

    /* Killaura (multi) */
    private int lasthitentity;
    private long lasthitmutli;
    private HashMap<Integer, Long> hittetEntitys;

    /* MorePackets */
    private long lastpacket;
    private boolean morepacketblacklist;
    private boolean morepacketsblacklist2;
    private Map.Entry<Integer, Long> morepackets;

    /* Speed */
    private int togglesneaklastsec;
    private Pair<Location, Location> lastPattern;

    /* BadBackets */private LimitedMap<Integer, Pair<Double, Integer>> movePackets;
    /* Nuker */private int blockbreakslastsec;
    /* Fastswitch */private long lastitemwsitch;

    private float lastpitch;
    private float lastyaw;
    private long lastTeleport = 0;
    private boolean teleportUsed = true;

    /* ChestStealer */
    private long lastcheststealer;
    private ArrayList<Long> cheststealercon;

    /* Speed */
    private Location lastspeedloc;
    private Location lastFlagloc;

    /* FastLadder */
    private double lastfastladderlongY;
    private long fastladderlongstart;
    private Location fastladderstart;

    /* FastRespawn */private long lastdead;

    private int commands10sec;
    private int chats10sec;

    private Block startbreak;
    private long startbreaktime;

    private int ticksonground;

    private long lastrightclick;
    private double stepjump;
    private int stepjump2;
    private Location laststep;

    private HashMap<Long, Boolean> accuracy;
    /* Glide */private long glide;
    /* Step */private double newstep;

    /* VehicleMove*/
    private double vehicley;
    private int vehicledif;

    private long lastachivementopeninv;
    private float hitboxyaw;
    private Player lastattaked;
    /* Killaura(bot) */private EntityPlayer bot;

    /* Killaura(hit miss radio) */
    private int packethit;
    private int packetswing;

    private Location aimborloc;
    private double aimbotdiff;
    private Location lastticklocation;
    private long lastongroundtime;

    /* GodMode Variables */
    private int godlastDamageTick;
    private int godlastNoDamageTicks;
    private int godAcc;
    private double godhealth;
    private int godhealthtick;

    /* Rotate */ private int snappyRotate;

    /* Criticals */ private double critHight = -1;

    /* Phase */ private Location lastPhaseOkay;
    /* Fly */ private FlyData flyData;


    private FalsePositive.FalsePositiveChecks falsepositives;
    private General.GeneralValues generals;
    private AsyncGeneral.AsyncGeneralValues asyncGenerals;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.lastchat = System.currentTimeMillis()-15000L;
        this.lastcommand = System.currentTimeMillis()-15000L;
        this.lastnbtpacket = System.currentTimeMillis()-15000L;
        this.interactslastsecond = 0;
        this.hitslastsecond = 0;
        this.blockplacelastsecond = 0;
        this.armSwung = false;
        this.itemswinginonesecond = 0;
        this.itemshwitchinonesecond = 0;
        this.lastbowshot = System.currentTimeMillis()-15000L;
        this.lasteat = System.currentTimeMillis()-15000L;
        this.lasthitentity = 0;
        this.lasthitmutli = System.currentTimeMillis()-15000L;
        this.morepacketblacklist = false;
        this.lastpacket = 0;
        this.morepackets = new AbstractMap.SimpleEntry<>(0, System.currentTimeMillis());
        this.togglesneaklastsec = 0;
        this.morepacketsblacklist2 = false;
        this.movePackets = new LimitedMap<>(450);
        this.blockbreakslastsec = 0;
        this.lastitemwsitch = System.currentTimeMillis()-15000L;
        this.lastyaw = 1000;
        this.lastpitch = 1000;
        this.lastcheststealer = System.currentTimeMillis()-15000L;
        this.cheststealercon = new ArrayList<>();
        this.lastspeedloc = null;
        this.lastfastladderlongY = -1;
        this.fastladderlongstart = System.currentTimeMillis()-15000L;
        this.fastladderstart = null;
        this.lastdead = System.currentTimeMillis()-15000L;
        this.chats10sec = 0;
        this.commands10sec = 0;
        this.startbreak = null;
        this.startbreaktime = 0;
        this.ticksonground = 0;
        this.lastrightclick = System.currentTimeMillis()-15000L;
        this.falsepositives = new FalsePositive.FalsePositiveChecks();
        this.stepjump = 0;
        this.stepjump2 = -1;
        this.laststep = null;
        this.accuracy = new HashMap<>();
        this.glide = -1;
        this.vehicledif = -1;
        this.vehicley = -1;
        this.lastachivementopeninv = System.currentTimeMillis()-15000L;
        this.hitboxyaw = 0;
        this.lastattaked = null;
        this.bot = null;
        this.packethit = 0;
        this.packetswing = 0;
        this.fightSpeed = new ArrayList<>();
        this.aimborloc = null;
        this.aimbotdiff = -1;
        this.lastticklocation = null;
        this.lastongroundtime = System.currentTimeMillis()-15000L;
        this.godAcc = 0;
        this.godlastDamageTick = 0;
        this.godlastNoDamageTicks = 0;
        this.godhealth = 0;
        this.godhealthtick = 0;
        generals = new General.GeneralValues();
        asyncGenerals = new AsyncGeneral.AsyncGeneralValues();
        newstep = 0;
        hittetEntitys = new HashMap<>();
        lastRegen = -1;
        lastRegenMagic = -1;
        lastRegenPeaceful = -1;
        snappyRotate = -1;
        critHight = -1;
        lastPhaseOkay = null;
        flyData = new FlyData();
        lastFlagloc = null;
        lastPattern = null;
    }

    /**
     * Getter for falsepositives
     *
     * @return value of falsepositives
     */
    public FalsePositive.FalsePositiveChecks getFalsepositives() {
        return falsepositives;
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

    public Location getLastFlagloc() {
        return lastFlagloc;
    }

    public void setLastFlagloc(Location lastFlagloc) {
        this.lastFlagloc = lastFlagloc;
    }

    public double getNewstep() {
        return newstep;
    }

    public void setNewstep(double newstep) {
        this.newstep = newstep;
    }

    public double getGodhealth() {
        return godhealth;
    }

    public void setGodhealth(double godhealth) {
        this.godhealth = godhealth;
    }

    public int getGodhealthtick() {
        return godhealthtick;
    }

    public void setGodhealthtick(int godhealthtick) {
        this.godhealthtick = godhealthtick;
    }

    public int getGodlastDamageTick() {
        return godlastDamageTick;
    }

    public void setGodlastDamageTick(int godlastDamageTick) {
        this.godlastDamageTick = godlastDamageTick;
    }

    public int getGodlastNoDamageTicks() {
        return godlastNoDamageTicks;
    }

    public void setGodlastNoDamageTicks(int godlastNoDamageTicks) {
        this.godlastNoDamageTicks = godlastNoDamageTicks;
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

    public long getLastongroundtime() {
        return lastongroundtime;
    }

    public void setLastongroundtime(long lastongroundtime) {
        this.lastongroundtime = lastongroundtime;
    }

    public Location getLastticklocation() {
        return lastticklocation;
    }

    public void setLastticklocation(Location lastticklocation) {
        this.lastticklocation = lastticklocation;
    }

    public Location getAimborloc() {
        return aimborloc;
    }

    public void setAimborloc(Location aimborloc) {
        this.aimborloc = aimborloc;
    }

    public double getAimbotdiff() {
        return aimbotdiff;
    }

    public void setAimbotdiff(double aimbotdiff) {
        this.aimbotdiff = aimbotdiff;
    }

    public ArrayList<Long> getFightSpeed() {
        return fightSpeed;
    }

    public int getPackethit() {
        return packethit;
    }

    public void setPackethit(int packethit) {
        this.packethit = packethit;
    }

    public int getPacketswing() {
        return packetswing;
    }

    public void setPacketswing(int packetswing) {
        this.packetswing = packetswing;
    }

    public EntityPlayer getBot() {
        return bot;
    }

    public void setBot(EntityPlayer bot) {
        this.bot = bot;
    }

    public Player getLastattaked() {
        return lastattaked;
    }

    public void setLastattaked(Player lastattaked) {
        this.lastattaked = lastattaked;
    }

    public float getHitboxyaw() {
        return hitboxyaw;
    }

    public void setHitboxyaw(float hitboxyaw) {
        this.hitboxyaw = hitboxyaw;
    }

    public long getLastachivementopeninv() {
        return lastachivementopeninv;
    }

    public void setLastachivementopeninv(long lastachivementopeninv) {
        this.lastachivementopeninv = lastachivementopeninv;
    }

    public double getVehicley() {
        return vehicley;
    }

    public void setVehicley(double vehicley) {
        this.vehicley = vehicley;
    }

    public int getVehicledif() {
        return vehicledif;
    }

    public void setVehicledif(int vehicledif) {
        this.vehicledif = vehicledif;
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

    public Location getLaststep() {
        return laststep;
    }

    public void setLaststep(Location laststep) {
        this.laststep = laststep;
    }

    public int getStepjump2() {
        return stepjump2;
    }

    public void setStepjump2(int stepjump2) {
        this.stepjump2 = stepjump2;
    }

    public double getStepjump() {
        return stepjump;
    }

    public void setStepjump(double stepjump) {
        this.stepjump = stepjump;
    }

    public long getLastrightclick() {
        return lastrightclick;
    }

    public void setLastrightclick(long lastrightclick) {
        this.lastrightclick = lastrightclick;
    }

    public int getTicksonground() {
        return ticksonground;
    }

    public void setTicksonground(int ticksonground) {
        this.ticksonground = ticksonground;
    }

    public Block getStartbreak() {
        return startbreak;
    }

    public void setStartbreak(Block startbreak) {
        this.startbreak = startbreak;
    }

    public long getStartbreaktime() {
        return startbreaktime;
    }

    public void setStartbreaktime(long startbreaktime) {
        this.startbreaktime = startbreaktime;
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

    public long getLastdead() {
        return lastdead;
    }

    public void setLastdead(long lastdead) {
        this.lastdead = lastdead;
    }

    public Location getFastladderstart() {
        return fastladderstart;
    }

    public void setFastladderstart(Location fastladderstart) {
        this.fastladderstart = fastladderstart;
    }

    public double getLastfastladderlongY() {
        return lastfastladderlongY;
    }

    public void setLastfastladderlongY(double lastfastladderlongY) {
        this.lastfastladderlongY = lastfastladderlongY;
    }

    public long getFastladderlongstart() {
        return fastladderlongstart;
    }

    public void setFastladderlongstart(long fastladderlongstart) {
        this.fastladderlongstart = fastladderlongstart;
    }

    public Location getLastspeedloc() {
        return lastspeedloc;
    }

    public void setLastspeedloc(Location lastspeedloc) {
        this.lastspeedloc = lastspeedloc;
    }

    public ArrayList<Long> getCheststealercon() {
        return cheststealercon;
    }

    public void setCheststealercon(ArrayList<Long> cheststealercon) {
        this.cheststealercon = cheststealercon;
    }

    public long getLastcheststealer() {
        return lastcheststealer;
    }

    public void setLastcheststealer(long lastcheststealer) {
        this.lastcheststealer = lastcheststealer;
    }

    public float getLastpitch() {
        return lastpitch;
    }

    public void setLastpitch(float lastpitch) {
        this.lastpitch = lastpitch;
    }

    public float getLastyaw() {
        return lastyaw;
    }

    public void setLastyaw(float lastyaw) {
        this.lastyaw = lastyaw;
    }

    public long getLastitemwsitch() {
        return lastitemwsitch;
    }

    public void setLastitemwsitch(long lastitemwsitch) {
        this.lastitemwsitch = lastitemwsitch;
    }

    public int getBlockbreakslastsec() {
        return blockbreakslastsec;
    }

    public void setBlockbreakslastsec(int blockbreakslastsec) {
        this.blockbreakslastsec = blockbreakslastsec;
    }

    public boolean isMorepacketsblacklist2() {
        return morepacketsblacklist2;
    }

    public void setMorepacketsblacklist2(boolean morepacketsblacklist2) {
        this.morepacketsblacklist2 = morepacketsblacklist2;
    }

    public int getTogglesneaklastsec() {
        return togglesneaklastsec;
    }

    public void setTogglesneaklastsec(int togglesneaklastsec) {
        this.togglesneaklastsec = togglesneaklastsec;
    }

    public boolean isMorepacketblacklist() {
        return morepacketblacklist;
    }

    public void setMorepacketblacklist(boolean morepacketblacklist) {
        this.morepacketblacklist = morepacketblacklist;
    }

    public Map.Entry<Integer, Long> getMorepackets() {
        return morepackets;
    }

    public void setMorepackets(Map.Entry<Integer, Long> morepackets) {
        this.morepackets = morepackets;
    }

    public long getLastpacket() {
        return lastpacket;
    }

    public void setLastpacket(long lastpacket) {
        this.lastpacket = lastpacket;
    }

    public int getLasthitentity() {
        return lasthitentity;
    }

    public void setLasthitentity(int lasthitentity) {
        this.lasthitentity = lasthitentity;
    }

    public long getLasthitmutli() {
        return lasthitmutli;
    }

    public void setLasthitmutli(long lasthitmutli) {
        this.lasthitmutli = lasthitmutli;
    }

    public long getLasteat() {
        return lasteat;
    }

    public void setLasteat(long lasteat) {
        this.lasteat = lasteat;
    }

    public long getLastbowshot() {
        return lastbowshot;
    }

    public void setLastbowshot(long lastbowshot) {
        this.lastbowshot = lastbowshot;
    }

    public int getItemswinginonesecond() {
        return itemswinginonesecond;
    }

    public void setItemswinginonesecond(int itemswinginonesecond) {
        this.itemswinginonesecond = itemswinginonesecond;
    }

    public int getItemshwitchinonesecond() {
        return itemshwitchinonesecond;
    }

    public void setItemshwitchinonesecond(int itemshwitchinonesecond) {
        this.itemshwitchinonesecond = itemshwitchinonesecond;
    }

    public boolean isArmSwung() {
        return armSwung;
    }

    public void setArmSwung(boolean armSwung) {
        this.armSwung = armSwung;
    }

    public int getBlockplacelastsecond() {
        return blockplacelastsecond;
    }

    public void setBlockplacelastsecond(int blockplacelastsecond) {
        this.blockplacelastsecond = blockplacelastsecond;
    }

    public int getInteractslastsecond() {
        return interactslastsecond;
    }

    public void setInteractslastsecond(int interactslastsecond) {
        this.interactslastsecond = interactslastsecond;
    }

    public int getHitslastsecond() {
        return hitslastsecond;
    }

    public void setHitslastsecond(int hitslastsecond) {
        this.hitslastsecond = hitslastsecond;
    }

    public UUID getUuid() {
        return uuid;
    }

    public long getLastchat() {
        return lastchat;
    }

    public void setLastchat(long lastchat) {
        this.lastchat = lastchat;
    }

    public long getLastcommand() {
        return lastcommand;
    }

    public void setLastcommand(long lastcommand) {
        this.lastcommand = lastcommand;
    }

    public long getLastnbtpacket() {
        return lastnbtpacket;
    }

    public void setLastnbtpacket(long lastnbtpacket) {
        this.lastnbtpacket = lastnbtpacket;
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

    public double getCritHight() {
        return critHight;
    }

    public void setCritHight(double critHight) {
        this.critHight = critHight;
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
}
