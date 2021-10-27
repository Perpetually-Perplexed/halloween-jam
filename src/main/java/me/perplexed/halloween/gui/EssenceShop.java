package me.perplexed.halloween.gui;

import me.perplexed.halloween.player.Profile;
import me.perplexed.halloween.utils.Txt;
import me.perplexed.halloween.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EssenceShop extends GUIMenu {

    final Inventory inv = Bukkit.createInventory(this,9, Component.text("Essence shop"));

    public EssenceShop() {
        inv.setItem(0,item(Txt.yellow("Costume Essence"),3, Material.REDSTONE, NamedTextColor.GREEN,
                "Essence for buying costumes","Cost: 10 Essence"));
        inv.setItem(1,item(Txt.yellow("Candy Essence"),3,Material.SUGAR, NamedTextColor.GREEN,
                "Essence for buying candy","Cost: 3 Essence"));
        inv.setItem(2,item(Txt.yellow("Essence"),5,Material.COCOA_BEANS, NamedTextColor.GREEN,
                "Essence for buying essence!","Cost: 1 Gold and 3 Iron"));
        inv.setItem(8,item(Txt.red("Close"),1,Material.BARRIER,null));
        fillEmptySpaces(null);
    }

    @Override
    public void onClick(Player clicker, int slot, ClickType type) {
        int pEss = Utils.getProfile(clicker).getEssence();
        Profile prof = Utils.getProfile(clicker);
        switch (slot) {
            case 0 -> {
                if (pEss < 10) {
                    clicker.sendMessage(Txt.red("You don't have enough essence to do this!"));
                    return;
                }

                prof.removeEssence(10);
                prof.addCostumeEssence(3);
            }

            case 1 -> {
                if (pEss < 3) {
                    clicker.sendMessage(Txt.red("You don't have enough essence to do this!"));
                    return;
                }

                prof.removeEssence(3);
                prof.addCandyEssence(3);
            }


            case 2 -> {
                if (!clicker.getInventory().contains(Material.GOLD_INGOT,1) || !clicker.getInventory().contains(Material.IRON_INGOT,3)) {
                    clicker.sendMessage(Txt.red("You don't have enough resources to do this!"));
                    return;
                }

                clicker.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT,1), new ItemStack(Material.IRON_INGOT,3));
                prof.addEssence(5);
            }

            case 8 -> clicker.openInventory(new PhoneGUI().getInventory());
        }
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
