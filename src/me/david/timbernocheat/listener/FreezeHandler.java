package me.david.timbernocheat.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.UUID;

public class FreezeHandler implements Listener {

    private HashMap<UUID, Long> freezePlayers = new HashMap<>();

    public void freeze(final UUID uuid, final long delay){
        freezeTo(uuid, System.currentTimeMillis()+delay);
    }

    public void freezeTo(final UUID uuid, final long time){
        freezePlayers.put(uuid, time);
    }

    public void freeze(final Player player, final long delay){
        freeze(player.getUniqueId(), delay);
    }

    public void freezeTo(final Player player, final long time){
        freezeTo(player.getUniqueId(), time);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onMove(final PlayerMoveEvent event){
        final UUID uuid = event.getPlayer().getUniqueId();
        Long time = freezePlayers.get(uuid);
        if(time != null){
            if(time < System.currentTimeMillis()) freezePlayers.remove(uuid);
            else event.setCancelled(true);
        }
    }
}
