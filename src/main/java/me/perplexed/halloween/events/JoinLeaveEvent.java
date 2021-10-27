package me.perplexed.halloween.events;

import me.perplexed.halloween.Main;
import me.perplexed.halloween.player.Profile;
import me.perplexed.halloween.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class JoinLeaveEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Main.getInstance().onlineProfiles.add(new Profile(event.getPlayer().getUniqueId()));
    }


    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Profile plyrProf = Utils.getProfile(event.getPlayer());
        if (plyrProf.isInCombat()) {
            plyrProf.getPlayer().setHealth(0);
        }

        plyrProf.save();
        Main.getInstance().onlineProfiles.remove(plyrProf);
    }
}
