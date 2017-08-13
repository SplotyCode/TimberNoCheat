package me.david.TimberNoCheat.CheckManager;

import java.util.ArrayList;

public class PlayerData {
    private String uuid;
    private long lastchat;
    private ArrayList<String> messages;

    public PlayerData(String uuid) {
        this.uuid = uuid;
        this.lastchat = 0;
        this.messages = new ArrayList<String>();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getLastchat() {
        return lastchat;
    }

    public void setLastchat(long lastchat) {
        this.lastchat = lastchat;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }
}
