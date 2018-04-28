package me.david.timbernocheat.checkes.player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.api.utils.DateTimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.AbstractMap;

public class MorePackets extends Check {

    private final long elapsed;
    private final int maxpackets;
    private final long blacklistadd;
    private final long blacklistremove;
    private final long worlddownloadingdelayinticks;
    public MorePackets(){
        super("MorePackets", Category.PLAYER);
        elapsed = getLong("elapsed");
        maxpackets = getInt("maxpackets");
        blacklistadd = getLong("blacklistadd");
        blacklistremove = getLong("blacklistremove");
        worlddownloadingdelayinticks = getLong("worlddownloadingdelayinticks");
        register(new PacketAdapter(TimberNoCheat.getInstance(), PacketType.Play.Client.POSITION_LOOK) {
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if(p != null) {
                    e.setCancelled(check(p));
                }
            }
        });
        register(new PacketAdapter(TimberNoCheat.getInstance(), PacketType.Play.Client.LOOK) {
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if(p != null) {
                    e.setCancelled(check(p));
                }
            }
        });
        register(new PacketAdapter(TimberNoCheat.getInstance(), PacketType.Play.Client.POSITION) {
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if(p != null) {
                    e.setCancelled(check(p));
                }
            }
        });
        register(new PacketAdapter(TimberNoCheat.getInstance(), PacketType.Play.Client.FLYING) {
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if(p != null) {
                    e.setCancelled(check(p));
                }
            }
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
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
    private void blacklistadd(Player p){
        if(!TimberNoCheat.getCheckManager().isvalid_create(p)){
            return;
        }
        PlayerData pd = TimberNoCheat.getCheckManager().getPlayerdata(p);
        pd.setMorePacketBlacklist(true);
        pd.setMorePacketBlacklist2(true);
        Bukkit.getScheduler().runTaskLater(TimberNoCheat.getInstance(), () -> pd.setMorePacketBlacklist2(false), worlddownloadingdelayinticks);
    }
    private boolean check(Player p){
        if(!TimberNoCheat.getCheckManager().isvalid_create(p)){
            return false;
        }
        if(p.getGameMode() == GameMode.CREATIVE){
            return false;
        }
        PlayerData pd = TimberNoCheat.getCheckManager().getPlayerdata(p);
        int count = pd.getMorepackets().getKey();
        long time = pd.getMorepackets().getValue();

        if(pd.getLastPacket() != 0){
            long delay = System.currentTimeMillis() - pd.getLastPacket();
            if(delay >= blacklistadd) {
                pd.setMorePacketBlacklist(true);
            } else if(delay > blacklistremove) {
                pd.setMorePacketBlacklist(false);
            }
        }

        if(!pd.isMorePacketBlacklist() && !pd.isMorePacketBlacklist2()) {
            ++count;
            if(pd.getLastPacket() != 0 && DateTimeUtil.elapsed(time, elapsed)) {
                if(count > maxpackets) {
                    //flag
                    updateVio(this, p, maxpackets-count, " §6PACKETS: §b" + count);
                    //TimberNoCheat.getCheckManager().notify(this, p, " §6PACKETS: §b" + count);
                    pd.setMorepackets(new AbstractMap.SimpleEntry<Integer, Long>(0, System.currentTimeMillis()));
                    pd.setLastPacket(System.currentTimeMillis());
                    return true;
                }
                count = 0;
                time = System.currentTimeMillis();
            }
        }

        pd.setMorepackets(new AbstractMap.SimpleEntry<Integer, Long>(count, time));
        pd.setLastPacket(System.currentTimeMillis());
        return false;
    }
}
