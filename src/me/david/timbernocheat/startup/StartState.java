package me.david.timbernocheat.startup;

public enum StartState {

    INIT(StateType.INIT),
    LOAD_DEPENDENCY(StateType.LOAD),
    LOAD_CONFIGURATION(StateType.LOAD),
    START_DEBUGGING(StateType.START),
    START_CHECKS(StateType.START),
    START_LISTENER_AND_COMMANDS(StateType.START),
    START_OTHER(StateType.START),
    START_GUIS(StateType.START),
    RUNNING(StateType.RUN),
    STOP(StateType.STOP),
    STOP_RECORDINGS(StateType.STOP),
    DISABLE_CHECKS(StateType.STOP),
    STOPPED(StateType.STOPPED);

    private final StateType stateType;

    StartState(StateType stateType){
        this.stateType = stateType;
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
