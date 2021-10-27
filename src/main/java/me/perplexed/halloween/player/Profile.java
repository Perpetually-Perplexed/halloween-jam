package me.perplexed.halloween.player;

import me.perplexed.halloween.Main;
import me.perplexed.halloween.utils.Txt;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

public final class Profile {

    final Player plyr;
    boolean inCombat = false;
    boolean newPlayer;
    long lastCandyCheck = 0;
    int essence;
    int costumeEssence;
    int candyEssence;
    boolean phoneProgressed;
    boolean freeze = false;


    public Profile(UUID player) {
        plyr = Bukkit.getServer().getPlayer(player);
        Objects.requireNonNull(player);

        FileConfiguration fc = Main.getInstance().getManager().getDataConfig();
        newPlayer = fc.get("players."+player) == null;
        costumeEssence = fc.getInt("players."+player + "stumeEssence");
        candyEssence = fc.getInt("players."+player + ".candyEssence");
        essence = fc.getInt("players."+player+".essence");
        phoneProgressed = fc.getBoolean("players."+player+".phoneProgressed");
    }

    public void save() {
        FileConfiguration config = Main.getInstance().getManager().getDataConfig();
        config.set("players." + plyr.getUniqueId() + ".essence",essence);
        config.set("players." + plyr.getUniqueId() + ".stumeEssence",costumeEssence); // "Pretty cool 'stume huh?"
        config.set("players." +plyr.getUniqueId()+".candyEssence",candyEssence);
        config.set("players."+plyr.getUniqueId()+".phoneProgressed",phoneProgressed);

        Main.getInstance().getManager().saveConfig();
    }

    public boolean candyCheck() {
        return System.currentTimeMillis() - lastCandyCheck < 5000;
    }

    public void eatCandy(long cd) {
        lastCandyCheck = System.currentTimeMillis();
    }

    public Player getPlayer() {
        return plyr;
    }


    public boolean isFrozen() {
        return freeze;
    }

    public void setIfFrozen(boolean b) {
        this.freeze = b;
    }

    public int getCandyEssence() {
        return candyEssence;
    }

    public void removeCandyEssence(int essence) { this.candyEssence -= essence;}
    public void removeCostumeEssence(int essence) { this.costumeEssence -= essence;}
    public void removeEssence(int essence) { this.essence -= essence;}


    public int getCostumeEssence() {
        return costumeEssence;
    }


    public int getEssence() {
        return essence;
    }

    public void addCandyEssence(int candyEssence) {
        this.candyEssence += candyEssence;
        getPlayer().sendActionBar(Txt.custom("+" + candyEssence + " candy essence", NamedTextColor.WHITE));
    }

    public void setInCombat(boolean inCombat) {
        this.inCombat = inCombat;
    }

    public void addCostumeEssence(int costumeEssence) {
        this.costumeEssence += costumeEssence;
        getPlayer().sendActionBar(Txt.custom("+" + costumeEssence + " costume essence", NamedTextColor.WHITE));
    }

    public void addEssence(int essence) {
        this.essence += essence;
        getPlayer().sendActionBar(Txt.custom("+" + essence + " essence", NamedTextColor.WHITE));
    }

    public boolean isInCombat() {
        return inCombat;
    }

    public boolean hasPhoneProgressed() {
        return phoneProgressed;
    }

    public boolean isNewPlayer() {
        return newPlayer;
    }

    public void progressPhone() {
        phoneProgressed = true;
    }


}



