package me.david.timbernocheat.gui.profiler;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.config.Permissions;
import me.david.api.FarbCodes;
import me.david.api.guis.Gui;
import me.david.api.sound.Sound;
import me.david.api.sound.SoundCategory;
import me.david.api.utils.ItemStackUtil;
import me.david.api.utils.ScoreboardUtil;
import me.david.timbernocheat.debug.Scheduler;
import me.david.timbernocheat.runnable.TimberScheduler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Map;

public class GuiMoveProfiler extends Gui implements Listener {

    private ArrayList<Player> profilePlayer = new ArrayList<>();

    public GuiMoveProfiler(Plugin pl) {
        super("GuiMoveProfiler", Permissions.PROFILER_MOVEMENT, new Sound("MoveProfiler", SoundCategory.INVENOTY_OPEN, org.bukkit.Sound.LEVEL_UP, TimberNoCheat.getInstance()));
        new TimberScheduler(Scheduler.SCHEDULER_PROFILER, () -> {
            TimberNoCheat.getInstance().getMoveprofiler().setRunning(!profilePlayer.isEmpty());
            String[] profile = new String[TimberNoCheat.getInstance().getMoveprofiler().getTimes().size()];
            int i = 0;
            //System.out.println("---");
            for(Map.Entry<String, Long> pr : TimberNoCheat.getInstance().getMoveprofiler().getTimes().entrySet()){
                profile[i] = "§6" + pr.getKey() + ": §b" + pr.getValue();
                //System.out.println(profile[i]);
                i++;
            }
            ScoreboardUtil.sendScoreboard(ScoreboardUtil.createScoreboard("§bMove§6Profiler", profile), profilePlayer);
        }).startTimer(20);
        Bukkit.getPluginManager().registerEvents(this, pl);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        if(profilePlayer.contains(event.getPlayer())) profilePlayer.remove(event.getPlayer());
    }

    @Override
    public Inventory build(Player p) {
        Inventory inv = Bukkit.getServer().createInventory(null, 9, "§6Move Profiler");
        inv.addItem(ItemStackUtil.createColoredWool("§aStart", 1, FarbCodes.LIME.getId()), ItemStackUtil.createColoredWool("§cStop", 1, FarbCodes.RED.getId()), ItemStackUtil.createbasic("§eInfo", 1, Material.SIGN), ItemStackUtil.createbasic("§cClear", 1, Material.REDSTONE));
        return inv;
    }

    @Override
    public void itemclick(Player p, Inventory inv, ItemStack itemstack, InventoryAction inventoryaction, ClickType clicktype, int slot) {
        if(itemstack.getType() == Material.SIGN){
            for(Player p1 : profilePlayer){
                p.sendMessage(TimberNoCheat.getInstance().getPrefix() + p1.getName());
            }
        }else if(itemstack.getType() == Material.REDSTONE){
            TimberNoCheat.getInstance().getMoveprofiler().reset();
        }else if(itemstack.getDurability() == FarbCodes.LIME.getId()){
            if(!profilePlayer.contains(p)){
                profilePlayer.add(p);
            }
        }else if(profilePlayer.contains(p)){
            profilePlayer.remove(p);
        }
    }
}
