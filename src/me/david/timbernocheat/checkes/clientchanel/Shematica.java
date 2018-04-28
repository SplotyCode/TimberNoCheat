package me.david.timbernocheat.checkes.clientchanel;

import me.david.api.Api;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;

public class Shematica extends Check implements PluginMessageListener {

    private final String channel = "schematica";
    private final boolean BLOCK;
    private final boolean PRINT;
    private final boolean LOAD;
    private final boolean SAVE;

    public Shematica(){
        super("Shematica", Category.CLIENT_CHANEL);
        BLOCK = getBoolean("block");
        PRINT = getBoolean("print");
        LOAD = getBoolean("load");
        SAVE = getBoolean("save");
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(TimberNoCheat.getInstance(), channel, this);
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(TimberNoCheat.getInstance(), channel);
    }

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        updateVio(this, player, 1);
    }

    @Override
    public void disable() {
        Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(TimberNoCheat.getInstance(), channel, this);
        Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(TimberNoCheat.getInstance(), channel);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event){
        final Player player = event.getPlayer();
        if(!CheckManager.getInstance().isvalid_create(player)) return;
        Api.getNms().sendPluingMessage(player, getPayload(), channel, TimberNoCheat.getInstance());
        player.sendPluginMessage(TimberNoCheat.getInstance(), channel, getPayload());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        final Player player = event.getPlayer();
        if(BLOCK && CheckManager.getInstance().isvalid_create(player)){
            String json = "{\"text\":\"\",\"extra\":[{\"text\":\"\u00a70\u00a72\u00a70\u00a70\u00a7e\u00a7f\"},{\"text\":\"\u00a70\u00a72\u00a71\u00a70\u00a7e\u00a7f\"},{\"text\":\"\u00a70\u00a72\u00a71\u00a71\u00a7e\u00a7f\"}]}";
            IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(json);
            PacketPlayOutChat chat = new PacketPlayOutChat(icbc, BigInteger.valueOf(0).toByteArray()[0]);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(chat);
        }
    }

    private byte[] getPayload() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeByte(0);
            dos.writeBoolean(PRINT);
            dos.writeBoolean(SAVE);
            dos.writeBoolean(LOAD);
            return baos.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
