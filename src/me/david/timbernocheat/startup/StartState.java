package me.david.timbernocheat.startup;

import java.util.ArrayList;

public enum StartState {

    INIT(StateType.INIT),
    LOAD_DEPENDENCY(StateType.LOAD),
    LOAD_CONFIGURATION(StateType.LOAD),
    START_DEBUGGING(StateType.START),
    START_DISCORD(StateType.START),
    START_CHECKS(StateType.START),
    START_OTHER(StateType.START),
    START_LISTENER_AND_COMMANDS(StateType.START),
    START_GUIS(StateType.START),
    FINISHING(StateType.START),
    RUNNING(StateType.RUN),
    STOP(StateType.STOP),
    STOP_OTHERS(StateType.STOP),
    DISABLE_CHECKS(StateType.STOP),
    STOP_RECORDINGS(StateType.STOP),
    STOPPED(StateType.STOPPED);

    private final StateType stateType;

    StartState(StateType stateType){
        this.stateType = stateType;
    }

    public static ArrayList<StartState> getStagebyType(StateType type){
        ArrayList<StartState> list = new ArrayList<>();
        for(StartState stage : values())
            if(stage.getStateType() == type)
                list.add(stage);
        return list;
    }

    public enum StateType {
        INIT,
        LOAD,
        START,
        RUN,
        STOP,
        STOPPED
    }

    public StateType getStateType() {
        return stateType;
    }
}
