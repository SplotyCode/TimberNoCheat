package me.david.timbernocheat.api;

import me.david.timbernocheat.checkbase.Violation;
import org.bukkit.entity.Player;

public class PunishmentEvent extends TNCCancelEvent {

    private Player player;
    private Violation.ViolationTypes type;

    public PunishmentEvent(Player player, Violation.ViolationTypes type) {
        this.player = player;
        this.type = type;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Violation.ViolationTypes getType() {
        return type;
    }

    public void setType(Violation.ViolationTypes type) {
        this.type = type;
    }
}
