package me.david.TimberNoCheat.checkes.combat;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import org.bukkit.Difficulty;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class Regen extends Check {

    long delay;
    public Regen(){
        super("Regen", Category.COBMAT);
        delay = getLong("delay");
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
        if(delay < this.delay){
            e.setCancelled(true);
            updatevio(this, p, 1, " §6DELAY: §b" + delay);
        }
        pd.setLastregen(System.currentTimeMillis());
    }
}
