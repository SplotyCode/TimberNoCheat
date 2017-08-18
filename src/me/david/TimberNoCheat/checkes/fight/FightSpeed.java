package me.david.TimberNoCheat.checkes.fight;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class FightSpeed extends Check{

    public FightSpeed(){
        super("FightSpeed", Category.FIGHT);
    }

    @EventHandler
    public void oninteract(PlayerInteractEvent e){
        final Player p = e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        pd.setInteractslastsecond(pd.getInteractslastsecond()+1);
        Bukkit.getScheduler().runTaskLater(TimberNoCheat.instance, new Runnable() {
            @Override
            public void run() {
                pd.setInteractslastsecond(pd.getInteractslastsecond()-1);
            }
        }, 20);
        if(pd.getInteractslastsecond() > TimberNoCheat.instance.settings.fight_fightspeed_maxinteractspersecond && TimberNoCheat.instance.settings.fight_fightspeed_maxinteractspersecond != -1){
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bINTERACTS", " §6INTERACTLASTSECOND: §b" + pd.getInteractslastsecond());
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onHit(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof  Player)){
            return;
        }
        if(e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            return;
        }
        final Player p = (Player) e.getDamager();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        pd.setHitslastsecond(pd.getHitslastsecond()+1);
        Bukkit.getScheduler().runTaskLater(TimberNoCheat.instance, new Runnable() {
            @Override
            public void run() {
                pd.setHitslastsecond(pd.getHitslastsecond()-1);
            }
        }, 20);
        if(pd.getHitslastsecond() > TimberNoCheat.instance.settings.fight_fightspeed_maxhitspersecond && TimberNoCheat.instance.settings.fight_fightspeed_maxhitspersecond != -1){
            TimberNoCheat.checkmanager.notify(this, p, " §6CHECK: §bHITS", " §6HITSTSECOND: §b" + pd.getHitslastsecond());
            e.setCancelled(true);
        }
    }
}
