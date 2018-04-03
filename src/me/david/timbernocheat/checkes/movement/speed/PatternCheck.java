package me.david.timbernocheat.checkes.movement.speed;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import me.david.api.utils.JsonFileUtil;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.checkes.movement.speed.pattern.PatternRunnable;
import me.david.timbernocheat.checkes.movement.speed.pattern.SpeedPattern;
import me.david.timbernocheat.checktools.FalsePositive;
import me.david.timbernocheat.checktools.General;
import me.david.timbernocheat.debug.Scheduler;
import me.david.timbernocheat.runnable.TimberScheduler;
import org.bukkit.entity.Player;

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

    public SpeedPattern generateSpeedPattern(int tick, PlayerData pd){
        General.GeneralValues general = pd.getGenerals();
        return new SpeedPattern("P-" + patterns.size(),
                general.speedLevelTick(tick),
                general.jumpLevelTick(tick),
                general.slowLevelTick(tick),
                0F, 0F, 0F,
                general.vehicleTick(tick),
                general.liquidTick(tick),
                general.iceTick(tick),
                general.bloclTick(tick),
                general.sprintTick(tick),
                general.sneakTick(tick),
                general.webTick(tick),
                general.ladderTick(tick),
                general.slabTick(tick),
                general.stairTick(tick),
                general.headCollidate(tick),
                general.ground(tick),
                general.soulSandTick(tick));
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
