package me.david.timbernocheat.gui.profiler;

import me.david.timbernocheat.config.Permissions;
import me.david.api.guis.Gui;
import me.david.api.utils.ItemStackUtil;
import me.david.api.utils.OLD_Sounds;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.scheduler.CraftScheduler;
import org.bukkit.craftbukkit.v1_8_R3.scheduler.CraftTask;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProfilerAllScheduler extends Gui {
    public ProfilerAllScheduler() {
        super("ProfilerAllScheduler", OLD_Sounds.LEVELUP, Permissions.PROFILER_ALLSCHEDULER);
    }

    @Override
    public Inventory build(Player p) {
        try {
            ArrayList<ItemStack> shedular = new ArrayList<>();
            Field f = ((CraftScheduler) Bukkit.getScheduler()).getClass().getDeclaredField("runners");
            f.setAccessible(true);
            ConcurrentHashMap<Integer, CraftTask> tasks = (ConcurrentHashMap<Integer, CraftTask>) f.get(Bukkit.getScheduler());
            for(Map.Entry<Integer, CraftTask> task : tasks.entrySet()){
                if(task.getValue().getClass().getSimpleName().equals("CraftAsyncTask")){
                    shedular.add(ItemStackUtil.createLohre("§6" + task.getValue().getTaskName(), 1, Material.WATCH, "§6plugin; §b" + task.getValue().getOwner().getName(), "§6Id; §b" + task.getKey() + " §6| §b" + task.getValue().getTaskId(), "§6Async: §b" + task.getValue().isSync(), "§6Time: §bAsync :("));
                    continue;
                }
                Method pe = task.getValue().getClass().getDeclaredMethod("getPeriod");
                pe.setAccessible(true);
                Method c = task.getValue().getClass().getDeclaredMethod("getTaskClass");
                c.setAccessible(true);
                shedular.add(ItemStackUtil.createLohre("§6" + task.getValue().getTaskName(), 1, Material.WATCH, "§6plugin; §b" + task.getValue().getOwner().getName(), "§6Id; §b" + task.getKey() + " §6| §b" + task.getValue().getTaskId(), "§6Async: §b" + !task.getValue().isSync(), "§6Time: §b" + pe.invoke(task.getValue()), "§6Class: §b" + ((Class<? extends Runnable>)c.invoke(task.getValue())).getSimpleName()));
            }
            Inventory inv = Bukkit.getServer().createInventory(null, 9*6, "§6Shedular List");
            for(ItemStack is : shedular) inv.addItem(is);
            return inv;
        } catch (NoSuchFieldException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
