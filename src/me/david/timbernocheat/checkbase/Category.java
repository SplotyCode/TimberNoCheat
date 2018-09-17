package me.david.timbernocheat.checkbase;

import org.bukkit.Material;

/*
 * Check Category's
 */
public enum Category {

    /* For Example Spammer, Whitelist etc. */
    CHAT(Material.SIGN, "chat"),
    /* For Example NBT-Data-Exploits, ServerCrasher etc. */
    EXPLOITS(Material.REDSTONE_COMPARATOR_ON, "exploits"),
    /* Labymod, Vape etc prevention */
    CLIENT_CHANEL(Material.CHEST, "clientchannel"),
    /* All the stuff that is to less for its own category */
    OTHER(Material.SLIME_BLOCK, "other"),
    /* For Example AirPlace, NoSwing Scaffold etc. */
    INTERACT(Material.STICK, "interact"),
    /* For Exaple FastEat, Respawn, ChestStealer etc. */
    PLAYER(Material.SKULL_ITEM, "player"),
    /* All Movement related Checks */
    MOVEMENT(Material.ENDER_PEARL, "movement"),
    /* All Combat related Checks */
    COMBAT(Material.DIAMOND_SWORD, "combat");

    private final Material material;
    private final String packageName;

    Category(Material material, String packageName){
        this.material = material;
        this.packageName = packageName;
    }

    public Material getMaterial() {
        return material;
    }

    public String getPackageName() {
        return packageName;
    }
}
