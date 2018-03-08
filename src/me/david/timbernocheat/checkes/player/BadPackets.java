package me.david.timbernocheat.checkes.player;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class BadPackets extends Check {

    private final int max_moves;

    public BadPackets(){
        super("BadPackets", Category.PLAYER);
        max_moves = getInt("max_moves");
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        final Player p = e.getPlayer();
        if(!TimberNoCheat.getCheckManager().isvalid_create(p)) return;
        PlayerData pd = TimberNoCheat.getCheckManager().getPlayerdata(p);
        if(!e.getTo().getWorld().getName().equals(e.getFrom().getWorld().getName()))return;
        if(!(e.getTo().getX() != e.getFrom().getX() || e.getTo().getZ() != e.getFrom().getZ() || e.getTo().getY() != e.getFrom().getY()))return;
        pd.setMoveslastticks(pd.getMoveslastticks()+1);
        Bukkit.getScheduler().runTaskLater(TimberNoCheat.getInstance(), () -> pd.setMoveslastticks(pd.getMoveslastticks()-1), 3);
        if(pd.getMoveslastticks() > max_moves){
            updateVio(this, p, (pd.getMoveslastticks()-max_moves)/2);
            //TimberNoCheat.getCheckManager().notify(this, p, " §6LEVEL: §b" + (pd.getMoveslastticks()-TimberNoCheat.getInstance().settings.player_badpackets_maxmoves)/2);
            //pd.setMoveslastticks(0);
        }
    }
}
