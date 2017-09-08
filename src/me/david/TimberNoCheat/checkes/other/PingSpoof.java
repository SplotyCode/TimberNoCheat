package me.david.TimberNoCheat.checkes.other;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.api.utils.NumberUtil;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class PingSpoof extends Check {

    private final int maxrealping;
    private final int movespeed;
    private final int interactspeed;
    public PingSpoof(){
        super("PingSpoof", Category.OTHER);
        maxrealping = getInt("maxrealping");
        movespeed = getInt("move_checkverscheinlichkeit");
        interactspeed = getInt("interact_checkverscheinlichkeit");
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
    public void check(Player p ){
        try{
            Process cmd = Runtime.getRuntime().exec("ping -c 1 " +p.getAddress().getAddress().getHostAddress());
            cmd.waitFor();
            BufferedReader r = new BufferedReader(new InputStreamReader(cmd.getInputStream(), StandardCharsets.UTF_8));
            String str = null;
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
                updatevio(this, p, realping-ping, " §6PING: §b" + ping, " §6REALPING: §b" + realping);
                //TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6PING: §b" + ping, " §6REALPING: §b" + realping);
            }
        }catch (IOException | InterruptedException ex){
            ex.printStackTrace();
        }
    }
}
