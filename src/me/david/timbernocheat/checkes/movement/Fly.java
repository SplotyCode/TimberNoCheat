package me.david.timbernocheat.checkes.movement;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import me.david.timbernocheat.checkmanager.PlayerData;
import me.david.timbernocheat.checktools.FalsePositive;
import me.david.timbernocheat.checktools.General;
import me.david.timbernocheat.checktools.MaterialHelper;
import me.david.timbernocheat.debug.Debuggers;
import me.david.timbernocheat.util.SpeedUtil;
import me.david.api.utils.BlockUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffectType;

public class Fly extends Check {

    private final boolean vanilla;
    private final String setback;

    public Fly(){
        super("Fly", Category.MOVEMENT);
        vanilla = getBoolean("vanilla");
        setback = getString("setbackmethode");
    }

    @EventHandler
    public void kick(PlayerKickEvent e) {
        if (!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer()) || e.isCancelled()) {
            return;
        }
        if(vanilla && e.getReason().equals("Flying is not enabled on this server")) {
            updateVio(this, e.getPlayer(), 1, " §6CHECK: §bVANILLA");
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void playerMove(PlayerMoveEvent e){
        final Player p = e.getPlayer();
        final Location to = e.getTo();
        final Location from = e.getFrom();
        if (!TimberNoCheat.checkmanager.isvalid_create(p)) return;
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        General.GeneralValues general = pd.getGenerals();
        FalsePositive.FalsePositiveChecks fp = pd.getFalsepositives();
        TimberNoCheat.instance.getMoveprofiler().start("Fly ");
        TimberNoCheat.instance.getDebugger().sendDebug(Debuggers.AIRTICKSVSMOVEVELOCITY, pd.getGenerals().getTicksInAir() + " " + p.getVelocity().getY());
        final double yDiff = to.getY()-from.getY();
        final double yDiffBlock = to.getY()-from.getBlockY();

        //Lets Start with Slime Blocks (And Beds for >1.12 Support)
        

        TimberNoCheat.instance.getMoveprofiler().end();
    }



    private void setBack(Player p){
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        switch (setback){
            case "cancel":
                p.teleport(pd.getGenerals().getLastOnGround(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                break;
            case "down":
                p.teleport(p.getLocation().subtract(0, Math.min(3, groundDistance(p)), 0));
                break;
        }

    }

    private double groundDistance(Player p){
        double distance = 0;
        Location location = p.getLocation();
        while (!location.getBlock().getType().isSolid() && !MaterialHelper.LIQUID.contains(location.getBlock().getType())) {
            location.subtract(0, 0.1, 0);
            distance += 0.1;
        }
        return distance;
    }

    private boolean inair(Player p){
        for(Block b : BlockUtil.getBlocksAround(p.getLocation(), 3))
            if(b.getType() != Material.AIR)
                return false;
        return true;
    }

    private double getJump(Player p) {
        double d;
        if (p.hasPotionEffect(PotionEffectType.JUMP)) {
            int level = SpeedUtil.getPotionEffectLevel(p, PotionEffectType.JUMP);
            switch (level){
                case 1:
                    d = 1.9;
                    break;
                case 2:
                    d = 2.7;
                    break;
                case 3:
                    d = 3.36;
                    break;
                case 4:
                    d = 4.22;
                    break;
                case 5:
                    d = 5.16;
                    break;
                default:
                    d = (level) + 1;
                    break;
            }
        }else return 0;
        return d+1.35;
    }


}
