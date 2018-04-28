package me.david.timbernocheat.checkes.movement;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.runnable.Tps;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class MotionLoop extends Check {

    public MotionLoop() {
        super("MotionLoop", Category.MOVEMENT);
    }

    @EventHandler
    public void onMove(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (!CheckManager.getInstance().isvalid_create(player)) return;
        final PlayerData pd = CheckManager.getInstance().getPlayerdata(player);

        double yMotion = event.getTo().getY()-event.getFrom().getY();

        TimberNoCheat.getInstance().getMoveprofiler().start("MotionLoop");

        if (pd.getLastYMotion() == Double.MIN_VALUE) {
            pd.setLastYMotion(yMotion);
            return;
        }
        if (yMotion == pd.getLastYMotion()) {
            if (pd.getMotionLoopRepeat() == 0) pd.setFirstRepeat(event.getFrom());
            pd.setMotionLoopRepeat(pd.getMotionLoopRepeat()+1);
            if (Tps.tickCount-pd.getGenerals().getLastAirTick() > pd.getMotionLoopRepeat()) {
                pd.setMotionLoopRepeat(0);
            } else if (pd.getMotionLoopRepeat() > 2)
                if (updateVio(this, player, (pd.getMotionLoopRepeat()-2)*1.2))
                    player.teleport(pd.getFirstRepeat());
        } else {
            pd.setMotionLoopRepeat(0);
        }

        TimberNoCheat.getInstance().getMoveprofiler().end();
    }
}
