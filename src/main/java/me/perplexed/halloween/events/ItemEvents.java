package me.perplexed.halloween.events;


import me.perplexed.halloween.Main;
import me.perplexed.halloween.items.ItemBase;
import me.perplexed.halloween.player.Profile;
import me.perplexed.halloween.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;


@SuppressWarnings("ConstantConditions")
public class ItemEvents implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (!Utils.customItemCheck(e.getItem()) && !Utils.customItemCheck(e.getPlayer().getInventory().getChestplate())) {
            return;
        }

        if (e.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }

        Profile pr = Utils.getProfile(e.getPlayer());

        if (e.getPlayer().getInventory().getChestplate() != null) {
            ItemStack chestplate = e.getPlayer().getInventory().getChestplate();
            if (Utils.customItemCheck(chestplate)) {
                ItemBase base = Utils.asItemBase(chestplate);
                switch (e.getAction()) {
                    case LEFT_CLICK_AIR -> base.onLeftClick(pr,base);
                    case RIGHT_CLICK_AIR -> base.onRightClick(pr,base);
                }
            }
        }

        if (e.getItem() != null) {
            ItemStack item = e.getItem();
            if (Utils.customItemCheck(item)) {
                ItemBase base = Utils.asItemBase(item);
                switch (e.getAction()) {
                    case LEFT_CLICK_AIR,LEFT_CLICK_BLOCK -> base.onLeftClick(pr,base);
                    case RIGHT_CLICK_AIR,RIGHT_CLICK_BLOCK -> base.onRightClick(pr,base);
                }
            }
        }

    }

    
    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent event) {
        
        
        if (event.getDamager() instanceof Player dmg) {
            if (dmg.getInventory().getItemInMainHand().getType() == Material.AIR && dmg.getInventory().getChestplate() == null) {
                return;
            }

            Profile pr = Utils.getProfile(dmg);

            if (dmg.getInventory().getChestplate() != null) {
                ItemStack chestplate = dmg.getInventory().getChestplate();
                if (Utils.customItemCheck(chestplate)) {
                    ItemBase base = Utils.asItemBase(chestplate);
                    base.attack(pr,event.getEntity(),base);
                }
            }

            if (dmg.getInventory().getItemInMainHand().getType() != Material.AIR) {
                ItemStack item = dmg.getInventory().getItemInMainHand();
                if (Utils.customItemCheck(item)) {
                    ItemBase base = Utils.asItemBase(item);
                    base.attack(pr,event.getEntity(),base);
                }
            }
        }
        
        if (event.getEntity() instanceof Player att) {
            if (att.getInventory().getItemInMainHand().getType() == Material.AIR && att.getInventory().getChestplate() == null) {
                return;
            }

            Profile pr = Utils.getProfile(att);

            if (att.getInventory().getChestplate() != null) {
                ItemStack chestplate = att.getInventory().getChestplate();
                if (Utils.customItemCheck(chestplate)) {
                    ItemBase base = Utils.asItemBase(chestplate);
                    base.damaged(pr,event.getEntity(),base);
                }
            }

            if (att.getInventory().getItemInMainHand().getType() != Material.AIR) {
                ItemStack item = att.getInventory().getItemInMainHand();
                if (Utils.customItemCheck(item)) {
                    ItemBase base = Utils.asItemBase(item);
                    base.damaged(pr,event.getEntity(),base);
                }
            }
        }
    }

    
    
}
