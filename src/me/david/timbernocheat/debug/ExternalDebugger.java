package me.david.timbernocheat.debug;

import me.david.timbernocheat.TimberNoCheat;
import org.bukkit.event.Listener;

public class ExternalDebugger implements Listener {

    protected void send(Debuggers debugger, String msg, Object... data){
        TimberNoCheat.instance.getDebugger().sendDebug(debugger, msg, data);
    }
}
