package me.david.timbernocheat.checkes.combat;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import me.david.timbernocheat.checkmanager.PlayerData;
import me.david.timbernocheat.util.PotionUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.potion.PotionEffectType;

public class Regen extends Check {

    private final long delaySatiated;
    private final long delayPeaceful;
    private final Long[] delayRegens;

    public Regen(){
        super("Regen", Category.COBMAT);
        delaySatiated = getLong("delaySatiated");
        delayPeaceful = getLong("delayPeaceful");
        delayRegens = getLongList("delayRegens");
    }

    @EventHandler
    public void onregen(EntityRegainHealthEvent e){
        if(!(e.getEntity() instanceof Player)){
            return;
        }
        final Player p = (Player) e.getEntity();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        if(!p.hasPotionEffect(PotionEffectType.REGENERATION) && p.getFoodLevel() <= 17 && e.getRegainReason() != EntityRegainHealthEvent.RegainReason.CUSTOM && e.getRegainReason() != EntityRegainHealthEvent.RegainReason.MAGIC){
            updatevio(this, p, 4+(17-p.getFoodLevel())*2, " §6TYPE: §bIMPOSIBLE STATE");
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        long speed = 0;
        long delay = -1;
        switch (e.getRegainReason()){
            case EATING:
                speed = delaySatiated;
                if(pd.getLastRegen() != -1) delay = System.currentTimeMillis()-pd.getLastRegen();
                pd.setLastRegen(System.currentTimeMillis());
                break;
            case CUSTOM:
                speed = delayPeaceful;
                if(pd.getLastRegenPeaceful() != -1) delay = System.currentTimeMillis()-pd.getLastRegenPeaceful();
                pd.setLastRegenPeaceful(System.currentTimeMillis());
                break;
            case REGEN:
                speed = delayRegens[PotionUtil.getPotionEffectAmplifier(p, PotionEffectType.REGENERATION)];
                if(pd.getLastRegenMagic() != -1) delay = System.currentTimeMillis()-pd.getLastRegenMagic();
                pd.setLastRegenMagic(System.currentTimeMillis());
                break;
        }
        if(delay == -1)return;
        if(speed > delay){
            updatevio(this, p, speed-delay/2, " §6TYPE: §bDelay");
        }
    }
}
