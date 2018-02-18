package me.david.timbernocheat.command.oreNotify;

import org.bukkit.Material;

public class OreNotifyData {

    private boolean active;
    private boolean diamond, gold, redstone, emeralds;

    public OreNotifyData(){
        active = false;
        diamond = true;
        gold = true;
        emeralds = true;
        redstone = false;
    }

    public OreNotifyData(boolean active){
        this.active = active;
        diamond = true;
        gold = true;
        emeralds = true;
        redstone = false;
    }

    public OreNotifyData(boolean active, boolean diamond, boolean gold, boolean redstone, boolean emeralds) {
        this.active = active;
        this.diamond = diamond;
        this.gold = gold;
        this.redstone = redstone;
        this.emeralds = emeralds;
    }

    public boolean shoudNotify(Material material){
        if(material == Material.DIAMOND_ORE && diamond)return true;
        if(material == Material.GOLD_ORE && gold)return true;
        if(material == Material.REDSTONE_ORE)return true;
        if(material == Material.EMERALD_ORE)return true;
        return false;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDiamond() {
        return diamond;
    }

    public void setDiamond(boolean diamond) {
        this.diamond = diamond;
    }

    public boolean isGold() {
        return gold;
    }

    public void setGold(boolean gold) {
        this.gold = gold;
    }

    public boolean isRedstone() {
        return redstone;
    }

    public void setRedstone(boolean redstone) {
        this.redstone = redstone;
    }

    public boolean isEmeralds() {
        return emeralds;
    }

    public void setEmeralds(boolean emeralds) {
        this.emeralds = emeralds;
    }
}
