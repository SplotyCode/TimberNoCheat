package me.david.TimberNoCheat.CheckManager;

import me.david.TimberNoCheat.TimberNoCheat;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Settings {
    public int chat_delay_schwelle = 6;
    public double chat_delay_small = 0.0;
    public double chat_delay_big = 0.0;

    public boolean chat_address_blockcompletly = false;

    public boolean chat_spamming_equlsignore = false;
    public int chat_spamming_toshort = 4;
    public List<String> chat_spamming_whitelist = new ArrayList<String>();
    public boolean chat_spamming_whitelist_equlsignore = false;


    public boolean expoit_sign_notiyplayer = false;
    public boolean expoit_sign_sendcommand = false;
    public String expoit_sign_command = "";


    public Settings(){
        load();
    }

    public void load(){
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(TimberNoCheat.instance.config);
        chat_delay_schwelle = yml.getInt("chat_delay_schwelle");
        chat_delay_small = yml.getDouble("chat_delay_small_milis");
        chat_delay_big = yml.getDouble("chat_delay_big_milis");

        chat_address_blockcompletly = yml.getBoolean("delete_domain_complety");

        chat_spamming_equlsignore = yml.getBoolean("chat_spamming_equlsignore");
        chat_spamming_toshort = yml.getInt("chat_spamming_toshort");
        chat_spamming_whitelist = yml.getStringList("chat_spamming_whitelist");
        chat_spamming_whitelist_equlsignore = yml.getBoolean("chat_spamming_whitelist_equlsignore");

        expoit_sign_notiyplayer = yml.getBoolean("expoit_sign_notiyplayer");
        expoit_sign_sendcommand = yml.getBoolean("expoit_sign_sendcommand");
        if(expoit_sign_sendcommand){
            expoit_sign_command = yml.getString("expoit_sign_command");
        }
    }
}
