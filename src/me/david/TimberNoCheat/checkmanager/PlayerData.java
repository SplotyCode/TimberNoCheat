package me.david.TimberNoCheat.checkmanager;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public class PlayerData {
    private String uuid;
    private long lastchat;
    private ArrayList<String> messages;
    private long lastcommand;
    private long lastnbtpacket;
    private int interactslastsecond;
    private int hitslastsecond;
    private int blockplacelastsecond;
    private long lastflagmessage;
    private boolean shoudswing;
    private long jesus;
    private int itemswinginonesecond;
    private int itemshwitchinonesecond;
    private long lastbowshot;
    private long lasteat;
    //private long lastreach;
    //private ArrayList<Double> reaches;
    private long lastregen;
    private long lastmove;
    private long lastrealmove;
    private int lasthitentity;
    private long lasthitmutli;
    private long lastpacket;
    private boolean morepacketblacklist;
    private Map.Entry<Integer, Long> morepackets;
   // private long lasttimer;
    private ArrayList<Long> timerms;
    private int togglesneaklastsec;
    /*private Map.Entry<Integer, Long> speedticks;
    private Map.Entry<Integer, Long> speedticksflagt;
    private boolean firstspeed;
    private boolean firstspeedflag;*/
   // private int timerflag;
    private boolean morepacketsblacklist2;
   // private int fly_nodown;
    private int moveslastticks;
    private int blockbreakslastsec;
    private long lastitemwsitch;
    private float lastpitch;
    private float lastyaw;
    private long lastinv;
    private long lastcheststealer;
    private ArrayList<Long> cheststealercon;
    private Location lastspeedloc;
    private double lastfastladderlongZ;
    private long fastladderlongstart;
    private Location fastladderstart;
    private long lastdead;
    private int commands10sec;
    private int chats10sec;
    private double jumpspeed;
    private long startslowdown;
    private Location startsowdownloc;
    private Block startbreak;
    private long startbreaktime;
    private int ticksonground;
    private long lastrightclick;

    public PlayerData(String uuid) {
        this.uuid = uuid;
        this.lastchat = System.currentTimeMillis()-15000L;
        this.messages = new ArrayList<String>();
        this.lastcommand = System.currentTimeMillis()-15000L;
        this.lastnbtpacket = System.currentTimeMillis()-15000L;
        this.interactslastsecond = 0;
        this.hitslastsecond = 0;
        this.blockplacelastsecond = 0;
        this.lastflagmessage = System.currentTimeMillis()-15000L;
        this.shoudswing = false;
        this.jesus = System.currentTimeMillis();
        this.itemswinginonesecond = 0;
        this.itemshwitchinonesecond = 0;
        this.lastbowshot = System.currentTimeMillis()-15000L;
        this.lasteat = System.currentTimeMillis()-15000L;
        //this.lastreach = System.currentTimeMillis();
        //this.reaches = new ArrayList<Double>();
        this.lastregen = System.currentTimeMillis()-15000L;
        this.lastmove = System.currentTimeMillis()-15000L;
        this.lastrealmove = System.currentTimeMillis()-15000L;
        this.lasthitentity = 0;
        this.lasthitmutli = System.currentTimeMillis()-15000L;
        this.morepacketblacklist = false;
        this.lastpacket = 0;
        this.morepackets = new AbstractMap.SimpleEntry<Integer, Long>(0, System.currentTimeMillis());
        //this.lasttimer = System.currentTimeMillis()-15000L;
        this.timerms = new ArrayList<Long>();
        this.togglesneaklastsec = 0;
        /*this.speedticks = new AbstractMap.SimpleEntry<Integer, Long>(0, System.currentTimeMillis());
        this.speedticksflagt = new AbstractMap.SimpleEntry<Integer, Long>(0, System.currentTimeMillis());
        this.firstspeed = true;
        this.firstspeedflag = true;*/
        //this.timerflag = 0;
        this.morepacketsblacklist2 = false;
        //this.fly_nodown = 0;
        this.moveslastticks = 0;
        this.blockbreakslastsec = 0;
        this.lastitemwsitch = System.currentTimeMillis()-15000L;
        this.lastyaw = 1000;
        this.lastpitch = 1000;
        this.lastinv = System.currentTimeMillis()-15000L;
        this.lastcheststealer = System.currentTimeMillis()-15000L;
        this.cheststealercon = new ArrayList<Long>();
        this.lastspeedloc = null;
        this.lastfastladderlongZ = -1;
        this.fastladderlongstart = System.currentTimeMillis()-15000L;
        this.fastladderstart = null;
        this.lastdead = System.currentTimeMillis()-15000L;
        this.chats10sec = 0;
        this.commands10sec = 0;
        this.jumpspeed = 0;
        this.startslowdown = 0;
        this.startsowdownloc = null;
        this.startbreak = null;
        this.startbreaktime = 0;
        this.ticksonground = 0;
        this.lastrightclick = System.currentTimeMillis()-15000L;
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

    public long getStartslowdown() {
        return startslowdown;
    }

    public void setStartslowdown(long startslowdown) {
        this.startslowdown = startslowdown;
    }

    public Location getStartsowdownloc() {
        return startsowdownloc;
    }

    public void setStartsowdownloc(Location startsowdownloc) {
        this.startsowdownloc = startsowdownloc;
    }

    public double getJumpspeed() {
        return jumpspeed;
    }

    public void setJumpspeed(double jumpspeed) {
        this.jumpspeed = jumpspeed;
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

    public double getLastfastladderlongZ() {
        return lastfastladderlongZ;
    }

    public void setLastfastladderlongZ(double lastfastladderlongZ) {
        this.lastfastladderlongZ = lastfastladderlongZ;
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

    public long getLastinv() {
        return lastinv;
    }

    public void setLastinv(long lastinv) {
        this.lastinv = lastinv;
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

    public int getMoveslastticks() {
        return moveslastticks;
    }

    public void setMoveslastticks(int moveslastticks) {
        this.moveslastticks = moveslastticks;
    }

    /*public int getFly_nodown() {
        return fly_nodown;
    }

    public void setFly_nodown(int fly_nodown) {
        this.fly_nodown = fly_nodown;
    }*/

    public boolean isMorepacketsblacklist2() {
        return morepacketsblacklist2;
    }

    public void setMorepacketsblacklist2(boolean morepacketsblacklist2) {
        this.morepacketsblacklist2 = morepacketsblacklist2;
    }

    /*public int getTimerflag() {
        return timerflag;
    }

    public void setTimerflag(int timerflag) {
        this.timerflag = timerflag;
    }
    */
    /*public Map.Entry<Integer, Long> getSpeedticks() {
        return speedticks;
    }

    public boolean isFirstspeed() {
        return firstspeed;
    }

    public void setFirstspeed(boolean firstspeed) {
        this.firstspeed = firstspeed;
    }

    public boolean isFirstspeedflag() {
        return firstspeedflag;
    }

    public void setFirstspeedflag(boolean firstspeedflag) {
        this.firstspeedflag = firstspeedflag;
    }

    public void setSpeedticks(Map.Entry<Integer, Long> speedticks) {
        this.speedticks = speedticks;
    }

    public Map.Entry<Integer, Long> getSpeedticksflagt() {
        return speedticksflagt;
    }

    public void setSpeedticksflagt(Map.Entry<Integer, Long> speedticksflagt) {
        this.speedticksflagt = speedticksflagt;
    }*/

    public int getTogglesneaklastsec() {
        return togglesneaklastsec;
    }

    public void setTogglesneaklastsec(int togglesneaklastsec) {
        this.togglesneaklastsec = togglesneaklastsec;
    }

    /*public long getLasttimer() {
        return lasttimer;
    }

    public void setLasttimer(long lasttimer) {
        this.lasttimer = lasttimer;
    }*/

    public ArrayList<Long> getTimerms() {
        return timerms;
    }

    public void setTimerms(ArrayList<Long> timerms) {
        this.timerms = timerms;
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

    public long getLastmove() {
        return lastmove;
    }

    public void setLastmove(long lastmove) {
        this.lastmove = lastmove;
    }

    public long getLastrealmove() {
        return lastrealmove;
    }

    public void setLastrealmove(long lastrealmove) {
        this.lastrealmove = lastrealmove;
    }

    public long getLastregen() {
        return lastregen;
    }

    public void setLastregen(long lastregen) {
        this.lastregen = lastregen;
    }

    /*public long getLastreach() {
        return lastreach;
    }

    public void setLastreach(long lastreach) {
        this.lastreach = lastreach;
    }

    public ArrayList<Double> getReaches() {
        return reaches;
    }

    public void setReaches(ArrayList<Double> reaches) {
        this.reaches = reaches;
    }*/

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

    public long getJesus() {
        return jesus;
    }

    public void setJesus(long jesus) {
        this.jesus = jesus;
    }

    public boolean isShoudswing() {
        return shoudswing;
    }

    public void setShoudswing(boolean shoudswing) {
        this.shoudswing = shoudswing;
    }

    public long getLastflagmessage() {
        return lastflagmessage;
    }

    public void setLastflagmessage(long lastflagmessage) {
        this.lastflagmessage = lastflagmessage;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getLastchat() {
        return lastchat;
    }

    public void setLastchat(long lastchat) {
        this.lastchat = lastchat;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
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
}
