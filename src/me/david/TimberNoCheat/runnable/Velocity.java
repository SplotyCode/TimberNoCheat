package me.david.TimberNoCheat.runnable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/* Use FalsePositives in PlayerData instead of this*/
@Deprecated
public class Velocity implements Listener{

    @Deprecated
    public static HashMap<UUID, Map.Entry<Long, Vector>> velocity = new HashMap<UUID, Map.Entry<Long, Vector>>();

    @EventHandler
    public void onVelocity(PlayerVelocityEvent event) {
        velocity.put(event.getPlayer().getUniqueId(), new AbstractMap.SimpleEntry<Long, Vector>(System.currentTimeMillis(), event.getVelocity()));
    }

    @Deprecated
    public Velocity(Plugin pl){
        Bukkit.getScheduler().runTaskTimer(pl, new Runnable() {
            @Override
            public void run() {
                for(Map.Entry<UUID, Map.Entry<Long, Vector>> p : velocity.entrySet()){
                    UUID uid = p.getKey();
                    Player player = Bukkit.getPlayer(uid);
                    Vector vec = p.getValue().getValue();
                    if(p.getValue().getKey() + 500L <= System.currentTimeMillis()) {
                        final double velY = vec.getY() * vec.getY();
                        final double Y = player.getVelocity().getY() * player.getVelocity().getY();
                        if(Y < 0.02D || Y > velY * 3.0D)
                            velocity.remove(uid);
                    }
                }
            }
        }, 0, 1);
    }
}
