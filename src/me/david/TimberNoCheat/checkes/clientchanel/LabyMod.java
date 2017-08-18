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
    public LabyMod() {
        super("LabyMod", Category.CLIENT_CHANEL);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e){
        if(!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer())){
            return;
        }
        try {
            HashMap<String, Boolean> list = new HashMap<String, Boolean>();
            list.put("ARMOR", TimberNoCheat.instance.settings.labymod_use_armor);
            list.put("ANIMATIONS", TimberNoCheat.instance.settings.labymod_use_animations);
            list.put("BLOCKBUILD", TimberNoCheat.instance.settings.labymod_use_blockbuild);
            list.put("CHAT", TimberNoCheat.instance.settings.labymod_use_chat);
            list.put("DAMAGEINDICATOR", TimberNoCheat.instance.settings.labymod_use_damageindicator);
            list.put("EXTRAS", TimberNoCheat.instance.settings.labymod_use_extras);
            list.put("FOOD", TimberNoCheat.instance.settings.labymod_use_food);
            list.put("GUI", TimberNoCheat.instance.settings.labymod_use_gui);
            list.put("MINIMAP_RADAR", TimberNoCheat.instance.settings.labymod_use_minimap_radar);
            list.put("NICK", TimberNoCheat.instance.settings.labymod_use_nick);
            list.put("POTIONS", TimberNoCheat.instance.settings.labymod_use_potions);

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
