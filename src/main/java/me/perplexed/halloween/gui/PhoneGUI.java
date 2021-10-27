package me.perplexed.halloween.gui;

import me.perplexed.halloween.Main;
import me.perplexed.halloween.player.Profile;
import me.perplexed.halloween.utils.Txt;
import me.perplexed.halloween.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.prefs.PreferencesFactory;

public class PhoneGUI extends GUIMenu {

    final Inventory main = Bukkit.createInventory(this,9, Component.text("MinePhone Z"));

    public PhoneGUI() {

        main.setItem(0,item(Txt.green("Candy Shop"),1,Material.CAKE,NamedTextColor.GREEN,"Buy all your candy here!"));
        main.setItem(1,item(Txt.green("Costume Shop"),1,Material.LEATHER_CHESTPLATE,NamedTextColor.GREEN,"Buy all your clothes here!","Costumes give you nice abilities!"));
        main.setItem(2,item(Txt.green("Essence Trader"),1,Material.BREWING_STAND,NamedTextColor.GREEN,"Exchange Essence for stuff"));
        main.setItem(8,super.item(Txt.red("Close"),1,Material.BARRIER, null));

        // end
        fillEmptySpaces(null);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return main;
    }

    @Override
    public void onClick(Player clicker, int slot, ClickType type) {
        switch (slot) {
            case 0 -> clicker.openInventory(new CandyShop().getInventory());
            case 1 -> clicker.openInventory(new CostumeShop().getInventory());
            case 2 -> clicker.openInventory(new EssenceShop().getInventory());
            case 8 -> {
                clicker.closeInventory(InventoryCloseEvent.Reason.PLAYER);
                return;
            }

            default -> {
                return;
            }
        }


    }

    @Override
    public void onOpen(Player opener) {

    }

    @Override
    public void onClose(Player closer) {
    }
}
