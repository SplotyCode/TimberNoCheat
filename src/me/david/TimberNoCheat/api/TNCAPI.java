package me.david.TimberNoCheat.api;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checktools.Tps;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class TNCAPI {

    public static double getTPS() {
        return Tps.getTPS();
    }
    public static double getTPS(int ticks) {
        return Tps.getTPS(ticks);
    }
    public static String getTPSColor(){
        return TimberNoCheat.checkmanager.gettpscolor();
    }
    public static int getPing(Player p){
        return TimberNoCheat.checkmanager.getping(p);
    }
    public static String getPingColor(Player p){
        return TimberNoCheat.checkmanager.getpingcolor(p);
    }
    public static void enablecheck(Check c){
        if(c == null){
            TimberNoCheat.instance.getLogger().log(Level.WARNING, "[API] Falsche API benutzung: Check darf nicht null sein");
            return;
        }
        TimberNoCheat.checkmanager.register(c);
    }
    public static void disablecheck(Check c){
        if(c == null){
            TimberNoCheat.instance.getLogger().log(Level.WARNING, "[API] Falsche API benutzung: Check darf nicht null sein");
            return;
        }
        if(!TimberNoCheat.checkmanager.checks.contains(c)){
            TimberNoCheat.instance.getLogger().log(Level.WARNING, "[API] Falsche API benutzung: Check muss enabled sein!");
            return;
        }
        TimberNoCheat.checkmanager.unregister(c);
    }
    public static void disablecheck(String s){
        disablecheck(TimberNoCheat.checkmanager.getCheckbyName(s));
    }
    public static Category[] getCategorys(){
        return Category.values();
    }
    public static Check getCheckbyName(String s){
        return TimberNoCheat.checkmanager.getCheckbyName(s);
    }
    public static double getAllViolations(Player p){
        double vio = 0;
        for(Check c : TimberNoCheat.checkmanager.checks)
            if(c.getViolations().containsKey(p))
                vio += c.getViolations().get(p);
        return vio;
    }
}
