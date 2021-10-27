package me.perplexed.halloween.items;

import me.perplexed.halloween.player.Profile;
import me.perplexed.halloween.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class ArmorBase extends ItemBase {

    public ItemStack armor;

    protected ArmorBase(String lore, String name, Material mat, ItemData data) {
        super(ItemType.COSTUME, lore, name, (mat.toString().endsWith("CHESTPLATE") ? mat : Material.LEATHER_CHESTPLATE), data);

        this.armor = new ItemStack(mat);
    }


    public void onWear(Profile p, ArmorBase item) {

    }

    public void onRemove(Profile p, ArmorBase item) { }

    public final void onRemoveArmor(Player p) {
        if (p.getInventory().getChestplate() != null) {
            return;
        }

        p.getInventory().setLeggings(null);
        p.getInventory().setHelmet(null);
        p.getInventory().setBoots(null);
        onRemove(Utils.getProfile(p),this);
    }

    public final void onWearArmor(Player p) {
        if (!Utils.customItemCheck(p.getInventory().getChestplate())) {
            return;
        }

        if (!thisItem(Utils.asItemBase(p.getInventory().getChestplate()))) {
            return;
        }

        Profile pr = Utils.getProfile(p);


        // try to reattempt this without having to have a method in every child class

        /*
        if (armor.getType().toString().startsWith("LEATHER")) {
            Color armorClr = ((LeatherArmorMeta) armor.getItemMeta()).getColor();

            ItemStack boots = new ItemStack(Material.valueOf(mat.toString().split("_")[0] + "_BOOTS"));
            LeatherArmorMeta bmeta = (LeatherArmorMeta) boots.getItemMeta();
            bmeta.displayName(Txt.custom(Txt.textCase(data.localizedNamed + "_boots"),type.getDesc().color()).decoration(TextDecoration.ITALIC,false));
            bmeta.setColor(armorClr);
            bmeta.lore(super.lore());
            boots.setItemMeta(bmeta);
            // Leggings
            ItemStack leggings = new ItemStack(Material.valueOf(mat.toString().split("_")[0] + "LEGGINGS"));
            LeatherArmorMeta lmeta = (LeatherArmorMeta) leggings.getItemMeta();
            lmeta.setColor(armorClr);
            lmeta.displayName(Txt.custom(Txt.textCase(data.localizedNamed + "_leggings"),type.getDesc().color()).decoration(TextDecoration.ITALIC,false));
            lmeta.lore(super.lore());
            leggings.setItemMeta(lmeta);
            // Helmet
            ItemStack helmet = new ItemStack(Material.valueOf(mat.toString().split("_")[0] + "_HELMET"));
            LeatherArmorMeta hmeta = (LeatherArmorMeta) helmet.getItemMeta();
            hmeta.setColor(armorClr);
            hmeta.displayName(Txt.custom(Txt.textCase(data.localizedNamed + "_helmet"),type.getDesc().color()).decoration(TextDecoration.ITALIC,false));
            hmeta.lore(super.lore());
            helmet.setItemMeta(hmeta);

            p.getInventory().setBoots(boots);
            p.getInventory().setHelmet(helmet);
            p.getInventory().setLeggings(leggings);

            onWear(pr,this);
            return;
        }

        // Boots
        ItemStack boots = new ItemStack(Material.valueOf(mat.toString().split("_")[0] + "_BOOTS"));
        ItemMeta bmeta = boots.getItemMeta();
        bmeta.displayName(Txt.custom(Txt.textCase(data.localizedNamed + "_boots"),type.getDesc().color()).decoration(TextDecoration.ITALIC,false));
        bmeta.lore(super.lore());
        boots.setItemMeta(bmeta);
        // Leggings
        ItemStack leggings = new ItemStack(Material.valueOf(mat.toString().split("_")[0] + "_LEGGINGS"));
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.displayName(Txt.custom(Txt.textCase(data.localizedNamed + "_leggings"),type.getDesc().color()).decoration(TextDecoration.ITALIC,false));
        lmeta.lore(super.lore());
        leggings.setItemMeta(lmeta);
        // Helmet
        ItemStack helmet = new ItemStack(Material.valueOf(mat.toString().split("_")[0] + "_HELMET"));
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.displayName(Txt.custom(Txt.textCase(data.localizedNamed + "_helmet"),type.getDesc().color()).decoration(TextDecoration.ITALIC,false));
        hmeta.lore(super.lore());
        helmet.setItemMeta(hmeta);

        p.getInventory().setBoots(boots);
        p.getInventory().setHelmet(helmet);
        p.getInventory().setLeggings(leggings);
        System.out.println("yes");

         */


        suitUp(p);
        onWear(pr,this);
    }

    public abstract void suitUp(Player p);

    public void sneak(Profile p, ArmorBase item) {}

    public void onMove(Profile p, ArmorBase item) {}
}
