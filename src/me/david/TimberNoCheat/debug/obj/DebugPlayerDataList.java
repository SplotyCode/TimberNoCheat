package me.david.TimberNoCheat.debug.obj;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import me.david.TimberNoCheat.debug.Debuggers;

import java.util.ArrayList;
import java.util.Collection;

public class DebugPlayerDataList extends ArrayList<PlayerData> {

    @Override
    public boolean remove(Object o) {
        TimberNoCheat.instance.getDebuger().sendDebug(Debuggers.PLAYERDATA_MANAGE, "remove via object");
        return super.remove(o);
    }

    @Override
    public PlayerData remove(int i) {
        TimberNoCheat.instance.getDebuger().sendDebug(Debuggers.PLAYERDATA_MANAGE, "remove via number");
        return super.remove(i);
    }

    @Override
    protected void removeRange(int i, int i1) {
        super.removeRange(i, i1);
        TimberNoCheat.instance.getDebuger().sendDebug(Debuggers.PLAYERDATA_MANAGE, "remove via range");
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        TimberNoCheat.instance.getDebuger().sendDebug(Debuggers.PLAYERDATA_MANAGE, "remove via collection");
        return super.removeAll(collection);
    }

    @Override
    public boolean add(PlayerData playerData) {
        TimberNoCheat.instance.getDebuger().sendDebug(Debuggers.PLAYERDATA_MANAGE, "add via object");
        return super.add(playerData);
    }

    @Override
    public void add(int i, PlayerData playerData) {
        TimberNoCheat.instance.getDebuger().sendDebug(Debuggers.PLAYERDATA_MANAGE, "add via number");
        super.add(i, playerData);
    }

    @Override
    public boolean addAll(Collection<? extends PlayerData> collection) {
        TimberNoCheat.instance.getDebuger().sendDebug(Debuggers.PLAYERDATA_MANAGE, "add via collection");
        return super.addAll(collection);
    }

    @Override
    public boolean addAll(int i, Collection<? extends PlayerData> collection) {
        TimberNoCheat.instance.getDebuger().sendDebug(Debuggers.PLAYERDATA_MANAGE, "add via collection and index");
        return super.addAll(i, collection);
    }
}
