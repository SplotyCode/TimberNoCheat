package me.david.timbernocheat.checkes.clientchanel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutCustomPayload;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class LabyMod extends Check {
    
    private final boolean ANMIMATION;
    private final boolean ARMOR;
    private final boolean BLOCKBUILD;
    private final boolean CHAT;
    private final boolean DAMAGEINDICATOR;
    private final boolean EXTRAS;
    private final boolean FOOD;
    private final boolean GUI;
    private final boolean MINIMAP_RADAR;
    private final boolean NICK;
    private final boolean POTIONS;

    public LabyMod() {
        super("LabyMod", Category.CLIENT_CHANEL);
        ANMIMATION = getBoolean("animations");
        ARMOR = getBoolean("armor");
        BLOCKBUILD = getBoolean("blockbuild");
        CHAT = getBoolean("chat");
        DAMAGEINDICATOR = getBoolean("damageindicator");
        EXTRAS = getBoolean("extras");
        FOOD = getBoolean("food");
        GUI = getBoolean("gui");
        MINIMAP_RADAR = getBoolean("minimap_radar");
        NICK = getBoolean("nick");
        POTIONS = getBoolean("potions");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(final PlayerJoinEvent event){
        final Player player = event.getPlayer();
        if(!TimberNoCheat.getCheckManager().isvalid_create(event.getPlayer()))
            return;
        try {
            HashMap<String, Boolean> list = new HashMap<String, Boolean>();
            list.put("ARMOR", ARMOR);
            list.put("ANIMATIONS", ANMIMATION);
            list.put("BLOCKBUILD", BLOCKBUILD);
            list.put("CHAT", CHAT);
            list.put("DAMAGEINDICATOR", DAMAGEINDICATOR);
            list.put("EXTRAS", EXTRAS);
            list.put("FOOD", FOOD);
            list.put("GUI", GUI);
            list.put("MINIMAP_RADAR", MINIMAP_RADAR);
            list.put("NICK", NICK);
            list.put("POTIONS", POTIONS);

            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(list);
            ByteBuf a = Unpooled.copiedBuffer(byteOut.toByteArray());
            PacketDataSerializer b = new PacketDataSerializer(a);
            PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload("LABYMOD", b);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
