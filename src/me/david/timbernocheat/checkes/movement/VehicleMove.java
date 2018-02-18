package me.david.timbernocheat.checkes.movement;

import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import comphenix.packetwrapper.WrapperPlayClientSteerVehicle;
import comphenix.packetwrapper.WrapperPlayClientVehicleMove;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import me.david.timbernocheat.checkmanager.PlayerData;
import me.david.api.utils.player.PlayerUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class VehicleMove extends Check {

    private final boolean boat;
    private final boolean horse;

    public VehicleMove() {
        super("VehicleMove", Category.MOVEMENT);
        boat = getBoolean("boat");
        horse = getBoolean("horse");
        register(new PacketAdapter(TimberNoCheat.instance, WrapperPlayClientSteerVehicle.TYPE, WrapperPlayClientVehicleMove.TYPE) {
            public void onPacketReceiving(PacketEvent event) {
                if(!TimberNoCheat.checkmanager.isvalid_create(event.getPlayer())) return;
                PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(event.getPlayer());
                if (event.getPacketType() == WrapperPlayClientSteerVehicle.TYPE) {
                    WrapperPlayClientSteerVehicle packet = new WrapperPlayClientSteerVehicle(event.getPacket());
                    if (packet.isUnmount() && pd.getVehicley() != -1) pd.setVehicley(-1);
                }
                if (event.getPacketType() == WrapperPlayClientVehicleMove.TYPE) {
                    WrapperPlayClientVehicleMove packet = new WrapperPlayClientVehicleMove(event.getPacket());
                    Player p = event.getPlayer();
                    if (p.isInsideVehicle()) {
                        Entity vehicle = p.getVehicle();
                        EntityType type = vehicle.getType();
                        if ((type == EntityType.BOAT && !boat) || (type == EntityType.HORSE && !horse) || (type != EntityType.BOAT && type != EntityType.HORSE)) return;
                        double y = packet.getY();
                        double lastYPos = pd.getVehicley() != -1?pd.getVehicley():y;
                        double yDiff = y - lastYPos;
                        if (yDiff > 0.005D) {
                            if ((((isOnWater(p, -1.0D) ? 0 : 1) & (isOnWater(p, 0.0D) ? 0 : 1)) != 0) && isOnWater(p, -0.5D)) {
                                pd.setVehicledif(pd.getVehicledif()==-1?1:pd.getVehicledif()+1);
                                if (pd.getVehicledif() / (type == EntityType.BOAT ? 4 : 8) > 0) updateVio(VehicleMove.this, p, 1);
                            } else pd.setVehicledif(-1);
                        } else pd.setVehicledif(-1);
                        pd.setVehicley(y);
                    }
                }
            }
        });
    }

    private boolean isOnWater(Player p, double depth) {
        Location loc = p.getLocation().subtract(0.0D, depth, 0.0D);
        return PlayerUtil.standOnBlockLocation(loc, Material.STATIONARY_WATER).getBlock().getType() == Material.STATIONARY_WATER;
    }


}
