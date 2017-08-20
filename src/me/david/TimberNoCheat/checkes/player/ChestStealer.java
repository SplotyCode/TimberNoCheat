package me.david.TimberNoCheat.checkes.player;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class ChestStealer extends Check {

    public ChestStealer(){
        super("ChestStealer", Category.PLAYER);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onitem(InventoryClickEvent e){
        final Player p = (Player) e.getWhoClicked();
        if(!TimberNoCheat.checkmanager.isvalid_create(p) || e.isCancelled() || e.getAction() != InventoryAction.MOVE_TO_OTHER_INVENTORY){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        long delay = System.currentTimeMillis()-pd.getLastcheststealer();
        if(delay < TimberNoCheat.instance.settings.player_cheststeler_delay && (TimberNoCheat.checkmanager.getping(p) < TimberNoCheat.instance.settings.player_cheststeler_maxping && TimberNoCheat.instance.settings.player_cheststeler_maxping != -1)){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6MODE: §bNORMAL", " §6DELAY: §b" + delay);
            pd.getCheststealercon().clear();
        }
        for(int i = 0;i<pd.getCheststealercon().size();i++){
            if(pd.getCheststealercon().get(0) < System.currentTimeMillis()-TimberNoCheat.instance.settings.player_cheststeler_consistent_cacheinmilis){
                pd.getCheststealercon().remove(0);
                //System.out.println("a");
            }
        }
        if(pd.getCheststealercon().size() >= TimberNoCheat.instance.settings.player_cheststeler_consistent_maxcachesize){
            int i = 0;
            for(long l : pd.getCheststealercon()){
                if(i == TimberNoCheat.instance.settings.player_cheststeler_consistent_maxcachesize){
                    pd.getCheststealercon().remove(l);
                }
            }
        }
        if(pd.getCheststealercon().size() >= TimberNoCheat.instance.settings.player_cheststeler_consistent_mincachesize){
            long firstdelay = System.currentTimeMillis()-pd.getCheststealercon().get(0);
            boolean consisdent = true;
            for(long l : pd.getCheststealercon()){
                l = System.currentTimeMillis()-l;
                if(firstdelay<(l-TimberNoCheat.instance.settings.player_cheststeler_consistent_maxdelaymilis) || firstdelay>(l+TimberNoCheat.instance.settings.player_cheststeler_consistent_maxdelaymilis)){
                    consisdent = false;
                    //System.out.println(firstdelay + " " + (l-TimberNoCheat.instance.settings.player_cheststeler_consistent_maxdelaymilis) + " " + l+TimberNoCheat.instance.settings.player_cheststeler_consistent_maxdelaymilis);
                    break;
                }
            }
            if(consisdent){
                TimberNoCheat.checkmanager.notify(this, p, " §6MODE: §bCONSISDEND");
                pd.getCheststealercon().clear();
            }
        }
        pd.getCheststealercon().add(System.currentTimeMillis());
        pd.setLastcheststealer(System.currentTimeMillis());
    }

}
