package me.david.timbernocheat.checkes.other;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class AntiESP extends Check {

    private HashMap<String, Set<Integer>> hidden = new HashMap<String, Set<Integer>>();

    private final boolean items;

    public AntiESP() {
        super("AntiESP", Category.OTHER);
        items = getBoolean("items");
        TimberNoCheat.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(TimberNoCheat.getInstance(), () -> {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if (!CheckManager.getInstance().isvalid_create(player)) return;
                List<Entity> nearbyEntities = player.getNearbyEntities(12, 255, 12);
                nearbyEntities.remove(player);
                nearbyEntities.forEach(target -> {
                    if (!(target instanceof Item) || items) check(player, target);
                });
            }
        }, 0, 2);
        register(new PacketAdapter(TimberNoCheat.getInstance(), PacketType.Play.Server.ENTITY_EQUIPMENT, PacketType.Play.Server.BED, PacketType.Play.Server.ANIMATION, PacketType.Play.Server.NAMED_ENTITY_SPAWN, PacketType.Play.Server.COLLECT, PacketType.Play.Server.SPAWN_ENTITY, PacketType.Play.Server.SPAWN_ENTITY_LIVING, PacketType.Play.Server.SPAWN_ENTITY_PAINTING, PacketType.Play.Server.SPAWN_ENTITY_EXPERIENCE_ORB, PacketType.Play.Server.ENTITY_VELOCITY, PacketType.Play.Server.REL_ENTITY_MOVE, PacketType.Play.Server.ENTITY_LOOK, PacketType.Play.Server.ENTITY_TELEPORT, PacketType.Play.Server.ENTITY_HEAD_ROTATION, PacketType.Play.Server.ENTITY_STATUS, PacketType.Play.Server.ATTACH_ENTITY, PacketType.Play.Server.ENTITY_METADATA, PacketType.Play.Server.ENTITY_EFFECT, PacketType.Play.Server.REMOVE_ENTITY_EFFECT, PacketType.Play.Server.BLOCK_BREAK_ANIMATION) {
            @Override
            public void onPacketSending(PacketEvent event) {
                int entityID = event.getPacket().getIntegers().read(0);
                if (!isVisible(event.getPlayer(), entityID)) {
                    event.setCancelled(true);
                }
            }
        });
    }

    private void check(Player player, Entity entity){
            if (!player.hasLineOfSight(entity)) hideEntity(player, entity);
            else Bukkit.getScheduler().runTaskLater(TimberNoCheat.getInstance(), () -> showEntity(player, entity), 1);
    }

    private void showEntity(Player observer, Entity entity) {
        boolean hiddenBefore = !setVisibility(observer, entity.getEntityId(), true);
        if(entity.isDead() ) {
            removeEntity(entity);
            return;
        }
        if (TimberNoCheat.getInstance().getProtocolmanager() != null && hiddenBefore) {
            try{
                TimberNoCheat.getInstance().getProtocolmanager().updateEntity(entity, Collections.singletonList(observer));
            } catch (IllegalArgumentException e){
                e.printStackTrace();
            } finally {
                if(entity.isInsideVehicle()){
                    showEntity(observer, entity.getVehicle());
                    PacketContainer mountEntity = new PacketContainer(PacketType.Play.Server.MOUNT);
                    mountEntity.getIntegers().write(0, entity.getVehicle().getEntityId());
                    mountEntity.getIntegerArrays().write(0, new int[]{entity.getEntityId()});
                    try {
                        TimberNoCheat.getInstance().getProtocolmanager().sendServerPacket(observer, mountEntity);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    private void hideEntity(Player observer, Entity entity) {
        boolean visibleBefore = setVisibility(observer, entity.getEntityId(), false);
        if (visibleBefore) {
            PacketContainer destroyEntity = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
            destroyEntity.getIntegerArrays().write(0, new int[]{entity.getEntityId()});
            try {
                TimberNoCheat.getInstance().getProtocolmanager().sendServerPacket(observer, destroyEntity);
            } catch (InvocationTargetException e) {
               e.printStackTrace();
            }
        }
    }

    private boolean setVisibility(Player observer, int entityID, boolean visible) {
        Set<Integer> hiddenEntity = getHiddenEntity(observer);
        assert hiddenEntity != null;
        if (hiddenEntity.contains(entityID)) {
            if (visible) {
                hiddenEntity.remove(entityID);
            }
            return false;
        }
        if (!visible) {
            hiddenEntity.add(entityID);
        }
        return true;
    }

    private Set<Integer> getHiddenEntity(Player p) {
        Set<Integer> hiddenEntity;
        try {
            hiddenEntity = hidden.get(p.getUniqueId().toString());
        }
        catch (UnsupportedOperationException e) {
            return null;
        }
        if (hiddenEntity == null) {
            hiddenEntity = Collections.newSetFromMap(new LinkedHashMap<Integer, Boolean>(){
                @Override
                protected boolean removeEldestEntry(Map.Entry<Integer, Boolean> eldest) {
                    return size() > 500;
                }
            });
            hidden.put(p.getUniqueId().toString(), hiddenEntity);
        }
        return hiddenEntity;
    }

    private boolean isVisible(Player player, int entityID) {
        Set<Integer> hiddenEntity = getHiddenEntity(player);
        return hiddenEntity == null || !hiddenEntity.contains(entityID);
    }

    @EventHandler
    private void onEntityDeath(EntityDeathEvent e) {
        removeEntity(e.getEntity());
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent e) {
        hidden.remove(e.getPlayer().getUniqueId().toString());
        removeEntity(e.getPlayer());
    }

    private void removeEntity(Entity entity) {
        hidden.forEach((k, hideEntities) -> {
                    Iterator iter = hideEntities.iterator();
                    while (iter.hasNext()) {
                        Integer hideEntityId = (Integer)iter.next();
                        if (hideEntityId != entity.getEntityId()) continue;
                        iter.remove();
                    }
                }
        );
    }
}
