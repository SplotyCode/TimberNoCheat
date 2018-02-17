package me.david.TimberNoCheat.command.oreNotify;

import me.david.TimberNoCheat.TimberNoCheat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class OreNotifyManager {

    private HashMap<UUID, OreNotifyData> data = new HashMap<>();
    private HashMap<UUID, HashMap<Material, Integer>> counters = new HashMap<>();

    /* Return the current state of the watcher */
    public boolean toggleNotify(final Player player){
        final UUID uuid = player.getUniqueId();
        OreNotifyData data = this.data.get(uuid);
        if(data == null){
            this.data.put(uuid, new OreNotifyData(true));
            return true;
        }
        data.setActive(!data.isActive());
        return data.isActive();
    }

    public static final Material[] POSSBILE_ORES;

    static {
        POSSBILE_ORES = new Material[]{
                Material.DIAMOND_ORE,
                Material.GOLD_ORE,
                Material.REDSTONE_ORE,
                Material.EMERALD_ORE
        };
    }

    public OreNotifyData getData(final Player player){
        return data.get(player.getUniqueId());
    }

    public int getMaterialCountAndAdd(final Player player, final Material material){
        final UUID uuid = player.getUniqueId();
        HashMap<Material, Integer> count = counters.get(uuid);
        if(count == null){
            counters.put(uuid, new HashMap<>(){{
                put(material, 1);
            }});
            return 1;
        }
        int number = count.get(material);
        number++;
        return number;
    }

    public void notifyOre(final Player player, final Material material){
        final String message = TimberNoCheat.instance.prefix + " Player " + player + " mined " + material + " (" + getMaterialCountAndAdd(player, material) + ")";
        for(final Player supporter : Bukkit.getOnlinePlayers()){
            OreNotifyData data = getData(supporter);
            if(data != null && data.isActive() && data.shoudNotify(material))
                supporter.sendMessage(message);
        }
    }

}
