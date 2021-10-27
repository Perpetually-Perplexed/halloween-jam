package me.perplexed.halloween.items.custom;

import me.perplexed.halloween.Main;
import me.perplexed.halloween.items.ArmorBase;
import me.perplexed.halloween.items.ItemData;
import me.perplexed.halloween.items.ItemType;
import me.perplexed.halloween.player.Profile;
import me.perplexed.halloween.utils.Txt;
import me.perplexed.halloween.utils.Utils;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PotionDealer extends ArmorBase {

    Map<Player, Integer> nextPotTime = new HashMap<>();
    Map<Player, Integer[]> taskID = new HashMap<>();

    public PotionDealer() {
        super("Some guy robbed it off a cleric who made it at 3am~~~Gives you a positive potion effect every 30 seconds~~~Sneak + Right Click to shoot tipped of a random type~~~Cancelled by many due to a non gender exclusive term!",
                "Witch Costume", Material.CHAINMAIL_CHESTPLATE, new ItemData(ItemType.COSTUME,"witch"));
    }


    @Override
    public void onRemove(Profile p, ArmorBase item) {


        if (taskID.containsKey(p.getPlayer()) && taskID.get(p.getPlayer()) != null && taskID.get(p.getPlayer()).length > 0) {
            for (Integer integer : taskID.get(p.getPlayer())) {
                Bukkit.getScheduler().cancelTask(integer);
            }
        }

        taskID.remove(p.getPlayer());
        nextPotTime.remove(p.getPlayer());;
    }

    @Override
    public void onWear(Profile p, ArmorBase item) {
        nextPotTime.put(p.getPlayer(),30);

        int taskID = new BukkitRunnable() {
            @Override
            public void run() {
                PotionType type = randomPos();
                p.getPlayer().addPotionEffect(new PotionEffect(type.getEffectType(),15*20,1,false,true,true));
                nextPotTime.replace(p.getPlayer(),30);
                p.getPlayer().sendMessage(Txt.green("You got " + type.toString().toLowerCase().replaceAll("_"," ") + " for 15 seconds! You will get a new ability in 30 seconds"));
            }
        }.runTaskTimer(Main.getInstance(),0L,30*20L).getTaskId();

        int actionID = new BukkitRunnable() {
            @Override
            public void run() {
                p.getPlayer().sendActionBar(Txt.red("Time till next effect: " + nextPotTime.get(p.getPlayer())));
                nextPotTime.replace(p.getPlayer(),nextPotTime.get(p.getPlayer()) -1);
            }
        }.runTaskTimer(Main.getInstance(),0L,20L).getTaskId();

        this.taskID.put(p.getPlayer(), new Integer[]{taskID,actionID});
    }

    @Override
    public void suitUp(Player p) {
        ItemStack boots = new ItemStack(Material.valueOf(mat.toString().split("_")[0] + "_BOOTS"));
        ItemMeta bmeta = boots.getItemMeta();
        bmeta.displayName(Txt.custom("Witch Boots",type.getDesc().color()).decoration(TextDecoration.ITALIC,false));
        bmeta.lore(super.lore());
        bmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        bmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        bmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        bmeta.setUnbreakable(true);
        boots.setItemMeta(bmeta);
        // Leggings
        ItemStack leggings = new ItemStack(Material.valueOf(mat.toString().split("_")[0] + "_LEGGINGS"));
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.displayName(Txt.custom("Witch Leggings",type.getDesc().color()).decoration(TextDecoration.ITALIC,false));
        lmeta.lore(super.lore());
        lmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        lmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        lmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        lmeta.setUnbreakable(true);
        leggings.setItemMeta(lmeta);
        // Helmet
        ItemStack helmet = new ItemStack(Material.valueOf(mat.toString().split("_")[0] + "_HELMET"));
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.displayName(Txt.custom("Witch Helmet", type.getDesc().color()).decoration(TextDecoration.ITALIC,false));
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


    private boolean validPositive(PotionType pt) {
         return switch (pt) {
             case NIGHT_VISION, INVISIBILITY, JUMP, FIRE_RESISTANCE, SPEED, SLOWNESS, WATER_BREATHING, REGEN, STRENGTH, LUCK, SLOW_FALLING -> true;
             default -> false;
         };
    }

    private PotionType randomPos() {
        PotionType ret;
        Random rando = new Random();
        do {
            ret = PotionType.values()[rando.nextInt(PotionType.values().length - 1)];
        } while (!validPositive(ret));
        return ret;
    }



}
