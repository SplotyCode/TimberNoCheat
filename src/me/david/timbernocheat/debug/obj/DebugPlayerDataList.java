package me.david.timbernocheat.debug.obj;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.debug.Debuggers;

import java.util.ArrayList;
import java.util.Collection;

public class DebugPlayerDataList extends ArrayList<PlayerData> {

    @Override
    public boolean remove(Object o) {
        TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.PLAYERDATA_MANAGE, "remove via object");
        return super.remove(o);
    }

    @Override
    public PlayerData remove(int i) {
        TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.PLAYERDATA_MANAGE, "remove via number");
        return super.remove(i);
    }

    @Override
    protected void removeRange(int i, int i1) {
        super.removeRange(i, i1);
        TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.PLAYERDATA_MANAGE, "remove via range");
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.PLAYERDATA_MANAGE, "remove via collection");
        return super.removeAll(collection);
    }

    @Override
    public boolean add(PlayerData playerData) {
        TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.PLAYERDATA_MANAGE, "add via object");
        return super.add(playerData);
    }

    @Override
    public void add(int i, PlayerData playerData) {
        TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.PLAYERDATA_MANAGE, "add via number");
        super.add(i, playerData);
    }

    @Override
    public boolean addAll(Collection<? extends PlayerData> collection) {
        TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.PLAYERDATA_MANAGE, "add via collection");
        return super.addAll(collection);
    }

    @Override
    public boolean addAll(int i, Collection<? extends PlayerData> collection) {
        TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.PLAYERDATA_MANAGE, "add via collection and index");
        return super.addAll(i, collection);
    }
}
