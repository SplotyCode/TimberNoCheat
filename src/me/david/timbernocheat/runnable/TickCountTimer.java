package me.david.timbernocheat.runnable;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Check;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class TickCountTimer implements Runnable {

    public TickCountTimer(){
        Bukkit.getScheduler().runTaskTimer(TimberNoCheat.instance, this, 1, 1);
    }

    @Override
    public void run() {
        for(Check check : TimberNoCheat.checkmanager.getChecks()){
            for(Map.Entry<Player, HashMap<String, Double>> list : check.tickCounts.entrySet())
                for(Map.Entry<String, Double> values : list.getValue().entrySet())
                    check.tickCounts.get(list.getKey()).put(values.getKey(), values.getValue()-1 <= 0?0:values.getValue()-1);
        }
    }
}
