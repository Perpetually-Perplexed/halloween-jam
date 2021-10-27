package me.perplexed.halloween.events;

import me.perplexed.halloween.gui.GUIMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class GUIEvents implements Listener {


    @EventHandler(ignoreCancelled = true)
    public void onGUIOpen(InventoryOpenEvent event) {
        if (event.getInventory().getHolder() instanceof GUIMenu m) {
            m.invChecker((Player) event.getPlayer(),0,null,"open",event.getInventory().getHolder());
        }
    }

    @EventHandler()
    public void onGUIClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof GUIMenu m) {
            event.setCancelled(true);
            m.invChecker((Player) event.getWhoClicked(),event.getRawSlot(),event.getClick(),"bub",event.getInventory().getHolder());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onGUIClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof GUIMenu m) {
            m.invChecker((Player) event.getPlayer(),0,null,"close",event.getInventory().getHolder());
        }
    }

}
