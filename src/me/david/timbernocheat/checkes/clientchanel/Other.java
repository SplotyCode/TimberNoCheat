package me.david.timbernocheat.checkes.clientchanel;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.api.utils.StringUtil;
import me.david.timbernocheat.checkbase.CheckManager;
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

    private final String chanelZIG = "5zig_Set";
    private final String chanelBSPRINT = "BSprint";
    private final String chanelBSM = "BSM";
    private final String chanelWDLINIT = "WDL|INIT";
    private final String chanelWDLCONTROL = "WDL|CONTROL";
    private final String chanelMCBRAND = "MC|Brand";
    private final String chanelWDLREQ = "WDL|REQUEST";
    private final String chanelFML = "FML";
    private final String chanelFMLHS = "FMLHS";
    private final boolean betterpvp_block;
    private final boolean reiminimap_block;
    private final boolean damageindicators_block;
    private final boolean voxelmap_block;
    private final boolean smartmoving_block;
    private final boolean zig5_kick;
    private final boolean wdl_kick;
    private final boolean bsprint_kick;
    private final boolean bsm_kick;
    private final boolean fml_kick;
    private final boolean liteloader_kick;

    private final ArrayList<String>jsons = new ArrayList<>();

    @Override
    public void onPluginMessageReceived(String chanel, Player player, byte[] bytes) {
        if(!CheckManager.getInstance().isvalid_create(player)) return;
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        if(chanel.equals(chanelZIG)){
            if(zig5_kick){
                player.kickPlayer(TimberNoCheat.getInstance().getPrefix() + "§cBitte deinstaliere 5zig!");
            }
            updateVio(this, player, 1, " §6MOD: §b5ZIG", " §6KICK: §b" + zig5_kick);
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
            player.sendPluginMessage(TimberNoCheat.getInstance(), chanelZIG, out.toByteArray());
            player.sendPluginMessage(TimberNoCheat.getInstance(), chanelZIG, out2.toByteArray());
            player.sendPluginMessage(TimberNoCheat.getInstance(), chanelZIG, out4.toByteArray());
            player.sendPluginMessage(TimberNoCheat.getInstance(), chanelZIG, out8.toByteArray());
            player.sendPluginMessage(TimberNoCheat.getInstance(), chanelZIG, out10.toByteArray());
            player.sendPluginMessage(TimberNoCheat.getInstance(), chanelZIG, all.toByteArray());
        }else if(chanel.equals(chanelBSPRINT)){
            if(bsprint_kick){
                player.kickPlayer(TimberNoCheat.getInstance().getPrefix() + "§cBitte deinstaliere BSPRINT!");
            }
            updateVio(this, player, 1, " §6MOD: §bBSPRINT", " §6KICK: §b" + bsprint_kick);
        }else if(chanel.equals(chanelBSM)){
            if(bsm_kick){
                player.kickPlayer(TimberNoCheat.getInstance().getPrefix() + "§cBitte deinstaliere BSM!");
            }
            updateVio(this, player, 1, " §6MOD: §bBSM", " §6KICK: §b" + bsm_kick);
        }else if(StringUtil.containsIgnoreCase(chanel, "fml")){
            if(fml_kick){
                player.kickPlayer(TimberNoCheat.getInstance().getPrefix() + "§cBitte deinstaliere FORGE!");
            }
            updateVio(this, player, 1, " §6MOD: §bFORGE", " §6KICK: §b" + fml_kick);
        }else if(StringUtil.containsIgnoreCase(chanel, "wbl")){
            if(wdl_kick){
                player.kickPlayer(TimberNoCheat.getInstance().getPrefix() + "§cBitte deinstaliere WBL!");
            }
            updateVio(this, player, 1, " §6MOD: §bWBL", " §6KICK: §b" + wdl_kick);
        }else if (chanel.equalsIgnoreCase("MC|Brand")) {
            String brand;
            try {
                brand = new String(bytes, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return;
            }
            if (brand.equalsIgnoreCase("worlddownloader-vanilla")) {
                if(wdl_kick){
                    player.kickPlayer(TimberNoCheat.getInstance().getPrefix() + "§cBitte deinstaliere WDL!");
                }
                updateVio(this, player, 1, " §6MOD: §bWDL", " §6KICK: §b" + wdl_kick);
            }
            if (brand.contains("fml") || brand.contains("forge")) {
                if(fml_kick){
                    player.kickPlayer(TimberNoCheat.getInstance().getPrefix() + "§cBitte deinstaliere FML!");
                }
                updateVio(this, player, 1, " §6MOD: §bFML", " §6KICK: §b" + fml_kick);
            }
            if (brand.contains("LiteLoader") || brand.equalsIgnoreCase("LiteLoader")) {
                if(liteloader_kick){
                    player.kickPlayer(TimberNoCheat.getInstance().getPrefix() + "§cBitte deinstaliere LITELOADER!");
                }
                updateVio(this, player, 1, " §6MOD: §bLITELOADER", " §6KICK: §b" + liteloader_kick);
            }
        }
    }
    @EventHandler
    public void onJoin(final PlayerJoinEvent event){
        final Player player = event.getPlayer();
        if(!CheckManager.getInstance().isvalid_create(player)){
            return;
        }
        for(String json : jsons){
            IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a(json);
            PacketPlayOutChat chat = new PacketPlayOutChat(icbc);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(chat);
        }
    }
    @Override
    public void disable() {
        Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(TimberNoCheat.getInstance(), chanelZIG, this);
        Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(TimberNoCheat.getInstance(), chanelBSPRINT, this);
        Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(TimberNoCheat.getInstance(), chanelBSM, this);
        Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(TimberNoCheat.getInstance(), chanelWDLINIT, this);
        Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(TimberNoCheat.getInstance(), chanelWDLCONTROL, this);
        Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(TimberNoCheat.getInstance(), chanelMCBRAND, this);
        Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(TimberNoCheat.getInstance(), chanelWDLREQ, this);
        Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(TimberNoCheat.getInstance(), chanelFML, this);
        Bukkit.getServer().getMessenger().unregisterIncomingPluginChannel(TimberNoCheat.getInstance(), chanelFMLHS, this);

        Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(TimberNoCheat.getInstance(), chanelZIG);
        Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(TimberNoCheat.getInstance(), chanelBSPRINT);
        Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(TimberNoCheat.getInstance(), chanelBSM);
        Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(TimberNoCheat.getInstance(), chanelWDLINIT);
        Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(TimberNoCheat.getInstance(), chanelWDLCONTROL);
        Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(TimberNoCheat.getInstance(), chanelMCBRAND);
        Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(TimberNoCheat.getInstance(), chanelWDLREQ);
        Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(TimberNoCheat.getInstance(), chanelFML);
        Bukkit.getServer().getMessenger().unregisterOutgoingPluginChannel(TimberNoCheat.getInstance(), chanelFMLHS);
    }

    public Other() {
        super("Other", Category.CLIENT_CHANEL);
        betterpvp_block = getBoolean("betterpvp_block");
        reiminimap_block = getBoolean("reiminimap_block");
        damageindicators_block = getBoolean("damageindicators_block");
        voxelmap_block = getBoolean("voxelmap_block");
        smartmoving_block = getBoolean("smartmoving_block");
        zig5_kick = getBoolean("zig5_kick");
        wdl_kick = getBoolean("wdl_kick");
        bsprint_kick = getBoolean("bsprint_kick");
        bsm_kick = getBoolean("bsm_kick");
        fml_kick = getBoolean("fml_kick");
        liteloader_kick = getBoolean("liteloader_kick");
        if(betterpvp_block)jsons.add("{\"text\":\"\",\"extra\":[{\"text\":\"\u00a7c \u00a7r\u00a75 \u00a7r\u00a71 \u00a7r\u00a7f \u00a7r\u00a70\"}]}");
        if(reiminimap_block)jsons.add("{\"text\":\"\",\"extra\":[{\"text\":\"\u00a70\u00a70\u00a71\u00a72\u00a73\u00a75\u00a7e\u00a7f\"},{\"text\":\"\u00a70\u00a70\u00a72\u00a73\u00a74\u00a75\u00a76\u00a77\u00a7e\u00a7f\"},{\"text\":\"\u00a7A\u00a7n\u00a7t\u00a7i\u00a7M\u00a7i\u00a7n\u00a7i\u00a7m\u00a7a\u00a7p\"}]}");
        if(damageindicators_block)jsons.add("{\"text\":\"\",\"extra\":[{\"text\":\"\u00a70\u00a70\u00a7c\u00a7d\u00a7e\u00a7f\"}]}");
        if(voxelmap_block)jsons.add("{\"text\":\"\",\"extra\":[{\"text\":\"\u00a73 \u00a76 \u00a73 \u00a76 \u00a73 \u00a76 \u00a7d\"},{\"text\":\"\u00a73 \u00a76 \u00a73 \u00a76 \u00a73 \u00a76 \u00a7e\"}]}");
        if(smartmoving_block)jsons.add("{\"text\":\"\",\"extra\":[{\"text\":\"\u00a70\u00a71\u00a70\u00a71\u00a72\u00a7f\u00a7f\"},{\"text\":\"\u00a70\u00a71\u00a73\u00a74\u00a7f\u00a7f\"},{\"text\":\"\u00a70\u00a71\u00a75\u00a7f\u00a7f\"},{\"text\":\"\u00a70\u00a71\u00a76\u00a7f\u00a7f\"},{\"text\":\"\u00a70\u00a71\u00a78\u00a79\u00a7a\u00a7b\u00a7f\u00a7f\"}]}");

        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(TimberNoCheat.getInstance(), chanelZIG, this);
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(TimberNoCheat.getInstance(), chanelBSPRINT, this);
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(TimberNoCheat.getInstance(), chanelBSM, this);
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(TimberNoCheat.getInstance(), chanelWDLINIT, this);
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(TimberNoCheat.getInstance(), chanelWDLCONTROL, this);
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(TimberNoCheat.getInstance(), chanelMCBRAND, this);
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(TimberNoCheat.getInstance(), chanelWDLREQ, this);
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(TimberNoCheat.getInstance(), chanelFML, this);
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(TimberNoCheat.getInstance(), chanelFMLHS, this);

        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(TimberNoCheat.getInstance(), chanelZIG);
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(TimberNoCheat.getInstance(), chanelBSPRINT);
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(TimberNoCheat.getInstance(), chanelBSM);
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(TimberNoCheat.getInstance(), chanelWDLINIT);
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(TimberNoCheat.getInstance(), chanelWDLCONTROL);
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(TimberNoCheat.getInstance(), chanelMCBRAND);
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(TimberNoCheat.getInstance(), chanelWDLREQ);
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(TimberNoCheat.getInstance(), chanelFML);
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(TimberNoCheat.getInstance(), chanelFMLHS);
    }

}
