package net.kalbskinder.helpers.commands;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.registry.RegistryKey;

import java.util.ArrayList;
import java.util.List;

/**
 * Shared base class that provides argument registration methods for both
 * CommandHelper (base command) and SubCommand (subcommands).
 */
@SuppressWarnings("unchecked")
public abstract class ArgumentBuilder<SELF extends ArgumentBuilder<SELF>> {

    protected final LiteralArgumentBuilder<CommandSourceStack> builder;
    protected final List<RequiredArgumentBuilder<CommandSourceStack, ?>> argChain = new ArrayList<>();
    protected CommandExecutor argExecutor = null;

    protected ArgumentBuilder(LiteralArgumentBuilder<CommandSourceStack> builder) {
        this.builder = builder;
    }

    public SELF executes(CommandExecutor executor) {
        if (!argChain.isEmpty()) {
            argExecutor = executor;
        } else {
            builder.executes(ctx -> {
                executor.run(new CommandContextWrapper(ctx));
                return 1;
            });
        }
        return (SELF) this;
    }

    public SELF stringArg(String name) {
        argChain.add(Commands.argument(name, StringArgumentType.string()));
        return (SELF) this;
    }

    public SELF booleanArg(String name) {
        argChain.add(Commands.argument(name, BoolArgumentType.bool()));
        return (SELF) this;
    }

    public SELF intArg(String name) {
        argChain.add(Commands.argument(name, IntegerArgumentType.integer()));
        return (SELF) this;
    }

    public SELF floatArg(String name) {
        argChain.add(Commands.argument(name, FloatArgumentType.floatArg()));
        return (SELF) this;
    }

    public SELF playerArg(String name) {
        argChain.add(Commands.argument(name, ArgumentTypes.player()));
        return (SELF) this;
    }

    public SELF entityArg(String name) {
        argChain.add(Commands.argument(name, ArgumentTypes.resource(RegistryKey.ENTITY_TYPE)));
        return (SELF) this;
    }

    public SELF itemArg(String name) {
        argChain.add(Commands.argument(name, ArgumentTypes.itemStack()));
        return (SELF) this;
    }

    public SELF blockArg(String name) {
        argChain.add(Commands.argument(name, ArgumentTypes.blockState()));
        return (SELF) this;
    }

    public SELF worldArg(String name) {
        argChain.add(Commands.argument(name, ArgumentTypes.world()));
        return (SELF) this;
    }

    public SELF gameModeArg(String name) {
        argChain.add(Commands.argument(name, ArgumentTypes.gameMode()));
        return (SELF) this;
    }

    public SELF colorCodeArg(String name) {
        return customArg(name, List.of("black", "dark_blue", "dark_green", "dark_aqua", "dark_red",
                "dark_purple", "gold", "gray", "dark_gray", "blue", "green", "aqua", "red",
                "light_purple", "yellow", "white"));
    }

    public SELF customArg(String name, List<String> suggestions) {
        argChain.add(Commands.argument(name, SingleTokenArgumentType.singleToken(suggestions)));
        return (SELF) this;
    }

    /**
     * Applies the built arg chain onto the literal builder.
     * Must be called before building/registering the command node.
     */
    protected void applyArgChain() {
        if (!argChain.isEmpty()) {
            if (argExecutor != null) {
                CommandExecutor exec = argExecutor;
                argChain.getLast().executes(ctx -> {
                    exec.run(new CommandContextWrapper(ctx));
                    return 1;
                });
            }
            for (int i = argChain.size() - 2; i >= 0; i--) {
                argChain.get(i).then(argChain.get(i + 1));
            }
            builder.then(argChain.getFirst());
        }
    }
}

