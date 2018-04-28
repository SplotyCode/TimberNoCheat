package me.david.timbernocheat.checkes.player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
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
    private final int maxPackets;
    private final long blackListAdd;
    private final long blackListRemove;
    private final long worldDownloadingDelayTicks;

    public MorePackets(){
        super("MorePackets", Category.PLAYER);
        elapsed = getLong("elapsed");
        maxPackets = getInt("maxPackets");
        blackListAdd = getLong("blackListAdd");
        blackListRemove = getLong("blackListRemove");
        worldDownloadingDelayTicks = getLong("worldDownloadingDelayTicks");

        register(new PacketAdapter(TimberNoCheat.getInstance(), PacketType.Play.Client.POSITION_LOOK) {
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                if(player != null) {
                    event.setCancelled(check(player));
                }
            }
        });
        register(new PacketAdapter(TimberNoCheat.getInstance(), PacketType.Play.Client.LOOK) {
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                if(player != null) {
                    event.setCancelled(check(player));
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
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                if(player != null) {
                    event.setCancelled(check(player));
                }
            }
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void PlayerJoin(PlayerJoinEvent event) {
        blacklistAdd(event.getPlayer());
    }

    @EventHandler
    public void PlayerChangedWorld(PlayerChangedWorldEvent event) {
        blacklistAdd(event.getPlayer());
    }

    @EventHandler
    public void PlayerRespawn(PlayerRespawnEvent event) {
        blacklistAdd(event.getPlayer());
    }

    private void blacklistAdd(Player player) {
        if(!CheckManager.getInstance().isvalid_create(player)) {
            return;
        }
        PlayerData pd = CheckManager.getInstance().getPlayerdata(player);
        pd.setMorePacketBlacklist(true);
        pd.setMorePacketBlacklist2(true);
        Bukkit.getScheduler().runTaskLater(TimberNoCheat.getInstance(), () -> pd.setMorePacketBlacklist2(false), worldDownloadingDelayTicks);
    }

    private boolean check(Player player) {
        if(!CheckManager.getInstance().isvalid_create(player)){
            return false;
        }
        if(player.getGameMode() == GameMode.CREATIVE){
            return false;
        }
        PlayerData pd = CheckManager.getInstance().getPlayerdata(player);
        int count = pd.getMorepackets().getKey();
        long time = pd.getMorepackets().getValue();

        if(pd.getLastPacket() != 0){
            long delay = System.currentTimeMillis() - pd.getLastPacket();
            if (delay >= blackListAdd) {
                pd.setMorePacketBlacklist(true);
            } else if (delay > blackListRemove) {
                pd.setMorePacketBlacklist(false);
            }
        }

        if(!pd.isMorePacketBlacklist() && !pd.isMorePacketBlacklist2()) {
            ++count;
            if(pd.getLastPacket() != 0 && DateTimeUtil.elapsed(time, elapsed)) {
                if(count > maxPackets) {
                    pd.setMorepackets(new AbstractMap.SimpleEntry<>(0, System.currentTimeMillis()));
                    pd.setLastPacket(System.currentTimeMillis());
                    return updateVio(this, player, maxPackets -count, " §6PACKETS: §b" + count);
                }
                count = 0;
                time = System.currentTimeMillis();
            }
        }

        pd.setMorepackets(new AbstractMap.SimpleEntry<>(count, time));
        pd.setLastPacket(System.currentTimeMillis());
        return false;
    }
}
