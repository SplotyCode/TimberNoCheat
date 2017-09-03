package me.david.TimberNoCheat.checkes.other;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;

public class VanillaBug extends Check {

    public ArrayList<Material> fences = new ArrayList<Material>();
    public VanillaBug(){
        super("VanillaBug", Category.OTHER);
        fences.add(Material.FENCE);
        fences.add(Material.FENCE_GATE);
        fences.add(Material.ACACIA_FENCE);
        fences.add(Material.BIRCH_FENCE);
        fences.add(Material.DARK_OAK_FENCE);
        fences.add(Material.IRON_FENCE);
        fences.add(Material.SPRUCE_FENCE);
        fences.add(Material.NETHER_FENCE);
        fences.add(Material.JUNGLE_FENCE);
        fences.add(Material.ACACIA_FENCE_GATE);
        fences.add(Material.BIRCH_FENCE_GATE);
        fences.add(Material.DARK_OAK_FENCE_GATE);
        fences.add(Material.JUNGLE_FENCE_GATE);
        fences.add(Material.SPRUCE_FENCE_GATE);
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if (!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer()) || e.getAction() != Action.RIGHT_CLICK_BLOCK || e.getItem() == null || !fences.contains(e.getClickedBlock().getType()) && e.getItem().getType().isBlock()) {
            return;
        }
        e.setCancelled(true);
        updatevio(this, e.getPlayer(), 1);
        //TimberNoCheat.instance.getLogger().log(Level.INFO, "[VanillaBug] " + e.getPlayer().getDisplayName() + " versucht den VanillaFenceBug zu nutzen! Die Aktion wurde gestoppt!");
        //e.getPlayer().sendMessage(TimberNoCheat.instance.prefix + "Durch einen Bug in Minecraft darfst du nur mit blöcken auf ein " + e.getClickedBlock().getType().name() + " Rechts klicken!");
    }
}
