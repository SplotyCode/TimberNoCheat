package me.david.timbernocheat.checkes.combat;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.debug.Debuggers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;

public class FastBow extends Check {

    private final double mimimumforce;
    private final long delay;
    public FastBow(){
        super("FastBow", Category.COBMAT);
        mimimumforce = getDouble("mimimumforce");
        delay = getLong("delay");
    }

    @EventHandler
    public void onshot(final EntityShootBowEvent event){
        if(!(event.getEntity() instanceof Player)) return;
        final Player player = (Player) event.getEntity();
        TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.BOWFORCE, "Force: " + event.getEntity());
        if(!CheckManager.getInstance().isvalid_create(player)) return;
        PlayerData pd = CheckManager.getInstance().getPlayerdata(player);
        long delay = System.currentTimeMillis() - pd.getLastBowShot();
        if(event.getForce() <= mimimumforce) return;
        if(delay < this.delay){
            if(updateVio(this, player, (this.delay-delay),  " §6DELAY: §b" + delay, " §6FORCE: §b" + event.getForce()))
                event.setCancelled(true);
        }
        pd.setLastBowShot(System.currentTimeMillis());
    }
}
