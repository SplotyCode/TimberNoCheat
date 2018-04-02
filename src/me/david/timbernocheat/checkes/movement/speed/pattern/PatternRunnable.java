package me.david.timbernocheat.checkes.movement.speed.pattern;

import me.david.api.utils.cordinates.LocationUtil;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.checkes.movement.speed.PatternCheck;
import me.david.timbernocheat.checktools.FalsePositive;
import me.david.timbernocheat.debug.Debuggers;
import me.david.timbernocheat.runnable.ExceptionRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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

            if(pd.getLastticklocation() != null)     {
                FalsePositive.FalsePositiveChecks fp = pd.getFalsepositives();
                if (p.isSleeping() || fp.hasVehicle(40) || fp.hasExplosion(60) || fp.hasPiston(50) || fp.hasTeleport(80) || fp.hasWorld(120) || fp.hasHitorbow(40) || fp.worldboarder(p) || fp.hasRod(60) || fp.hasOtherKB(50) || fp.hasSlime(120) || fp.hasBed(80) || fp.hasChest(20)) continue;

                SpeedPattern optimalpattern = check.generateSpeedPattern(p, pd), best;
                boolean found = false;

                best = getOptimalPattern(optimalpattern);
                if(best != null){
                    optimalpattern = best;
                    found = true;
                }

                double xzDiff = LocationUtil.getHorizontalDistance(pd.getLastticklocation(), p.getLocation());
                double yDiffUp = p.getLocation().getY() - pd.getLastticklocation().getY();
                double yDiffDown = pd.getLastticklocation().getY() - p.getLocation().getY();

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
                        if (pd.getLastticklocation() == null)
                            p.teleport(pd.getLastticklocation());
                        else {
                            p.teleport(pd.getLastFlagloc());
                            pd.setLastFlagloc(null);
                        }
                    }
                } else if(toomushper > 0 && pd.getLastFlagloc() == null) pd.setLastFlagloc(pd.getLastticklocation());
            }
            pd.setLastticklocation(p.getLocation());
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
