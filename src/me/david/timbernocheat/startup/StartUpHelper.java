package me.david.timbernocheat.startup;

import com.comphenix.protocol.ProtocolLibrary;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.config.Config;

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
            TimberNoCheat.getInstance().setProtocolmanager(ProtocolLibrary.getProtocolManager());
        }catch (NoClassDefFoundError ex){
            TimberNoCheat.getInstance().getLogger().log(Level.WARNING, "ProtocolLib konnte nicht gefunden wurde!");
            crash();
        }
        if(TimberNoCheat.getInstance().getProtocolmanager() == null){
            TimberNoCheat.getInstance().getLogger().log(Level.WARNING, "Es sieht so aus als hätte ProtocolLib probleme...");
            crash();
        }
    }

    public void loadConfiguration(){
        TimberNoCheat.getInstance().setStartState(StartState.LOAD_CONFIGURATION);
        if(stopped)return;
        if (Config.check(TimberNoCheat.getInstance().getConfigFile().getFile(), TimberNoCheat.CONFIGURATION_VERSION, TimberNoCheat.getInstance().getResource("me/david/timbernocheat/config/config.yml"))) {
            crash();
        }
    }

    private void crash(){
        onStop.run();
        stopped = true;
    }
}
