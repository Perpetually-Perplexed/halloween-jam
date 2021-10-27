package me.perplexed.halloween.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;


// if this class is unused, I had an idea for it, scrapped the idea and then never removed the method because I thought I may have a use for it
public abstract class ConsoleOnlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof ConsoleCommandSender)) {
            sender.sendMessage(Txt.red("Only console can use this command!"));
            return true;
        }



        return true;
    }

    public abstract void cmd(CommandSender sender, Command cmd, String label, String[] args);
}
