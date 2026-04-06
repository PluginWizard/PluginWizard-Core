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

public class CommandContextWrapper {

    private final CommandContext<CommandSourceStack> ctx;

    public CommandContextWrapper(CommandContext<CommandSourceStack> ctx) {
        this.ctx = ctx;
    }

    public CommandSender getSender() {
        return ctx.getSource().getSender();
    }

    public Player getPlayer(String arg) throws CommandSyntaxException {
        return ctx.getArgument(arg, PlayerSelectorArgumentResolver.class)
                .resolve(ctx.getSource())
                .getFirst();
    }

    public EntityType getEntity(String arg) {
        return getArgument(arg, EntityType.class);
    }

    public ItemStack getItem(String arg) {
        return getArgument(arg, ItemStack.class);
    }

    public BlockState getBlock(String arg) {
        return getArgument(arg, BlockState.class);
    }

    public World getWorld(String arg) {
        return getArgument(arg, World.class);
    }

    public GameMode getGameMode(String arg) {
        return getArgument(arg, GameMode.class);
    }

    public String getColorCode(String arg) {
        return getArgument(arg, String.class);
    }

    public String getCustomArg(String arg) {
        return getArgument(arg, String.class);
    }

    public String getString(String arg) {
        return getArgument(arg, String.class);
    }

    public Boolean getBoolean(String arg) {
        return getArgument(arg, Boolean.class);
    }

    public Integer getInt(String arg) {
        return getArgument(arg, Integer.class);
    }

    public Float getFloat(String arg) {
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

