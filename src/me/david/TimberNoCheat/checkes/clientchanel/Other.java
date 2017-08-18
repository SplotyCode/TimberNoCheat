package me.david.TimberNoCheat.checkes.clientchanel;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.api.utils.StringUtil;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Other extends Check implements PluginMessageListener {

    String chanelZIG = "5zig_Set";
    String chanelBSPRINT = "BSprint";
    String chanelBSM = "BSM";
    String chanelWDLINIT = "WDL|INIT";
    String chanelWDLCONTROL = "WDL|CONTROL";
    String chanelMCBRAND = "MC|Brand";
    String chanelWDLREQ = "WDL|REQUEST";
    String chanelFML = "FML";
    String chanelFMLHS = "FMLHS";

    ArrayList<String>jsons = new ArrayList<String>();

    @Override
    public void onPluginMessageReceived(String chanel, Player player, byte[] bytes) {
        if(!TimberNoCheat.checkmanager.isvalid_create(player)){
            return;
        }
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        if(chanel.equals(chanelZIG)){
            if(TimberNoCheat.instance.settings.zig5_kick){
                player.kickPlayer(TimberNoCheat.instance.prefix + "§cBitte deinstaliere 5zig!");
            }
            TimberNoCheat.checkmanager.notify(this, player, " §6MOD: §b5ZIG", " §6KICK: §b" + TimberNoCheat.instance.settings.zig5_kick);
            out.writeByte(1);
            ByteArrayDataOutput out2 = ByteStreams.newDataOutput();
            out2.writeByte(2);
            ByteArrayDataOutput out4 = ByteStreams.newDataOutput();
            out4.writeByte(4);
            ByteArrayDataOutput out8 = ByteStreams.newDataOutput();
            out8.writeByte(8);
            ByteArrayDataOutput out10 = ByteStreams.newDataOutput();
            out10.writeByte(16);
            ByteArrayDataOutput all = ByteStreams.newDataOutput();
            all.writeByte(31);
            player.sendPluginMessage(TimberNoCheat.instance, chanelZIG, out.toByteArray());
            player.sendPluginMessage(TimberNoCheat.instance, chanelZIG, out2.toByteArray());
            player.sendPluginMessage(TimberNoCheat.instance, chanelZIG, out4.toByteArray());
            player.sendPluginMessage(TimberNoCheat.instance, chanelZIG, out8.toByteArray());
            player.sendPluginMessage(TimberNoCheat.instance, chanelZIG, out10.toByteArray());
            player.sendPluginMessage(TimberNoCheat.instance, chanelZIG, all.toByteArray());
        }else if(chanel.equals(chanelBSPRINT)){
            if(TimberNoCheat.instance.settings.bsprint_kick){
                player.kickPlayer(TimberNoCheat.instance.prefix + "§cBitte deinstaliere BSPRINT!");
            }
            TimberNoCheat.checkmanager.notify(this, player, " §6MOD: §bBSPRINT", " §6KICK: §b" + TimberNoCheat.instance.settings.bsprint_kick);
        }else if(chanel.equals(chanelBSM)){
            if(TimberNoCheat.instance.settings.bsm_kick){
                player.kickPlayer(TimberNoCheat.instance.prefix + "§cBitte deinstaliere BSM!");
            }
            TimberNoCheat.checkmanager.notify(this, player, " §6MOD: §bBSM", " §6KICK: §b" + TimberNoCheat.instance.settings.bsm_kick);
        }else if(StringUtil.containsIgnoreCase(chanel, "fml")){
            if(TimberNoCheat.instance.settings.fml_kick){
                player.kickPlayer(TimberNoCheat.instance.prefix + "§cBitte deinstaliere FORGE!");
            }
            TimberNoCheat.checkmanager.notify(this, player, " §6MOD: §bFORGE", " §6KICK: §b" + TimberNoCheat.instance.settings.fml_kick);
        }else if(StringUtil.containsIgnoreCase(chanel, "wbl")){
            if(TimberNoCheat.instance.settings.wdl_kick){
                player.kickPlayer(TimberNoCheat.instance.prefix + "§cBitte deinstaliere WBL!");
            }
            TimberNoCheat.checkmanager.notify(this, player, " §6MOD: §bWBL", " §6KICK: §b" + TimberNoCheat.instance.settings.wdl_kick);
        }else if (chanel.equalsIgnoreCase("MC|Brand")) {
            String brand;
            try {
                brand = new String(bytes, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return;
            }
            if (brand.equalsIgnoreCase("worlddownloader-vanilla")) {
                if(TimberNoCheat.instance.settings.wdl_kick){
                    player.kickPlayer(TimberNoCheat.instance.prefix + "§cBitte deinstaliere WDL!");
                }
                TimberNoCheat.checkmanager.notify(this, player, " §6MOD: §bWDL", " §6KICK: §b" + TimberNoCheat.instance.settings.wdl_kick);
            }
            if (brand.contains("fml") || brand.contains("forge")) {
                if(TimberNoCheat.instance.settings.fml_kick){
                    player.kickPlayer(TimberNoCheat.instance.prefix + "§cBitte deinstaliere FML!");
                }
                TimberNoCheat.checkmanager.notify(this, player, " §6MOD: §bFML", " §6KICK: §b" + TimberNoCheat.instance.settings.fml_kick);
            }
            if (brand.contains("LiteLoader") || brand.equalsIgnoreCase("LiteLoader")) {
                if(TimberNoCheat.instance.settings.liteloader_kick){
                    player.kickPlayer(TimberNoCheat.instance.prefix + "§cBitte deinstaliere LITELOADER!");
                }
                TimberNoCheat.checkmanager.notify(this, player, " §6MOD: §bLITELOADER", " §6KICK: §b" + TimberNoCheat.instance.settings.liteloader_kick);
            }
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer())){
            return;
        }
        for(String json : jsons){
            IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(json);
            PacketPlayOutChat chat = new PacketPlayOutChat(icbc);
            ((CraftPlayer)e.getPlayer()).getHandle().playerConnection.sendPacket(chat);
        }
    }
    @Override
    public void disable() {
        Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(TimberNoCheat.instance, chanelZIG, this);
        Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(TimberNoCheat.instance, chanelBSPRINT, this);
        Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(TimberNoCheat.instance, chanelBSM, this);
        Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(TimberNoCheat.instance, chanelWDLINIT, this);
        Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(TimberNoCheat.instance, chanelWDLCONTROL, this);
        Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(TimberNoCheat.instance, chanelMCBRAND, this);
        Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(TimberNoCheat.instance, chanelWDLREQ, this);
        Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(TimberNoCheat.instance, chanelFML, this);
        Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(TimberNoCheat.instance, chanelFMLHS, this);

        Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(TimberNoCheat.instance, chanelZIG);
        Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(TimberNoCheat.instance, chanelBSPRINT);
        Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(TimberNoCheat.instance, chanelBSM);
        Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(TimberNoCheat.instance, chanelWDLINIT);
        Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(TimberNoCheat.instance, chanelWDLCONTROL);
        Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(TimberNoCheat.instance, chanelMCBRAND);
        Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(TimberNoCheat.instance, chanelWDLREQ);
        Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(TimberNoCheat.instance, chanelFML);
        Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(TimberNoCheat.instance, chanelFMLHS);
    }

    public Other() {
        super("Other", Category.CLIENT_CHANEL);
        if(!TimberNoCheat.instance.settings.betterpvp_use)jsons.add("{\"text\":\"\",\"extra\":[{\"text\":\"\u00a7c \u00a7r\u00a75 \u00a7r\u00a71 \u00a7r\u00a7f \u00a7r\u00a70\"}]}");
        if(!TimberNoCheat.instance.settings.reiminimap_use)jsons.add("{\"text\":\"\",\"extra\":[{\"text\":\"\u00a70\u00a70\u00a71\u00a72\u00a73\u00a75\u00a7e\u00a7f\"},{\"text\":\"\u00a70\u00a70\u00a72\u00a73\u00a74\u00a75\u00a76\u00a77\u00a7e\u00a7f\"},{\"text\":\"\u00a7A\u00a7n\u00a7t\u00a7i\u00a7M\u00a7i\u00a7n\u00a7i\u00a7m\u00a7a\u00a7p\"}]}");
        if(!TimberNoCheat.instance.settings.damageindicators_use)jsons.add("{\"text\":\"\",\"extra\":[{\"text\":\"\u00a70\u00a70\u00a7c\u00a7d\u00a7e\u00a7f\"}]}");
        if(!TimberNoCheat.instance.settings.voxelmap_use)jsons.add("{\"text\":\"\",\"extra\":[{\"text\":\"\u00a73 \u00a76 \u00a73 \u00a76 \u00a73 \u00a76 \u00a7d\"},{\"text\":\"\u00a73 \u00a76 \u00a73 \u00a76 \u00a73 \u00a76 \u00a7e\"}]}");
        if(!TimberNoCheat.instance.settings.smartmoving_use)jsons.add("{\"text\":\"\",\"extra\":[{\"text\":\"\u00a70\u00a71\u00a70\u00a71\u00a72\u00a7f\u00a7f\"},{\"text\":\"\u00a70\u00a71\u00a73\u00a74\u00a7f\u00a7f\"},{\"text\":\"\u00a70\u00a71\u00a75\u00a7f\u00a7f\"},{\"text\":\"\u00a70\u00a71\u00a76\u00a7f\u00a7f\"},{\"text\":\"\u00a70\u00a71\u00a78\u00a79\u00a7a\u00a7b\u00a7f\u00a7f\"}]}");

        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(TimberNoCheat.instance, chanelZIG, this);
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(TimberNoCheat.instance, chanelBSPRINT, this);
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(TimberNoCheat.instance, chanelBSM, this);
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(TimberNoCheat.instance, chanelWDLINIT, this);
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(TimberNoCheat.instance, chanelWDLCONTROL, this);
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(TimberNoCheat.instance, chanelMCBRAND, this);
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(TimberNoCheat.instance, chanelWDLREQ, this);
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(TimberNoCheat.instance, chanelFML, this);
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(TimberNoCheat.instance, chanelFMLHS, this);

        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(TimberNoCheat.instance, chanelZIG);
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(TimberNoCheat.instance, chanelBSPRINT);
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(TimberNoCheat.instance, chanelBSM);
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(TimberNoCheat.instance, chanelWDLINIT);
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(TimberNoCheat.instance, chanelWDLCONTROL);
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(TimberNoCheat.instance, chanelMCBRAND);
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(TimberNoCheat.instance, chanelWDLREQ);
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(TimberNoCheat.instance, chanelFML);
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(TimberNoCheat.instance, chanelFMLHS);

    }

}
