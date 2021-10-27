package me.perplexed.halloween.items.custom;

import me.perplexed.halloween.items.CandyBase;
import me.perplexed.halloween.items.ItemData;
import me.perplexed.halloween.items.ItemType;
import me.perplexed.halloween.player.Profile;
import me.perplexed.halloween.utils.Txt;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class StonedCandy extends CandyBase {

    public  StonedCandy() {
        super("Uhh that smells weird~~~Gives you a random potion effect for 10 seconds", "Weird Candy", Material.APPLE, 1, new ItemData(ItemType.CANDY,"candy_weird"));
    }

    @Override
    public void onEat(Profile p, CandyBase base) {
        Random rando = new Random();
        if (p.candyCheck()) {
            p.getPlayer().sendMessage(Txt.red("My head hurts from this candy"));
            return;
        }

        PotionEffect effect = new PotionEffect(PotionEffectType.values()[rando.nextInt(PotionEffectType.values().length) -1],60*20,2,false,false,false);
        p.getPlayer().addPotionEffect(effect);
        p.getPlayer().sendMessage(Txt.green("You got " + effect.getType().getName().toLowerCase().replaceAll("_"," ") + " " + effect.getAmplifier() + 1 ));

        p.eatCandy(15*300);
    }
}
