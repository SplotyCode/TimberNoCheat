package me.david.TimberNoCheat.CheckManager;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkes.chat.Address;
import me.david.TimberNoCheat.checkes.chat.Delay;
import me.david.TimberNoCheat.checkes.exploits.Sign;
import me.david.TimberNoCheat.checktools.Tps;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.logging.Level;

public class CheckManager {
    public ArrayList<Check> checks = new ArrayList<Check>();
    public ArrayList<PlayerData> playerdata = new ArrayList<PlayerData>();

    public CheckManager(){
        register(new Address());
        register(new Delay());
        register(new Sign());
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
        String message = "§bName: §6" + check.getCategory().name() + "_" + check.getName() + " §bName: §6" + p.getName() + " §bTPS: " + gettpscolor() + " §bPING: " + getpingcolor(p) + args.toString();
        Bukkit.broadcast(TimberNoCheat.instance.prefix + message, "tnc.notify");
        TimberNoCheat.instance.getLogger().log(Level.INFO, message);
    }
    private String gettpscolor(){
        double tps = Tps.getTPS();
        if(tps == 20){
            return "§a20";
        }else if(tps >= 18){
            return String.valueOf(Math.round(tps));
        }else if(tps > 16){
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
    public Check getCheckbyName(String name){
        for(Check c : checks){
            if(c.getName().equals(name)){
                return c;
            }
        }
        return null;
    }
}
