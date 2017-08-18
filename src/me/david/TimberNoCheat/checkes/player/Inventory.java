package me.david.TimberNoCheat.checkes.player;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;

public class Inventory extends Check {

    public Inventory(){
        super("Inventory", Category.PLAYER);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void oninventory(InventoryClickEvent e){
        final Player p = (Player) e.getWhoClicked();
        if(!TimberNoCheat.checkmanager.isvalid_create(p) || e.isCancelled()){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        long delay = System.currentTimeMillis() - pd.getLastmove();
        if(delay < TimberNoCheat.instance.settings.player_inv_delay_inmilis){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bMOVE", " §6DELAY: §b" + delay);
            return;
        }
        if(p.isSneaking() && TimberNoCheat.instance.settings.player_inv_sneak){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bSNEAKING");
            return;
        }
        if(p.isSprinting() && TimberNoCheat.instance.settings.player_inv_sprint){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bSPRINT");
            return;
        }
        if(p.isBlocking() && TimberNoCheat.instance.settings.player_inv_block){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bBLOCK");
            return;
        }
    }
}
