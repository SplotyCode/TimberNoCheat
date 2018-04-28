package me.david.timbernocheat.checkes.movement.speed.pattern;

import javafx.util.Pair;
import me.david.api.utils.cordinates.LocationUtil;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.checkes.movement.speed.PatternCheck;
import me.david.timbernocheat.checktools.FalsePositive;
import me.david.timbernocheat.checktools.General;
import me.david.timbernocheat.debug.Debuggers;
import me.david.timbernocheat.runnable.ExceptionRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class PatternRunnable implements ExceptionRunnable {

    private final PatternCheck check;

    public PatternRunnable(PatternCheck check){
        this.check = check;
    }

    @Override
    public void run() throws Exception {
        for(Player p : Bukkit.getOnlinePlayers()){
            if(!TimberNoCheat.getCheckManager().isvalid_create(p) || p.getAllowFlight())continue;
            PlayerData pd = TimberNoCheat.getCheckManager().getPlayerdata(p);
            if(pd.getLastTickLocation() != null) {
                FalsePositive.FalsePositiveChecks fp = pd.getFalsePositives();
                General.GeneralValues generals = pd.getGenerals();
                if (p.isSleeping() || fp.hasVehicle(8*20) || fp.hasExplosion(8*20) || fp.hasPiston(8*20) || fp.hasTeleport(8*20) || fp.hasWorld(120) || fp.hasHitorbow(8*20) || fp.worldboarder(p) || fp.hasRod(8*20) || fp.hasOtherKB(8*20) || fp.hasSlime(8*20) || fp.hasBed(8*20) || fp.hasChest(8*20)) continue;

                List<Integer> moveTicks = generals.getMovingTicks();
                if(moveTicks.size() < 2) continue;
                Location from = generals.moveEventLoc(moveTicks.get(moveTicks.size()-3));
                Location to = generals.firstPos(moveTicks.get(moveTicks.size()-2));

                if(pd.getLastPattern() != null && pd.getLastPattern().equals(new Pair<>(from, to))) continue;

                SpeedPattern optimalpattern = check.generateSpeedPattern(moveTicks.size()-3, pd), best;
                boolean found = false;

                best = getOptimalPattern(optimalpattern);
                if(best != null){
                    optimalpattern = best;
                    found = true;
                }

                double xzDiff = LocationUtil.getHorizontalDistance(from, to);
                double yDiffUp = to.getY() - from.getY();
                double yDiffDown = from.getY() - to.getY();

                if (!found) {
                    if(PatternCheck.getGenerators().contains(p.getUniqueId())){
                        create(p, optimalpattern, (float) yDiffDown, (float) yDiffUp, (float) xzDiff);
                    }else p.sendMessage(TimberNoCheat.getInstance().prefix + " [DEBUG] [SPEED-PATTERN] Es konnte keine Pattern gefunden werden!");
                    continue;
                }

                if(check.getDisabledpatterns().contains(optimalpattern.getName())) continue;
                double toomuch = 0;
                int toomushper = 0;
                TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.PATTERN_SPEED, "CAPTURED: xz=" + xzDiff + " yUp=" + yDiffUp + " yDown=" + yDiffDown);
                TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.PATTERN_SPEED, "PATTERN: xz=" + optimalpattern.getHorizontal() + " yUP=" + optimalpattern.getVerticalup() + " yDown=" + optimalpattern.getVerticaldown());
                if (optimalpattern.getVerticaldown() < yDiffDown) {
                    if(PatternCheck.getGenerators().contains(p.getUniqueId())){
                        p.sendMessage(TimberNoCheat.getInstance().prefix + "[SPEED-PATTERN] '" + optimalpattern.getName() + "' updatet yDiffdown to '" + yDiffDown + "'!");
                        optimalpattern.setVerticaldown((float) yDiffDown);
                        check.savepatterns();
                        continue;
                    }
                    toomuch += optimalpattern.getVerticaldown() - yDiffDown;
                    toomushper += optimalpattern.getVerticaldown()/yDiffDown;
                }
                if (optimalpattern.getVerticalup() < yDiffUp) {
                    if(PatternCheck.getGenerators().contains(p.getUniqueId())){
                        p.sendMessage(TimberNoCheat.getInstance().prefix + "[SPEED-PATTERN] '" + optimalpattern.getName() + "' updatet yDiffup to '" + yDiffDown + "'!");
                        optimalpattern.setVerticalup((float) yDiffUp);
                        check.savepatterns();
                        continue;
                    }
                    toomuch += optimalpattern.getVerticalup() - yDiffUp;
                    toomushper +=  optimalpattern.getVerticalup()/yDiffUp;
                }
                if (optimalpattern.getHorizontal() < xzDiff) {
                    if(PatternCheck.getGenerators().contains(p.getUniqueId())){
                        p.sendMessage(TimberNoCheat.getInstance().prefix + "[SPEED-PATTERN] '" + optimalpattern.getName() + "' updatet xzDiff to '" + yDiffDown + "'!");
                        optimalpattern.setHorizontal((float) xzDiff);
                        check.savepatterns();
                        continue;
                    }
                    toomuch += optimalpattern.getHorizontal() - xzDiff;
                    toomushper += optimalpattern.getHorizontal() / xzDiff;
                }
                toomushper *= 100;
                if (toomushper >= check.getPatternlatency()) {
                    if (check.updateVio(check, p, toomushper / 2, "§6MODE: §bPATTERN", "§6PERCENTAGE: §b" + toomushper, "§6DISTANCE: §b" + toomuch)) {
                        if (pd.getLastFlagLoc() == null)
                            p.teleport(from);
                        else {
                            p.teleport(pd.getLastFlagLoc());
                            pd.setLastFlagLoc(null);
                        }
                    }
                } else if(toomushper > 0 && pd.getLastFlagLoc() == null) pd.setLastFlagLoc(pd.getLastTickLocation());
            }
            pd.setLastTickLocation(p.getLocation());
        }
    }

    private void create(Player player, SpeedPattern pattern, float yDiffdown, float yDiffUp, float xzDiff){
        pattern.setVerticaldown(yDiffdown);
        pattern.setVerticalup(yDiffUp);
        pattern.setHorizontal(xzDiff);
        check.getPatterns().add(pattern);
        check.savepatterns();
        player.sendMessage(TimberNoCheat.getInstance().prefix + "[SPEED-PATTERN] Neue Pattern '" + pattern.getName() + "' erstellt!");
    }

    private SpeedPattern getOptimalPattern(SpeedPattern pattern) {
        for (SpeedPattern cPattern : check.getPatterns())
            if (cPattern.equalsnospeed(pattern))
                return cPattern;
        return null;
    }

}
