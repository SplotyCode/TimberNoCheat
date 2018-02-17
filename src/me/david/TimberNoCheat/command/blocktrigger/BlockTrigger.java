package me.david.TimberNoCheat.command.blocktrigger;

import me.david.TimberNoCheat.storage.YamlComponent;
import me.david.TimberNoCheat.storage.YamlFile;
import me.david.api.utils.cordinates.LocationUtil;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.UUID;

public class BlockTrigger implements YamlComponent {

    private Location location;
    private TriggerAction action;
    private UUID creator;
    private AtributeList atributes;

    @Override
    public void read(YamlFile yaml) {
        location = yaml.getLocationBlock("location");
        action = yaml.getEnum("action", TriggerAction.class);
        creator = yaml.getUUID("uuid");
        atributes = new AtributeList();
        atributes.read(yaml.getConfigurationSection("attributes"));
    }

    @Override
    public void save(YamlFile yaml) {

    }
}
