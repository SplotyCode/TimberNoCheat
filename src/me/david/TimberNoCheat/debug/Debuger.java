package me.david.TimberNoCheat.debug;

import me.david.TimberNoCheat.TimberNoCheat;
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
            debuggingPlayers.put(uuid, new ArrayList<>(){{
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

    public void sendDebug(Debuggers debug, String message){
        for(Map.Entry<UUID, ArrayList<String>> entry : debuggingPlayers.entrySet())
            if(isDebugging(entry.getKey(), debug.name())){
                Player player = Bukkit.getPlayer(entry.getKey());
                if(player == null) continue;
                player.sendMessage(TimberNoCheat.instance + "§7[§eDEBUG§7][§b" + debug.name().toUpperCase() + "§7] §6" + message);
            }
    }
}
