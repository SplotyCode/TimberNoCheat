package me.david.timbernocheat.checkes.movement;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import comphenix.packetwrapper.WrapperPlayClientLook;
import comphenix.packetwrapper.WrapperPlayClientPositionLook;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.timbernocheat.runnable.Tps;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Rotate extends Check {

    public Rotate(){
        super("Rotate", Category.MOVEMENT);
        //setViofornotify(7);
        //setViodelay(8000);
        register(new PacketAdapter(TimberNoCheat.getInstance(), ListenerPriority.HIGH, PacketType.Play.Client.POSITION_LOOK, PacketType.Play.Client.LOOK) {
             public void onPacketReceiving(PacketEvent event) {
                 Player p = event.getPlayer();
                 if (p == null || !CheckManager.getInstance().isvalid_create(p) || p.isDead()) return;
                 PlayerData pd = CheckManager.getInstance().getPlayerdata(p);
                 PacketType type = event.getPacket().getType();
                 final long teleportDelay = System.currentTimeMillis()-pd.getLastTeleport();
                 if(!pd.isTeleportUsed() && teleportDelay < 5000){
                     pd.setTeleportUsed(true);
                     return;
                 }
                 float yaw = -1;
                 float pitch = -1;
                 if (type == WrapperPlayClientLook.TYPE){
                    WrapperPlayClientLook wrapper = new WrapperPlayClientLook(event.getPacket());
                    yaw = wrapper.getYaw();
                    pitch = wrapper.getPitch();
                 }else if(type == WrapperPlayClientPositionLook.TYPE){
                     WrapperPlayClientPositionLook wrapper = new WrapperPlayClientPositionLook(event.getPacket());
                     yaw = wrapper.getYaw();
                     pitch = wrapper.getPitch();
                 }
                 if(pd.getLastYaw() == yaw && pd.getLastPitch() == pitch)
                     Rotate.this.updateVio(Rotate.this, p, 1, " §6MODE: §bEQULAS");
                 pd.setLastYaw(yaw);
                 pd.setLastPitch(pitch);
             }
        });
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRotate(PlayerMoveEvent event){
        Player p = event.getPlayer();
        if (p == null || !CheckManager.getInstance().isvalid_create(p)) return;
        PlayerData pd = CheckManager.getInstance().getPlayerdata(p);
        if(event.isCancelled()){
            pd.setTeleportUsed(false);
            pd.setLastTeleport(System.currentTimeMillis());
        }
        float from = (event.getFrom().getYaw()-90) % 360;
        float to = (event.getTo().getYaw()-90) % 360;
        if(from < 0) from += 360;
        if(to < 0) to += 360;
        float distance = Math.abs(from-to);
        if ((!(from >= 90 || to <= 270) && (to >= 90 || from <= 270))) distance -= 360;
        distance = Math.abs(distance);
        if(distance > 80)
            pd.setSnappyRotate(Tps.tickCount);
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player){
            Player p = (Player) event.getDamager();
            if (p == null || !CheckManager.getInstance().isvalid_create(p)) return;
            PlayerData pd = CheckManager.getInstance().getPlayerdata(p);
            if(Tps.tickCount - pd.getSnappyRotate() < 2 && event.getEntity().getLocation().toVector().subtract(p.getLocation().toVector()).normalize().dot(p.getLocation().getDirection()) > 0.97)
                updateVio(this, p, 1, " §6MODE: §bSNAPPY");
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onTeleport(final PlayerTeleportEvent event){
        final long time = System.currentTimeMillis();
        final Player player = event.getPlayer();
        if (!CheckManager.getInstance().isvalid_create(player)) return;
        PlayerData pd = CheckManager.getInstance().getPlayerdata(player);
        pd.setTeleportUsed(false);
        pd.setLastTeleport(System.currentTimeMillis());
    }


}
