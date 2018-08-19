package me.david.timbernocheat.startup;

import me.david.api.utils.StringUtil;
import me.david.timbernocheat.TimberNoCheat;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.logging.Level;

public class StageHelper {

    private StartState startState = StartState.INIT;
    private long lastState = System.currentTimeMillis();

    private boolean hasValidate = false;
    private ArrayList<StartState> calledStates = new ArrayList<>();

    public StageHelper(){
        calledStates.add(StartState.INIT);
    }

    public void onPluginStart(){
        Bukkit.getScheduler().runTaskLater(TimberNoCheat.getInstance(), () -> {
            if(!hasValidate){
                String message = "Es sieht so aus als währe ein Fehler beim starten von TNC bassiert...\n" +
                        "Normalerweise gibt es Skripte die Fehler beim starten finden und Informationen ausgeben... Keiner von denen ist angeschrungen...\n" +
                        "Wir sind uns trotzdem sicher das es einen Fehler gab\n" +
                        "Da keiner unserer Checks ausgelöst wurden können wir nur raten... Wir denken es liegd an '" + startState + "'\n" +
                        "Stages die nicht zuende gelaufen sind: " + startState + "\n" +
                        "Stages die garnicht erst angefangen wurden: " + StringUtil.toString(notCalledStages(), Enum::name, ", ");
                TimberNoCheat.getInstance().getLogger().log(Level.WARNING, message);
                TimberNoCheat.getInstance().getOnPostLoad().add(() -> TimberNoCheat.getInstance().getDiscordManager().sendWarning(message));
            }
        }, 2);
    }

    public void changeState(StartState startState){
        if(calledStates.contains(startState)){
            TimberNoCheat.getInstance().getLogger().info("Someone Requested to call Stage '" + startState.name() + "' but it was already called... Ignore for now and call it again...");
        }else calledStates.add(startState);
        TimberNoCheat.log(Level.INFO, "Changed from Stage '" + this.startState.name() + "(Category: '" + this.startState.getStateType().name() + "')' to Stage '" + startState.name() + "(Category: '" + startState.getStateType().name() + "')' in " + (System.currentTimeMillis()-lastState) + "ms");
        this.startState = startState;
        lastState = System.currentTimeMillis();
    }

    private ArrayList<StartState> notCalledStages(){
        ArrayList<StartState> list = new ArrayList<>();
        ArrayList<StartState> shutCalled = StartState.getStagebyType(StartState.StateType.INIT);
        shutCalled.addAll(StartState.getStagebyType(StartState.StateType.LOAD));
        shutCalled.addAll(StartState.getStagebyType(StartState.StateType.START));
        for(StartState stages : shutCalled)
            if(!calledStates.contains(stages))
                list.add(stages);
        return list;

    }

    public void validate(){
        hasValidate = true;
        //Double checking :D
        if(startState != StartState.FINISHING || notCalledStages().size() != 0){
            String message = "Fehler in Stage: " + startState + "\n" +
                    "Eigentlich ist das Plugin fertig geladen aber ist gibt einen Fehler...\n" +
                    "Folgende Stages wurden nicht gestartet: " + StringUtil.toString(notCalledStages(), Enum::name, ", ");
            TimberNoCheat.getInstance().getLogger().log(Level.WARNING, message);
            TimberNoCheat.getInstance().getDiscordManager().sendWarning(message);
        }
    }

    public StartState getCurrentStartState(){
        return startState;
    }
}
