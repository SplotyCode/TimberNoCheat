package me.david.TimberNoCheat.api;

import me.david.TimberNoCheat.checkmanager.Check;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ViolationUpdateEvent extends TNCCancelEvent{

    private Player p;
    private double newviolation;
    private double oldviolation;
    private Check c;

    public ViolationUpdateEvent(Player p, double newviolation, double oldviolation, Check c){
        this.p = p;
        this.newviolation = newviolation;
        this.oldviolation = oldviolation;
        this.c = c;
    }

    public Check getC() {
        return c;
    }

    public void setC(Check c) {
        this.c = c;
    }

    public Player getP() {
        return p;
    }

    public void setP(Player p) {
        this.p = p;
    }

    public double getNewviolation() {
        return newviolation;
    }

    public void setNewviolation(double newviolation) {
        this.newviolation = newviolation;
    }

    public double getOldviolation() {
        return oldviolation;
    }

    public void setOldviolation(double oldviolation) {
        this.oldviolation = oldviolation;
    }
}
