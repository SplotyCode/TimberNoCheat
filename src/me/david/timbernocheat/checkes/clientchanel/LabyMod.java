package me.david.timbernocheat.checkes.clientchanel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
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
    
    private final boolean animations;
    private final boolean armor;
    private final boolean blockbuild;
    private final boolean chat;
    private final boolean damageindicator;
    private final boolean extras;
    private final boolean food;
    private final boolean gui;
    private final boolean minimap_radar;
    private final boolean nick;
    private final boolean potions;

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