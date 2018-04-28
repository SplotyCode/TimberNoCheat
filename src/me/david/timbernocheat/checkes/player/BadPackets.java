package me.david.timbernocheat.checkes.player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.david.api.objects.Pair;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.debug.Scheduler;
import me.david.timbernocheat.runnable.TimberScheduler;
import me.david.timbernocheat.runnable.Tps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

public class BadPackets extends Check {

    public BadPackets(){
        super("BadPackets", Category.PLAYER);
        register(new TimberScheduler(Scheduler.BADPACKETS, () -> Bukkit.getOnlinePlayers().stream().filter(player ->
            TimberNoCheat.getCheckManager().isvalid_create(player)).forEach(player -> {
                PlayerData pd = TimberNoCheat.getCheckManager().getPlayerdata(player);
                int tick = Tps.tickCount-3;
                Pair<Double, Integer> data = pd.getMovePackets().get(tick);
                if (data != null && data.getTwo() > 1) {
                    int toMany = data.getTwo()-1;
                    double skiped = 0;
                    for (Map.Entry<Integer, Pair<Long, Pair<Double, Integer>>> pair : pd.getMovePackets().getMap().entrySet()) {
                        if (pair.getKey() == tick) break;
                        Pair<Double, Integer> dat = pair.getValue().getTwo();
                        if (dat.getTwo() == 0) skiped+= dat.getOne()/20;
                    }
                    if (toMany-Math.floor(skiped) > 0)
                        if (updateVio(this, player, (toMany-skiped) * 1.2))
                            player.teleport(pd.getGenerals().getLastLocations(tick));
                }
            }
        )).startTimer(1));

        register(new PacketAdapter(TimberNoCheat.getInstance(), PacketType.Play.Client.LOOK, PacketType.Play.Client.POSITION_LOOK, PacketType.Play.Client.POSITION) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                final Player player = event.getPlayer();
                if(!TimberNoCheat.getCheckManager().isvalid_create(player)) return;
                PlayerData pd = TimberNoCheat.getCheckManager().getPlayerdata(player);
                Pair<Double, Integer> data = pd.getMovePackets().get(Tps.tickCount);
                if (data == null)
                    pd.getMovePackets().put(Tps.tickCount, new Pair<>(Tps.getTPS(5), 1));
                else {
                    if (Tps.getTPS(5) < data.getOne())
                        data.setOne(Tps.getTPS(5));
                    data.setTwo(data.getTwo()+1);
                }
            }
        });
    }

}
