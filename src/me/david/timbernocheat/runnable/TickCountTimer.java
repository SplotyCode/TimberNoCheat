package me.david.timbernocheat.runnable;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Check;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TickCountTimer implements ExceptionRunnable {

    public TickCountTimer(){
        new TimberScheduler("TickCountDecreaser", this).startTimer(1);
    }

    @Override
    public void run() {
        for(Check check : TimberNoCheat.getCheckManager().getChecks()){
            for(Map.Entry<UUID, HashMap<String, Double>> list : check.tickCounts.entrySet())
                for(Map.Entry<String, Double> values : list.getValue().entrySet())
                    check.tickCounts.get(list.getKey()).put(values.getKey(), values.getValue()-1 <= 0?0:values.getValue()-1);
        }
    }
}
