package net.kalbskinder.helpers.commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

public class CommandHelper extends ArgumentBuilder<CommandHelper> {

    private CommandHelper(String name) {
        super(Commands.literal(name));
    }

    public static CommandHelper create(String name) {
        return new CommandHelper(name);
    }

    public SubCommand sub(String name) {
        com.mojang.brigadier.builder.LiteralArgumentBuilder<CommandSourceStack> sub = Commands.literal(name);
        return new SubCommand(sub, this, builder);
    }

    public LiteralCommandNode<CommandSourceStack> build() {
        applyArgChain();
        return builder.build();
    }
}