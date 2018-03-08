package me.david.timbernocheat.debug.obj;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.debug.Debuggers;
import me.david.api.PermissionCache;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class DebugPermissionCache extends PermissionCache{

    public DebugPermissionCache(boolean permissionex, long autoupdate, boolean clearonleave) {
        super(TimberNoCheat.getInstance(), permissionex, autoupdate, clearonleave, new HashMap<UUID, HashMap<String, Boolean>>(){
            @Override
            public HashMap<String, Boolean> put(UUID uuid, HashMap<String, Boolean> stringBooleanHashMap) {
                TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.PERMISSIONCACHE, "Put Nodes into " + uuid.toString());
                return super.put(uuid, stringBooleanHashMap);
            }
        }, new HashMap<String, Boolean>(){
            @Override
            public Boolean put(String s, Boolean aBoolean) {
                TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.PERMISSIONCACHE, "Updatet '" + s + "' to " + aBoolean);
                return super.put(s, aBoolean);
            }
        });
    }

    @Override
    public void clearAll() {
        TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.PERMISSIONCACHE, "Clear complete permission cache");
        super.clearAll();
    }

    @Override
    public void clearPlayer(Player p) {
        TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.PERMISSIONCACHE, "Clear permission cache for '" + p.getName() + "'");
        super.clearPlayer(p);
    }

    @Override
    public boolean hasPermission(Player p, String permission) {
        boolean result =  super.hasPermission(p, permission);
        TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.PERMISSIONCACHE, "Cashed result for '" + permission + "' is " + result, "PermissionCheck");
        return result;
    }

    @Override
    public void sendAll(String permission, String message) {
        super.sendAll(permission, message);
        TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.PERMISSIONCACHE, "Send Message '" + message + "' for players with permission '" + permission + "'", "Messages");
    }

    @Override
    public void sendAll(String permission, TextComponent message) {
        super.sendAll(permission, message);
        TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.PERMISSIONCACHE, "Send Message '" + message + "' for players with permission '" + permission + "'(2)", "Messages");
    }
}
