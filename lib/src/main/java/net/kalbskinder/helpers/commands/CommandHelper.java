package net.kalbskinder.helpers.commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class CommandHelper extends ArgumentBuilder<CommandHelper> {
    private static LifecycleEventManager<Plugin> lifecycleManager;

    private CommandHelper(String name) {
        super(Commands.literal(name));
    }

    public static void inject(LifecycleEventManager<Plugin> injectLifecycleManager) {
        lifecycleManager = injectLifecycleManager;
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

    public void registerCommands(List<CommandHelper> commandList) {
        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commandList.forEach(command -> commands.registrar().register(command.build()));
        });
    }
}