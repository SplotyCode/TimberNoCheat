package me.david.TimberNoCheat.debug;

import me.david.TimberNoCheat.TimberNoCheat;
import org.bukkit.event.Listener;

public class ExternalDebuger implements Listener {

    protected void send(Debuggers debugger, String msg, Object... data){
        TimberNoCheat.instance.getDebuger().sendDebug(debugger, msg, data);
    }
}
