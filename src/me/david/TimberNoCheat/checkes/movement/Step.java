package me.david.TimberNoCheat.checkes.movement;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import me.david.TimberNoCheat.checktools.FalsePositive;
import me.david.TimberNoCheat.checktools.MaterialHelper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

public class Step extends Check {

    public Step() {
        super("Step", Category.MOVEMENT);
    }


    @EventHandler(priority = EventPriority.LOW)
    public void onMove(PlayerMoveEvent e) {
        final Player p = e.getPlayer();
        if (!TimberNoCheat.checkmanager.isvalid_create(p)) {
            return;
        }
        Location to = e.getTo();
        Location from = e.getFrom();
        double yDis = to.getY() - from.getY();
        double yDisblock = to.getY() - to.getBlockY();
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        FalsePositive.FalsePositiveChecks fp = pd.getFalsepositives();
        double maxshoud = 0.601D;

        if (fp.hasExplosion(80 * 5)) {
            maxshoud = 5.0D;
        /* slime, bed  */
        } else if ((fp.hasBed(60 * 5) || fp.hasSlime(60 * 5)) || ((MaterialHelper.slime(p, 3)) &&
                (yDis <= -1.0D))) {
            maxshoud = 3.6D;
        /* GETS THINKS WITH A ROD*/
        } else if (fp.hasRod(40 * 5)) {
            maxshoud = 3.1D;
        /* LIQUID */
        } else if (fp.hasLiquid(20 * 5)) {
            maxshoud = 2.0D;
        /* PISTONS */
        } else if (fp.hasPiston(30 * 5)) {
            maxshoud = 1.6D;
        /* Hit or shot */
        } else if (fp.hasHitorbow(60 * 5)) {
            maxshoud = 1.3D;
            //SNOW or carpet to step
        } else if (MaterialHelper.slime(p, 1)) {
            maxshoud = 1.1D;
            //BED to step
        } else if (MaterialHelper.bed(p, 1)) {
            maxshoud = 0.7D;
        }
        if (yDis >= maxshoud) {
            updatevio(this, p, yDis * 4, " §6MODE: §bHIGHT", " §6HIGHT: §b" + yDis, " §6MAX: §b" + maxshoud);
        } else if (yDis == 0.25D && maxshoud == 0.601 && !fp.hasLiquid(20 * 5) && !fp.hasHitorbow(60 * 5) && !a(yDis) && !(fp.hasBed(60 * 5) || fp.hasSlime(60 * 5)) && !fp.hasClimp(30 * 5) && !fp.enderpearl && !fp.hasOtherKB(30 * 5) &&
                MaterialHelper.checksouroundpos3(p, 1, 0, 1) && !MaterialHelper.CHESTS.contains(p.getLocation().add(0.0D, -1.0D, 0).getBlock().getType()))
            updatevio(this, p, 1, " §6MODE: §bLOW");
        else if (!fp.hasPiston(30 * 5)) {
            double d5 = e.getFrom().getY() - e.getFrom().getBlockY();
            double d9;
            int i1;
            if (yDis > 0.0D && (yDisblock == 0.0D || yDisblock >= 0.01D) && !fp.jumpboost(p) && (!fp.hasLiquid(20 * 5)) && (!fp.hasHitorbow(60 * 5)) /*TODO: vieleicht onground*/) {
                Location localLocation1 = p.getLocation();
                int k = 0;
                double d7 = 0.0D;
                d9 = (!MaterialHelper.checksouroundpos2(p, 0.3, 1, 0.3)) || (!MaterialHelper.checksouroundpos2(p, 0.3, 2, 0.3)) ? 0.02 : 0.01;
                i1 = (d5 == 0.5D) || (yDisblock == 0.5D) ? 1 : 0;
                boolean i2 = i1 == 0 || (checkall(to, 0.3D, 0.0D, 0.3D) && checkall(from, 0.3D, 0.0D, 0.3D));
                boolean i3 = a(yDisblock) && !a(yDis);
                //Location localLocation2 = ��(paramLocation1, paramLocation2, 0.7D);
                if (a(yDis)){
                    d7 = pd.getStepjump();
                    if ((a(d7)) &&(yDis < d7)) {
                        if(pd.getStepjump2() != -1){
                            if(pd.getStepjump() > 0)pd.setStepjump(0);
                            pd.setStepjump2(4);
                        }
                        pd.setStepjump(pd.getStepjump()+1);
                        int i6 = (int) Math.abs(pd.getStepjump2() - pd.getStepjump());
                        if (pd.getStepjump2() != 4 && pd.getStepjump() != 4 || i6 != 3 || pd.getStepjump2() != 4 && pd.getStepjump() != 4) k = 2;
                    }
                }
                if (a(d7)) {
                    pd.setStepjump(yDis);
                } else {
                    pd.setStepjump(0);
                    pd.setLaststep(from);
                    k = 1;
                }
                if (yDis >= d9 && !i3 && i2 && k != 0 && (yDis == 0.5D || (yDis != yDisblock)) && ((yDis >= 0.24D) || (MaterialHelper.checksouroundpos2(p, 0.3D, 2.0D, 0.3D))) &&
                (!MaterialHelper.checkloc(localLocation1)) && check(from) &&
                (check(localLocation1.clone().add(0.0D, -1.0D, 0.0D))) &&
                (check(from.clone().add(0.0D, -1.0D, 0.0D)))) {
                    updatevio(this, p, 5, " §6MODE: §bJUMPING", " §6HIGHT: §bJUMPING" + yDis);
                }
            }
            if (!fp.hasHitorbow(80) && (yDis >= 0.34D) && !(fp.hasBed(60) || fp.hasSlime(60)) && (yDisblock != 0.5D) && !a(yDis) &&  !fp.jumpboost(p) && !fp.hasLiquid(140)){
                for (int j = -1; j <= 1; j++)
                    if (!checkall2(p, 1.0D, j, 1.0D))
                        return;
                double d6 = Math.abs(yDis - d5);
                double d8 = Math.abs(yDisblock - d6);
                if ((d6 != yDis) && (yDisblock != d5) && (d8 >= 0.35D)) {
                    updatevio(this, p, 5, " §6MODE: §bDIFF", " §6HIGHT: §bJUMPING" + yDis);
                }
            }
        }
    }


    private boolean a(double paramDouble) {
        double d = g(paramDouble, 3);
        return (d == 0.419D) || (d == 0.333D) || (d == 0.248D) || (d == 0.164D) || (d == 0.083D);
    }

    private double g(double paramDouble, int paramInt) {
        int i = 0;
        if (paramDouble < 0.0D) {
            paramDouble = Math.abs(paramDouble);
            i = 1;
        }
        paramInt = (int) Math.pow(10.0D, paramInt);
        paramDouble *= paramInt;
        paramDouble = Math.floor(paramDouble) / paramInt;
        return i != 0 ? -paramDouble : paramDouble;
    }

    private boolean check(Location loc) {
        Material mat = loc.getBlock().getType();
        return (MaterialHelper.check(loc, false)) ||
        (MaterialHelper.CHESTS.contains(mat)) || (mat == Material.PISTON_EXTENSION) || (mat == Material.PISTON_MOVING_PIECE) || (mat == Material.DRAGON_EGG) || (mat == Material.LEAVES) || (mat == Material.LEAVES_2) || (mat == Material.SOIL) || (mat == Material.SOUL_SAND) || (mat == Material.CAULDRON) || (mat == Material.ANVIL);
    }

    private boolean checkall(Location loc, double x, double y, double z) {
        return (check(loc.clone().add(0, y, 0))) &&
        (check(loc.clone().add(x, y, 0))) &&
        (check(loc.clone().add(-x, y, 0))) &&
        (check(loc.clone().add(0, y, z))) &&
        (check(loc.clone().add(0, y, -z))) &&
        (check(loc.clone().add(x, y, z))) &&
        (check(loc.clone().add(-x, y, -z))) &&
        (check(loc.clone().add(x, y, -z))) &&
        (check(loc.clone().add(-x, y, z)));
    }

    private boolean checkall2(Player p, double x, double y, double z)
    {
        return (check2(p.getLocation().add(0.0D, y, 0.0D))) &&
        (check2(p.getLocation().add(x, y, 0.0D))) &&
        (check2(p.getLocation().add(-x, y, 0.0D))) &&
        (check2(p.getLocation().add(0.0D, y, z))) &&
        (check2(p.getLocation().add(0.0D, y, -z))) &&
        (check2(p.getLocation().add(x, y, z))) &&
        (check2(p.getLocation().add(-x, y, -z))) &&
        (check2(p.getLocation().add(x, y, -z))) &&
        (check2(p.getLocation().add(-x, y, z)));
    }

    private boolean check2(Location loc) {
        Material mat = loc.getBlock().getType();
        if (mat == Material.SLIME_BLOCK) return false;
        return !MaterialHelper.FENCES.contains(mat) &&
        !MaterialHelper.STAIRS.contains(mat) &&
        !MaterialHelper.TRAPDOOR.contains(mat) &&
        !MaterialHelper.DOOR.contains(mat) &&
        !MaterialHelper.CHESTS.contains(mat) &&
        !MaterialHelper.PLATES.contains(mat) && (mat != Material.getMaterial(127)) && (mat != Material.FIRE) && mat != Material.COCOA && mat != Material.DRAGON_EGG && (mat != Material.ENDER_PORTAL_FRAME) && (mat != Material.ENCHANTMENT_TABLE) && (mat != Material.FLOWER_POT) && (mat != Material.BREWING_STAND);
    }
}
