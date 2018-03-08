package me.david.timbernocheat.debug;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.debug.debuggers.MoveVelocity;
import me.david.timbernocheat.debug.debuggers.OnGround;
import me.david.timbernocheat.debug.debuggers.OnGroundCheckDiff;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public enum  Debuggers {

    RANGE("PlaceReach"),
    ATTACK_RANGE("Killaura"),
    HITBOX("HitBoxes"),
    SPEED("Speed"),
    PATTERN_SPEED("Speed"),
    RAY_TRACE("Interact"),
    MOVE_VELOCITY(new MoveVelocity()),
    ONGROUND(new OnGround()),
    ONGROUNDCHECKDIF(new OnGroundCheckDiff()),
    PLAYERDATA_USE(""),
    PLAYERDATA_MANAGE(""),
    PERMISSIONCACHE("Messages", "PermissionCheck"),
    FASTLADDER("Add"),
    CHECKWATCHER(""),
    SCHEDULEREXEPTION("Deactivate Cooldown"),
    BOWFORCE("FastBow"),
    AIRTICKSVSMOVEVELOCITY("Fly");

    private final ExternalDebugger debugger;
    private final boolean external;
    private final Check dependecy;
    private HashMap<String, HashMap<UUID, Boolean>> settings = new HashMap<>();

    Debuggers(String dependecy, String... settings){
        debugger = null;
        external = false;
        this.dependecy = TimberNoCheat.getCheckManager().getCheckbyString(dependecy);
        for(String setting : settings)
            this.settings.put(setting, new HashMap<>());
    }

    Debuggers(ExternalDebugger debugger, String... settings){
        external = true;
        this.debugger = debugger;
        dependecy = null;
        TimberNoCheat.getInstance().registerListener(debugger);
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

    public Check getDependecy() {
        return dependecy;
    }

    public ExternalDebugger getDebugger() {
        return debugger;
    }
}
