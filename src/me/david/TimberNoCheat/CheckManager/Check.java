package me.david.TimberNoCheat.CheckManager;

import org.bukkit.event.Listener;

public class Check implements Listener{
    private String name;
    private Category category;

    public Check(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
