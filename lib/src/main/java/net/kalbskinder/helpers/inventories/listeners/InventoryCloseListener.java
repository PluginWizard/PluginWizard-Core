package net.kalbskinder.helpers.inventories.listeners;

import net.kalbskinder.helpers.events.EventHelper;
import net.kalbskinder.helpers.inventories.GUI;
import net.kalbskinder.helpers.inventories.GuiHelper;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener {

    public InventoryCloseListener(EventHelper eventHelper, GuiHelper guiHelper) {
        eventHelper.subscribe(InventoryCloseEvent.class, event -> {
            if (!(event.getInventory().getHolder() instanceof GUI gui)) {
                return;
            }

            // The event runs before the viewer is dropped, so the player closing the gui is still
            // listed as one. Only unregister once nobody else is looking at it.
            final boolean stillWatched = gui.getViewers().stream()
                    .anyMatch(viewer -> !viewer.getUniqueId().equals(event.getPlayer().getUniqueId()));

            if (!stillWatched) {
                guiHelper.remove(gui);
            }
        });
    }
}
