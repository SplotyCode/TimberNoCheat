package me.david.timbernocheat.startup;

import com.comphenix.protocol.ProtocolLibrary;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.config.Config;

import java.io.IOException;
import java.util.logging.Level;

public class StartUpHelper {

    private boolean stopped = false;

    private Runnable onStop;

    public StartUpHelper(Runnable onStop){
        this.onStop = onStop;
    }

    public void loadProtocolLib(){
        TimberNoCheat.getInstance().setStartState(StartState.LOAD_DEPENDENCY);
        if(stopped)return;
        try{
            TimberNoCheat.getInstance().setProtocolManager(ProtocolLibrary.getProtocolManager());
        }catch (NoClassDefFoundError ex){
            TimberNoCheat.getInstance().getLogger().log(Level.WARNING, "ProtocolLib konnte nicht gefunden wurde!");
            crash();
        }
        if(TimberNoCheat.getInstance().getProtocolManager() == null){
            TimberNoCheat.getInstance().getLogger().log(Level.WARNING, "Es sieht so aus als hätte ProtocolLib probleme...");
            crash();
        }
    }

    public void loadConfiguration(){
        TimberNoCheat.getInstance().setStartState(StartState.LOAD_CONFIGURATION);
        if(stopped)return;
        if (Config.check(TimberNoCheat.getInstance().getConfigFile(), TimberNoCheat.CONFIGURATION_VERSION, TimberNoCheat.getInstance().getResource("me/david/timbernocheat/config/config.yml"))) {
            crash();
        }
    }

    public void loadOtherFiles() {
        if (!TimberNoCheat.getInstance().getChecksRan().exists()) {
            try {
                TimberNoCheat.getInstance().getChecksRan().createNewFile();
            } catch (IOException ex) {
                TimberNoCheat.getInstance().reportException(ex, "Count not create Checks Runned file!");
            }
        }
    }

    private void crash(){
        onStop.run();
        stopped = true;
    }
}
