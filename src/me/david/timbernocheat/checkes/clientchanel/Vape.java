package me.david.timbernocheat.checkes.clientchanel;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class Vape extends Check implements PluginMessageListener {

    public Vape() {
        super("Vape", Category.CLIENT_CHANEL);
        Bukkit.getMessenger().registerIncomingPluginChannel(TimberNoCheat.instance, "LOLIMAHCKER", this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        final Player p = event.getPlayer();
        if(TimberNoCheat.checkmanager.isvalid_create(p)) p.sendMessage("§8 §8 §1 §3 §3 §7 §8 ");
    }

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        if(TimberNoCheat.checkmanager.isvalid_create(player)) updatevio(this, player, 1);
    }
}
