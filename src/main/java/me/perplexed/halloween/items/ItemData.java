package me.perplexed.halloween.items;

import java.io.Serializable;

public class ItemData implements Serializable {

    final ItemType type;
    final String localizedNamed;

    public ItemData(ItemType type, String localizedNamed) {
        this.type = type;
        this.localizedNamed = localizedNamed;
    }


    public String getLocalizedNamed() {
        return localizedNamed;
    }

    public ItemType getType() {
        return type;
    }
}
