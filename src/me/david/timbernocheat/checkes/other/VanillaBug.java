package me.david.timbernocheat.checkes.other;

import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;

public class VanillaBug extends Check {

    private final ArrayList<Material> fences = new ArrayList<>();
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
    public void onInteract(PlayerInteractEvent event){
        //System.out.println(!fences.contains(event.getClickedBlock().getType()));
        if (!CheckManager.getInstance().isvalid_create(event.getPlayer()) || event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getItem() == null || !fences.contains(event.getClickedBlock().getType()) || !event.getItem().getType().isBlock()) {
            return;
        }
        event.setCancelled(true);
        updateVio(this, event.getPlayer(), 1);
        //TimberNoCheat.getInstance().getLogger().log(Level.INFO, "[VanillaBug] " + event.getPlayer().getDisplayName() + " versucht den VanillaFenceBug zu nutzen! Die Aktion wurde gestoppt!");
        //event.getPlayer().sendMessage(TimberNoCheat.getInstance().getPrefix() + "Durch einen Bug in Minecraft darfst du nur mit blöcken auf ein " + event.getClickedBlock().getType().name() + " Rechts klicken!");
    }
}
