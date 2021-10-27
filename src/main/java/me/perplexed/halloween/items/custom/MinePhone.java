package me.perplexed.halloween.items.custom;

import me.perplexed.halloween.gui.PhoneGUI;
import me.perplexed.halloween.items.ItemBase;
import me.perplexed.halloween.items.ItemData;
import me.perplexed.halloween.items.ItemType;
import me.perplexed.halloween.player.Profile;
import me.perplexed.halloween.utils.Txt;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class MinePhone extends ItemBase {

    public MinePhone() {
        super(ItemType.MISC, "Allows you to connect with the world!~~~No physical contact! We live in a socially distancing world!",
                "MinePhone Z", Material.CLOCK, new ItemData(ItemType.MISC,"minephone_z"));
    }

    List<Player> phoneProg1 = new ArrayList<>();

    @Override
    public void onRightClick(Profile p, ItemBase base) {
        if (p.hasPhoneProgressed()) {
            if (phoneProg1.contains(p.getPlayer())) return;
            Player plyr = p.getPlayer();
            new Thread(() -> {
                try {
                    plyr.sendMessage(Txt.yellow("<Operator> Hello, this is a prerecorded message to tell you how to use your new MinePhone Z"));
                    Thread.sleep(3000);
                    plyr.sendMessage(Txt.yellow("<Operator> Thanks to the global infestation of enemies in the overworld, human laziness and human innovation, people have made the internet!"));
                    Thread.sleep(5000);
                    plyr.sendMessage(Txt.yellow("<Operator> Now everything has gone on to the World Wide Web also known as the internet!"));
                    Thread.sleep(2000);
                    plyr.sendMessage(Txt.yellow("<Operator> You can even send things for upgrades, repair or even buy things on this internet without having to mail anyone through our sculk delivery system!"));
                    Thread.sleep(5000);
                    plyr.sendMessage(Txt.yellow("<Operator> That's it for our tutorial, use your phone again to get the full experience of the MinePhone"));
                    p.progressPhone();
                } catch (InterruptedException e) {
                    plyr.sendMessage(Txt.red("<Operator> You seem to have lost connection! Use your home console unit to check out what the error is!"));
                    e.printStackTrace();
                }

                phoneProg1.remove(p.getPlayer());
            }).start();

            return;
        }

        p.getPlayer().openInventory(new PhoneGUI().getInventory());
    }

    @Override
    public void onLeftClick(Profile p, ItemBase base) {
        onRightClick(p,base);
    }

}
