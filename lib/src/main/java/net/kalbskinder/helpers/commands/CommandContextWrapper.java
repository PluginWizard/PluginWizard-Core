package net.kalbskinder.helpers.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.Nullable;

public class CommandContextWrapper {

    private final CommandContext<CommandSourceStack> ctx;

    public CommandContextWrapper(CommandContext<CommandSourceStack> ctx) {
        this.ctx = ctx;
    }

    public CommandSender getSender() {
        return ctx.getSource().getSender();
    }

    /**
     * Get the command executor if it was a player
     * @return The player that executed or null
     */
    public @Nullable Player getPlayer() {
        return (ctx.getSource().getSender() instanceof Player player) ? player : null;
    }

    public boolean wasExecutedByPlayer() {
        return ctx.getSource().getSender() instanceof Player;
    }

    public Player getPlayerArg(String arg) throws CommandSyntaxException {
        return ctx.getArgument(arg, PlayerSelectorArgumentResolver.class)
                .resolve(ctx.getSource())
                .getFirst();
    }

    public EntityType getEntityArg(String arg) {
        return getArgument(arg, EntityType.class);
    }

    public ItemStack getItemArg(String arg) {
        return getArgument(arg, ItemStack.class);
    }

    public BlockState getBlockArg(String arg) {
        return getArgument(arg, BlockState.class);
    }

    public World getWorldArg(String arg) {
        return getArgument(arg, World.class);
    }

    public GameMode getGameModeArg(String arg) {
        return getArgument(arg, GameMode.class);
    }

    public String getColorCodeArg(String arg) {
        return getArgument(arg, String.class);
    }

    public String getCustomArg(String arg) {
        return getArgument(arg, String.class);
    }

    public String getStringArg(String arg) {
        return getArgument(arg, String.class);
    }

    public Boolean getBooleanArg(String arg) {
        return getArgument(arg, Boolean.class);
    }

    public Integer getIntArg(String arg) {
        return getArgument(arg, Integer.class);
    }

    public Float getFloatArg(String arg) {
        return getArgument(arg, Float.class);
    }

    private <T> T getArgument(String arg, Class<T> type) {
        try {
            return ctx.getArgument(arg, type);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

