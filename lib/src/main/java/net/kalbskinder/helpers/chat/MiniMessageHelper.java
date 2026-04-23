package net.kalbskinder.helpers.chat;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class MiniMessageHelper {
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    /**
     * Parses a formatted string into a Component.
     *
     * @param message The message to format
     * @return The parsed Component
     */
    public Component parse(String message) {
        LegacyComponentSerializer legacy = LegacyComponentSerializer.builder()
                .character('&')
                .build();

        // If it contains MiniMessage tags, parse with MiniMessage
        if (message.contains("<") && message.contains(">")) {
            return miniMessage.deserialize("<!italic>" + message);
        }

        // Otherwise parse legacy &-codes
        return legacy.deserialize(message);
    }

    public String serialize(Component message) {
        return miniMessage.serialize(message);
    }
}
