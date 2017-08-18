package me.david.TimberNoCheat.checkes.fight;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import org.bukkit.Difficulty;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class Regen extends Check {

    public Regen(){
        super("Regen", Category.FIGHT);
    }

    @EventHandler
    public void onregen(EntityRegainHealthEvent e){
        if(!(e.getEntity() instanceof Player) || e.getRegainReason() != EntityRegainHealthEvent.RegainReason.SATIATED){
            return;
        }
        final Player p = (Player) e.getEntity();
        if(!TimberNoCheat.checkmanager.isvalid_create(p) || p.getWorld().getDifficulty() == Difficulty.PEACEFUL){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        long delay = System.currentTimeMillis() - pd.getLastregen();
        if(delay < TimberNoCheat.instance.settings.fight_regen_delay){
            e.setCancelled(true);
            TimberNoCheat.checkmanager.notify(this, p, " §6DELAY: §b" + delay);
            return;
        }
        pd.setLastregen(System.currentTimeMillis());
    }
}
