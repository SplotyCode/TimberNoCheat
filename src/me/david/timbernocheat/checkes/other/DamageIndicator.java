package me.david.timbernocheat.checkes.other;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.vehicle.VehicleEnterEvent;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DamageIndicator extends Check {

    public DamageIndicator(){
        super("DamageIndicator", Category.OTHER);
        register(new PacketAdapter(TimberNoCheat.getInstance(), PacketType.Play.Server.ENTITY_METADATA) {
            @Override
            public void onPacketSending(PacketEvent event) {
                if(!CheckManager.getInstance().isvalid_create(event.getPlayer())){
                    return;
                }
                check(event);
            }
        });
    }

    private void check(PacketEvent event){
        Player observer = event.getPlayer();
        StructureModifier entityModifer = event.getPacket().getEntityModifier(observer.getWorld());
        Entity entity = (Entity)entityModifer.read(0);
        if (!(entity == null || observer == entity || !(entity instanceof LivingEntity) || entity instanceof EnderDragon && entity instanceof Wither || entity.getPassenger() != null && entity.getPassenger() == observer)) {
            event.setPacket(event.getPacket().deepClone());
            StructureModifier watcher = event.getPacket().getWatchableCollectionModifier();
            for (WrappedWatchableObject watch : (List<WrappedWatchableObject>)watcher.read(0)) {
                if (watch.getIndex() != 6 || (Float)watch.getValue() <= 0.0f) continue;
                watch.setValue(new Random().nextInt((int)((LivingEntity)entity).getMaxHealth()) + new Random().nextFloat());
            }
        }
    }
    @EventHandler
    public void onMount(final VehicleEnterEvent event) {
        if (event.getEntered() instanceof Player) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(TimberNoCheat.getInstance(), () -> {
                if (event.getVehicle().isValid() && event.getEntered().isValid()) {
                    ProtocolLibrary.getProtocolManager().updateEntity(event.getVehicle(), Collections.singletonList((Player) event.getEntered()));
                }
            });
        }
    }
}
