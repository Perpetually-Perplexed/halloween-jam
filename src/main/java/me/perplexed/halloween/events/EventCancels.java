package me.perplexed.halloween.events;

import me.perplexed.halloween.Main;
import me.perplexed.halloween.items.CandyBase;
import me.perplexed.halloween.utils.Utils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataType;

public class EventCancels implements Listener {


    @EventHandler(ignoreCancelled = true)
    public void onClickCampfire(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        if (e.getClickedBlock() == null) return;

        if (e.getItem() == null) return;

        if (e.getItem().getItemMeta() == null) return;

        if (e.getClickedBlock().getType() == Material.CAMPFIRE && Utils.customItemCheck(e.getItem())) {
            if (Utils.asItemBase(e.getItem()) instanceof CandyBase) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onArmorRemove(InventoryClickEvent event) {

        if (!Utils.customItemCheck(event.getWhoClicked().getInventory().getChestplate())) {
            return;
        }

        if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
            if (event.getCurrentItem() == null) return;

            if (event.getCurrentItem().getType().toString().endsWith("_CHESTPLATE")) {
                return;
            }

            event.setCancelled(true);
        }
    }

}
