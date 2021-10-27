package me.perplexed.halloween.items;

import me.perplexed.halloween.Main;
import org.apache.commons.lang.SerializationUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class ItemPerstDataContainer implements PersistentDataType<byte[],ItemData> {

    public static final ItemPerstDataContainer data = new ItemPerstDataContainer();
    public static final NamespacedKey dataKey = new NamespacedKey(Main.getInstance(),"info");
    @Override
    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public @NotNull Class<ItemData> getComplexType() {
        return ItemData.class;
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull ItemData complex, @NotNull PersistentDataAdapterContext context) {
        return SerializationUtils.serialize(complex);
    }

    @Override
    public @NotNull ItemData fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        try {
            InputStream is = new ByteArrayInputStream(primitive);
            ObjectInputStream ois = new ObjectInputStream(is);
            return (ItemData) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        //noinspection ConstantConditions
        return null;
    }
}
