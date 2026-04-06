package net.kalbskinder.helpers.actions;

import lombok.RequiredArgsConstructor;
import net.kalbskinder.helpers.chat.MiniMessageHelper;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class TitleHelper {
    private final MiniMessageHelper miniMessageUtils;

    /**
     * Displays a title with subtitle to a player with specified fade in, stay, and fade out durations.
     *
     * @param player   The player to display the title to (may not be null)
     * @param title    The title text (may not be null)
     * @param fadeIn   The fade-in duration in milliseconds
     * @param stay     The stay duration in milliseconds
     * @param fadeOut  The fade-out duration in milliseconds
     */
    public void displayTitle(Player player, String title, int fadeIn, int stay, int fadeOut) {
        if (player == null || title == null) return;
        player.showTitle(parseTitle(title, null, fadeIn, stay, fadeOut));
    }

    /**
     * Displays a title with subtitle to a player with specified fade in, stay, and fade out durations.
     *
     * @param player   The player to display the title to (may not be null)
     * @param title    The title text (may not be null)
     * @param subTitle The subtitle text
     * @param fadeIn   The fade-in duration in milliseconds
     * @param stay     The stay duration in milliseconds
     * @param fadeOut  The fade-out duration in milliseconds
     */
    public void displayTitle(Player player, String title, String subTitle, int fadeIn, int stay, int fadeOut) {
        if (player == null || title == null) return;
        player.showTitle(parseTitle(title, subTitle, fadeIn, stay, fadeOut));
    }

    public void displayActionbar(Player player, String message) {
        if (player == null || message == null) return;
        player.sendActionBar(miniMessageUtils.parse(message));
    }

    /**
     * Parses the title and subtitle strings into a Title object with specified timings.
     *
     * @param title    The title text
     * @param subTitle The subtitle text
     * @param fadeIn   The fade-in duration in milliseconds
     * @param stay     The stay duration in milliseconds
     * @param fadeOut  The fade-out duration in milliseconds
     * @return The constructed Title object
     */
    private Title parseTitle(String title, String subTitle, int fadeIn, int stay, int fadeOut) {
        if (title == null) title = "";
        if (subTitle == null) subTitle = "";

        return Title.title(
                miniMessageUtils.parse(title),
                miniMessageUtils.parse(subTitle),
                Title.Times.times(
                        java.time.Duration.ofMillis(fadeIn),
                        java.time.Duration.ofMillis(stay),
                        java.time.Duration.ofMillis(fadeOut)
                )
        );
    }
}
