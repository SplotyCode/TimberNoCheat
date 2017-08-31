package me.david.TimberNoCheat.checkes.movement;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import org.bukkit.entity.Player;

public class Rotate extends Check {

    public Rotate(){
        super("Rotate", Category.MOVEMENT);
        //setViofornotify(7);
        //setViodelay(8000);
        TimberNoCheat.instance.protocolmanager.addPacketListener(new PacketAdapter(TimberNoCheat.instance, new PacketType[]{PacketType.Play.Client.POSITION_LOOK}) {
             public void onPacketReceiving(PacketEvent event) {
                 Player p = event.getPlayer();
                 if (p == null || !TimberNoCheat.checkmanager.isvalid_create(p)) {
                     return;
                 }
                 PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
                 float pitch = event.getPacket().getFloat().read(1);
                 float yaw = event.getPacket().getFloat().read(0);
                 //System.out.println("1 " + event.getPacket().getFloat().read(1) + " " + event.getPacket().getFloat().read(0) + " " + pd.getLastyaw() + " " + pd.getLastpitch());
                 if (pd.getLastpitch() == pitch && pd.getLastyaw() == yaw){
                     updatevio(Rotate.this, p, 1, " §6MODE: §bEQUALS");
                 }
                 pd.setLastpitch(pitch);
                 pd.setLastyaw(yaw);
             }
        });
        /*TimberNoCheat.instance.protocolmanager.addPacketListener(new PacketAdapter(TimberNoCheat.instance, new PacketType[]{PacketType.Play.Client.LOOK}) {
            public void onPacketReceiving(PacketEvent event) {
                //System.out.println("0 " + event.getPacket().getFloat().read(1) + " " + event.getPacket().getFloat().read(0));
                 /*Player p = event.getPlayer();
                 if (p == null) {
                     return;
                 }
                 if (!TimberNoCheat.checkmanager.isvalid_create(p)) {
                     return;
                 }
                 PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
                 if (pd.getLastpitch() == event.getPacket().getFloat().read(1) || pd.getLastyaw() == event.getPacket().getFloat().read(0)){
                    updatevio(Rotate.this, p, 1, " §6MODE: §bEQUALS");
                 }
                 pd.setLastpitch(event.getPacket().getFloat().read(1));
                 pd.setLastyaw(event.getPacket().getFloat().read(0));
            }
        });*/
    }


}
