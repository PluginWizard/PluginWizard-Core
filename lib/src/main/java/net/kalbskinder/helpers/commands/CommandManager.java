package net.kalbskinder.helpers.commands;

import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;

import java.util.List;

@RequiredArgsConstructor
public class CommandManager {
    private final LifecycleEventManager<Plugin> lifecycleManager;

    public void registerCommands(List<CommandHelper> commandList) {
        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commandList.forEach(command -> commands.registrar().register(command.build()));
        });
    }
}
