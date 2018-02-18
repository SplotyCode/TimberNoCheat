package me.david.timbernocheat.checkes.combat;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import me.david.timbernocheat.checkmanager.PlayerData;
import me.david.timbernocheat.runnable.Tps;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
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
        final Entity damaged = event.getEntity();
        final Player damagedPlayer = damaged instanceof Player ? (Player) damaged : null;
        if (damagedPlayer != null && !damaged.isDead()) {
            if (check(damagedPlayer, event.getDamage())) {
                damagedPlayer.setNoDamageTicks(0);
            }
        }
    }

    private boolean check(final Player player, final double damage){
        final int tick = Tps.tickcount;
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(player);
        final int noDamageTicks = Math.max(0, player.getNoDamageTicks());
        final int invulnerabilityTicks = ((CraftPlayer) player).getHandle().invulnerableTicks;

        boolean legit = false, set = false, resetAcc = false;

        final int dTick = tick - pd.getGodlastDamageTick();
        final int dNDT = pd.getGodlastNoDamageTicks() - noDamageTicks;
        final int delta = dTick - dNDT;

        if (pd.getGodhealth() > player.getHealth()){
            pd.setGodhealthtick(tick);
            legit = set = resetAcc = true;
        }
        if (invulnerabilityTicks != Integer.MAX_VALUE && invulnerabilityTicks > 0 || tick < pd.getGodlastDamageTick())
            legit = set = resetAcc = true;
        if (20 + pd.getGodAcc() < dTick || dTick > 40){
            legit = resetAcc = true;
            set = true;
        }
        if (delta <= 0  || pd.getGodlastNoDamageTicks() <= player.getMaximumNoDamageTicks() / 2 || dTick > pd.getGodlastNoDamageTicks() || damage > player.getLastDamage() || damage == 0) legit = set = true;
        if (dTick == 1 && noDamageTicks < 19) set = true;
        if (delta == 1) legit = true;
        pd.setGodhealth(player.getHealth());
        if (resetAcc) pd.setGodAcc(0);
        if (set){
            pd.setGodlastNoDamageTicks(noDamageTicks);
            pd.setGodlastDamageTick(tick);
            return false;
        } else if (legit) return false;

        //TODO: Keep alive checks!!!
        pd.setGodAcc(pd.getGodAcc()+delta);
        boolean cancel;
        if (pd.getGodAcc() > 2){
            updatevio(this, player, delta);
            cancel = true;
        } else cancel = false;
        pd.setGodlastNoDamageTicks(noDamageTicks);
        pd.setGodlastDamageTick(tick);
        return cancel;
    }
}
