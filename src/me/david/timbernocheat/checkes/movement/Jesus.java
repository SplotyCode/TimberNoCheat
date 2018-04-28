package me.david.timbernocheat.checkes.movement;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
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
        if (!TimberNoCheat.getCheckManager().isvalid_create(player)) return;
        TimberNoCheat.getInstance().getMoveprofiler().start("Jesus");
        PlayerData playerData = TimberNoCheat.getCheckManager().getPlayerdata(player);
        final FalsePositive.FalsePositiveChecks falsePositive = playerData.getFalsePositives();
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

        TimberNoCheat.getInstance().getMoveprofiler().end();
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
