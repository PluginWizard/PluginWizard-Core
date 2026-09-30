package net.kalbskinder.helpers.inventories;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * A callback run when a player clicks a slot of a {@link GUI}.
 * Receives the clicking player, the gui, the clicked slot, and the raw
 * {@link InventoryClickEvent}, so a single handler can be reused across slots and
 * tell a left click from a right or shift click (via {@code event.getClick()}).
 */
@FunctionalInterface
public interface GuiInteraction {
    void handle(Player player, GUI gui, int slot, InventoryClickEvent event);
}
