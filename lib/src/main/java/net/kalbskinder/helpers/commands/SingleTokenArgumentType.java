package net.kalbskinder.helpers.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.papermc.paper.command.brigadier.argument.CustomArgumentType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * A custom PaperMC-compatible argument type that reads a single token of any
 * non-whitespace characters
 */
public class SingleTokenArgumentType implements CustomArgumentType.Converted<String, String> {

    private final List<String> suggestions;

    private SingleTokenArgumentType(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public static SingleTokenArgumentType singleToken(List<String> suggestions) {
        return new SingleTokenArgumentType(suggestions);
    }

    @Override
    public @NonNull String convert(@NonNull String nativeType) throws CommandSyntaxException {
        return nativeType;
    }

    @Override
    public @NonNull StringArgumentType getNativeType() {
        return StringArgumentType.word();
    }

    @Override
    public @NonNull String parse(@NonNull StringReader reader) throws CommandSyntaxException {
        int start = reader.getCursor();
        while (reader.canRead() && reader.peek() != ' ') {
            reader.skip();
        }
        return reader.getString().substring(start, reader.getCursor());
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        String remaining = builder.getRemaining().toLowerCase();
        for (String suggestion : suggestions) {
            if (suggestion.toLowerCase().startsWith(remaining)) {
                builder.suggest(suggestion);
            }
        }
        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples() {
        return suggestions.isEmpty() ? List.of("value") : suggestions.subList(0, Math.min(3, suggestions.size()));
    }
}
