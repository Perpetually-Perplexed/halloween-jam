package me.perplexed.halloween.gui;

import me.perplexed.halloween.utils.Txt;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


// based of the idea from deerjump pls dont cancel me
public abstract class GUIMenu implements InventoryHolder {

    public abstract void onClick(Player clicker, int slot, ClickType type);

    public abstract void onOpen(Player opener);

    public abstract void onClose(Player closer);

    public final void invChecker(Player p, int slot, ClickType type, String method, InventoryHolder holder) {
        if (holder.getClass().equals(getClass())) {
            switch (method.toLowerCase()) {
                case "open" -> onOpen(p);
                case "close" -> onClose(p);
                default -> onClick(p,slot,type);
            }
        }
    }

    protected final void fillEmptySpaces(ItemStack is) {
        ItemStack stack = (is == null ? item(Component.empty(),1,Material.BLACK_STAINED_GLASS_PANE,null) : is);

        for (int i = 0; i <= getInventory().getSize() -1; i++) {
            if (getInventory().getItem(i) == null) {
                getInventory().setItem(i,stack);
            }
        }
    }


    protected final ItemStack item(Component name, int amt, Material mat, TextColor loreClr, String... lore) {
        ItemStack item = new ItemStack(mat,amt);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(name);

        if (lore.length == 0) {
            item.setItemMeta(meta);
            return item;
        }

        List<Component> txt = new ArrayList<>();
        txt.add(Component.empty());

        for (String s : lore) {
            txt.add(Txt.custom(s,loreClr));
        }

        meta.lore(txt);
        item.setItemMeta(meta);

        return item;
    }


}
