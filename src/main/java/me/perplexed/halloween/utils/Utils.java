package me.perplexed.halloween.utils;

import me.perplexed.halloween.Main;
import me.perplexed.halloween.items.ItemBase;
import me.perplexed.halloween.items.ItemPerstDataContainer;
import me.perplexed.halloween.player.Profile;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class Utils {
    
    
    public static Profile getProfile(Player p) {
        Profile toGet = null;

        for (Profile pr : Main.getInstance().onlineProfiles) {
            if (pr.getPlayer().getUniqueId().equals(p.getUniqueId())) {
                toGet = pr;
                break;
            }
        }
        
        return (toGet == null ? new Profile(p.getUniqueId()) : toGet);
    }

    public static ItemBase asItemBase(ItemStack item) {

        if (!customItemCheck(item)) {
            return null;
        }

        PersistentDataContainer contain = item.getItemMeta().getPersistentDataContainer();

        String localName = contain.get(ItemPerstDataContainer.dataKey,ItemPerstDataContainer.data).getLocalizedNamed();

        return instOf(localName);
    }


    public static void otherCostumePieceNBT(ItemStack stack) {
        ItemMeta im = stack.getItemMeta();
        im.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(),"other_piece"), PersistentDataType.INTEGER,1);
        stack.setItemMeta(im);
    }

    public static ItemBase instOf(String localName) {
        for (ItemBase itemBase : Main.getInstance().registry) {
            if (itemBase.getData().getLocalizedNamed().equals(localName)) {
                return itemBase;
            }
        }

        // item isnt registered
        return null;
    }

    public static boolean customItemCheck(ItemStack i) {
        if (i == null) return false;
        return i.getItemMeta() != null && i.getItemMeta().getPersistentDataContainer().has(ItemPerstDataContainer.dataKey,ItemPerstDataContainer.data);
    }


}
