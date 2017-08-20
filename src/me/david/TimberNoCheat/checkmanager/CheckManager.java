package me.david.TimberNoCheat.checkmanager;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkes.fight.*;
import me.david.TimberNoCheat.checkes.interact.BreakCovered;
import me.david.TimberNoCheat.checkes.chat.*;
import me.david.TimberNoCheat.checkes.clientchanel.LabyMod;
import me.david.TimberNoCheat.checkes.clientchanel.Other;
import me.david.TimberNoCheat.checkes.clientchanel.Shematica;
import me.david.TimberNoCheat.checkes.exploits.*;
import me.david.TimberNoCheat.checkes.interact.FastPlace;
import me.david.TimberNoCheat.checkes.interact.Interact;
import me.david.TimberNoCheat.checkes.interact.NoSwing;
import me.david.TimberNoCheat.checkes.movement.*;
import me.david.TimberNoCheat.checkes.other.*;
import me.david.TimberNoCheat.checkes.player.*;
import me.david.TimberNoCheat.checktools.Tps;
import me.david.api.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.logging.Level;

public class CheckManager {
    public ArrayList<Check> checks = new ArrayList<Check>();
    public ArrayList<PlayerData> playerdata = new ArrayList<PlayerData>();
    public ArrayList<Player> notify = new ArrayList<Player>();

    public CheckManager(){
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
    }
    public void execute(String cmd, String player){
        if(!cmd.equals("")){
            cmd.replaceAll("/", "");
            cmd.replaceAll("%player%", player);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
        }
    }
    public boolean isvalid_create(Player p ){
        if(p.hasPermission("tnc.notcheck")){
            return false;
        }
        if(getPlayerdata(p) == null){
            playerdata.add(new PlayerData(p.getUniqueId().toString()));
        }
        return true;
    }
    public PlayerData getPlayerdata(Player p){
        for(PlayerData playerdata : playerdata){
            if(playerdata.getUuid().equals(p.getUniqueId().toString())){
                return playerdata;
            }
        }
        return null;
    }
    public void register(Check check){
        checks.add(check);
        TimberNoCheat.instance.getServer().getPluginManager().registerEvents(check, TimberNoCheat.instance);
    }
    public void notify(Check check, Player p, String... args){
        if(System.currentTimeMillis() - getPlayerdata(p).getLastflagmessage() < 1200L){
            return;
        }
        String message = "§bName: §6" + check.getCategory().name() + "_" + check.getName() + " §bPlayerName: §6" + p.getName() + " §bTPS: " + gettpscolor() + " §bPING: " + getpingcolor(p) + StringUtil.toString(args, "");
        for(Player p1 : notify){
            p1.sendMessage(TimberNoCheat.instance.prefix + message);
        }
        getPlayerdata(p).setLastflagmessage(System.currentTimeMillis());
        TimberNoCheat.instance.getLogger().log(Level.INFO, message.replace("§", "&"));
    }
    public void notify(Check check, String arg, Player p, String...args){
        if(System.currentTimeMillis() - getPlayerdata(p).getLastflagmessage() < 1200L){
            return;
        }
        String message = "§bName: §6" + check.getCategory().name() + "_" + check.getName() + " §bPlayerName: §6" + p.getName() + " §bTPS: " + gettpscolor() + " §bPING: " + getpingcolor(p) + arg + StringUtil.toString(args, "");
        for(Player p1 : notify){
            p1.sendMessage(TimberNoCheat.instance.prefix + message);
        }
        getPlayerdata(p).setLastflagmessage(System.currentTimeMillis());
        TimberNoCheat.instance.getLogger().log(Level.INFO, message.replace("§", "&"));
    }
    private String gettpscolor(){
        double tps = Tps.getTPS();
        if(tps >= 20L){
            return "§a20";
        }else if(tps >= 18L){
            return "§2" + Math.round(tps);
        }else if(tps > 16L){
            return "§e" + Math.round(tps);
        }
        return "§c" + Math.round(tps);
    }
    private String getpingcolor(Player p){
        int ping = ((CraftPlayer)p).getHandle().ping;
        if(ping < 80){
            return "§a"+ping;
        }else if(ping < 160){
            return "§e"+ping;
        }
        return "§c"+ (ping <= 0?"0":ping);
    }
    public int getping(Player p){
        return ((CraftPlayer)p).getHandle().ping<0?0:((CraftPlayer)p).getHandle().ping;
    }
    public Check getCheckbyName(String name){
        for(Check c : checks){
            if(c.getName().equals(name)){
                return c;
            }
        }
        return null;
    }
}
