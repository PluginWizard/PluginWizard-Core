package net.kalbskinder.helpers.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;

public class SubCommand extends ArgumentBuilder<SubCommand> {

    private final CommandHelper root;
    private final LiteralArgumentBuilder<CommandSourceStack> parent;

    public SubCommand(LiteralArgumentBuilder<CommandSourceStack> builder, CommandHelper root, LiteralArgumentBuilder<CommandSourceStack> parent) {
        super(builder);
        this.root = root;
        this.parent = parent;
    }

    public CommandHelper end() {
        applyArgChain();
        parent.then(builder);
        return root;
    }
}
