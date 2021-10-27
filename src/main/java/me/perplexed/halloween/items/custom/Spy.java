package me.perplexed.halloween.items.custom;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import me.perplexed.halloween.Main;
import me.perplexed.halloween.items.ArmorBase;
import me.perplexed.halloween.items.ItemBase;
import me.perplexed.halloween.items.ItemData;
import me.perplexed.halloween.items.ItemType;
import me.perplexed.halloween.player.Profile;
import me.perplexed.halloween.utils.Txt;
import me.perplexed.halloween.utils.Utils;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Spy extends ArmorBase {


    public Spy() {
        super("Sold on the BLACK market! Get it? That isn't racist right?~~~Shift to see all nearby mobs and players",
                "Spy Costume", Material.DIAMOND_CHESTPLATE, new ItemData(ItemType.COSTUME,"spy"));
    }


    @Override
    public void suitUp(Player p) {
        ItemStack boots = new ItemStack(Material.valueOf(mat.toString().split("_")[0] + "_BOOTS"));
        ItemMeta bmeta = boots.getItemMeta();
        bmeta.displayName(Txt.custom("Spy Boots",type.getDesc().color()).decoration(TextDecoration.ITALIC,false));
        bmeta.lore(super.lore());
        bmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        bmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        bmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        bmeta.setUnbreakable(true);
        boots.setItemMeta(bmeta);
        // Leggings
        ItemStack leggings = new ItemStack(Material.valueOf(mat.toString().split("_")[0] + "_LEGGINGS"));
        ItemMeta lmeta = leggings.getItemMeta();
        lmeta.displayName(Txt.custom("Spy Leggings",type.getDesc().color()).decoration(TextDecoration.ITALIC,false));
        lmeta.lore(super.lore());
        lmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        lmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        lmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        lmeta.setUnbreakable(true);
        leggings.setItemMeta(lmeta);
        // Helmet
        ItemStack helmet = new ItemStack(Material.valueOf(mat.toString().split("_")[0] + "_HELMET"));
        ItemMeta hmeta = helmet.getItemMeta();
        hmeta.displayName(Txt.custom("Spy Helmet", type.getDesc().color()).decoration(TextDecoration.ITALIC,false));
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
        if (p.getPlayer().isSwimming()) return;

        List<Entity> glowEnboob = p.getPlayer().getNearbyEntities(25,25,25).stream().filter(e -> e instanceof LivingEntity).toList(); //master of naming vars
        glowEnboob.forEach(glow -> glow(p.getPlayer(),glow));
    }



    private void glow(Player p, Entity entity) {


        ProtocolManager manager = Main.getInstance().pm;

        PacketContainer pc = manager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        pc.getIntegers().write(0, entity.getEntityId());
        WrappedDataWatcher wdw = new WrappedDataWatcher();
        WrappedDataWatcher.Serializer s = WrappedDataWatcher.Registry.get(Byte.class);
        wdw.setEntity(entity);

        wdw.setObject(0, s, (byte) 0x40);
        pc.getWatchableCollectionModifier().write(0, wdw.getWatchableObjects());

        try {
            manager.sendServerPacket(p, pc);
            p.getInventory().setChestplate(null);
            Bukkit.getScheduler().runTask(Main.getInstance(),() -> p.getInventory().setChestplate(asItem()));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }



    }

}
