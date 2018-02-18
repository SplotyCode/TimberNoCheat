package me.david.timbernocheat.checkes.player;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import me.david.timbernocheat.checkmanager.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ChestStealer extends Check {

    private final boolean enable;
    private final long delay;
    private final long cacheinmilis;
    private final long maxdelaymilis;
    private final int maxcachesize;
    private final int mincachesize;

    public ChestStealer(){
        super("ChestStealer", Category.PLAYER);
        delay = getLong("delay");
        enable = getBoolean("consisdent.enable");
        cacheinmilis = getLong("consisdent.cacheinmilis");
        maxdelaymilis = getLong("consisdent.maxdelaymilis");
        maxcachesize = getInt("consisdent.maxcachesize");
        mincachesize = getInt("consisdent.mincachesize");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onitem(InventoryClickEvent e){
        final Player p = (Player) e.getWhoClicked();
        if(!TimberNoCheat.checkmanager.isvalid_create(p) || e.isCancelled() || e.getAction() != InventoryAction.MOVE_TO_OTHER_INVENTORY){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        long delay = System.currentTimeMillis()-pd.getLastcheststealer();
        if(delay < this.delay){
            e.setCancelled(true);
            updatevio(this, p, 2, " §6MODE: §bNORMAL", " §6DELAY: §b" + delay);
            //TimberNoCheat.checkmanager.notify(this, p, " §6MODE: §bNORMAL", " §6DELAY: §b" + delay);
            pd.getCheststealercon().clear();
        }
        if(!enable)return;
        for(int i = 0;i<pd.getCheststealercon().size();i++){
            if(pd.getCheststealercon().get(0) < System.currentTimeMillis()-cacheinmilis){
                pd.getCheststealercon().remove(0);
                //System.out.println("a");
            }
        }
        if(pd.getCheststealercon().size() >= maxcachesize){
            int i;
            for(i = 0;i < pd.getCheststealercon().size();i++)
                if(i == maxcachesize)
                    pd.getCheststealercon().remove(i);
        }
        if(pd.getCheststealercon().size() >= mincachesize){
            boolean consisdent = true;
            long last = -1;
            for(int i = 0;i < pd.getCheststealercon().size()-1;i++){
                long bet = pd.getCheststealercon().get(i+1)-pd.getCheststealercon().get(i);
                if(last == -1){
                    last = bet;
                    continue;
                }
                double dif = bet-last;
                if(dif<(dif-maxdelaymilis) || dif>(dif+maxcachesize)){
                    consisdent = false;
                    //System.out.println(firstdelay + " " + (l-TimberNoCheat.instance.settings.player_cheststeler_consistent_maxdelaymilis) + " " + l+TimberNoCheat.instance.settings.player_cheststeler_consistent_maxdelaymilis);
                    break;
                }
                last = bet;
            }
            if(consisdent){
                updatevio(this, p, 8, " §6MODE: §bCONSISDEND");
                //TimberNoCheat.checkmanager.notify(this, p, " §6MODE: §bCONSISDEND");
                pd.getCheststealercon().clear();
            }
        }
        pd.getCheststealercon().add(System.currentTimeMillis());
        pd.setLastcheststealer(System.currentTimeMillis());
    }

}
