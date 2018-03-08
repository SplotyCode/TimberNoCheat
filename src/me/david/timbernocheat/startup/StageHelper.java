package me.david.timbernocheat.startup;

import me.david.timbernocheat.TimberNoCheat;

import java.util.logging.Level;

public class StageHelper {

    private StartState startState = StartState.INIT;
    private long lastState = System.currentTimeMillis();


    public void changeState(StartState startState){
        TimberNoCheat.log(Level.INFO, "Changed from Stage '" + this.startState.name() + "' to Stage '" + startState.name() + "' in " + (System.currentTimeMillis()-lastState) + "ms");
        this.startState = startState;
        lastState = System.currentTimeMillis();
    }

    public StartState getCurrentStartState(){
        return startState;
    }
}
