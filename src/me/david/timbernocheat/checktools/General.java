package me.david.timbernocheat.checktools;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.api.utils.player.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

public class General implements Listener, Runnable  {

    public General(){
        Bukkit.getScheduler().runTaskTimer(TimberNoCheat.getInstance(), this, 1, 1);
    }

    @Override
    public void run() {
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            if(TimberNoCheat.getCheckManager().isvalid_create(player)) {
                GeneralValues generals = TimberNoCheat.getCheckManager().getPlayerdata(player).getGenerals();
                if(PlayerUtil.isOnGround(player)) generals.ticksInAir = 0;
                else generals.ticksInAir++;
            }
        }
    }

    public static class GeneralValues {

        private ArrayList<String> messages;
        private long lastMove;
        private long lastRealMove;
        private Location lastLocation;
        private Location loginLocation;
        private long lastItemClick;
        private Location lastOnGround;
        private int ticksInAir;

        public GeneralValues(){
            messages = new ArrayList<>();
            lastLocation = null;
            loginLocation = null;
            lastMove = System.currentTimeMillis()-15000L;
            lastRealMove = System.currentTimeMillis()-15000L;
            lastItemClick = System.currentTimeMillis()-15000L;
            lastOnGround = null;
            ticksInAir = 0;
        }

        public Location getLastOnGround() {
            return lastOnGround;
        }

        public ArrayList<String> getMessages() {
            return messages;
        }

        public long getLastMove() {
            return lastMove;
        }

        public long getLastRealMove() {
            return lastRealMove;
        }

        public Location getLastLocation() {
            return lastLocation;
        }

        public Location getLoginLocation() {
            return loginLocation;
        }

        public long getLastItemClick() {
            return lastItemClick;
        }

        public int getTicksInAir() {
            return ticksInAir;
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void message(AsyncPlayerChatEvent event){
        if(!event.isCancelled() && TimberNoCheat.getCheckManager().isvalid_create(event.getPlayer()))
            TimberNoCheat.getCheckManager().getPlayerdata(event.getPlayer()).getGenerals().messages.add(event.getMessage());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event){
        if(TimberNoCheat.getCheckManager().isvalid_create(event.getPlayer()))
            TimberNoCheat.getCheckManager().getPlayerdata(event.getPlayer()).getGenerals().loginLocation = event.getPlayer().getLocation();
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event){
        if(TimberNoCheat.getCheckManager().isvalid_create(event.getPlayer())){
            PlayerData pd = TimberNoCheat.getCheckManager().getPlayerdata(event.getPlayer());
            if(PlayerUtil.isOnGround(event.getPlayer())){
                pd.getGenerals().lastOnGround = event.getPlayer().getLocation();
            }
            pd.getGenerals().lastMove = System.currentTimeMillis();
            Location to = event.getTo();
            Location fr = event.getFrom();

            if(!(to.getX() == fr.getX() && to.getY() == fr.getY() && to.getZ() == to.getZ())) pd.getGenerals().lastRealMove = System.currentTimeMillis();
            pd.getGenerals().lastLocation = to;
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onItemClick(final InventoryClickEvent event){
        final Player player = (Player) event.getWhoClicked();
        if(TimberNoCheat.getCheckManager().isvalid_create(player))
            TimberNoCheat.getCheckManager().getPlayerdata(player).getGenerals().lastItemClick = System.currentTimeMillis();
    }

}
