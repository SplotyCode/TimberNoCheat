package me.david.timbernocheat.checkes.movement;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.checktools.FalsePositive;
import me.david.timbernocheat.checktools.General;
import me.david.timbernocheat.util.CheckUtils;
import me.david.timbernocheat.util.MoveingUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

//TODO finishing check
public class Jesus extends Check {

    public Jesus(){
        super("Jesus", Category.MOVEMENT);
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onMove(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (!CheckManager.getInstance().isvalid_create(player)) return;

        /* Starting the Profiler... */
        TimberNoCheat.getInstance().getMoveprofiler().start("Jesus");

        /* Setting Up the Player Data */
        final PlayerData playerData = CheckManager.getInstance().getPlayerdata(player);
        final FalsePositive.FalsePositiveChecks falsePositive = playerData.getFalsePositives();
        final General.GeneralValues generals = playerData.getGenerals();

        /* Make Some checks */
        if (falsePositive.hasElytra() || falsePositive.hasVehicle(45) ||
                player.isFlying() || CheckUtils.doesColidateWithMaterial(Material.WEB, event.getFrom()) ||
                CheckUtils.doesColidateWithMaterial(Material.WEB, event.getTo()) ||
                CheckUtils.doesColidateWithMaterial(Material.WATER_LILY, event.getFrom()) ||
                CheckUtils.doesColidateWithMaterial(Material.WATER_LILY, event.getTo())) return;

        final Location to = event.getTo();
        final Location from = event.getFrom();
        final double yDiss = to.getY()-from.getY();

        Material inType = player.getLocation().add(0, 0.5, 0).getBlock().getType();
        if (inType == Material.WATER || inType == Material.STATIONARY_WATER) {
            resetJump(playerData);
            return;
        }
        if (isOnWater(player)) {
            if (from.getY() < to.getY()) {
                if (playerData.getJumpStart() == null) {
                    playerData.setJumpStart(event.getFrom());
                }
                playerData.setJesusJumpLenght(playerData.getJesusJumpLenght() + (to.getY() - from.getY()));
                if (playerData.getWaterJumpingTicks() > 3) {
                    if (updateVio(this, player, Math.min(playerData.getJesusJumpLenght() * 2.4, 1), "Weird Water Jump Movement!")) {
                        player.teleport(playerData.getJumpStart());
                        resetJump(playerData);
                    }
                }
            }
        }
        TimberNoCheat.getInstance().getMoveprofiler().end();
    }

    private void resetJump(final PlayerData playerData) {
        playerData.setWaterJumpingTicks(0);
        playerData.setJesusJumpLenght(0);
        playerData.setJumpStart(null);
    }

    private boolean isOnWater(Player player) {
        Location loc = player.getLocation().subtract(0, 1, 0);
        return getPlayerStandOnBlockLocation(loc, Material.STATIONARY_WATER).getBlock().getType() == Material.STATIONARY_WATER;
    }

    private Location getPlayerStandOnBlockLocation(Location locationUnderPlayer, Material mat) {
        Location b11 = locationUnderPlayer.clone().add(0.3, 0, -0.3);
        if (b11.getBlock().getType() != mat) {
            return b11;
        }
        Location b12 = locationUnderPlayer.clone().add(-0.3, 0, -0.3);
        if (b12.getBlock().getType() != mat) {
            return b12;
        }
        Location b21 = locationUnderPlayer.clone().add(0.3, 0, 0.3);
        if (b21.getBlock().getType() != mat) {
            return b21;
        }
        Location b22 = locationUnderPlayer.clone().add(-0.3, 0, +0.3);
        if (b22.getBlock().getType() != mat) {
            return b22;
        }
        return locationUnderPlayer;
    }

}
