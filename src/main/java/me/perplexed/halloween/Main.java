package me.perplexed.halloween;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import me.perplexed.halloween.events.*;
import me.perplexed.halloween.items.ArmorBase;
import me.perplexed.halloween.items.ItemBase;
import me.perplexed.halloween.items.custom.*;
import me.perplexed.halloween.player.Profile;
import me.perplexed.halloween.utils.DataManager;
import me.perplexed.halloween.utils.Txt;
import me.perplexed.halloween.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin {

    private final DataManager manager = new DataManager(this);
    private static Main instance;
    public final List<ItemBase> registry = new ArrayList<>();
    public final List<Profile> onlineProfiles = new ArrayList<>();
    public ProtocolManager pm;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        pm = ProtocolLibrary.getProtocolManager();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
             onlineProfiles.add(new Profile(onlinePlayer.getUniqueId()));
             onlinePlayer.getWorld().setGameRule(GameRule.KEEP_INVENTORY,true);
        }


        lambdaCommands();
        configLoad();
        registerEvents();
        registerItems();
    }



    public void configLoad() {

        this.saveDefaultConfig();

        try {
            this.reloadConfig();
        } catch (Exception e) {
            getLogger().warning("Invalid Character in config!");
            return;
        }
    }

    // if this method is unused, I had an idea for it, scrapped the idea and then never removed the method because I thought I may have a use for it
    public void lambdaCommands() {
        getCommand("essence").setExecutor(((sender, command, label, args) -> {
            if (!(sender instanceof  Player p)) {
                sender.sendMessage(Txt.red("no"));
                return true;
            }

            Profile prof = Utils.getProfile(p);
            p.sendMessage(Txt.green("Essence: " + prof.getEssence()));
            p.sendMessage(Txt.green("Costume Essence: " + prof.getCostumeEssence()));
            p.sendMessage(Txt.green("Candy Essence: " + prof.getCandyEssence()));

            return true;
        }));
        getCommand("phone").setExecutor(((sender, command, label, args) -> {
            if (!(sender instanceof  Player p)) {
                sender.sendMessage(Txt.red("no"));
                return true;
            }

            if (p.getInventory().firstEmpty() == -1) {
                p.sendMessage("Your inventory is full!");
                return true;
            }

            p.getInventory().addItem(Utils.instOf("minephone_z").asItem());

            return true;
        }));
    }

    @Override
    public void onDisable() {
        onlineProfiles.forEach(Profile::save);
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (Utils.asItemBase(p.getInventory().getChestplate()) == null) continue;
            if (Utils.asItemBase(p.getInventory().getChestplate()) instanceof ArmorBase a) {
                a.onRemoveArmor(p);
                p.sendMessage(Txt.red("The server is reloading! You may need to take your armor off and put it back on for it to work!"));
            }

        }
    }


    public void registerItems() {
        registry.add(new MinePhone());
        registry.add(new SonicCostume());
        registry.add(new SugaryCandy());
        registry.add(new HitmanBird());
        registry.add(new PotionDealer());
        registry.add(new MaxHealthIncrease());
        registry.add(new StonedCandy());
        registry.add(new Spy());
    }

    public void registerEvents() {
        this.getServer().getPluginManager().registerEvents(new ItemEvents(),this);
        this.getServer().getPluginManager().registerEvents(new CandyEvents(),this);
        this.getServer().getPluginManager().registerEvents(new ArmorEvents(),this);
        this.getServer().getPluginManager().registerEvents(new EventCancels(),this);
        this.getServer().getPluginManager().registerEvents(new GUIEvents(), this);
        this.getServer().getPluginManager().registerEvents(new JoinLeaveEvent(), this);
    }

    public static Main getInstance() {
        return instance;
    }

    public DataManager getManager() {
        return manager;
    }
}
