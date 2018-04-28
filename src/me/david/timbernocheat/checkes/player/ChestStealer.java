package me.david.timbernocheat.checkes.player;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.PlayerData;
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
    public void onItem(InventoryClickEvent event){
        final Player player = (Player) event.getWhoClicked();
        if(!CheckManager.getInstance().isvalid_create(player) || event.isCancelled() || event.getAction() != InventoryAction.MOVE_TO_OTHER_INVENTORY){
            return;
        }
        PlayerData pd = CheckManager.getInstance().getPlayerdata(player);
        long delay = System.currentTimeMillis()-pd.getLastChestStealer();
        if(delay < this.delay){
            event.setCancelled(true);
            updateVio(this, player, 2, " §6MODE: §bNORMAL", " §6DELAY: §b" + delay);
            //CheckManager.getInstance().notify(this, player, " §6MODE: §bNORMAL", " §6DELAY: §b" + delay);
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
                    //System.out.println(firstdelay + " " + (l-TimberNoCheat.getInstance().settings.player_cheststeler_consistent_maxdelaymilis) + " " + l+TimberNoCheat.getInstance().settings.player_cheststeler_consistent_maxdelaymilis);
                    break;
                }
                last = bet;
            }
            if(consisdent){
                updateVio(this, player, 8, " §6MODE: §bCONSISDEND");
                //CheckManager.getInstance().notify(this, player, " §6MODE: §bCONSISDEND");
                pd.getCheststealercon().clear();
            }
        }
        pd.getCheststealercon().add(System.currentTimeMillis());
        pd.setLastChestStealer(System.currentTimeMillis());
    }

}
