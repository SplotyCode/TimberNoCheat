package me.david.timbernocheat.checkes.combat;

import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.PlayerData;
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
        super("Regen", Category.COMBAT);
        delaySatiated = getLong("delaySatiated");
        delayPeaceful = getLong("delayPeaceful");
        delayRegens = getLangLongArray("delayRegens");
    }

    @EventHandler
    public void onregen(final EntityRegainHealthEvent event){
        if(!(event.getEntity() instanceof Player)){
            return;
        }
        final Player player = (Player) event.getEntity();
        if(!CheckManager.getInstance().isvalid_create(player)){
            return;
        }
        if(!player.hasPotionEffect(PotionEffectType.REGENERATION) && player.getFoodLevel() <= 17 && event.getRegainReason() != EntityRegainHealthEvent.RegainReason.CUSTOM && event.getRegainReason() != EntityRegainHealthEvent.RegainReason.MAGIC){
            if(updateVio(this, player, 4+(17-player.getFoodLevel())*2, " §6TYPE: §bIMPOSIBLE STATE")) event.setCancelled(true);
        }
        PlayerData pd = CheckManager.getInstance().getPlayerdata(player);
        long speed = 0;
        long delay = -1;
        switch (event.getRegainReason()){
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
                speed = delayRegens[PotionUtil.getPotionEffectAmplifier(player, PotionEffectType.REGENERATION)];
                if(pd.getLastRegenMagic() != -1) delay = System.currentTimeMillis()-pd.getLastRegenMagic();
                pd.setLastRegenMagic(System.currentTimeMillis());
                break;
        }
        if(delay == -1)return;
        if(speed > delay){
            updateVio(this, player, speed-delay/2, " §6TYPE: §bDelay");
        }
    }
}
