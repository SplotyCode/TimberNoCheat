package me.david.timbernocheat.checkes.movement.fly.checks;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import comphenix.packetwrapper.WrapperPlayClientAbilities;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.Disable;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.checkes.movement.fly.AbstractFlyCheck;
import me.david.timbernocheat.checkes.movement.fly.Fly;
import me.david.timbernocheat.checkes.movement.fly.FlyData;
import me.david.timbernocheat.checkes.movement.fly.FlyMoveData;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@Disable(reason = "This Check is handled by the Main Flying Check")
public class Ability extends AbstractFlyCheck {
    
    public Ability(Fly flyCheck) {
        super("Ability", flyCheck);
        register(new PacketAdapter(TimberNoCheat.getInstance(), ListenerPriority.HIGH, PacketType.Play.Client.ABILITIES) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                WrapperPlayClientAbilities packet = new WrapperPlayClientAbilities(event.getPacket());
                if (player == null) return;

                GameMode gameMode = player.getGameMode();
                if (CheckManager.getInstance().isvalid_create(player)) {
                    if (gameMode != GameMode.CREATIVE && gameMode != GameMode.SPECTATOR) {
                        if (packet.canFly()) {
                            if (updateVio(Ability.this, player, 1, "Player just desites that he now can fly!")) {
                                packet.setCanFly(false);
                            }
                        }
                        if (packet.isFlying()) {
                            if (updateVio(Ability.this, player, 1, "Player just desites that he IS flying!")) {
                                packet.setFlying(false);
                            }
                        }
                        if (packet.isInvulnurable()) {
                            if (updateVio(Ability.this, player, 1, "Player just desites that he can not get damaged anymore...")) {
                                packet.setInvulnurable(false);
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onMove(FlyData flyData, Player player, PlayerData playerData, FlyMoveData move) {

    }
}
