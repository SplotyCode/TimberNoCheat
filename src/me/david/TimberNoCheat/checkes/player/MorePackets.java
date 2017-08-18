package me.david.TimberNoCheat.checkes.player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import me.david.api.utils.DateTimeUtil;
import me.ezjamo.helios.packets.events.PacketPlayerEvent;
import me.ezjamo.helios.packets.events.PacketPlayerType;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.AbstractMap;

public class MorePackets extends Check {

    public MorePackets(){
        super("MorePackets", Category.PLAYER);
        TimberNoCheat.instance.protocolmanager.addPacketListener(new PacketAdapter(TimberNoCheat.instance, new PacketType[]{PacketType.Play.Client.POSITION_LOOK}) {
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if(p != null) {
                    e.setCancelled(check(p));
                }
            }
        });
        TimberNoCheat.instance.protocolmanager.addPacketListener(new PacketAdapter(TimberNoCheat.instance, new PacketType[]{PacketType.Play.Client.LOOK}) {
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if(p != null) {
                    e.setCancelled(check(p));
                }
            }
        });
        TimberNoCheat.instance.protocolmanager.addPacketListener(new PacketAdapter(TimberNoCheat.instance, new PacketType[]{PacketType.Play.Client.POSITION}) {
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if(p != null) {
                    e.setCancelled(check(p));
                }
            }
        });
        TimberNoCheat.instance.protocolmanager.addPacketListener(new PacketAdapter(TimberNoCheat.instance, new PacketType[]{PacketType.Play.Client.FLYING}) {
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if(p != null) {
                    e.setCancelled(check(p));
                }
            }
        });
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e) {
        blacklistadd(e.getPlayer());
    }

    @EventHandler
    public void PlayerChangedWorld(PlayerChangedWorldEvent e) {
        blacklistadd(e.getPlayer());
    }

    @EventHandler
    public void PlayerRespawn(PlayerRespawnEvent e) {
        blacklistadd(e.getPlayer());
    }
    public void blacklistadd(Player p){
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        pd.setMorepacketblacklist(true);
        pd.setMorepacketsblacklist2(true);
        Bukkit.getScheduler().runTaskLater(TimberNoCheat.instance, new Runnable() {
            @Override
            public void run() {
                pd.setMorepacketsblacklist2(false);
            }
        }, TimberNoCheat.instance.settings.player_morepackets_worlddownloadingdelayinticks);
    }
    public boolean check(Player p){
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return false;
        }
        if(p.getGameMode() == GameMode.CREATIVE){
            return false;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        int count = pd.getMorepackets().getKey();
        long time = pd.getMorepackets().getValue();

        if(pd.getLastpacket() != 0){
            long delay = System.currentTimeMillis() - pd.getLastpacket();
            if(delay >= TimberNoCheat.instance.settings.player_morepackets_blacklistadd) {
                pd.setMorepacketblacklist(true);
            } else if(delay > TimberNoCheat.instance.settings.player_morepackets_blacklistremove) {
                pd.setMorepacketblacklist(false);
            }
        }

        if(!pd.isMorepacketblacklist() && !pd.isMorepacketsblacklist2()) {
            ++count;
            if(pd.getLastpacket() != 0 && DateTimeUtil.elapsed(time, TimberNoCheat.instance.settings.player_morepackets_elapsed)) {
                if(count > TimberNoCheat.instance.settings.player_morepackets_maxpackets) {
                    //flag
                    TimberNoCheat.checkmanager.notify(this, p, " §6PACKETS: §b" + count);
                    pd.setMorepackets(new AbstractMap.SimpleEntry(0, System.currentTimeMillis()));
                    pd.setLastpacket(System.currentTimeMillis());
                    return true;
                }
                count = 0;
                time = System.currentTimeMillis();
            }
        }

        pd.setMorepackets(new AbstractMap.SimpleEntry(count, time));
        pd.setLastpacket(System.currentTimeMillis());
        return false;
    }
}
