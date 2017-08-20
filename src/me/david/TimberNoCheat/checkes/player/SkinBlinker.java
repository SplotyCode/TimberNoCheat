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

    public SkinBlinker(){
        super("SkinBlinker", Category.PLAYER);
        TimberNoCheat.instance.protocolmanager.addPacketListener(new PacketAdapter(TimberNoCheat.instance, new PacketType[]{PacketType.Play.Client.SETTINGS}) {
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
        if(delay < TimberNoCheat.instance.settings.player_skinblinker_movemindelay){
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bMOVE", " §6DELAY: §b" + delay);
            return true;
        }
        if(p.isSneaking() && TimberNoCheat.instance.settings.player_skinblinker_sneak){
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bSNEAKING");
            return true;
        }
        if(p.isSprinting() && TimberNoCheat.instance.settings.player_skinblinker_sprint){
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bSPRINT");
            return true;
        }
        if(p.isBlocking() && TimberNoCheat.instance.settings.player_skinblinker_block){
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bBLOCK");
            return true;
        }
        if(p.isSleeping() && TimberNoCheat.instance.settings.player_skinblinker_sleep){
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bSLEEP");
            return true;
        }
        return false;
    }
}
