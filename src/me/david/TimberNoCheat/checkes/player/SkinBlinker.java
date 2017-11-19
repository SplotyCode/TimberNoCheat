package me.david.TimberNoCheat.checkes.player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import org.bukkit.entity.Player;

public class SkinBlinker extends Check {

    final boolean sprint;
    final boolean sneak;
    final boolean block;
    final boolean sleep;
    final boolean cursor;
    final long move_delay;
    public SkinBlinker(){
        super("SkinBlinker", Category.PLAYER);
        sprint = getBoolean("sprint");
        sneak = getBoolean("sneak");
        block = getBoolean("block");
        sleep = getBoolean("sleep");
        cursor = getBoolean("cursor");
        move_delay = getLong("move_delay");
        TimberNoCheat.instance.protocolmanager.addPacketListener(new PacketAdapter(TimberNoCheat.instance, PacketType.Play.Client.SETTINGS) {
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if(p != null) {
                    e.setCancelled(check(p));
                }
            }
        });
    }
    public boolean check(Player p){
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return false;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        long delay = System.currentTimeMillis() - pd.getLastmove();
        if(delay < move_delay){
            updatevio(this, p, 1, " §6CHECK: §bMOVE", " §6DELAY: §b" + delay);
            //TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bMOVE", " §6DELAY: §b" + delay);
            return true;
        }
        if(p.isSneaking() && sneak){
            updatevio(this, p, 1, " §6CHECK: §bSNEAKING");
            //TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bSNEAKING");
            return true;
        }
        if(p.isSprinting() && sprint){
            //TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bSPRINT");
            updatevio(this, p, 1, " §6CHECK: §bSPRINT");
            return true;
        }
        if(p.isBlocking() && block){
            updatevio(this, p, 1, " §6CHECK: §bBLOCK");
            //TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bBLOCK");
            return true;
        }
        if(p.isSleeping() && sleep){
            updatevio(this, p, 1, " §6CHECK: §bSLEEP");
            //TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bSLEEP");
            return true;
        }
        return false;
    }
}
