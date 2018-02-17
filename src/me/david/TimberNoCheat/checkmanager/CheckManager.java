package me.david.TimberNoCheat.checkmanager;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkes.chat.Bots;
import me.david.TimberNoCheat.checkes.clientchanel.Vape;
import me.david.TimberNoCheat.checkes.combat.*;
import me.david.TimberNoCheat.checkes.interact.*;
import me.david.TimberNoCheat.checkes.chat.*;
import me.david.TimberNoCheat.checkes.clientchanel.LabyMod;
import me.david.TimberNoCheat.checkes.clientchanel.Other;
import me.david.TimberNoCheat.checkes.clientchanel.Shematica;
import me.david.TimberNoCheat.checkes.exploits.*;
import me.david.TimberNoCheat.checkes.movement.*;
import me.david.TimberNoCheat.checkes.other.*;
import me.david.TimberNoCheat.checkes.player.*;
import me.david.TimberNoCheat.debug.obj.DebugPlayerDataList;
import me.david.TimberNoCheat.debug.Debuggers;
import me.david.TimberNoCheat.runnable.TickCountTimer;
import me.david.TimberNoCheat.runnable.Tps;
import me.david.TimberNoCheat.config.Permissions;
import me.david.api.anotations.NotNull;
import me.david.api.anotations.Nullable;
import me.david.api.utils.StringUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.logging.Level;

public class CheckManager {

    /* List of active all Checks */
    private ArrayList<Check> checks = new ArrayList<Check>();
    /* List of disabled Checks (only via config) */
    private ArrayList<Check> disabledChecks = new ArrayList<>();
    /*
     * List of all PlayerDate
     * TNC Never write PlayerData to Disk
     * When a Player Disconnects TNC Will delete his PlayerData
     */
    private ArrayList<PlayerData> playerdata = new DebugPlayerDataList();

    /*
     * Registering/Starting checks
     */
    public void loadchecks(){
        checks.clear();
        disabledChecks.clear();
        register(new Address());
        register(new Delay());
        register(new Sign());
        register(new Spamming());
        register(new CommandDelay());
        register(new Book());
        register(new LabyMod());
        register(new Shematica());
        register(new Other());
        register(new MCLeaks());
        register(new BlackList());
        register(new BreakCovered());
        register(new NBT());
        register(new Packet_Flood());
        register(new Channels());
        register(new Grifing());
        register(new Charaters());
        register(new DamageIndicator());
        register(new Derb());
        register(new FightSpeed());
        register(new FastPlace());
        register(new Blink());
        register(new NoSwing());
        register(new BedLeave());
        register(new Clip());
        register(new Jesus());
        register(new Crasher());
        register(new FastBow());
        register(new FastEat());
        register(new Interact());
        register(new Step());
        register(new Fly());
        register(new Killaura());
        register(new SelfHit());
        register(new Regen());
        register(new Speed());
        register(new Inventory());
        register(new MorePackets());
        register(new PingSpoof());
        register(new SkinBlinker());
        register(new BadPackets());
        register(new Nuker());
        register(new FastSwitch());
        register(new Rotate());
        register(new ChestStealer());
        register(new FastLadder());
        register(new Respawn());
        register(new VanillaBug());
        register(new FreeCam());
        register(new NoFall());
        register(new BreakReach());
        register(new PlaceReach());
        register(new InteractReach());
        register(new Scaffold());
        register(new Break());
        register(new RightClickTimer());
        register(new AirPlace());
        register(new Criticals());
        register(new Clock());
        register(new Accuracy());
        register(new AntiESP());
        register(new VehicleMove());
        register(new Bots());
        register(new Similarity());
        register(new Caps());
        register(new CharSpamm());
        register(new Vape());
        register(new HitBoxes());
        register(new AuraBots());
        register(new Velocity());
        register(new GodMode());
        register(new Elytra());
    }

    /* Loading all Checks when this Objects gets createt (usually on the start of TNC */
    public CheckManager(){
        loadchecks();
        new TickCountTimer();
    }

    @Deprecated
    public void execute(String cmd, String player){
        if(!cmd.equals("")){
            cmd = cmd.replaceAll("/", "");
            cmd = cmd.replaceAll("%player%", player);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
        }
    }

    /*
     * Checks if an Player if Valid for Checking.
     * If there is no PlayerData for the Player TNC will create one
     * To prevent NullPointerExeptions call this method before checking players (on events, schedulers etc)
     */
    public boolean isvalid_create(Player p){
        if(TimberNoCheat.instance.permissioncache.hasPermission(p, Permissions.NOTCHECKT)) {
            TimberNoCheat.instance.getDebuger().sendDebug(Debuggers.PLAYERDATA_USE, "Access granted: " + p.getName());
            return false;
        }
        if(getPlayerdata(p) == null) playerdata.add(new PlayerData(p.getUniqueId()));
        TimberNoCheat.instance.getDebuger().sendDebug(Debuggers.PLAYERDATA_USE, "Data Okay: " + p.getName());

        return true;
    }

    /*
     * Returns the PlayerData of an Player
     * If there is no PlayerData bind to that Player return null
     */
    public @Nullable PlayerData getPlayerdata(@NotNull Player p){
        for(PlayerData playerdata : (ArrayList<PlayerData>)playerdata.clone())
            if(playerdata.getUuid().equals(p.getUniqueId()))
                return playerdata;
        return null;
    }

    /* Registers an Check to TNC and Bukkit */
    //TODO: Performance Load the YamlConfiguration only once
    public void register(Check check){
        if(disabledChecks.contains(check) || checks.contains(check))
            throw new IllegalStateException("Try to register a Plugin that is already Registered/Config Blacklisted!");
        if(!YamlConfiguration.loadConfiguration(TimberNoCheat.instance.config).getBoolean(check.getName().toLowerCase() + ".enable")) {
            disabledChecks.add(check);
            return;
        }
        checks.add(check);
        TimberNoCheat.instance.getServer().getPluginManager().registerEvents(check, TimberNoCheat.instance);
    }


    public void unregister(Check check) {
        check.disable();
        checks.remove(check);
        HandlerList.unregisterAll(check);
        check.disablelisteners();
        check.disabletasks();
    }

    /* Methods for Notifying Players, Console about Violation Updates */
    public void notify(Check check, Player p, String... args){
        notify(p, "§bName: §6" + check.getCategory().name() + "_" + check.getName() + " §bPlayer: §6" + p.getName() + " §bTPS: " + gettpscolor() + " §bPING: " + getpingcolor(p) + StringUtil.toString(args, ""));
    }

    public void notify(Check check, String arg, Player p, String...args){
        notify(p, "§bName: §6" + check.getCategory().name() + "_" + (check.isChild()?check.getParent().getName():check.getName()) + " §bPlayer: §6" + p.getName() + " §bTPS: " + gettpscolor() + " §bPING: " + getpingcolor(p) + arg + (check.isChild()?"§bModule: §6" + check.getName():"") + StringUtil.toString(args, ""));
    }

    public void notity(Check check, double vio, Location location){
        String message = "§bName: §6" + check.getCategory().name() + "_" + check.getName() + " §bTPS: " + gettpscolor() + " §6LEVEL: §b" + vio;
        TextComponent text = new TextComponent(message);
        text.setBold(true);
        text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.DARK_PURPLE + "Tleportieren").create()));
        text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "minecraft:tp " + location.getX()));
        TimberNoCheat.instance.permissioncache.sendAll(Permissions.NOTITY, TimberNoCheat.instance.prefix + message);
        TimberNoCheat.instance.getLogger().log(Level.INFO, message.replace("§", "&"));
    }

    public void notify(Player p, String message){
        TimberNoCheat.instance.permissioncache.sendAll(Permissions.NOTITY, message);
        TimberNoCheat.instance.getLogger().log(Level.INFO, message);
    }

    /* Get Server Tps with Colour*/
    public String gettpscolor(){
        double tps = Tps.getTPS();
        if(tps >= 20L) return "§a20";
        else if(tps >= 18L) return "§2" + Math.round(tps);
        else if(tps > 16L) return "§e" + Math.round(tps);
        return "§c" + Math.round(tps);
    }

    /* Get Player Ping with Colour*/
    public String getpingcolor(Player p){
        int ping = getping(p);
        if(ping < 80) return "§a"+ping;
        else if(ping < 160) return "§e"+ping;
        return "§c"+ ping;
    }

    /* Gets the Player Ping */
    public int getping(Player p){
        return ((CraftPlayer)p).getHandle().ping<0?0:((CraftPlayer)p).getHandle().ping;
    }

    /*
     * Returns the Check from the Check Name
     * If there is no Check with that Name return null
     */
    public @Nullable Check getCheckbyName(String name){
        for(Check c : checks)
            if(c.getName().equals(name))
                return c;
        return null;
    }

    public ArrayList<Check> getChecks() {
        return checks;
    }

    public ArrayList<Check> getDisabledChecks() {
        return disabledChecks;
    }

    public ArrayList<PlayerData> getPlayerdata() {
        return playerdata;
    }

    public ArrayList<Check> getAllChecks(){
        ArrayList<Check> list = ((ArrayList<Check>) TimberNoCheat.checkmanager.getChecks().clone());
        list.addAll(TimberNoCheat.checkmanager.getDisabledChecks());
        return list;
    }

    public Check getCheckbyString(String name){
        boolean child = name.contains("_");
        String[] split = name.split("_");
        Check mother = getCheckbyName(split[0]);
        return child?mother.getChildbyString(split[1]):mother;
    }
}
