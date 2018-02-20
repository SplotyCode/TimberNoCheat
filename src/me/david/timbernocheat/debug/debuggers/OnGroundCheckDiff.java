package me.david.timbernocheat.debug.debuggers;

import me.david.timbernocheat.api.DebugMessageEvent;
import me.david.timbernocheat.debug.Debuggers;
import me.david.timbernocheat.debug.ExternalDebugger;
import org.bukkit.event.EventHandler;

import java.util.HashMap;
import java.util.Map;

public class OnGroundCheckDiff extends ExternalDebugger {

    @EventHandler
    public void onDebug(DebugMessageEvent event){
        if(event.getDebugger() == Debuggers.ONGROUND){
            HashMap<Integer, Integer> differences = new HashMap<>();
            int i = 0, i2 = 0;
            for(Object obj : event.getData()){
                boolean bool = (boolean) obj;
                for(Object compObj : event.getData()) {
                    boolean compBool = (boolean) compObj;
                    if(compBool != bool && (!differences.containsKey(i2) || differences.get(i2) != i))
                        differences.put(i, i2);
                    i2++;
                }
                i2 = 0;
                i++;
            }
            if(differences.isEmpty())return;
            send(Debuggers.ONGROUNDCHECKDIF, "---");
            for(Map.Entry<Integer, Integer> entry : differences.entrySet())
                send(Debuggers.ONGROUNDCHECKDIF, entry.getKey() + "(" + event.getData()[entry.getKey()] + ") <-> " + entry.getValue() + "(" + event.getData()[entry.getValue()] + ")");
            send(Debuggers.ONGROUNDCHECKDIF, "---");
        }
    }

}
