package me.david.TimberNoCheat.checkes.other;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.api.utils.NumberUtil;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class PingSpoof extends Check {

    public PingSpoof(){
        super("PingSpoof", Category.OTHER);
    }

    @EventHandler
    public void oninteract(PlayerInteractEvent e){
        final Player p = e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        if(NumberUtil.randInt(0, 10) != 1){
            return;
        }
        try{
            Process cmd = Runtime.getRuntime().exec("ping -c 1 " +p.getAddress().getAddress().getHostAddress());
            cmd.waitFor();
            InputStream is = cmd.getInputStream();
            BufferedReader r = new BufferedReader(new InputStreamReader(cmd.getInputStream(), StandardCharsets.UTF_8));
            String str = null;
            StringBuilder sb = new StringBuilder(8192);
            while ((str = r.readLine()) != null) {
                sb.append(str);
            }
            double realping = Double.valueOf(sb.toString().split(" ")[12].substring(5));
            double ping = ((CraftPlayer)p).getHandle().ping;
            if(ping-realping > 20){
                TimberNoCheat.checkmanager.notify(this, e.getPlayer(), " §6PING: §b" + ping, " §6REALPING: §b" + realping);
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }catch (InterruptedException ex2){
            ex2.printStackTrace();
        }
    }
}
