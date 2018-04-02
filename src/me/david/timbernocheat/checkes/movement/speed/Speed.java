package me.david.timbernocheat.checkes.movement.speed;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.checktools.FalsePositive;
import me.david.timbernocheat.checktools.General;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class Speed extends Check {

    public Speed() {
        super("Speed", Category.MOVEMENT);
        registerChilds(new SpeedXZLimit(this), new PatternCheck(this));
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        final Player player = event.getPlayer();
        if (!TimberNoCheat.getCheckManager().isvalid_create(player)) return;
        final PlayerData playerData = TimberNoCheat.getCheckManager().getPlayerdata(player);
        final SpeedMoveData move = new SpeedMoveData(event);
        final FalsePositive.FalsePositiveChecks falsePositive = playerData.getFalsepositives();
        final General.GeneralValues generals = playerData.getGenerals();

        if(player.getAllowFlight() || falsePositive.hasVehicle(60)) return;

        TimberNoCheat.getInstance().getMoveprofiler().start("Speed");

        for(Check child : getChilds())
            ((AbstractSpeed) child).move(move, player, playerData, falsePositive, generals);

        TimberNoCheat.getInstance().getMoveprofiler().end();
    }

}
