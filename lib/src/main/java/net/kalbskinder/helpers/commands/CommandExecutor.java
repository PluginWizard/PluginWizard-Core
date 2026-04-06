package net.kalbskinder.helpers.commands;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

@FunctionalInterface
public interface CommandExecutor {
    void run(CommandContextWrapper ctx) throws CommandSyntaxException;
}

