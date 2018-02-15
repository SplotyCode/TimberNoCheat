package me.david.TimberNoCheat.debug;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.api.DebugMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Debuger {

    private HashMap<UUID, ArrayList<String>> debuggingPlayers = new HashMap<>();

    public boolean isDebugging(UUID uuid, String debugger) {
        ArrayList<String> list = debuggingPlayers.get(uuid);
        return list != null && list.contains(debugger);
    }

    public boolean isDebugging(Player player, String debugger) {
        return isDebugging(player.getUniqueId(), debugger);
    }

    public void addDebugg(UUID uuid, String debugger){
        ArrayList<String> list = debuggingPlayers.get(uuid);
        if(list != null){
            if(!list.contains(debugger)) list.add(debugger);
        }else {
            //java should detect the string automatically! But intellij is complaining so when building ..
            debuggingPlayers.put(uuid, new ArrayList<String>(){{
                add(debugger);
            }});
        }
    }

    public void removeDebugger(UUID uuid, String debugger){
        ArrayList<String> list = debuggingPlayers.get(uuid);
        if(list == null || !list.contains(debugger))return;
        list.remove(debugger);
    }

    public void toggleDebugger(UUID uuid, String debugger){
        if (isDebugging(uuid, debugger)) removeDebugger(uuid, debugger);
        else addDebugg(uuid, debugger);
    }

    public void sendDebug(Debuggers debug, String message, Object... data){
        DebugMessageEvent event = new DebugMessageEvent(debug, message, data);
        Bukkit.getPluginManager().callEvent(event);
        if(event.isCancelled())return;
        for(Map.Entry<UUID, ArrayList<String>> entry : debuggingPlayers.entrySet())
            if(isDebugging(entry.getKey(), event.getDebugger().name())){
                Player player = Bukkit.getPlayer(entry.getKey());
                if(player == null) continue;
                player.sendMessage(TimberNoCheat.instance + "§7[§eDEBUG§7][§b" + event.getDebugger().name().toUpperCase() + "§7] §6" + event.getMessage());
            }
    }
}
