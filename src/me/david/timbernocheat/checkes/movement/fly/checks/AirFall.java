package me.david.timbernocheat.checkes.movement.fly.checks;

import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.checkes.movement.fly.Fly;
import me.david.timbernocheat.checkes.movement.fly.AbstractFlyCheck;
import me.david.timbernocheat.checkes.movement.fly.FlyData;
import me.david.timbernocheat.checkes.movement.fly.FlyMoveData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class AirFall extends AbstractFlyCheck {

    public AirFall(Fly flyCheck) {
        super("AirFall", flyCheck);
    }

    @Override
    public void onMove(FlyData flyData, Player player, PlayerData playerData, FlyMoveData move) {}

    @EventHandler
    public void onFall(final EntityDamageEvent event){
        if(event.getEntityType() != EntityType.PLAYER || event.getCause() != EntityDamageEvent.DamageCause.FALL)return;
        final Player player = (Player) event.getEntity();
        if (!CheckManager.getInstance().isvalid_create(player)) return;
        FlyData data = CheckManager.getInstance().getPlayerdata(player).getFlyData();
        if(!data.getLastMove().isToGround()) if(updateVio(this, player, 1, "Want me to believe he fall on air ^^"))
            setBack(player);
    }
}
