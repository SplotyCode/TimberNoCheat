package me.david.timbernocheat.checkes.combat;

import com.mojang.authlib.GameProfile;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.api.utils.NumberUtil;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import java.util.Random;
import java.util.UUID;

//TODO
public class AuraBots extends Check {

    private final boolean armor;
    private final boolean armor_enchant;
    private final boolean visible;
    private final boolean realplayer;

    public AuraBots() {
        super("AuraBots", Category.COMBAT);
        armor = getBoolean("armor");
        armor_enchant = getBoolean("armor_enchant");
        visible = getBoolean("visible");
        realplayer = getBoolean("realplayer");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        final Player p = event.getPlayer();
        if(!CheckManager.getInstance().isvalid_create(p)) return;
        spawnncp(p);
    }

    private void spawnncp(Player player){
        EntityPlayer npc = new EntityPlayer(((CraftServer) Bukkit.getServer()).getServer(), ((CraftWorld) player.getWorld()).getHandle(), new GameProfile(UUID.randomUUID(), randomString()), new PlayerInteractManager(((CraftWorld) player.getWorld()).getHandle()));
        Location loc = player.getLocation();
        npc.setInvisible(visible);
        npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        CheckManager.getInstance().getPlayerdata(player).setBot(npc);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event){
        if(!CheckManager.getInstance().isvalid_create(event.getPlayer())) return;
        if(event.getFrom().distance(event.getTo()) < 15)return;
        PlayerData pd = CheckManager.getInstance().getPlayerdata(event.getPlayer());
        EntityPlayer npc = pd.getBot();
        npc.move(event.getTo().getX(), event.getTo().getY(), event.getTo().getZ());
        pd.setBot(npc);
    }

    @EventHandler
    public void onWorld(PlayerChangedWorldEvent event){
        if(!CheckManager.getInstance().isvalid_create(event.getPlayer())) return;
        ((CraftPlayer) event.getPlayer()).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(CheckManager.getInstance().getPlayerdata(event.getPlayer()).getBot().getId()));
        spawnncp(event.getPlayer());
    }

    @EventHandler
    public void onWorld(PlayerRespawnEvent event){
        if(!CheckManager.getInstance().isvalid_create(event.getPlayer())) return;
        spawnncp(event.getPlayer());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if(!CheckManager.getInstance().isvalid_create(event.getEntity())) return;
        ((CraftPlayer) event.getEntity()).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(CheckManager.getInstance().getPlayerdata(event.getEntity()).getBot().getId()));
    }

    private EntityPlayer armor(EntityPlayer player){
        for(int i = 0;i<4;i++)
            if(NumberUtil.randInt(1, 4) != 3){}
        return null;
    }



    private String randomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 16) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();

    }
}
