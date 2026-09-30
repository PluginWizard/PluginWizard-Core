package net.kalbskinder.helpers.inventories.listeners;

import net.kalbskinder.helpers.events.EventHelper;
import net.kalbskinder.helpers.inventories.GUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class InventoryClickListener {

    public InventoryClickListener(EventHelper eventHelper) {
        eventHelper.subscribe(InventoryClickEvent.class, event -> {
            if (!(event.getWhoClicked() instanceof Player player)) {
                return;
            }

            final Inventory clicked = event.getClickedInventory();
            if (clicked == null || !(clicked.getHolder() instanceof GUI gui)) {
                // The click landed in the player's own inventory (or outside the window). While a
                // locked gui is open that still has to be blocked, otherwise items could be
                // shift-clicked into the menu.
                if (topHolder(event) instanceof GUI open && open.isLocked()) {
                    event.setCancelled(true);
                }
                return;
            }

            if (gui.isLocked()) {
                event.setCancelled(true);
            }

            gui.dispatchClick(player, event.getSlot(), event);
        });

        // A drag can drop items across several slots at once, bypassing the click handler, so a
        // locked gui has to cancel those too.
        eventHelper.subscribe(InventoryDragEvent.class, event -> {
            if (topHolder(event) instanceof GUI gui && gui.isLocked()) {
                event.setCancelled(true);
            }
        });
    }

    private static InventoryHolder topHolder(InventoryClickEvent event) {
        return event.getView().getTopInventory().getHolder();
    }

    private static InventoryHolder topHolder(InventoryDragEvent event) {
        return event.getView().getTopInventory().getHolder();
    }
}
