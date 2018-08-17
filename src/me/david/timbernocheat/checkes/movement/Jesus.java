package me.david.timbernocheat.checkes.movement;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.checktools.FalsePositive;
import me.david.timbernocheat.checktools.General;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

//TODO finishing check
public class Jesus extends Check {

    private final double sensitivity;
    private final boolean speed;

    public Jesus(){
        super("Jesus", Category.MOVEMENT);
        sensitivity = getDouble("sensitivity");
        speed = getBoolean("speed");
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onMove(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (!CheckManager.getInstance().isvalid_create(player)) return;
        TimberNoCheat.getInstance().getMoveprofiler().start("Jesus");
        PlayerData playerData = CheckManager.getInstance().getPlayerdata(player);
        final FalsePositive.FalsePositiveChecks falsePositive = playerData.getFalsePositives();
        final General.GeneralValues generals = playerData.getGenerals();
        if(player.getAllowFlight() || falsePositive.hasVehicle(45)) return;

        final Location to = event.getTo();
        final Location from = event.getFrom();
        final double yDiss = to.getY()-from.getY();

        

        TimberNoCheat.getInstance().getMoveprofiler().end();
    }

}
