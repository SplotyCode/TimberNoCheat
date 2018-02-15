package me.david.TimberNoCheat.debug;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.debug.debuggers.MoveVelocity;
import me.david.TimberNoCheat.debug.debuggers.OnGround;
import me.david.TimberNoCheat.debug.debuggers.OnGroundCheckDiff;

public enum  Debuggers {

    RANGE,
    ATTACK_RANGE,
    HITBOX,
    SPEED,
    PATTERN_SPEED,
    RAY_TRACE,
    MOVE_VELOCITY(new MoveVelocity()),
    ONGROUND(new OnGround()),
    ONGROUNDCHECKDIF(new OnGroundCheckDiff());

    private final ExternalDebuger debuger;
    private final boolean external;

    Debuggers(){
        debuger = null;
        external = false;
    }

    Debuggers(ExternalDebuger debuger){
        external = true;
        this.debuger = debuger;
        TimberNoCheat.instance.registerListener(debuger);
    }

    public boolean isExternal() {
        return external;
    }

    public ExternalDebuger getDebuger() {
        return debuger;
    }
}
