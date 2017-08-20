package me.david.TimberNoCheat.checkes.fight;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;

public class FastBow extends Check{

    public FastBow(){
        super("FastBow", Category.FIGHT);
    }

    @EventHandler
    public void onshot(EntityShootBowEvent e){
        if(!(e.getEntity() instanceof Player)){
            return;
        }
        final Player p = (Player) e.getEntity();
        p.sendMessage(e.getForce()+"");
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        long delay = System.currentTimeMillis() - pd.getLastbowshot();
        if(e.getForce() <= TimberNoCheat.instance.settings.fight_fastbow_ignoreforce){
            return;
        }
        if(delay < TimberNoCheat.instance.settings.fight_fastbow_delay){
            TimberNoCheat.checkmanager.notify(this, p, " §6DELAY: §b" + delay, " §6FORCE: §b" + e.getForce());
            e.setCancelled(true);
            return;
        }
        pd.setLastbowshot(System.currentTimeMillis());
    }
}
