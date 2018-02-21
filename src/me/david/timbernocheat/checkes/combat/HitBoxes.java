package me.david.timbernocheat.checkes.combat;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import me.david.timbernocheat.checkmanager.PlayerData;
import me.david.timbernocheat.debug.Debuggers;
import me.david.api.utils.cordinates.RotationUtil;
import me.david.api.utils.player.PlayerUtil;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public class HitBoxes extends Check {

    private final boolean advanced;
    private final float advancedincrease;
    private final boolean normal;

    public HitBoxes() {
        super("HitBoxes", Category.COBMAT);
        advanced = getBoolean("advanced.enable");
        advancedincrease = (float) getDouble("advanced.increase");
        normal = getBoolean("normal");
        register(new PacketAdapter(TimberNoCheat.instance, ListenerPriority.HIGH, PacketType.Play.Client.POSITION_LOOK) {
            public void onPacketReceiving(PacketEvent event) {
                if(TimberNoCheat.checkmanager.isvalid_create(event.getPlayer())){
                    PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(event.getPlayer());
                    pd.setHitboxyaw(Math.abs(event.getPacket().getFloat().read(0)-pd.getLastyaw()));
                }
            }
        });
    }

    @EventHandler
    public void onUse(EntityDamageByEntityEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) return;
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) return;
        Player player = (Player) event.getDamager();
        if(!TimberNoCheat.checkmanager.isvalid_create(player))return;
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(player);
        Player attacked = (Player) event.getEntity();
        float nomral = normal(pd, player, attacked);
        if(normal && nomral != 0) if(updateVio(this, player, nomral<1?1:nomral, " §6MODE: §bNORMAL")) event.setCancelled(true);
        float advanced = advanced(pd, player, attacked);
        if(this.advanced && advanced != 0) if(updateVio(this, player, advanced<1?1:advanced*1.4, " §6MODE: §bADVANCED")) event.setCancelled(true);
    }

    private float advanced(PlayerData pd, Player player, Player attacked){
        AxisAlignedBB box = ((CraftEntity) attacked).getHandle().getBoundingBox();
        box = box.grow(advancedincrease, advancedincrease, advancedincrease);
        TimberNoCheat.instance.getDebugger().sendDebug(Debuggers.HITBOX, "MIN_COORDS: x=" + box.a + " y=" + box.b + " z=" + box.c);
        TimberNoCheat.instance.getDebugger().sendDebug(Debuggers.HITBOX, "MAX_COORDS: x=" + box.d + " y=" + box.e + " z=" + box.f);

        float[] min = RotationUtil.getRotation(player, box.a, box.b, box.c);
        float[] max = RotationUtil.getRotation(player, box.d, box.e, box.f);
        TimberNoCheat.instance.getDebugger().sendDebug(Debuggers.HITBOX, "MIN_ROTS: yaw=" + min[0] + " pitch=" + min[1]);
        TimberNoCheat.instance.getDebugger().sendDebug(Debuggers.HITBOX, "MAX_ROTS: yaw=" + max[0] + " pitch=" + max[0]);
        float yaw = player.getLocation().getYaw();
        float pitch = player.getLocation().getPitch();
        TimberNoCheat.instance.getDebugger().sendDebug(Debuggers.HITBOX, "PLAYER_ROTS: yaw=" + yaw + " pitch=" + pitch);
        if(yaw < Math.min(min[0], max[0])) return Math.min(min[0], max[0])-yaw;
        if(yaw > Math.max(min[0], max[0])) return yaw-Math.max(min[0], max[0]);
        if(pitch < Math.min(min[1], max[1])) return Math.min(min[1], max[1])-pitch;
        if(pitch > Math.max(min[1], max[1])) return pitch-Math.max(min[1], max[1]);
        return 0;
    }

    private float normal(PlayerData pd, Player player, Player attacked){
        Player lastPlayer = pd.getLastattaked()==null?attacked:pd.getLastattaked();

        if (lastPlayer != attacked) {
            pd.setLastattaked(attacked);
            return 0;
        }

        double offset = getOffsetCursor(player, attacked);
        double limit = 108D;
        double distance = Math.abs(player.getLocation().getY()-attacked.getLocation().getY());
        limit += distance * 57;
        limit += (attacked.getVelocity().length() + player.getVelocity().length()) * 64;
        limit += pd.getHitboxyaw() * 6;

        if (offset > limit) return (float) (offset - limit);
        return 0;
    }

    private double getOffsetCursor(Player player, Player player2) {
        double offset = 0.0D;
        double[] offsets = getOffsetsOffCursor(player, player2);

        offset += offsets[0];
        offset += offsets[1];

        return offset;
    }

    private double[] getOffsetsOffCursor(Player player, Player player2) {
        Location entityLoc = PlayerUtil.getEyeLocation(player2);
        Location playerLoc = PlayerUtil.getEyeLocation(player);

        Vector playerRotation = new Vector(playerLoc.getYaw(), playerLoc.getPitch(), 0.0F);
        Vector expectedRotation = RotationUtil.getRotation(playerLoc, entityLoc);

        double deltaYaw = RotationUtil.clamp180(playerRotation.getX() - expectedRotation.getX());
        double deltaPitch = RotationUtil.clamp180(playerRotation.getY() - expectedRotation.getY());

        double horizontalDistance = Math.abs(playerLoc.getY()-entityLoc.getY());
        double distance = RotationUtil.getDistance3D(playerLoc, entityLoc);

        double offsetX = deltaYaw * horizontalDistance * distance;
        double offsetY = deltaPitch * Math.abs(Math.sqrt(entityLoc.getY() - playerLoc.getY())) * distance;

        return new double[] { Math.abs(offsetX), Math.abs(offsetY) };
    }


}
