package me.perplexed.halloween.events;

import me.perplexed.halloween.Main;
import me.perplexed.halloween.items.CandyBase;
import me.perplexed.halloween.items.ItemBase;
import me.perplexed.halloween.items.ItemPerstDataContainer;
import me.perplexed.halloween.utils.Txt;
import me.perplexed.halloween.utils.Utils;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;


public class CandyEvents implements Listener {


    @EventHandler(ignoreCancelled = true)
    public void onEat(PlayerItemConsumeEvent e) {
        if (!Utils.customItemCheck(e.getItem())) return;

        ItemBase ib = Utils.asItemBase(e.getItem());

        if (!(ib instanceof CandyBase cb)) {
            return;
        }

        if (Utils.getProfile(e.getPlayer()).candyCheck()) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(Txt.red("Too... sick, can't... have... more"));
            return;
        }

        cb.onEat(Utils.getProfile(e.getPlayer()),cb);
    }
}
