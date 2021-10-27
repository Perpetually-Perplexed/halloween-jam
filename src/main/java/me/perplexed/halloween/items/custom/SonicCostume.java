package me.perplexed.halloween.items.custom;

import me.perplexed.halloween.Main;
import me.perplexed.halloween.items.ArmorBase;
import me.perplexed.halloween.items.ItemData;
import me.perplexed.halloween.items.ItemType;
import me.perplexed.halloween.player.Profile;
import me.perplexed.halloween.utils.Txt;
import me.perplexed.halloween.utils.Utils;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public final class SonicCostume extends ArmorBase  {

    public Map<Player,Integer> boost = new HashMap<>();
    Map<Player,BossBar> boostBar = new HashMap<>();
    Map<Player,Integer> taskID = new HashMap<>();

    public SonicCostume() {
        super("Sold by an entrepreneur hedgedog, a dog who lives in a hedge~~~Gives speed 2 (Passive)~~~Activates boost when you sneak! "
                , "Blue Hedgedog Costume", Material.IRON_CHESTPLATE, new ItemData(ItemType.COSTUME,"blue_hedgedog"));
    }


    @Override
    public void onWear(Profile p, ArmorBase item) {
        boost.put(p.getPlayer(),0);


        boostBar.put(p.getPlayer(),BossBar.bossBar(Txt.custom("Boost", NamedTextColor.BLUE),0.0f, BossBar.Color.BLUE, BossBar.Overlay.PROGRESS));

        p.getPlayer().showBossBar(boostBar.get(p.getPlayer()));

        int id = new BukkitRunnable() {
            @Override
            public void run() {
                if (!p.getPlayer().hasPotionEffect(PotionEffectType.SPEED)) {
                    p.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20,1,false,true,true));

                }
            }
        }.runTaskTimer(Main.getInstance(),0L,20L).getTaskId();

        taskID.put(p.getPlayer(),id);

    }

    @Override
    public void suitUp(Player p) {
        ItemStack boots = new ItemStack(Material.valueOf(mat.toString().split("_")[0] + "_BOOTS"));
        ItemMeta bmeta = boots.getItemMeta();
        bmeta.displayName(Txt.custom("Blue Hedgedog Boots",type.getDesc().color()).decoration(TextDecoration.ITALIC,false));
        bmeta.lore(super.lore());
        bmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        bmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        bmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        bmeta.setUnbreakable(true);
        boots.setItemMeta(bmeta);
        // Leggings
        ItemStack leggings = new ItemStack(Material.valueOf(mat.toString().split("_")[0] + "_LEGGINGS"));
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.displayName(Txt.custom("Blue Hedgedog Leggings",type.getDesc().color()).decoration(TextDecoration.ITALIC,false));
        lmeta.lore(super.lore());
        lmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        lmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        lmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        lmeta.setUnbreakable(true);
        leggings.setItemMeta(lmeta);
        // Helmet
        ItemStack helmet = new ItemStack(Material.valueOf(mat.toString().split("_")[0] + "_HELMET"));
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.displayName(Txt.custom("Blue Hedgedog Helmet", type.getDesc().color()).decoration(TextDecoration.ITALIC,false));
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
    public void onRemove(Profile p, ArmorBase item) {
        boost.remove(p.getPlayer());

        if (!boostBar.containsKey(p.getPlayer())) return;


        p.getPlayer().hideBossBar(boostBar.get(p.getPlayer()));
        boostBar.remove(p.getPlayer());
        Bukkit.getScheduler().cancelTask(taskID.get(p.getPlayer()));
    }

    @Override
    public void sneak(Profile p, ArmorBase item) {
        if (!boost.containsKey(p.getPlayer())) {
            boost.put(p.getPlayer(),0);
        }

        if(boost.get(p.getPlayer()) == 0) {
            return;
        }

        double prvsVal = p.getPlayer().getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue();
        p.getPlayer().getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3);

        new BukkitRunnable() {
            @Override
            public void run() {
                boost.replace(p.getPlayer(), boost.get(p.getPlayer()) - 1);
                updateBoostBar(p.getPlayer());


                if (boost.get(p.getPlayer()) <= 0) {
                    cancel();
                    p.getPlayer().getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15);
                }
            }
        }.runTaskTimer(Main.getInstance(),0L,2L);




    }

    public void updateBoostBar(Player p) {
        if (!boostBar.containsKey(p)) {
            boostBar.put(p,BossBar.bossBar(Txt.custom("Boost",NamedTextColor.DARK_BLUE),0, BossBar.Color.BLUE, BossBar.Overlay.PROGRESS));
            p.showBossBar(boostBar.get(p));
            return;
        }

        boostBar.get(p).progress((float) boost.get(p)/100);
    }

}
