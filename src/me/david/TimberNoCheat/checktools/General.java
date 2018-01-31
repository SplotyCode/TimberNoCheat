package me.david.TimberNoCheat.checktools;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import me.david.api.utils.player.PlayerUtil;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

public class General implements Listener {

    public static class GeneralValues {

        private ArrayList<String> messages;
        private long lastMove;
        private long lastRealMove;
        private Location lastLocation;
        private Location loginLocation;
        private long lastItemClick;
        private Location lastOnGround;

        public GeneralValues(){
            messages = new ArrayList<>();
            lastLocation = null;
            loginLocation = null;
            lastMove = System.currentTimeMillis()-15000L;
            lastRealMove = System.currentTimeMillis()-15000L;
            lastItemClick = System.currentTimeMillis()-15000L;
            lastOnGround = null;
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
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void message(AsyncPlayerChatEvent event){
        if(!event.isCancelled() && TimberNoCheat.checkmanager.isvalid_create(event.getPlayer()))
            TimberNoCheat.checkmanager.getPlayerdata(event.getPlayer()).getGenerals().messages.add(event.getMessage());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event){
        if(TimberNoCheat.checkmanager.isvalid_create(event.getPlayer()))
            TimberNoCheat.checkmanager.getPlayerdata(event.getPlayer()).getGenerals().loginLocation = event.getPlayer().getLocation();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent event){
        if(!event.isCancelled() && TimberNoCheat.checkmanager.isvalid_create(event.getPlayer())){
            PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(event.getPlayer());
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
}
