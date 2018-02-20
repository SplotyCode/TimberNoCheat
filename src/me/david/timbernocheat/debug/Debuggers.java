package me.david.timbernocheat.debug;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.debug.debuggers.MoveVelocity;
import me.david.timbernocheat.debug.debuggers.OnGround;
import me.david.timbernocheat.debug.debuggers.OnGroundCheckDiff;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public enum  Debuggers {

    RANGE,
    ATTACK_RANGE,
    HITBOX,
    SPEED,
    PATTERN_SPEED,
    RAY_TRACE,
    MOVE_VELOCITY(new MoveVelocity()),
    ONGROUND(new OnGround()),
    ONGROUNDCHECKDIF(new OnGroundCheckDiff()),
    PLAYERDATA_USE,
    PLAYERDATA_MANAGE,
    PERMISSIONCACHE("Messages", "PermissionCheck"),
    FASTLADDER("Add"),
    CHECKWATCHER,
    SCHEDULEREXEPTION("Deactivate Cooldown");

    private final ExternalDebugger debugger;
    private final boolean external;
    private HashMap<String, HashMap<UUID, Boolean>> settings = new HashMap<>();

    Debuggers(String... settings){
        debugger = null;
        external = false;
        for(String setting : settings)
            this.settings.put(setting, new HashMap<>());
    }

    Debuggers(ExternalDebugger debugger, String... settings){
        external = true;
        this.debugger = debugger;
        TimberNoCheat.instance.registerListener(debugger);
        for(String setting : settings)
            this.settings.put(setting, new HashMap<>());
    }

    public boolean getSetting(Player player, String setting){
        Boolean bool = settings.get(setting).get(player.getUniqueId());
        if(bool == null)return false;
        return bool;
    }

    public void setSetting(Player player, String setting, boolean value){
        settings.get(setting).put(player.getUniqueId(), value);
    }

    public HashMap<String, HashMap<UUID, Boolean>> getSettings() {
        return settings;
    }

    public boolean isExternal() {
        return external;
    }

    public ExternalDebugger getDebugger() {
        return debugger;
    }
}
