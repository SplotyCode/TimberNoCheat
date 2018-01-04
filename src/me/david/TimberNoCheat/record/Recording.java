package me.david.TimberNoCheat.record;

import org.bukkit.entity.Player;

import java.io.IOException;

public class Recording {

    private RecordState state;
    private RecordSaveHandler saveHandler;

    public Recording(RecordSaveHandler saveHandler){
        this.saveHandler = saveHandler;
        state = RecordState.INIT;
    }

    public void tick() {
        if (state == RecordState.RECORD)
            if(saveHandler.tick())
                stop();
    }

    public boolean isStopped() {
        return state == RecordState.FINISHED;
    }

    public void start() {
        if(state != RecordState.INIT)
            throw new IllegalStateException("Can not Start recording in this State! State: '" + state.name() + "'!");
        state = RecordState.RECORD;
    }

    public void stop() {
        if(state != RecordState.RECORD)
            throw new IllegalStateException("Can not Start recording in this State! State: '" + state.name() + "'!");
        state = RecordState.FINISHED;
        try {
            saveHandler.flushAndStop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void join(Player player) {
        saveHandler.addPlayer(player);
    }

    public void leave(Player player) {
        saveHandler.removePlayer(player);
    }

    public RecordState getState() {
        return state;
    }

    public void setState(RecordState state) {
        this.state = state;
    }

    public RecordSaveHandler getSaveHandler() {
        return saveHandler;
    }

    public void setSaveHandler(RecordSaveHandler saveHandler) {
        this.saveHandler = saveHandler;
    }
}
