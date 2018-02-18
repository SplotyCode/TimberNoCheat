package me.david.timbernocheat.checkes.other;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import me.david.api.utils.NumberUtil;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class PingSpoof extends Check {

    private final int maxrealping;
    private final int movespeed;
    private final int interactspeed;
    private final boolean keepalive;

    public PingSpoof(){
        super("PingSpoof", Category.OTHER);
        maxrealping = getInt("maxrealping");
        movespeed = getInt("move_checkverscheinlichkeit");
        interactspeed = getInt("interact_checkverscheinlichkeit");
        keepalive = getBoolean("keepalive");
        if(keepalive) {
            register(new PacketAdapter(TimberNoCheat.instance, ListenerPriority.NORMAL, PacketType.Play.Client.KEEP_ALIVE) {
                public void onPacketReceiving(PacketEvent event) {
                    Player p = event.getPlayer();
                    if (!TimberNoCheat.checkmanager.isvalid_create(p) || local(p.getAddress().getAddress())) return;
                    if (TimberNoCheat.checkmanager.getping(p) == 0) {
                        addCount(p, "keepalive");
                        if (getCount(p, "keepalive") / 5 > 0)
                            updatevio(PingSpoof.this, p, getCount(p, "keepalive") * 1.4, " §6MODE: §bKEEK_ALIVE");
                    } else resetCount(p, "keepalive");
                }
            });
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if(!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer())){
            return;
        }
        if(NumberUtil.randInt(0, movespeed) == 1){
            check(e.getPlayer());
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer())){
            return;
        }
        if(NumberUtil.randInt(0, interactspeed) == 1){
            check(e.getPlayer());
        }
    }
    private void check(Player p){
        try{
            Process cmd = Runtime.getRuntime().exec("ping -c 1 " +p.getAddress().getAddress().getHostAddress());
            cmd.waitFor();
            BufferedReader r = new BufferedReader(new InputStreamReader(cmd.getInputStream(), StandardCharsets.UTF_8));
            String str;
            StringBuilder sb = new StringBuilder(8192);
            while ((str = r.readLine()) != null) {
                sb.append(str);
            }
            double realping = Double.valueOf(sb.toString().split(" ")[12].substring(5));
            double ping = ((CraftPlayer)p).getHandle().ping;
            if(realping >= maxrealping){
                return;
            }
            if(ping>realping){
                updatevio(this, p, realping-ping, " §6MODE: §bNORMAL", " §6PING: §b" + ping, " §6REALPING: §b" + realping);
                //TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6PING: §b" + ping, " §6REALPING: §b" + realping);
            }
        }catch (IOException | InterruptedException ex){
            ex.printStackTrace();
        }
    }

    private boolean local(InetAddress addr) {
        if (addr.isAnyLocalAddress() || addr.isLoopbackAddress()) return true;
        try {
            return NetworkInterface.getByInetAddress(addr) != null;
        } catch (SocketException e) {
            return false;
        }
    }
}
