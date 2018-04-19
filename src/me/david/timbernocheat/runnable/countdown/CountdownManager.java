package me.david.timbernocheat.runnable.countdown;

import me.david.timbernocheat.TimberNoCheat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CountdownManager implements Runnable {

    private HashMap<String, Countdown> countdowns = new HashMap<>();

    public CountdownManager(){
        Bukkit.getScheduler().runTaskTimer(TimberNoCheat.getInstance(), this, 1, 1);
    }

    public void setCountdown(String countdown, int ticks){
        if(countdowns.containsKey(countdown)){
            Countdown cd = countdowns.get(countdown);
            cd.setTicks(cd.getTicks()+ticks);
            countdowns.put(countdown, cd);
        }else countdowns.put(countdown, new Countdown(() -> {
            if(countdowns.containsKey(countdown))
                countdowns.remove(countdown);
        }, ticks));
    }

    public int getCountdown(String countdown){
        if(countdowns.containsKey(countdown)) return countdowns.get(countdown).getTicks();
        else return 0;
    }

    public boolean isFinished(String countdown){
        return getCountdown(countdown) == 0;
    }

    public void setCountdown(String countdown, int ticks, Player player, boolean resetOnLeave){
        if(!countdowns.containsKey(countdown))
            countdowns.put(countdown, new MultiPlayerCountdown(resetOnLeave, () -> {
                if(countdowns.containsKey(countdown))
                    countdowns.remove(countdown);
            }, ticks));
        MultiPlayerCountdown multiCountdown = (MultiPlayerCountdown) countdowns.get(countdown);
        if(multiCountdown.getPlayerCountdown().containsKey(player.getUniqueId()))
            multiCountdown.getPlayerCountdown().put(player.getUniqueId(), multiCountdown.getPlayerCountdown().get(player.getUniqueId())+ticks);
        else multiCountdown.getPlayerCountdown().put(player.getUniqueId(), ticks);
    }

    public int getCountdown(String countdown, Player player){
        if(countdowns.containsKey(countdown)) return ((MultiPlayerCountdown)countdowns.get(countdown)).getPlayerCountdown().get(player.getUniqueId());
        else return 0;
    }

    public boolean isFinished(String countdown, Player player){
        return getCountdown(countdown, player) == 0;
    }

    @Override
    public void run() {
        for(Countdown countdown : countdowns.values())
            countdown.getUpdate().run();
    }
}
