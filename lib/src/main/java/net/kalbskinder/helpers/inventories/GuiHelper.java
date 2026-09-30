package net.kalbskinder.helpers.inventories;

import lombok.Getter;
import net.kalbskinder.helpers.chat.MiniMessageHelper;
import net.kalbskinder.helpers.events.EventHelper;
import net.kalbskinder.helpers.inventories.listeners.InventoryClickListener;
import net.kalbskinder.helpers.inventories.listeners.InventoryCloseListener;
import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryType;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Creates {@link GUI} menus and keeps track of the ones currently open, so the click
 * and close listeners can find them. Reach it through
 * {@link net.kalbskinder.helpers.Helpers#guiHelper}.
 */
public class GuiHelper {

    @Getter private static final Map<UUID, GUI> inventories = new ConcurrentHashMap<>();

    private final MiniMessageHelper miniMessageHelper;

    public GuiHelper(MiniMessageHelper miniMessageHelper) {
        this.miniMessageHelper = Objects.requireNonNull(miniMessageHelper, "miniMessageHelper");
    }

    /**
     * Wires up the click and close listeners. Called once during
     * {@link net.kalbskinder.helpers.Helpers#initialize}.
     */
    public void registerListeners(EventHelper eventHelper) {
        new InventoryClickListener(eventHelper);
        new InventoryCloseListener(eventHelper, this);
    }

    /** Creates a chest-style menu with the given number of rows (1-6). */
    public GUI createInventory(int rows, Component title, boolean locked) {
        GUI gui = new GUI(rows, title, locked);
        inventories.put(gui.getUuid(), gui);
        return gui;
    }

    /**
     * Creates a chest-style menu, parsing {@code title} through
     * {@link MiniMessageHelper#parse(String)} so MiniMessage and legacy {@code &}-codes work.
     */
    public GUI createInventory(int rows, String title, boolean locked) {
        return createInventory(rows, miniMessageHelper.parse(title), locked);
    }

    /** Creates a menu of a specific {@link InventoryType}. */
    public GUI createInventory(InventoryType type, Component title, boolean locked) {
        GUI gui = new GUI(type, title, locked);
        inventories.put(gui.getUuid(), gui);
        return gui;
    }

    /**
     * Creates a menu of a specific {@link InventoryType}, parsing {@code title} through
     * {@link MiniMessageHelper#parse(String)}.
     */
    public GUI createInventory(InventoryType type, String title, boolean locked) {
        return createInventory(type, miniMessageHelper.parse(title), locked);
    }

    /** Stops tracking a menu once nobody is looking at it. */
    public void remove(GUI gui) {
        inventories.remove(gui.getUuid());
    }
}
