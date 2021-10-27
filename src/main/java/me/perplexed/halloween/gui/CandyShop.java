package me.perplexed.halloween.gui;

import me.perplexed.halloween.utils.Txt;
import me.perplexed.halloween.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class CandyShop extends GUIMenu {

    final Inventory inv = Bukkit.createInventory(this, InventoryType.HOPPER, Component.text("CandyShop"));


    public CandyShop() {
        inv.setItem(0,item(Txt.green("Sugary Candy"),1, Material.POTATO, NamedTextColor.RED,"Gives you speed 2 for 15seconds and hunger 1 for 10 seconds","Cost: 5 Candy Essence"));
        inv.setItem(1,item(Txt.green("Weird Candy"),1,Material.APPLE,NamedTextColor.RED,"Gives you a random effect","Cost: 20 Candy Essence"));
        inv.setItem(2,item(Txt.green("Disgusting Candy"),1,Material.GOLDEN_CARROT,NamedTextColor.RED,"Gives you 2HP, stacks up to 3 times","Cost: 50 Candy Essence"));
        inv.setItem(4,item(Txt.red("Close"),1,Material.BARRIER,null));

        fillEmptySpaces(null);
    }

    @Override
    public void onClick(Player clicker, int slot, ClickType type) {
        int candyRequired = 0;
        String localName = "candy_healthy"; // in case it fails the default doesnt throw npe

        switch (slot) {
            case 0 -> {
                candyRequired = 5;
                localName= "candy_sugar";
            }
            case 1 -> {
                candyRequired = 20;
                localName = "candy_weird";
            }
            case 2 -> candyRequired = 50; // didnt change candy name because it just defers to default

            case 3 -> {
                return;
            }


            case 4 -> {
                clicker.openInventory(new PhoneGUI().getInventory());
                return;
            }
        }

        if (Utils.getProfile(clicker).getCandyEssence() < candyRequired) {
            clicker.sendMessage(Txt.red("You don't have enough candy essence to do this!"));
            return;
        }

        if (clicker.getInventory().firstEmpty() == -1) clicker.getWorld().dropItem(clicker.getLocation(),Utils.instOf(localName).asItem());
        else clicker.getInventory().addItem(Utils.instOf(localName).asItem());
        Utils.getProfile(clicker).removeCandyEssence(candyRequired);
    }

    @Override
    public void onOpen(Player opener) {

    }

    @Override
    public void onClose(Player closer) {

    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }
}
