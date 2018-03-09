package me.david.timbernocheat.storage;

public interface YamlComponent {

    void read(YamlSection yaml);
    void save(YamlSection yaml);

}
