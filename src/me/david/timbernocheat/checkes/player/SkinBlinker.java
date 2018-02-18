package me.david.timbernocheat.checkes.player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import me.david.timbernocheat.checkmanager.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SkinBlinker extends Check {

    private final boolean sprint;
    private final boolean sneak;
    private final boolean block;
    private final boolean sleep;
    private final boolean cursor;
    private final long move_delay;

    public SkinBlinker(){
        super("SkinBlinker", Category.PLAYER);
        sprint = getBoolean("sprint");
        sneak = getBoolean("sneak");
        block = getBoolean("block");
        sleep = getBoolean("sleep");
        cursor = getBoolean("cursor");
        move_delay = getLong("move_delay");

        register(new PacketAdapter(TimberNoCheat.instance, PacketType.Play.Client.SETTINGS) {
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if(p != null) {
                    e.setCancelled(check(p));
                }
            }
        });
    }

    private boolean check(Player p){
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return false;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        long delay = System.currentTimeMillis() - pd.getGenerals().getLastMove();
        if(delay < move_delay){
            updatevio(this, p, 1, " §6CHECK: §bMOVE", " §6DELAY: §b" + delay);
            return true;
        }
        if(p.isSneaking() && sneak){
            updatevio(this, p, 1, " §6CHECK: §bSNEAKING");
            return true;
        }
        if(p.isSprinting() && sprint){
            updatevio(this, p, 1, " §6CHECK: §bSPRINT");
            return true;
        }
        if(p.isBlocking() && block){
            updatevio(this, p, 1, " §6CHECK: §bBLOCK");
            return true;
        }
        if(p.isSleeping() && sleep){
            updatevio(this, p, 1, " §6CHECK: §bSLEEP");
            return true;
        }
        if(cursor && p.getItemOnCursor().getType() != Material.AIR) {
            updatevio(this, p, 1, " §6CHECK: §bCURSOR");
            return true;
        }

        return false;
    }
}
