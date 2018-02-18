package me.david.timbernocheat.checkmanager;

import org.bukkit.Material;

/*
 * Check Category's
 */
public enum Category {

    /* For Example Spammer, Whitelist etc. */
    CHAT(Material.SIGN),
    /* For Example NBT-Data-Exploits, ServerCrasher etc. */
    EXPLOITS(Material.REDSTONE_COMPARATOR_ON),
    /* Labymod, Vape etc prevention */
    CLIENT_CHANEL(Material.CHEST),
    /* All the stuff that is to less for its own category */
    OTHER(Material.SLIME_BLOCK),
    /* For Example AirPlace, NoSwing Scaffold etc. */
    INTERACT(Material.STICK),
    /* For Exaple FastEat, Respawn, ChestStealer etc. */
    PLAYER(Material.SKULL_ITEM),
    /* All Movement related Checks */
    MOVEMENT(Material.ENDER_PEARL),
    /* All Combat related Checks */
    COBMAT(Material.DIAMOND_SWORD);

    private final Material material;

    Category(Material material){
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }
}
