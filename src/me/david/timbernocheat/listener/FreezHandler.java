package me.david.timbernocheat.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.UUID;

public class FreezHandler implements Listener {

    private static HashMap<UUID, Long> freezPlayers = new HashMap<>();

    public static void freez(final UUID uuid, final long delay){
        freez(uuid, System.currentTimeMillis()+delay);
    }

    public static void freezTo(final UUID uuid, final long time){
        freezPlayers.put(uuid, time);
    }

    public static void freez(final Player player, final long delay){
        freez(player.getUniqueId(), delay);
    }

    public static void freezTo(final Player player, final long time){
        freezTo(player.getUniqueId(), time);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onMove(final PlayerMoveEvent event){
        final UUID uuid = event.getPlayer().getUniqueId();
        Long time = freezPlayers.get(uuid);
        if(time != null){
            if(time < System.currentTimeMillis()) freezPlayers.remove(uuid);
            else event.setCancelled(true);
        }
    }
}
