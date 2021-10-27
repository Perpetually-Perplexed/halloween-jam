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


// yes i copied this class from candyshop
public class CostumeShop extends GUIMenu {
    
    final Inventory inv = Bukkit.createInventory(this, InventoryType.HOPPER, Component.text("CandyShop"));


    public CostumeShop() {
        inv.setItem(0,item(Txt.green("Witch Costume"),1, Material.CHAINMAIL_CHESTPLATE, NamedTextColor.RED,"Cost: 20 Costume Essence"));
        inv.setItem(1,item(Txt.green("Hitman Bird Costume"),1,Material.CHAINMAIL_CHESTPLATE,NamedTextColor.RED,"Cost: 10 Costume Essence"));
        inv.setItem(2,item(Txt.green("Blue Hedgedog Costume"),1,Material.IRON_CHESTPLATE,NamedTextColor.RED,"Cost: 50 Costume Essence"));
        inv.setItem(3,item(Txt.green("Spy Costume"),1,Material.DIAMOND_CHESTPLATE,NamedTextColor.RED,"Cost: 100 Costume Essence"));
        inv.setItem(4,item(Txt.red("Close"),1,Material.BARRIER,null));

        fillEmptySpaces(null);
    }

    @Override
    public void onClick(Player clicker, int slot, ClickType type) {
        int costumeEssReq = 0;
        String localName = "blue_hedgedog"; // in case it fails the default doesnt throw npe

        switch (slot) {
            case 0 -> {
                costumeEssReq = 20;
                localName= "witch";
            }
            case 1 -> {
                costumeEssReq = 10;
                localName = "hitman_bird";
            }
            case 2 -> costumeEssReq = 50; // didnt change candy name because it just defers to default

            case 3 -> {
                costumeEssReq = 100;
                localName = "spy";
            }


            case 4 -> {
                clicker.openInventory(new PhoneGUI().getInventory());
                return;
            }
        }

        if (Utils.getProfile(clicker).getCostumeEssence() < costumeEssReq) {
            clicker.sendMessage(Txt.red("You don't have enough costume essence to do this!"));
            return;
        }

        if (clicker.getInventory().firstEmpty() == -1) clicker.getWorld().dropItem(clicker.getLocation(),Utils.instOf(localName).asItem());
        else clicker.getInventory().addItem(Utils.instOf(localName).asItem());
        Utils.getProfile(clicker).removeCostumeEssence(costumeEssReq);
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
