package me.david.timbernocheat.debug.debuggers;

import me.david.timbernocheat.debug.Debuggers;
import me.david.timbernocheat.debug.ExternalDebugger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.UUID;

public class MotionLoop extends ExternalDebugger {

    /*
     * I Don't use the player date here because it is a debugger...
     */
    private HashMap<UUID, MotionData> lastYMotion = new HashMap<>();

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();
        double yMotion = event.getTo().getY()-event.getFrom().getY();
        if(!lastYMotion.containsKey(uuid)){
            lastYMotion.put(uuid, new MotionData(0, yMotion));
            return;
        }
        MotionData data = lastYMotion.get(uuid);
        if(data.getLastY() == yMotion){
            data.count++;
            if(data.count > 2) send(Debuggers.MOTIONLOOP, data.count + " " + data.lastY);
        }else {
            data.count = 0;
            data.lastY = yMotion;
        }
    }

    private class MotionData {

        int count;
        double lastY;

        MotionData(int count, double lastY) {
            this.count = count;
            this.lastY = lastY;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public double getLastY() {
            return lastY;
        }

        public void setLastY(double lastY) {
            this.lastY = lastY;
        }
    }
}
