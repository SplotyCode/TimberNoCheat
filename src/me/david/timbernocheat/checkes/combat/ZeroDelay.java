package me.david.timbernocheat.checkes.combat;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import comphenix.packetwrapper.WrapperPlayClientBlockDig;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.runnable.Tps;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ZeroDelay extends Check {

    public ZeroDelay() {
        super("ZeroDelay", Category.COBMAT);

        register(new PacketAdapter(TimberNoCheat.getInstance(), PacketType.Play.Client.BLOCK_DIG) {
            public void onPacketReceiving(PacketEvent event) {
                long time = System.currentTimeMillis();
                PacketContainer packet = event.getPacket();
                Player player = event.getPlayer();
                if (player == null || !TimberNoCheat.getCheckManager().isvalid_create(player)) return;
                PlayerData pd = TimberNoCheat.getCheckManager().getPlayerdata(player);

                Location blockLocation = new WrapperPlayClientBlockDig(packet).getBlockLocation(player);
                if (blockLocation.equals(pd.getZeroDelayLocation())) {
                    if (time-pd.getZeroDelayTime() == 0)
                        if (updateVio(ZeroDelay.this, player, 1))
                            pd.setZeroDelayBlocked(Tps.tickCount);
                } else pd.setZeroDelayLocation(blockLocation);
                pd.setZeroDelayTime(time);
            }
        });
    }

    @EventHandler
    public void onHit(final EntityDamageByEntityEvent event) {
        if (event.getDamager().getType() != EntityType.PLAYER) return;
        final Player player = (Player) event.getDamager();
        if (!TimberNoCheat.getCheckManager().isvalid_create(player)) return;

        if (Tps.tickCount - TimberNoCheat.getCheckManager().getPlayerdata(player).getZeroDelayBlocked() < 45) {
            event.setCancelled(true);
        }

    }
}
