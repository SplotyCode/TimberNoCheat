package me.david.TimberNoCheat.checktools;

import me.ezjamo.helios.update.UpdateType;
import me.ezjamo.helios.update.events.UpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Velocity implements Listener{
    public static HashMap<UUID, Map.Entry<Long, Vector>> velocity = new HashMap();
    @EventHandler
    public void Velocity(PlayerVelocityEvent event) {
        this.velocity.put(event.getPlayer().getUniqueId(), new AbstractMap.SimpleEntry(Long.valueOf(System.currentTimeMillis()), event.getVelocity()));
    }
    @EventHandler
    public void Update(UpdateEvent event) {
        if (event.getType().equals(UpdateType.TICK)) {
            for(Map.Entry<UUID, Map.Entry<Long, Vector>> p : velocity.entrySet()){
                UUID uid = p.getKey();
                Player player = Bukkit.getPlayer(uid);
                Vector vec = p.getValue().getValue();
                if(p.getValue().getKey() + 500L <= System.currentTimeMillis()) {
                    double velY = vec.getY() * vec.getY();
                    double Y = player.getVelocity().getY() * player.getVelocity().getY();
                    if(Y < 0.02D || Y > velY * 3.0D) {
                        velocity.remove(uid);
                    }
                }
            }
        }
    }
}
