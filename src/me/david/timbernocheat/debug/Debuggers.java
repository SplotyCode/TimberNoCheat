package me.david.timbernocheat.debug;

import me.david.api.utils.StringUtil;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.debug.debuggers.*;
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
    PERMISSIONCACHE("", "Messages", "PermissionCheck"),
    FASTLADDER("FastLadder", "Add"),
    CHECKWATCHER(""),
    SCHEDULEREXEPTION("", "Deactivate Cooldown"),
    BOWFORCE("FastBow"),
    FLYDEBUG("Fly", "ticksup", "max"),
    NOFALL("NoFall"),
    MOTIONLOOP(new MotionLoop()),
    ALL_VIOLATIONS(new AllViolations()),
    MOVE_EVENTS(new SpigotMoveEvent());

    private final ExternalDebugger debugger;
    private boolean disabled = false;
    private final boolean external;
    private Check dependency;
    private final String rawDependency;
    private HashMap<String, HashMap<UUID, Boolean>> settings = new HashMap<>();

    Debuggers(String dependency, String... settings){
        debugger = null;
        external = false;
        this.rawDependency = dependency;
        for(String setting : settings)
            this.settings.put(setting, new HashMap<>());
    }

    Debuggers(ExternalDebugger debugger, String... settings){
        external = true;
        this.debugger = debugger;
        rawDependency = null;
        TimberNoCheat.getInstance().registerListener(debugger);
        for(String setting : settings)
            this.settings.put(setting, new HashMap<>());
    }

    public static void checkDependencies(){
        for(Debuggers debugger : values())
            if(!debugger.isExternal() && StringUtil.isNotEmpty(debugger.rawDependency)) {
               // System.out.println(debugger + " " + debugger.rawDependency + " " + CheckManager.getInstance());
                debugger.dependency = CheckManager.getInstance().getCheckByString(debugger.rawDependency);
                if(debugger.dependency == null){
                    TimberNoCheat.getInstance().getLogger().info("Der Debugger '" + debugger.name() + "' wurde disabled da er kein External ist und seine Dependecy(" + debugger.rawDependency + ") nicht geladen wurde!");
                    debugger.disabled = true;
                }
            }
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

    public Check getDependency() {
        return dependency;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public ExternalDebugger getDebugger() {
        return debugger;
    }
}
