package me.perplexed.halloween.items;

import me.perplexed.halloween.utils.Txt;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

import java.io.Serializable;

public enum ItemType implements Serializable {
    MISC(NamedTextColor.WHITE,""),
    COSTUME(NamedTextColor.GREEN,"Costume"),
    CANDY(NamedTextColor.DARK_PURPLE,"Candy");

    private final Component desc;


    ItemType(NamedTextColor clr, String itemName) {
        this.desc = Txt.custom(itemName,clr);
    }

    public Component getDesc() {
        return desc;
    }
}
