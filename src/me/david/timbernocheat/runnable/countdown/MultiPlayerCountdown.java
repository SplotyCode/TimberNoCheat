package me.david.timbernocheat.runnable.countdown;

import me.david.timbernocheat.TimberNoCheat;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MultiPlayerCountdown extends Countdown implements Listener {

    private HashMap<UUID, Integer> playerCountdown = new HashMap<>();
    private final boolean resetOnLeave;

    public MultiPlayerCountdown(boolean resetOnLeave, Runnable finished, int ticks) {
        super(finished, ticks);
        this.resetOnLeave = resetOnLeave;
        if(resetOnLeave) Bukkit.getPluginManager().registerEvents(this, TimberNoCheat.getInstance());
        setUpdate(() -> {
            for(Map.Entry<UUID, Integer> player : playerCountdown.entrySet()){
                player.setValue(player.getValue()-1);
                if(player.getValue() < 0) playerCountdown.remove(player.getKey());
            }
            if(playerCountdown.isEmpty()) finished.run();
        });
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLeave(final PlayerQuitEvent event){
        final UUID uuid = event.getPlayer().getUniqueId();
        if(resetOnLeave && playerCountdown.containsKey(uuid))
            playerCountdown.remove(uuid);
    }

    public boolean isResetOnLeave() {
        return resetOnLeave;
    }

    public HashMap<UUID, Integer> getPlayerCountdown() {
        return playerCountdown;
    }
}
