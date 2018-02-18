package me.david.timbernocheat.record;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class RecordPlayer {

    private Player player;

    public RecordPlayer(Player player, boolean join, boolean update) {
        this.player = player;
        this.join = join;
        this.loc = new Location(null, 0, 0, 0);
        this.vel = new Vector(0, 0, 0);
        this.updateSneaking = update;
        this.updateSprinting = update;
        this.updateArmor = update;
        this.updateFire = update;
    }

    public RecordPlayer(Player player) {
        this(player, true, true);
    }

    /* Main update Method for a Player this should been callen avery Tick*/
    public void update() {
        setItemInHand(player.getInventory().getItemInHand());
        setHelmet(player.getInventory().getHelmet());
        setChestplate(player.getInventory().getChestplate());
        setLeggings(player.getInventory().getLeggings());
        setBoots(player.getInventory().getBoots());
        if (isRespawn())
            setLoc(player.getLocation());
        setOnFire(this.player.getFireTicks() > 0);
    }

    private Location loc;
    private Vector vel;
    private boolean del;
    private boolean sneaking;
    private boolean updateSneaking;
    private boolean sprinting;
    private boolean updateSprinting;
    private boolean hurt;
    private boolean swingArm;
    private ItemStack itemInHand;
    /* TODO: Add 1.9 compability */
    private boolean updateHand;
    private ItemStack helmet;
    private ItemStack chestplate;
    private ItemStack leggings;
    private ItemStack boots;
    private boolean updateArmor;
    private boolean death;
    private boolean respawn;
    private boolean onFire;
    private boolean updateFire;
    private boolean join;


    public final void reset() {
        setJoin(false);
        setHurt(false);
        setSwingArm(false);
        setUpdateSneaking(false);
        setUpdateSprinting(false);
        setUpdateHand(false);
        setRespawn(false);
        setDeath(false);
        setUpdateFire(false);
    }



    public final void forceUpdate() {
        setUpdateSneaking(true);
        setUpdateSprinting(true);
        setUpdateArmor(true);
        setUpdateHand(true);
        setUpdateFire(true);
    }


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public Vector getVel() {
        return vel;
    }

    public void setVel(Vector vel) {
        this.vel = vel;
    }

    public boolean isDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public void setSneaking(boolean sneaking) {
        if(this.sneaking != sneaking) {
            this.sneaking = sneaking;
            setUpdateSneaking(true);
        }
    }

    public boolean isUpdateSneaking() {
        return updateSneaking;
    }

    public void setUpdateSneaking(boolean updateSneaking) {
        this.updateSneaking = updateSneaking;
    }

    public boolean isSprinting() {
        return sprinting;
    }

    public void setSprinting(boolean sprinting) {
        if(this.sprinting != sprinting) {
            this.sprinting = sprinting;
            setUpdateSprinting(true);
        }
    }

    public boolean isUpdateSprinting() {
        return updateSprinting;
    }

    public void setUpdateSprinting(boolean updateSprinting) {
        this.updateSprinting = updateSprinting;
    }

    public boolean isHurt() {
        return hurt;
    }

    public void setHurt(boolean hurt) {
        this.hurt = hurt;
    }

    public boolean isSwingArm() {
        return swingArm;
    }

    public void setSwingArm(boolean swingArm) {
        this.swingArm = swingArm;
    }

    public ItemStack getItemInHand() {
        return itemInHand;
    }

    public void setItemInHand(ItemStack itemInHand) {
        if(this.itemInHand != itemInHand){
            this.itemInHand = itemInHand;
            setUpdateHand(true);
        }
    }

    public boolean isUpdateHand() {
        return updateHand;
    }

    public void setUpdateHand(boolean updateHand) {
        this.updateHand = updateHand;
    }

    public ItemStack getHelmet() {
        return helmet;
    }

    public void setHelmet(ItemStack helmet) {
        if(!helmet.isSimilar(this.helmet)) {
            this.helmet = helmet;
            setUpdateArmor(true);
        }
    }

    public ItemStack getChestplate() {
        return chestplate;
    }

    public void setChestplate(ItemStack chestplate) {
        if(!chestplate.isSimilar(this.chestplate)) {
            this.chestplate = chestplate;
            setUpdateArmor(true);
        }
    }

    public ItemStack getLeggings() {
        return leggings;
    }

    public void setLeggings(ItemStack leggings) {
        if(!leggings.isSimilar(this.leggings)) {
            this.leggings = leggings;
            setUpdateArmor(true);
        }
    }

    public ItemStack getBoots() {
        return boots;
    }

    public void setBoots(ItemStack boots) {
        if(!boots.isSimilar(this.boots)) {
            this.boots = boots;
            setUpdateArmor(true);
        }
    }

    public boolean isUpdateArmor() {
        return updateArmor;
    }

    public void setUpdateArmor(boolean updateArmor) {
        this.updateArmor = updateArmor;
    }

    public boolean isDeath() {
        return death;
    }

    public void setDeath(boolean death) {
        this.death = death;
    }

    public boolean isRespawn() {
        return respawn;
    }

    public void setRespawn(boolean respawn) {
        this.respawn = respawn;
    }

    public boolean isOnFire() {
        return onFire;
    }

    public void setOnFire(boolean onFire) {
        if (onFire != this.onFire) {
            this.onFire = onFire;
            setUpdateFire(true);
        }
    }

    public boolean isUpdateFire() {
        return updateFire;
    }

    public void setUpdateFire(boolean updateFire) {
        this.updateFire = updateFire;
    }

    public boolean isJoin() {
        return join;
    }

    public void setJoin(boolean join) {
        this.join = join;
    }
}
