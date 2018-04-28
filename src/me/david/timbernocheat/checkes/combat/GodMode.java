package me.david.timbernocheat.checkes.combat;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.runnable.Tps;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;

public class GodMode extends Check {

    public GodMode() {
        super("GodMode", Category.COBMAT);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(final EntityDamageEvent event) {
        if(event.getEntityType() != EntityType.PLAYER || !TimberNoCheat.getCheckManager().isvalid_create((Player) event.getEntity())) return;
        final Player player = (Player) event.getEntity();
        if (!player.isDead()) {
            if (check(player, event.getDamage())) {
                player.setNoDamageTicks(0);
            }
        }
    }

    private boolean check(final Player player, final double damage){
        final int tick = Tps.tickCount;
        PlayerData pd = TimberNoCheat.getCheckManager().getPlayerdata(player);
        final int noDamageTicks = Math.max(0, player.getNoDamageTicks());
        final int invulnerabilityTicks = ((CraftPlayer) player).getHandle().invulnerableTicks;

        boolean legit = false, set = false, resetAcc = false;

        final int dTick = tick - pd.getGodLastDamageTick();
        final int dNDT = pd.getGodLastNoDamageTicks() - noDamageTicks;
        final int delta = dTick - dNDT;

        if (pd.getGodHealth() > player.getHealth()){
            pd.setGodhealthtick(tick);
            legit = set = resetAcc = true;
        }
        if (invulnerabilityTicks != Integer.MAX_VALUE && invulnerabilityTicks > 0 || tick < pd.getGodLastDamageTick())
            legit = set = resetAcc = true;
        if (20 + pd.getGodAcc() < dTick || dTick > 40){
            legit = resetAcc = true;
            set = true;
        }
        if (delta <= 0  || pd.getGodLastNoDamageTicks() <= player.getMaximumNoDamageTicks() / 2 || dTick > pd.getGodLastNoDamageTicks() || damage > player.getLastDamage() || damage == 0) legit = set = true;
        if (dTick == 1 && noDamageTicks < 19) set = true;
        if (delta == 1) legit = true;
        pd.setGodHealth(player.getHealth());
        if (resetAcc) pd.setGodAcc(0);
        if (set){
            pd.setGodLastNoDamageTicks(noDamageTicks);
            pd.setGodLastDamageTick(tick);
            return false;
        } else if (legit) return false;

        //TODO: Keep alive checks!!!
        pd.setGodAcc(pd.getGodAcc()+delta);
        boolean cancel = false;
        if (pd.getGodAcc() > 2 && updateVio(this, player, delta)){
            cancel = true;
        }
        pd.setGodLastNoDamageTicks(noDamageTicks);
        pd.setGodLastDamageTick(tick);
        return cancel;
    }
}
