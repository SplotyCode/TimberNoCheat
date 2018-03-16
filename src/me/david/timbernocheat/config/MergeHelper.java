package me.david.timbernocheat.config;

import com.google.common.io.ByteStreams;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.storage.YamlFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MergeHelper {


    public void merge(){
        //Get the 2 Files that we want to merge
        YamlFile newConfig = getNewConifg();
        YamlFile oldConfig = getOldConfig();
        //Get The result File
        YamlFile result = TimberNoCheat.getInstance().getConfig();

        //To the merge...
        TimberNoCheat.getInstance().log(false, "[Merge] We now start to merge '" + oldConfig.getFile().getPath() + "' with '" + newConfig.getFile().getPath() + "' to '" + TimberNoCheat.getInstance().getConfigFile().getPath() + "'!");
        boolean change = false;
        for(String key : newConfig.getKeys(true)){
            if(!oldConfig.contains(key)){
                Object value = newConfig.get(key);
                TimberNoCheat.getInstance().log(false, "[Merge] The current config does not has the Setting '" + key + "' lets set if with the defualt value '" + value + "'...");
                result.set(key, value);
                change = true;
            }
        }

        //Finishing and debugging
        if(!change) {
            TimberNoCheat.getInstance().log(false, "[Merge] No change detected in configs... As the versions of the configs are Diffrent we belive this is an error... NEVER EVER change the version proparty in the config File... We have Reported that error to Davids Discord(thats me :D )");
            TimberNoCheat.getInstance().getDiscordManager().sendWarning("Try to Merge with no change...");
            return;
        }
        /* Set the new config version in the config so we dont need to make a merge on the next restart*/
        result.set("generel.version", newConfig.getInt("generel.version"));
        result.save();
        TimberNoCheat.getInstance().log(false, "[Merge] Config merge completed...");
    }

    private YamlFile getOldConfig(){
        SimpleDateFormat date = new SimpleDateFormat("dd:MM:yy");
        File oldConfig = createFile(new File(TimberNoCheat.getInstance().getDataFolder() + "/backup/", date.format(new Date())));
        try {
            Files.copy(TimberNoCheat.getInstance().getConfigFile().toPath(), oldConfig.toPath());
            TimberNoCheat.getInstance().log(false, "Make a Backup of the current Config!");
        } catch (IOException e) {
            TimberNoCheat.getInstance().reportException(e, "Fehler beim Backup machen der aktuellen Config Datei...");
        }
        return new YamlFile(oldConfig);
    }

    private YamlFile getNewConifg(){
        File newConifg = createFile(new File("Temp/newconf.yml"));
        try (OutputStream out = new FileOutputStream(newConifg)) {
            ByteStreams.copy(TimberNoCheat.getInstance().getResource("me/david/timbernocheat/config/config.yml"), out);
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return new YamlFile(newConifg);
    }

    private File createFile(File file){
        file.getParentFile().mkdirs();
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return file;
        }
        int i = 0;
        while (new File(file.getPath() + "(" + i + ")").exists())
            i++;
        file = new File(file.getPath() + "(" + i + ")");
        return file;
    }
}
