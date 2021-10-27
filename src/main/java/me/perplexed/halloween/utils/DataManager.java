package me.perplexed.halloween.utils;


import me.perplexed.halloween.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;


// custom config file management
public class DataManager {

    private final Main instance;
    private FileConfiguration dataFile = null;
    private File configFile = null;

    public DataManager(Main instance) {
        this.instance = instance;
        saveBasicConfig();
    }

    public void reloadFile() {
        if (this.configFile == null) this.configFile = new File(this.instance.getDataFolder(), "playerinfo.yml");

        this.dataFile = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream basicStream = this.instance.getResource("playerinfo.yml");
        if (basicStream !=null) {
            YamlConfiguration basicConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(basicStream));
            this.dataFile.setDefaults(basicConfig);
        }
    }

    public FileConfiguration getDataConfig() {
        if (this.dataFile == null) reloadFile();
        return this.dataFile;
    }

    public void saveConfig() {
        if (this.dataFile == null || this.configFile == null) return;
        try {
            this.getDataConfig().save(this.configFile);
        } catch (IOException e) {
            instance.getLogger().log(Level.SEVERE, "Could not save player Timers to " + this.configFile, e);
        }

    }

    public void saveBasicConfig() {
        if (this.configFile == null) this.configFile = new File(this.instance.getDataFolder(),"playerinfo.yml");
        if (!this.configFile.exists()) {
            this.instance.saveResource("playerinfo.yml",false);
        }
    }

}
