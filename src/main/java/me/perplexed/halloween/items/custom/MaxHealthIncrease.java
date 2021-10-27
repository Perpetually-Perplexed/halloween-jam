package me.perplexed.halloween.items.custom;

import me.perplexed.halloween.Main;
import me.perplexed.halloween.items.CandyBase;
import me.perplexed.halloween.items.ItemData;
import me.perplexed.halloween.items.ItemType;
import me.perplexed.halloween.player.Profile;
import me.perplexed.halloween.utils.Txt;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class MaxHealthIncrease extends CandyBase {

    Map<Player,Integer> timesHad = new HashMap<>();

    public MaxHealthIncrease() {
        super("Ewwww, something green? Looks healthy though!~~~Increases max health by 2HP for 60 seconds, stacks up to 2 times","Disgusting Candy",
                Material.GOLDEN_CARROT, 1, new ItemData(ItemType.CANDY,"candy_healthy"));
    }

    @Override
    public void onEat(Profile p, CandyBase base) {
        if (!timesHad.containsKey(p.getPlayer())) {
            timesHad.put(p.getPlayer(),1);
        }

        if (timesHad.get(p.getPlayer()) == 3) {
            p.getPlayer().sendMessage(Txt.red("Disgusting! I can't have more!"));
            return;
        }

        timesHad.replace(p.getPlayer(),timesHad.get(p.getPlayer()) + 1);

        p.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(p.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + 2);

        new BukkitRunnable() {
            @Override
            public void run() {
                timesHad.replace(p.getPlayer(),timesHad.get(p.getPlayer()) -1);
                p.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(p.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - 2);
            }
        }.runTaskLater(Main.getInstance(),60*20L);
    }
}
