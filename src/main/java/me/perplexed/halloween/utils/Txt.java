package me.perplexed.halloween.utils;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

// utils class for chat msg management
public final class Txt {

    public static Component red(String msg) {
        return Component.text(msg).color(NamedTextColor.RED);
    }

    public static Component green(String msg) {
        return Component.text(msg).color(NamedTextColor.GREEN);
    }

    public static Component yellow(String msg) {
        return Component.text(msg).color(NamedTextColor.YELLOW);
    }

    public static Component custom(String msg, TextColor clr) {
        return Component.text(msg).color(clr);
    }

    public static Component textCase(String msg, TextColor clr) {
        msg = msg.replaceAll("_"," ");
        String[] msgArr = msg.split(" ");
        StringBuilder sb = new StringBuilder();

        for (String s : msgArr) {
            sb.append(s.replaceFirst(String.valueOf(s.charAt(0)), String.valueOf(s.charAt(0)).toLowerCase()));
            sb.append(" ");
        }

        msg = sb.toString();

        if (clr == null) return Component.text(msg);

        return custom(msg,clr);
    }

    public static String textCase(String msg) {
        if (msg.isBlank() || msg.isEmpty()) return "";
        msg = msg.replaceAll("_"," ");
        String[] msgArr = msg.split(" ");
        StringBuilder sb = new StringBuilder();

        for (String s : msgArr) {
            if (s.isBlank() || s.isEmpty()) continue;
            sb.append(s.replaceFirst(String.valueOf(s.toCharArray()[0]), String.valueOf(s.toCharArray()[0]).toUpperCase()));
            sb.append(" ");
        }

        msg = sb.toString();

        return msg;
    }


}
