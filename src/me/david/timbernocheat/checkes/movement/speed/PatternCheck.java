package me.david.timbernocheat.checkes.movement.speed;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import me.david.api.utils.JsonFileUtil;
import me.david.api.utils.player.PlayerUtil;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.checkes.movement.speed.pattern.PatternRunnable;
import me.david.timbernocheat.checkes.movement.speed.pattern.SpeedPattern;
import me.david.timbernocheat.checktools.FalsePositive;
import me.david.timbernocheat.checktools.General;
import me.david.timbernocheat.debug.Scheduler;
import me.david.timbernocheat.runnable.TimberScheduler;
import me.david.timbernocheat.util.CheckUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PatternCheck extends AbstractSpeed {

    private final List<String> disabledpatterns;
    private final double latency;

    public PatternCheck(Speed speed) {
        super(speed, "Pattern");
        disabledpatterns = getStringList("disabled");
        latency = getDouble("latency");
        loadpatterns();
    }

    @Override public void move(SpeedMoveData move, Player player, PlayerData data, FalsePositive.FalsePositiveChecks falsePositive, General.GeneralValues general) {}

    private ArrayList<SpeedPattern> patterns = new ArrayList<SpeedPattern>();
    public static ArrayList<UUID> generators = new ArrayList<>();

    private void loadpatterns(){
        try {
            File file = TimberNoCheat.getInstance().getSpeedPatterns();
            if(!file.exists()){
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedReader br = new BufferedReader(new FileReader(file));
            if (br.readLine() == null) {
                br.close();
                return;
            }
            br.close();
            JsonReader reader = new JsonReader(new FileReader(file));
            patterns = new Gson().fromJson(reader, new TypeToken<ArrayList<SpeedPattern>>() {}.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savepatterns(){
        JsonFileUtil.saveJSON(TimberNoCheat.getInstance().getSpeedPatterns(), new TypeToken<ArrayList<SpeedPattern>>() {}.getType(), patterns);
    }

    @Override
    public void startTasks() {
        register(new TimberScheduler(Scheduler.PATTERN_SPEED, new PatternRunnable(this)).runTaskLater(1));
    }

    public SpeedPattern generateSpeedPattern(Player player, PlayerData pd){
        FalsePositive.FalsePositiveChecks fp = pd.getFalsepositives();
        Material under = player.getLocation().subtract(0, 1, 0).getBlock().getType();
        return new SpeedPattern("P-" + patterns.size(), CheckUtils.getPotionEffectLevel(player, PotionEffectType.SPEED), CheckUtils.getPotionEffectLevel(player, PotionEffectType.JUMP), CheckUtils.getPotionEffectLevel(player, PotionEffectType.SLOW), 0F, 0F, 0F, player.isInsideVehicle(), fp.hasLiquid(25), under == Material.ICE, player.isBlocking(), player.isSprinting(), player.isSneaking(), PlayerUtil.isInWeb(player), PlayerUtil.isOnLadder(player), PlayerUtil.slabsNear(player.getLocation()), PlayerUtil.stairsNear(player.getLocation()), player.getLocation().add(0, 2, 0).getBlock().getType() != Material.AIR, System.currentTimeMillis()-pd.getLastongroundtime()<5, under == Material.SOUL_SAND);
    }

    public ArrayList<SpeedPattern> getPatterns() {
        return patterns;
    }

    public static ArrayList<UUID> getGenerators() {
        return generators;
    }

    public List<String> getDisabledpatterns() {
        return disabledpatterns;
    }

    public double getPatternlatency() {
        return latency;
    }
}
