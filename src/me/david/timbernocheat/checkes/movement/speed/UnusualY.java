package me.david.timbernocheat.checkes.movement.speed;

import me.david.api.utils.BlockUtil;
import me.david.api.utils.cordinates.LocationUtil;
import me.david.timbernocheat.checkbase.Disable;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.checktools.FalsePositive;
import me.david.timbernocheat.checktools.General;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

@Disable(reason = "Handled by Main Speed Check")
public class UnusualY extends AbstractSpeed {

    public UnusualY(Speed speed) {
        super(speed, "UnusualY");
    }

    @Override
    public void move(SpeedMoveData move, Player player, PlayerData data, FalsePositive.FalsePositiveChecks falsePositive, General.GeneralValues general) {
        double yspeed = LocationUtil.getVerticalVector(move.getMoveEvent().getFrom().toVector()).subtract(LocationUtil.getVerticalVector(move.getMoveEvent().getTo().toVector())).length();
        if(((yspeed == 0.25D || (yspeed >= 0.58D && yspeed < 0.581D)) && yspeed > 0.0D || (yspeed > 0.2457D && yspeed < 0.24582D) || (yspeed > 0.329 && yspeed < 0.33)) && !player.getLocation().clone().subtract(0, 0.1, 0.0D).getBlock().getType().equals(Material.SNOW))
            updateVio(this, player, 1, " §6MODE: §bUNUSUAL_Y", " §6SPEED: §b" + yspeed, " §6BLOCK: §b" + player.getLocation().clone().subtract(0, 0.1D, 0).getBlock().getType().name());
        for(Block block : BlockUtil.getBlocksAround(player.getLocation(), 1))
            if(block.getType().isSolid() && yspeed >= 0.321 && yspeed < 0.322)
                updateVio(this, player, 1, " §6MODE: §bBLOCKS", " §6SPEED: §b" + yspeed);
    }
}
