package me.david.timbernocheat.checkes.clientchanel;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class Vape extends Check implements PluginMessageListener {

    public Vape() {
        super("Vape", Category.CLIENT_CHANEL);
        Bukkit.getMessenger().registerIncomingPluginChannel(TimberNoCheat.getInstance(), "LOLIMAHCKER", this);
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event){
        final Player p = event.getPlayer();
        if(TimberNoCheat.getCheckManager().isvalid_create(p)) p.sendMessage("§8 §8 §1 §3 §3 §7 §8 ");
    }

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        if(TimberNoCheat.getCheckManager().isvalid_create(player)) updateVio(this, player, 1);
    }
}
