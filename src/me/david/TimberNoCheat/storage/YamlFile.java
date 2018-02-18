package me.david.TimberNoCheat.storage;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class YamlFile extends YamlConfiguration {

    private final File file;

    public YamlFile(){
        file = null;
    }

    public YamlFile(File file){
        this.file = file;
        try {
            load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void setBlockLocation(String path, Location location){
        set(path,
                location.getWorld().getName() + ":" +
                location.getBlockX() + ":" +
                location.getBlockY() + ":" +
                location.getBlockZ());
    }

    public void setPlayerLocation(String path, Location location){
        set(path,
                location.getWorld().getName() + ":" +
                location.getX() + ":" +
                location.getY() + ":" +
                location.getZ() + ":" +
                location.getYaw() + ":" +
                location.getPitch() + ":");
    }

    public Location getLocationPlayer(String path){
        if (!contains(path)) return null;
        String[] args = getString(path).split(":");
        String world = args[0];
        double x = Double.valueOf(args[1]);
        double y = Double.valueOf(args[2]);
        double z = Double.valueOf(args[3]);
        float yaw = Float.valueOf(args[4]);
        float pitch = Float.valueOf(args[5]);
        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    public Location getLocationBlock(String path){
        if (!contains(path)) return null;
        String[] args = getString(path).split(":");
        String world = args[0];
        double x = Double.valueOf(args[1]);
        double y = Double.valueOf(args[2]);
        double z = Double.valueOf(args[3]);
        return new Location(Bukkit.getWorld(world), x, y, z);
    }

    public void setUUID(final String path, final UUID uuid){
        set(path, uuid.getMostSignificantBits() + ":" + uuid.getLeastSignificantBits());
    }

    public UUID getUUID(final String path){
        if (!contains(path)) return null;
        String[] args = getString(path).split(":");
        long most = Long.valueOf(args[0]);
        long least = Long.valueOf(args[1]);
        return new UUID(most, least);
    }

    public OfflinePlayer getOfflinePlayer(final String path){
        return Bukkit.getOfflinePlayer(getUUID(path));
    }

    public void setEnum(final String path, final Enum<?> source){
        set(path, source.ordinal());
    }

    public <T> T getEnum(final String path, Class<T> enumClass){
        return enumClass.getEnumConstants()[getInt(path)];
    }

    public void save(){
        try {
            save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public YamlFile getConfigurationSection(String path) {
        YamlFile yaml = new YamlFile();
        String content = ((YamlConfiguration)super.getConfigurationSection(path)).saveToString();
        try {
            yaml.loadFromString(content);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return yaml;
    }
}
