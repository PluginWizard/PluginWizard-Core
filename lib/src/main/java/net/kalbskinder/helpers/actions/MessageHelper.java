package net.kalbskinder.helpers.actions;

import lombok.RequiredArgsConstructor;
import net.kalbskinder.helpers.chat.MiniMessageHelper;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class MessageHelper {
    private final MiniMessageHelper miniMessageUtils;

    public void sendMessage(Player player, String message) {
        if (player == null || message == null) return;
        player.sendMessage(miniMessageUtils.parse(message));
    }
}
