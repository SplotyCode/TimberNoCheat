package me.david.TimberNoCheat.checkes.clientchanel;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;

public class Shematica extends Check implements PluginMessageListener {

    public String channel = "schematica";
    public Shematica(){
        super("Shematica", Category.CLIENT_CHANEL);
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(TimberNoCheat.instance, channel, this);
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(TimberNoCheat.instance, channel);
    }

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        if(TimberNoCheat.instance.settings.shematica_kick)
            player.kickPlayer(TimberNoCheat.instance.prefix + "§cBitte deinstaliere Schematica!");
    }

    @Override
    public void disable() {
        Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(TimberNoCheat.instance, channel, this);
        Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(TimberNoCheat.instance, channel);
    }

    @EventHandler
    public void onlogin(PlayerLoginEvent e){
        final Player p = e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        sendPluginMessage(p, getPayload());
        p.sendPluginMessage(TimberNoCheat.instance, channel, getPayload());
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        final Player p = e.getPlayer();
        if(!TimberNoCheat.checkmanager.isvalid_create(p)){
            return;
        }
        if(TimberNoCheat.instance.settings.shematica_block){
            String json = "{\"text\":\"\",\"extra\":[{\"text\":\"\u00a70\u00a72\u00a70\u00a70\u00a7e\u00a7f\"},{\"text\":\"\u00a70\u00a72\u00a71\u00a70\u00a7e\u00a7f\"},{\"text\":\"\u00a70\u00a72\u00a71\u00a71\u00a7e\u00a7f\"}]}";
            IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(json);
            PacketPlayOutChat chat = new PacketPlayOutChat(icbc, BigInteger.valueOf(0).toByteArray()[0]);
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(chat);
        }
    }
    public static byte[] getPayload() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeByte(0);
            dos.writeBoolean(TimberNoCheat.instance.settings.shematica_print);
            dos.writeBoolean(TimberNoCheat.instance.settings.shematica_save);
            dos.writeBoolean(TimberNoCheat.instance.settings.shematica_load);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void sendPluginMessage(Player player, byte[] payload) {
        try {
            Class playerClass = player.getClass();
            if (playerClass.getSimpleName().equals("CraftPlayer")) {
                Method addChannel = playerClass.getDeclaredMethod("addChannel", String.class);
                Method removeChannel = playerClass.getDeclaredMethod("removeChannel", String.class);
                addChannel.invoke((Object)player, channel);
                player.sendPluginMessage(TimberNoCheat.instance, channel, payload);
                removeChannel.invoke((Object)player, channel);
            }
        }catch (NoSuchMethodException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (InvocationTargetException e){
            e.printStackTrace();
        }
    }
}
