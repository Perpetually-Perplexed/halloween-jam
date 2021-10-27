package me.perplexed.halloween.commands;

import me.perplexed.halloween.Main;
import me.perplexed.halloween.utils.Txt;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadConfig implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("halloween.reload")) {
            sender.sendMessage(Txt.red("You don't have permission for this!"));
            return true;
        }

        Main.getInstance().reloadConfig();
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',Main.getInstance().getConfig().getString("reload-message")));


        return true;
    }
}
