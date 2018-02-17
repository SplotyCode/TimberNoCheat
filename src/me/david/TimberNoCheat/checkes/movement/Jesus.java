package me.david.TimberNoCheat.checkes.movement;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import me.david.TimberNoCheat.checktools.FalsePositive;
import me.david.TimberNoCheat.checktools.General;
import me.david.TimberNoCheat.runnable.Velocity;
import me.david.TimberNoCheat.util.CheckUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.NumberConversions;

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
        if (!TimberNoCheat.checkmanager.isvalid_create(player)) return;
        TimberNoCheat.instance.getMoveprofiler().start("Jesus");
        PlayerData playerData = TimberNoCheat.checkmanager.getPlayerdata(player);
        final FalsePositive.FalsePositiveChecks falsePositive = playerData.getFalsepositives();
        final General.GeneralValues generals = playerData.getGenerals();
        if(player.getAllowFlight() || falsePositive.hasVehicle(45))return;
        final Location to = event.getTo();
        final Location from = event.getFrom();
        final double yDiss = to.getY()-from.getY();
        final double yBlock = to.getY()-to.getBlockY();
        if(generals.getTicksInAir() > 90){
            setCountTick(player, "air", 40);
        }
        if(countTickexsits(player, "air") && yDiss <= -2){
            setCountTick(player, "yDiss", 20);
        }
        //if(!invalidData())

        TimberNoCheat.instance.getMoveprofiler().end();
    }

    private boolean invalidData(final Player player, final FalsePositive.FalsePositiveChecks falsePositive){
        final Location location = player.getLocation().clone();
        if(!checkBlock(player, location, true) || !checkBlock(player, location.add(0, 1, 0), true)){
            setCountTick(player, "invalidata", 10);
        }
        //TODO
        return !falsePositive.hasVehicle(80) && !falsePositive.hasHitorbow(75);
    }

    private boolean checkBlock(final Player player, final Location location, final boolean other){
        final Block block = location.getBlock();
        final Block playerBlock = player.getLocation().getBlock();
        final byte data = block.getData();
        return !block.isLiquid() || (data == 0 || data == 8 || other && block != playerBlock);
    }
}
