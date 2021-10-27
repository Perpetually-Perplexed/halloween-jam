package me.perplexed.halloween.items.custom;

import me.perplexed.halloween.Main;
import me.perplexed.halloween.items.ArmorBase;
import me.perplexed.halloween.items.ItemData;
import me.perplexed.halloween.items.ItemType;
import me.perplexed.halloween.player.Profile;
import me.perplexed.halloween.utils.Txt;
import me.perplexed.halloween.utils.Utils;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class HitmanBird extends ArmorBase {

    Map<Player,Long> invisCD = new HashMap<>();

    public HitmanBird() {
        super("This bird had a lot of knowledge,~~~he wanted to share it but people were too lazy with social media so he took hostages~~~Sneak to go invisible,~~~Anything requiring exp is reduced by 50%",
                "Hitman Bird Costume", Material.CHAINMAIL_CHESTPLATE, new ItemData(ItemType.COSTUME,"hitman_bird"));
    }


    @Override
    public void suitUp(Player p) {
        ItemStack boots = new ItemStack(Material.valueOf(mat.toString().split("_")[0] + "_BOOTS"));
        ItemMeta bmeta = boots.getItemMeta();
        bmeta.displayName(Txt.custom("Hitman Bird Boots",type.getDesc().color()).decoration(TextDecoration.ITALIC,false));
        bmeta.lore(super.lore());
        bmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        bmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        bmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        bmeta.setUnbreakable(true);
        boots.setItemMeta(bmeta);
        // Leggings
        ItemStack leggings = new ItemStack(Material.valueOf(mat.toString().split("_")[0] + "_LEGGINGS"));
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.displayName(Txt.custom("Hitman Bird Leggings",type.getDesc().color()).decoration(TextDecoration.ITALIC,false));
        lmeta.lore(super.lore());
        lmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        lmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        lmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        lmeta.setUnbreakable(true);
        leggings.setItemMeta(lmeta);
        // Helmet
        ItemStack helmet = new ItemStack(Material.valueOf(mat.toString().split("_")[0] + "_HELMET"));
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.displayName(Txt.custom("Hitman Bird Helmet", type.getDesc().color()).decoration(TextDecoration.ITALIC,false));
        hmeta.lore(super.lore());
        hmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        hmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        hmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        hmeta.setUnbreakable(true);
        helmet.setItemMeta(hmeta);

        Utils.otherCostumePieceNBT(boots);
        Utils.otherCostumePieceNBT(leggings);
        Utils.otherCostumePieceNBT(helmet);

        p.getInventory().setBoots(boots);
        p.getInventory().setHelmet(helmet);
        p.getInventory().setLeggings(leggings);
    }

    @Override
    public void sneak(Profile p, ArmorBase item) {
        if (!cooldownCheck(p.getPlayer())) {
            p.getPlayer().sendMessage(Txt.red("Your ability is on cooldown!"));
            return;
        }

        p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,200,1,true,false,false));
        p.getPlayer().sendMessage(Txt.green("You have gone invisible"));
        p.getPlayer().getInventory().setChestplate(null);
        new BukkitRunnable() {
            @Override
            public void run() {
                p.getPlayer().getInventory().setChestplate(asItem());
                p.getPlayer().sendMessage(Txt.red("Your invisibility wore off"));
            }
        }.runTaskLater(Main.getInstance(),200L);
        invisCD.replace(p.getPlayer(), System.currentTimeMillis());
    }

    // returns false if cooldown is active
    private boolean cooldownCheck(Player p) {
        if (!invisCD.containsKey(p)) {
            invisCD.put(p,System.currentTimeMillis());
            return true;
        }


        return !(System.currentTimeMillis() - invisCD.get(p) >= 15*1000);
    }
}
