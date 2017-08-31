package me.david.TimberNoCheat.checkes.player;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class BadPackets extends Check {

    int max_moves;
    public BadPackets(){
        super("BadPackets", Category.PLAYER);
        max_moves = getInt("max_moves");
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        final Player p = e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        pd.setMoveslastticks(pd.getMoveslastticks()+1);
        Bukkit.getScheduler().runTaskLater(TimberNoCheat.instance, new Runnable() {
            @Override
            public void run() {
                pd.setMoveslastticks(pd.getMoveslastticks()-1);
            }
        }, 3);
        if(pd.getMoveslastticks() > max_moves){
            updatevio(this, p, (pd.getMoveslastticks()-TimberNoCheat.instance.settings.player_badpackets_maxmoves)/2);
            //TimberNoCheat.checkmanager.notify(this, p, " §6LEVEL: §b" + (pd.getMoveslastticks()-TimberNoCheat.instance.settings.player_badpackets_maxmoves)/2);
            //pd.setMoveslastticks(0);
        }

    }
}
