package me.david.TimberNoCheat.checkmanager;

import me.david.TimberNoCheat.TimberNoCheat;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Check implements Listener{
    private String name;
    private Category category;
    private long viodelay;
    private int viofornotify;
    private HashMap<Player, HashMap<Long, Integer>> viochache = new HashMap<Player, HashMap<Long, Integer>>();
    private HashMap<Player, Integer> violations = new HashMap<Player, Integer>();
    private boolean resetafter;

    public Check(String name, Category category) {
        this.name = name;
        this.category = category;
        this.resetafter = false;
    }

    public void updatevio(Check c, Player p, int vio, String... other){
        if(viodelay != 0 && viochache.containsKey(p) && violations.containsKey(p)){
            for(Map.Entry<Long, Integer> v : viochache.get(p).entrySet()){
                long delay = System.currentTimeMillis()-v.getKey();
                if(delay>viodelay){
                    violations.put(p, violations.get(p)-v.getValue());
                    viochache.get(p).remove(v.getKey());
                }
            }
        }
        if(violations.containsKey(p)){
            violations.put(p, violations.get(p)+vio);
        }else{
            violations.put(p, vio);
        }
        if(violations.get(p) >= viofornotify){
            TimberNoCheat.checkmanager.notify(c, " §6LEVEL: §b" + violations.get(p), p, other);
            if(resetafter)violations.put(p, 0);
        }
    }

    public long getViodelay() {
        return viodelay;
    }

    public void setViodelay(long viodelay) {
        this.viodelay = viodelay;
    }

    public int getViofornotify() {
        return viofornotify;
    }

    public void setViofornotify(int viofornotify) {
        this.viofornotify = viofornotify;
    }

    public HashMap<Player, HashMap<Long, Integer>> getViochache() {
        return viochache;
    }

    public void setViochache(HashMap<Player, HashMap<Long, Integer>> viochache) {
        this.viochache = viochache;
    }

    public HashMap<Player, Integer> getViolations() {
        return violations;
    }

    public void setViolations(HashMap<Player, Integer> violations) {
        this.violations = violations;
    }

    public String getName() {
        return name;
    }

    public void disable(){}

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
