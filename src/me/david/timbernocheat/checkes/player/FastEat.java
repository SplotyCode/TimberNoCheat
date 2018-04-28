package me.david.timbernocheat.checkes.player;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.PlayerData;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.ArrayList;

public class FastEat extends Check {

    private final long delay;

    private static final ArrayList<Material> food = new ArrayList<Material>();
    public FastEat(){
        super("FastEat", Category.PLAYER);
        delay = getLong("delay");
        food.add(Material.APPLE);
        food.add(Material.MUSHROOM_SOUP);
        food.add(Material.BREAD);
        food.add(Material.RAW_BEEF);
        food.add(Material.RAW_CHICKEN);
        food.add(Material.RAW_FISH);
        food.add(Material.COOKED_BEEF);
        food.add(Material.GOLDEN_APPLE);
        food.add(Material.COOKED_MUTTON);
        food.add(Material.COOKED_RABBIT);
        food.add(Material.COOKED_FISH);
        food.add(Material.COOKIE);
        food.add(Material.MELON);
        food.add(Material.MELON_STEM);
        food.add(Material.ROTTEN_FLESH);
        food.add(Material.SPIDER_EYE);
        food.add(Material.CARROT_ITEM);
        food.add(Material.RABBIT_STEW);
        food.add(Material.PUMPKIN_PIE);
        food.add(Material.GOLDEN_CARROT);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void oneat(PlayerItemConsumeEvent e){
        if(!CheckManager.getInstance().isvalid_create(e.getPlayer())){
            return;
        }
        if(!food.contains(e.getItem().getType())){
            return;
        }
        PlayerData pd = CheckManager.getInstance().getPlayerdata(e.getPlayer());
        long delay = System.currentTimeMillis()-pd.getLastEat();
        if(delay < this.delay){
            e.setCancelled(true);
            updateVio(this, e.getPlayer(), 1, " §6DELAY: §b" + delay);
            return;
        }
        pd.setLastEat(System.currentTimeMillis());
    }
}
