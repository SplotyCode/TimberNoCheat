package me.david.timbernocheat.checkes.movement.speed;

import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.Disable;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.checktools.FalsePositive;
import me.david.timbernocheat.checktools.General;
import org.bukkit.entity.Player;

@Disable(reason = "Handled by Main Speed Check")
public abstract class AbstractSpeed extends Check {

    private Speed speed;

    public AbstractSpeed(Speed speed, String name) {
        super(name, Category.MOVEMENT, true, speed);
        this.speed = speed;
    }

    public abstract void move(SpeedMoveData move, Player player, PlayerData data, FalsePositive.FalsePositiveChecks falsePositive, General.GeneralValues general);
}
