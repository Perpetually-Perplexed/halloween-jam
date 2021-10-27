package me.perplexed.halloween.items;

import me.perplexed.halloween.player.Profile;
import me.perplexed.halloween.utils.Txt;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemBase {

    protected final ItemType type;
    protected final Material mat;
    protected final String lore;
    protected final String name;
    protected final int amt;
    protected final ItemData data;

    protected ItemBase(ItemType type, String lore, String name, Material mat,ItemData data) {
        this(type,lore,name,mat,1,data);
    }

    protected ItemBase(ItemType type, String lore, String name, Material mat, int amt,ItemData data) {
        if (amt > 1 && (type != ItemType.CANDY && type != ItemType.MISC)) {
            amt = 1;
        } else {
            amt = Math.max(amt, 1);
        }

        this.name = name;
        this.lore = lore;
        this.type = type;
        this.mat = mat;
        this.amt = amt;
        this.data = data;
    }

    public ItemData getData() {
        return data;
    }

    public ItemStack asItem() {
        ItemStack item =  new ItemStack(mat,amt);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(name).color(type.getDesc().color()).decoration(TextDecoration.ITALIC,false));
        meta.lore(lore());
        meta.getPersistentDataContainer().set(ItemPerstDataContainer.dataKey,ItemPerstDataContainer.data,data);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);

        item.setItemMeta(meta);

        return item;
    }


    public List<Component> lore() {
        List<Component> retVal = new ArrayList<>();

        retVal.add(Component.text(""));
        for (String s : lore.split("~~~")) {
            retVal.add(Txt.custom(s, NamedTextColor.GOLD).decoration(TextDecoration.ITALIC,false));
        }

        if (type == ItemType.MISC) {
            return retVal;
        }

        retVal.add(Component.text(""));
        retVal.add(Component.text(""));
        retVal.add(type.getDesc().decorate(TextDecoration.BOLD).decoration(TextDecoration.ITALIC,false));
        return retVal;
    }

    public void onRightClick(Profile p, ItemBase base) {}

    public void onLeftClick(Profile p, ItemBase base) {}

    public void attack(Profile p, Entity attacked, ItemBase base) {}

    public void damaged(Profile p, Entity attacked, ItemBase base) {}

    public void jump(Profile p, ItemBase base) {}

    public boolean thisItem(ItemBase item) {
        return item.getData().getLocalizedNamed().equals(getData().getLocalizedNamed());
    }

}
