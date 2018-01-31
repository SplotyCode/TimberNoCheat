package me.david.TimberNoCheat.checkes.movement;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import comphenix.packetwrapper.WrapperPlayClientLook;
import comphenix.packetwrapper.WrapperPlayClientPosition;
import comphenix.packetwrapper.WrapperPlayClientPositionLook;
import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Rotate extends Check {

    public Rotate(){
        super("Rotate", Category.MOVEMENT);
        //setViofornotify(7);
        //setViodelay(8000);
        register(new PacketAdapter(TimberNoCheat.instance, ListenerPriority.HIGH, PacketType.Play.Client.POSITION_LOOK, PacketType.Play.Client.LOOK) {
             public void onPacketReceiving(PacketEvent event) {
                 Player p = event.getPlayer();
                 if (p == null || !TimberNoCheat.checkmanager.isvalid_create(p)) return;
                 PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
                 PacketType type = event.getPacket().getType();
                 Location lastLoc = pd.getAsyncGenerals().getLastLoc();
                 if(lastLoc == null)return;
                 float yaw = 0;
                 float pitch = 0;
                 if (type == WrapperPlayClientLook.TYPE){
                    WrapperPlayClientLook wrapper = new WrapperPlayClientLook(event.getPacket());
                    yaw = wrapper.getYaw();
                    pitch = wrapper.getPitch();
                 }else if(type == WrapperPlayClientPositionLook.TYPE){
                     WrapperPlayClientPositionLook wrapper = new WrapperPlayClientPositionLook(event.getPacket());
                     yaw = wrapper.getYaw();
                     pitch = wrapper.getPitch();
                 }
                 if(!(yaw-lastLoc.getYaw() != 0 || pitch-lastLoc.getPitch() != 0)){
                     updatevio(Rotate.this, p, 1);
                 }
             }
        });
    }


}
