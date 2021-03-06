package me.david.timbernocheat.checktools;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import comphenix.packetwrapper.WrapperPlayClientLook;
import comphenix.packetwrapper.WrapperPlayClientPosition;
import comphenix.packetwrapper.WrapperPlayClientPositionLook;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.PlayerData;
import org.bukkit.Location;

public class AsyncGeneral {

    public AsyncGeneral(){
        TimberNoCheat.getInstance().getProtocolManager().addPacketListener(new PacketAdapter(TimberNoCheat.getInstance(), ListenerPriority.LOW, PacketType.Play.Client.POSITION_LOOK, PacketType.Play.Client.LOOK, PacketType.Play.Client.POSITION, PacketType.Play.Client.FLYING) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketType type = event.getPacket().getType();
                if(!CheckManager.getInstance().isvalid_create(event.getPlayer()))return;
                PlayerData pd = CheckManager.getInstance().getPlayerdata(event.getPlayer());
                Location loc = pd.getAsyncGenerals().getLastLoc();
                if(loc == null) loc = event.getPlayer().getLocation();
                if (type == WrapperPlayClientLook.TYPE){
                    WrapperPlayClientLook wrapper = new WrapperPlayClientLook(event.getPacket());
                    loc.setPitch(wrapper.getPitch());
                    loc.setYaw(wrapper.getYaw());
                }else if(type == WrapperPlayClientPosition.TYPE){
                    WrapperPlayClientPosition wrapper = new WrapperPlayClientPosition(event.getPacket());
                    loc.setX(wrapper.getX());
                    loc.setZ(wrapper.getZ());
                    loc.setY(wrapper.getY());
                }else if(type == WrapperPlayClientPositionLook.TYPE){
                    WrapperPlayClientPositionLook wrapper = new WrapperPlayClientPositionLook(event.getPacket());
                    loc.setX(wrapper.getX());
                    loc.setZ(wrapper.getZ());
                    loc.setY(wrapper.getY());
                    loc.setPitch(wrapper.getPitch());
                    loc.setYaw(wrapper.getYaw());
                }
                pd.getAsyncGenerals().lastLoc = loc;
            }
        });
    }

    public static class AsyncGeneralValues {
        private Location lastLoc;

        public AsyncGeneralValues(){
            lastLoc = null;
        }

        public Location getLastLoc() {
            return lastLoc;
        }
    }
}
