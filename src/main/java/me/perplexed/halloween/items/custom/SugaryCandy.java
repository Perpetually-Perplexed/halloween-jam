package me.perplexed.halloween.items.custom;

import me.perplexed.halloween.items.CandyBase;
import me.perplexed.halloween.items.ItemBase;
import me.perplexed.halloween.items.ItemData;
import me.perplexed.halloween.items.ItemType;
import me.perplexed.halloween.player.Profile;
import me.perplexed.halloween.utils.Txt;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class SugaryCandy extends CandyBase {

    public SugaryCandy() {
        super("Yum, lot's of sugar!~~~Gives speed 2 and hunger 1 when eaten", "Sugary Candy", Material.POTATO, 1, new ItemData(ItemType.CANDY,"candy_sugar"));
    }



    @Override
    public void onEat(Profile p, CandyBase base) {
        Player profP = p.getPlayer();
        if (p.candyCheck()) {
            profP.sendMessage(Txt.red("Ugh, too... much... candy..."));
            return;
        }

        profP.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER,10*20,1));
        profP.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,15*20,2));
        profP.sendMessage(Txt.green("Yum! That was probably unhealthy though."));
        p.eatCandy(15*1000);
    }


    @Override
    public void onLeftClick(Profile p, ItemBase base) {
        p.getPlayer().sendMessage(Txt.green("This candy is sugary! It will give you Speed 2 for 15 seconds!"));
        p.getPlayer().sendMessage(Txt.red("I don't think it's very health though. It will give you Hunger 1 for 10 seconds"));
    }
}
