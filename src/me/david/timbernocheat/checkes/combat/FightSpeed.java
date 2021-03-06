package me.david.timbernocheat.checkes.combat;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

//TODO: Clear Up
public class FightSpeed extends Check {

    private final int hitspersecond;
    private final int interactspersecond;
    private final boolean enable;
    private final long cacheinmilis;
    private final long maxdelaymilis;
    private final int maxcachesize;
    private final int mincachesize;

    public FightSpeed(){
        super("FightSpeed", Category.COMBAT);
        hitspersecond = getInt("hitspersecond");
        interactspersecond = getInt("interactspersecond");
        enable = getBoolean("consisdent.enable");
        cacheinmilis = getLong("consisdent.cacheinmilis");
        maxdelaymilis = getLong("consisdent.maxdelaymilis");
        maxcachesize = getInt("consisdent.maxcachesize");
        mincachesize = getInt("consisdent.mincachesize");
    }

    @EventHandler
    public void oninteract(PlayerInteractEvent e){
        final Player p = e.getPlayer();
        if(!CheckManager.getInstance().isvalid_create(p)) return;
        PlayerData pd = CheckManager.getInstance().getPlayerdata(p);
        pd.setInteractsLastSecond(pd.getInteractsLastSecond()+1);
        Bukkit.getScheduler().runTaskLater(TimberNoCheat.getInstance(), () -> pd.setInteractsLastSecond(pd.getInteractsLastSecond()-1), 20);
        if(pd.getInteractsLastSecond() > interactspersecond){
            updateVio(this, p , pd.getInteractsLastSecond()-interactspersecond," §6CHECK: §bINTERACTS", " §6INTERACTLASTSECOND: §b" + pd.getInteractsLastSecond());
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof  Player) || e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) return;
        final Player p = (Player) e.getDamager();
        if(!CheckManager.getInstance().isvalid_create(p)) return;
        PlayerData pd = CheckManager.getInstance().getPlayerdata(p);
        pd.setHitsLastSecond(pd.getHitsLastSecond()+1);
        Bukkit.getScheduler().runTaskLater(TimberNoCheat.getInstance(), () -> pd.setHitsLastSecond(pd.getHitsLastSecond()-1), 20);
        if(pd.getHitsLastSecond() > hitspersecond){
            updateVio(this, p , pd.getHitsLastSecond()-hitspersecond, " §6CHECK: §bHITS", " §6HITSTSECOND: §b" + pd.getHitsLastSecond());
            e.setCancelled(true);
        }
        //Consistency
        if(!enable)return;
        for(int i = 0; i<pd.getFightSpeed().size(); i++){
            if(pd.getFightSpeed().get(0) < System.currentTimeMillis()-cacheinmilis){
                pd.getFightSpeed().remove(0);
            }
        }
        if(pd.getFightSpeed().size() >= maxcachesize){
            int i;
            for(i = 0; i < pd.getFightSpeed().size(); i++)
                if(i == maxcachesize) pd.getFightSpeed().remove(i);
        }
        if(pd.getFightSpeed().size() >= mincachesize){
            boolean consisdent = true;
            long last = -1;
            for(int i = 0; i < pd.getFightSpeed().size()-1; i++){
                long bet = pd.getFightSpeed().get(i+1)-pd.getFightSpeed().get(i);
                if(last == -1){
                    last = bet;
                    continue;
                }
                double dif = bet-last;
                if(dif<(dif-maxdelaymilis) || dif>(dif+maxcachesize)){
                    consisdent = false;
                    break;
                }
                last = bet;
            }
            if(consisdent){
                updateVio(this, p, 8, " §6MODE: §bCONSISDEND");
                pd.getFightSpeed().clear();
            }
        }
        pd.getFightSpeed().add(System.currentTimeMillis());
    }
}
