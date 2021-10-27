package me.perplexed.halloween.items;

import me.perplexed.halloween.player.Profile;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class CandyBase extends ItemBase {

    protected int amt;

    protected CandyBase(String lore, String name, Material mat, int amt, ItemData data) {
        super(ItemType.CANDY, lore, name, mat, amt, data);
        this.amt = amt;
    }

    public abstract void onEat(Profile p, CandyBase base);


    public void setAmt(int amt) {
        amt = Math.min(Math.max(amt,1),64);
    }

    public final ItemStack asItem() {
        ItemStack half = super.asItem();
        half.setAmount(amt);
        return half;
    }


}
