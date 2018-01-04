package me.david.TimberNoCheat.record;

import me.david.TimberNoCheat.TimberNoCheat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class RecordSaveHandler implements Listener {

    private DataOutputStream file;
    private HashMap<UUID, RecordPlayer> players;
    private UUID main;

    public RecordSaveHandler(UUID main, DataOutputStream file){
        this.main = main;
        this.file = file;
        players = new HashMap<>();
    }

    public RecordSaveHandler(UUID main, File file) throws FileNotFoundException {
        this.file = new DataOutputStream(new FileOutputStream(file));
        players = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, TimberNoCheat.instance);
    }

    public void flushAndStop() throws IOException {
        file.write(RecordConstants.END_OF_FILE);
        file.close();
        HandlerList.unregisterAll(this);
    }

    private int ticks;

    public boolean tick() {
        if(ticks == TimberNoCheat.instance.getRecordManager().getRecord().getMaxleanght())
            return true;
        ticks++;
        for(RecordPlayer player : players.values())
            player.update();
        try {
            write();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Iterator it = players.entrySet().iterator();
        while (it.hasNext()) {
            RecordPlayer e = (RecordPlayer)((Map.Entry)it.next()).getValue();
            if (e.isDel()) it.remove();
            else e.reset();
        }
        return false;
    }

    private void write() throws IOException {
        for (Map.Entry<UUID, RecordPlayer> entry : players.entrySet()) {
            RecordPlayer p = entry.getValue();
            Player player = p.getPlayer();
            if (p.isDel()) {
                file.writeByte(RecordConstants.REMOVE_PLAYER);
                continue;
            }
            if (p.isJoin()) {
                file.writeByte(RecordConstants.ADD_PLAYER);
                saveUUID(player.getUniqueId());
                file.writeUTF(player.getDisplayName());
                saveLoc(player.getLocation());
            }
            if (p.isRespawn())
                file.write(RecordConstants.RESPAWN);
            if (p.isUpdateSneaking())
                file.write(p.isSneaking()?RecordConstants.NOW_SNEAKING:RecordConstants.NOW_NOT_SNEAKING);
            if (p.isUpdateSprinting())
                file.write(p.isSprinting()?RecordConstants.NOW_SPRINTING:RecordConstants.NOW_NOT_SPRINTING);
            if (p.isSwingArm()) file.write(RecordConstants.SWING_ARM);
            if (p.isHurt()) file.write(RecordConstants.HURT);
            if(p.isUpdateHand()) {
                file.write(RecordConstants.UPDATE_HAND);
                saveItem(p.getItemInHand());
            }
            if (p.isUpdateArmor()) {
                file.write(RecordConstants.UPDATE_ARMOR);
                saveItem(p.getHelmet());
                saveItem(p.getChestplate());
                saveItem(p.getLeggings());
                saveItem(p.getBoots());
            }
            if (p.isDeath()) file.write(RecordConstants.DEATH);
            if (p.isUpdateFire())
                file.write(p.isOnFire()?RecordConstants.FIRE_START:RecordConstants.FIRE_STOP);
            file.write(RecordConstants.EXPECT_LOC_VEL);
            saveLoc(p.getLoc());
            saveVector(p.getVel());
        }
    }

    private void saveUUID(UUID uuid) throws IOException {
        file.writeLong(uuid.getMostSignificantBits());
        file.writeLong(uuid.getLeastSignificantBits());
    }

    private void saveLoc(Location location) throws IOException {
        file.writeDouble(location.getY());
        file.writeDouble(location.getZ());

        file.writeFloat(location.getYaw());
        file.writeFloat(location.getPitch());
    }

    private void saveVector(Vector vel) throws IOException {
        file.writeDouble(vel.getY());
        file.writeDouble(vel.getZ());
    }

    private void saveItem(ItemStack item) throws IOException {
        if ((item == null) || (item.getType() == Material.AIR))
            file.writeInt(0);
        else {
            file.writeInt(item.getTypeId());
            /* For sub-id for example wool */
            file.writeShort(item.getDurability());
            file.writeBoolean(!item.getEnchantments().isEmpty());
        }
    }

    public void removePlayer(Player player, RemoveReason reason) {
        if(isInCurrentlyInRecord(player)) {
            players.get(player.getUniqueId()).setDel(true);
            if(main == player.getUniqueId()){
                Recording rec = TimberNoCheat.instance.getRecordManager().getRecoardingbyMain(player);
                rec.stop();
                TimberNoCheat.instance.getRecordManager().getRecordings().remove(rec);
                TimberNoCheat.instance.notify("Die Aufname von " + player.getDisplayName() + " wurde gestopt der hauptcharakter nicht mehr da ist!");
            }else
                TimberNoCheat.instance.notify("Der Spieler" + player.getDisplayName() + " wurde aus der Aufnahme von " + Bukkit.getPlayer(main).getDisplayName() + " removed! Grund: " + (reason == RemoveReason.LEAVE?"Server Leave":reason == RemoveReason.WORLDCHANGE?"World Change":"Unbekannt"));
        }
    }

    public void addPlayer(Player player) {
        players.put(player.getUniqueId(), new RecordPlayer(player));
    }

    public boolean isInCurrentlyInRecord(Player player){
        return players.containsKey(player.getUniqueId());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        if(isInCurrentlyInRecord(event.getPlayer())){
            RecordPlayer player = players.get(event.getPlayer().getUniqueId());
            player.setLoc(event.getTo());
            player.setVel(event.getPlayer().getVelocity());
        }
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event){
        if(isInCurrentlyInRecord(event.getPlayer()))
            players.get(event.getPlayer().getUniqueId()).setSneaking(event.isSneaking());
    }

    @EventHandler
    public void onSprint(PlayerToggleSprintEvent event){
        if(isInCurrentlyInRecord(event.getPlayer()))
            players.get(event.getPlayer().getUniqueId()).setSneaking(event.isSprinting());
    }

    @EventHandler
    public void onHurt(EntityDamageEvent event){
        if(event.getEntity() instanceof  Player && isInCurrentlyInRecord((Player) event.getEntity()))
            players.get(event.getEntity().getUniqueId()).setHurt(true);
    }

    @EventHandler
    public void onSwing(PlayerAnimationEvent event){
        if(event.getAnimationType() == PlayerAnimationType.ARM_SWING && isInCurrentlyInRecord(event.getPlayer()))
            players.get(event.getPlayer().getUniqueId()).setSwingArm(true);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        if(isInCurrentlyInRecord(event.getEntity())) {
            RecordPlayer player = players.get(event.getEntity().getUniqueId());
            player.setDeath(true);
            player.forceUpdate();
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        if(isInCurrentlyInRecord(event.getPlayer())) {
            RecordPlayer player = players.get(event.getPlayer().getUniqueId());
            player.setDeath(false);
            player.setRespawn(true);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        removePlayer(event.getPlayer(), RemoveReason.LEAVE);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event){
        removePlayer(event.getPlayer(), RemoveReason.WORLDCHANGE);
    }

    public enum RemoveReason {

        LEAVE,
        WORLDCHANGE,
        UNKNOWN

    }

    @EventHandler
    public void onEquipt(InventoryClickEvent e){
        if(!isInCurrentlyInRecord((Player) e.getWhoClicked())) return;
        if(e.getSlotType() != InventoryType.SlotType.ARMOR) return;
        if(e.getCurrentItem() == null) return;
        RecordPlayer player = players.get(e.getWhoClicked().getUniqueId());
        Player p = player.getPlayer();
        switch (e.getCurrentItem().getType()) {
            case DIAMOND_HELMET:
            case GOLD_HELMET:
            case IRON_HELMET:
            case CHAINMAIL_HELMET:
            case LEATHER_HELMET:
            case PUMPKIN:
            case SKULL_ITEM:
                player.setHelmet(p.getEquipment().getHelmet());
                break;
            case DIAMOND_CHESTPLATE:
            case GOLD_CHESTPLATE:
            case IRON_CHESTPLATE:
            case CHAINMAIL_CHESTPLATE:
            case LEATHER_CHESTPLATE:
                player.setChestplate(p.getEquipment().getChestplate());
                break;
            case DIAMOND_LEGGINGS:
            case GOLD_LEGGINGS:
            case IRON_LEGGINGS:
            case CHAINMAIL_LEGGINGS:
            case LEATHER_LEGGINGS:
                player.setLeggings(p.getEquipment().getLeggings());
                break;
            case DIAMOND_BOOTS:
            case GOLD_BOOTS:
            case IRON_BOOTS:
            case CHAINMAIL_BOOTS:
            case LEATHER_BOOTS:
                player.setBoots(p.getEquipment().getBoots());
                break;
            default:
                /* Wir haben ein Problem -.- (Apollo 13 :D )*/
                break;
        }
    }

    public UUID getMain() {
        return main;
    }
}
