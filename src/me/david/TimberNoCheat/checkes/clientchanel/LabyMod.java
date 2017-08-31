package me.david.TimberNoCheat.checkes.clientchanel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutCustomPayload;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class LabyMod extends Check {
    
    boolean animations;
    boolean armor;
    boolean blockbuild;
    boolean chat;
    boolean damageindicator;
    boolean extras;
    boolean food;
    boolean gui;
    boolean minimap_radar;
    boolean nick;
    boolean potions;

    public LabyMod() {
        super("LabyMod", Category.CLIENT_CHANEL);
        animations = getBoolean("animations");
        armor = getBoolean("armor");
        blockbuild = getBoolean("blockbuild");
        chat = getBoolean("chat");
        damageindicator = getBoolean("damageindicator");
        extras = getBoolean("extras");
        food = getBoolean("food");
        gui = getBoolean("gui");
        minimap_radar = getBoolean("minimap_radar");
        nick = getBoolean("nick");
        potions = getBoolean("potions");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e){
        if(!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer())){
            return;
        }
        try {
            HashMap<String, Boolean> list = new HashMap<String, Boolean>();
            list.put("ARMOR", armor);
            list.put("ANIMATIONS", animations);
            list.put("BLOCKBUILD", blockbuild);
            list.put("CHAT", chat);
            list.put("DAMAGEINDICATOR", damageindicator);
            list.put("EXTRAS", extras);
            list.put("FOOD", food);
            list.put("GUI", gui);
            list.put("MINIMAP_RADAR", minimap_radar);
            list.put("NICK", nick);
            list.put("POTIONS", potions);

            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(list);
            ByteBuf a = Unpooled.copiedBuffer(byteOut.toByteArray());
            PacketDataSerializer b = new PacketDataSerializer(a);
            PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload("LABYMOD", b);
            ((CraftPlayer)e.getPlayer()).getHandle().playerConnection.sendPacket(packet);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
