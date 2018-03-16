package me.david.timbernocheat.listener;

import org.bukkit.Location;
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

    @EventHandler(priority = EventPriority.HIGH)
    public void onMove(final PlayerMoveEvent event){
        final UUID uuid = event.getPlayer().getUniqueId();
        final Location to = event.getTo();
        final Location fr = event.getFrom();
        if(!(to.getX() == fr.getX() && to.getY() == fr.getY() && to.getZ() == to.getZ())) {
            if (isNotFreezed(uuid)) {
                if(freezePlayers.containsKey(uuid)) freezePlayers.remove(uuid);
            }else event.setCancelled(true);
        }
    }

    public boolean isNotFreezed(final UUID uuid) {
        Long time = freezePlayers.get(uuid);
        return time == null || time < System.currentTimeMillis();
    }

    public boolean isNotFreezed(final Player player){
        return isNotFreezed(player.getUniqueId());
    }
}
