package me.david.timbernocheat.api;

import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.Violation;
import org.bukkit.entity.Player;

/**
 * Called everytime a player gets punished
 */
public class PunishmentEvent extends TNCCancelEvent {

    private final Player player;
    private final Violation.ViolationTypes type;
    private final Check check;

    public PunishmentEvent(Player player, Violation.ViolationTypes type, Check check) {
        this.player = player;
        this.type = type;
        this.check = check;
    }

    public Check getCheck() {
        return check;
    }

    public Player getPlayer() {
        return player;
    }

    public Violation.ViolationTypes getType() {
        return type;
    }

}
