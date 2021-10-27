package me.perplexed.halloween.events;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import io.papermc.paper.event.entity.EntityLoadCrossbowEvent;
import me.perplexed.halloween.Main;
import me.perplexed.halloween.items.ArmorBase;
import me.perplexed.halloween.items.custom.HitmanBird;
import me.perplexed.halloween.items.custom.SonicCostume;
import me.perplexed.halloween.items.custom.Spy;
import me.perplexed.halloween.utils.Txt;
import me.perplexed.halloween.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class ArmorEvents implements Listener {


    @EventHandler(ignoreCancelled = true)
    public void snek(PlayerToggleSneakEvent event) {
        if (!event.isSneaking()) {
            return;
        }


        PlayerInventory inv = event.getPlayer().getInventory();

        if (!Utils.customItemCheck(inv.getChestplate())) return;

        ArmorBase ab = (ArmorBase) Utils.asItemBase(inv.getChestplate());

        ab.sneak(Utils.getProfile(event.getPlayer()),ab);
    }

    @EventHandler(ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event) {


        PlayerInventory inv = event.getPlayer().getInventory();

        if (!Utils.customItemCheck(inv.getChestplate())) return;

        ArmorBase ab = (ArmorBase) Utils.asItemBase(inv.getChestplate());

        ab.onMove(Utils.getProfile(event.getPlayer()),ab);
    }
    
    @SuppressWarnings("ConstantConditions")
    @EventHandler(ignoreCancelled = true)
    public void armorEquip(PlayerArmorChangeEvent event) {
        if (Utils.customItemCheck(event.getOldItem())) {
            ArmorBase armor = (ArmorBase) Utils.asItemBase(event.getOldItem());
            armor.onRemoveArmor(event.getPlayer());
        }

        ItemStack newItem = event.getNewItem();

        if (!Utils.customItemCheck(newItem)) {
            return;
        }
        ArmorBase newArmor = (ArmorBase) Utils.asItemBase(newItem);
        newArmor.onWearArmor(event.getPlayer());





    }

    @EventHandler(ignoreCancelled = true)
    public void birdEnchantEvent(EnchantItemEvent event) {
        Player p = event.getEnchanter();


        if (!Utils.customItemCheck(p.getInventory().getChestplate())) {
            return;
        }

        if (!(Utils.asItemBase(p.getInventory().getChestplate()) instanceof HitmanBird)) {
            return;
        }

        event.setExpLevelCost((int) Math.floor(event.getExpLevelCost() * 0.5));
        p.sendMessage(Txt.green("The hitman bird has used his knowledge to reduce your enchantment cost by half!"));
    }

    @EventHandler(ignoreCancelled = true)
    public void birdAnvilEvent(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();


        if (!Utils.customItemCheck(p.getInventory().getChestplate())) {
            return;
        }

        if (!(Utils.asItemBase(p.getInventory().getChestplate()) instanceof HitmanBird)) {
            return;
        }

        if (!(event.getInventory() instanceof AnvilInventory)) {
            return;
        }

        AnvilInventory anInv = (AnvilInventory) event.getInventory();

        if (!(event.getClickedInventory() == anInv)) {
            return;
        }

        if (!(event.getSlot() == 2)) {
            return;
        }

        anInv.setRepairCost(Math.max((int) Math.floor(anInv.getRepairCost() * 0.5),1));
        p.sendMessage(Txt.green("The hitman bird has used his knowledge to reduce your anvil use cost by half!"));
    }

    @EventHandler(ignoreCancelled = true)
    public void boostEXPEvent(PlayerPickupExperienceEvent event){
        Player p = event.getPlayer();


        if (!Utils.customItemCheck(p.getInventory().getChestplate())) {
            return;
        }


        if (!(Utils.asItemBase(p.getInventory().getChestplate()) instanceof SonicCostume sc)) {
            return;
        }


        int amtToBeAdded = (int) ((event.getExperienceOrb().getExperience() * 3) * (0.5));

        amtToBeAdded = Math.max(amtToBeAdded,2);

        sc.boost.replace(p,Math.min(sc.boost.get(p) + amtToBeAdded,100));

        sc.updateBoostBar(p);


    }

}
