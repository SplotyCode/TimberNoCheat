package me.david.timbernocheat.debug.log;

import me.david.timbernocheat.storage.BinaryComponent;
import me.david.timbernocheat.storage.BinarySerializer;

import java.io.IOException;
import java.util.UUID;

public class DebugEntry implements BinaryComponent  {

    private long time;
    private String check;
    private UUID player;
    private double oldVio, newVio;
    private String[] executions;
    private boolean cancel;

    public DebugEntry() {}
    public DebugEntry(long time, String check, UUID player, double oldVio, double newVio, String[] executions, boolean cancel) {
        this.time = time;
        this.check = check;
        this.player = player;
        this.oldVio = oldVio;
        this.newVio = newVio;
        this.executions = executions;
        this.cancel = cancel;
    }

    @Override
    public void read(BinarySerializer serializer) throws IOException {
        time = serializer.readLong();
        check = serializer.readString();
        player = new UUID(serializer.readLong(), serializer.readLong());
        oldVio = serializer.readDouble();
        newVio = serializer.readDouble();
        executions = serializer.readStringArray();
        cancel = serializer.readBoolean();
    }

    @Override
    public void write(BinarySerializer serializer) throws IOException {
        serializer.writeLong(time);
        serializer.writeString(check);

        serializer.writeLong(player.getMostSignificantBits());
        serializer.writeLong(player.getLeastSignificantBits());

        serializer.writeDouble(oldVio);
        serializer.writeDouble(newVio);
        serializer.writeArray(executions);
        serializer.writeBoolean(cancel);
    }

    public double getNewVio() {
        return newVio;
    }

    public double getOldVio() {
        return oldVio;
    }

    public void setNewVio(double newVio) {
        this.newVio = newVio;
    }

    public void setOldVio(double oldVio) {
        this.oldVio = oldVio;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public UUID getPlayer() {
        return player;
    }

    public void setPlayer(UUID player) {
        this.player = player;
    }

    public String[] getExecutions() {
        return executions;
    }

    public void setExecutions(String[] executions) {
        this.executions = executions;
    }

}
