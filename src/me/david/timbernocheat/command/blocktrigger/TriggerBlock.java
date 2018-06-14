package me.david.timbernocheat.command.blocktrigger;

import me.david.api.storage.YamlComponent;
import me.david.api.storage.YamlSection;
import org.bukkit.Location;

import java.util.UUID;

public class TriggerBlock implements YamlComponent {

    private Location location;
    private TriggerAction action;
    private UUID creator;
    private AttributeList atributes;

    public TriggerBlock(){}

    public TriggerBlock(Location location, TriggerAction action, UUID creator, AttributeList atributes) {
        this.location = location;
        this.action = action;
        this.creator = creator;
        this.atributes = atributes;
    }

    @Override
    public void read(YamlSection yaml) {
        location = yaml.getLocationBlock("location");
        action = yaml.getEnum("action", TriggerAction.class);
        creator = yaml.getUUID("uuid");
        atributes = new AttributeList();
        atributes.read(yaml.getYamlSection("attributes"));
    }

    @Override
    public void save(YamlSection yaml) {
        yaml.setBlockLocation("location", location);
        yaml.setEnum("action", action);
        yaml.setUUID("uuid", creator);
        atributes.save(yaml.getYamlSection("attributes"));
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public TriggerAction getAction() {
        return action;
    }

    public void setAction(TriggerAction action) {
        this.action = action;
    }

    public UUID getCreator() {
        return creator;
    }

    public void setCreator(UUID creator) {
        this.creator = creator;
    }

    public AttributeList getAtributes() {
        return atributes;
    }

    public void setAtributes(AttributeList atributes) {
        this.atributes = atributes;
    }
}
