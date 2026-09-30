package net.kalbskinder.helpers.inventories;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * A clickable menu backed by a Bukkit {@link Inventory}. The GUI is its own
 * {@link InventoryHolder}, which is how the listeners recognise a click that landed in
 * one of these menus rather than an ordinary inventory.
 *
 * <p>Build one through {@link net.kalbskinder.helpers.Helpers#guiHelper}, place items with
 * {@link #setItem(int, ItemStack, GuiInteraction)}, and open it with {@link #open(Player)}.
 */
@Getter
@Setter
public class GUI implements InventoryHolder {

    private boolean locked;
    private final UUID uuid = UUID.randomUUID();

    private final Map<Integer, GuiInteraction> interactions = new HashMap<>();
    private GuiInteraction fallbackInteraction;

    private final Inventory inventory;

    /**
     * Creates a chest-style menu.
     *
     * @param rows   the number of rows (1-6); the inventory holds {@code rows * 9} slots
     * @param title  the menu title
     * @param locked when {@code true}, clicks are cancelled so items cannot be taken or moved
     */
    public GUI(int rows, Component title, boolean locked) {
        if (rows < 1 || rows > 6) {
            throw new IllegalArgumentException("rows must be between 1 and 6");
        }
        this.locked = locked;
        this.inventory = Bukkit.createInventory(this, rows * 9, title);
    }

    /**
     * Creates a menu of a specific {@link InventoryType} (e.g. a hopper or dispenser layout).
     *
     * @param type   the inventory type
     * @param title  the menu title
     * @param locked when {@code true}, clicks are cancelled so items cannot be taken or moved
     */
    public GUI(InventoryType type, Component title, boolean locked) {
        this.locked = locked;
        this.inventory = Bukkit.createInventory(this, type, title);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public int getSize() {
        return inventory.getSize();
    }

    public List<HumanEntity> getViewers() {
        return inventory.getViewers();
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }

    public void close(Player player) {
        player.closeInventory();
    }

    /** Places {@code stack} in a single slot. */
    public GUI setItemStack(int slot, ItemStack stack) {
        inventory.setItem(slot, stack);
        return this;
    }

    /** Fills every slot with {@code stack}. Call before placing your clickable items. */
    public GUI setBackground(ItemStack stack) {
        for (int i = 0; i < getSize(); i++) {
            inventory.setItem(i, stack);
        }
        return this;
    }

    /** Fills the outer border of a chest-style menu with {@code stack}. */
    public GUI setFrame(ItemStack stack) {
        int size = getSize();
        int rows = size / 9;

        for (int i = 0; i < 9; i++) inventory.setItem(i, stack);
        for (int i = size - 9; i < size; i++) inventory.setItem(i, stack);

        for (int i = 1; i < rows - 1; i++) {
            inventory.setItem(i * 9, stack);
            inventory.setItem(i * 9 + 8, stack);
        }
        return this;
    }

    /**
     * Registers what happens when {@code slot} is clicked.
     */
    public GUI onClick(int slot, GuiInteraction interaction) {
        interactions.put(slot, interaction);
        return this;
    }

    /**
     * Places an item and the handler that runs when a player clicks it.
     */
    public GUI setItem(int slot, ItemStack item, GuiInteraction interaction) {
        setItemStack(slot, item);
        return onClick(slot, interaction);
    }

    /** Drops the handler registered for {@code slot}, leaving the item in place. */
    public void removeClick(int slot) {
        interactions.remove(slot);
    }

    /**
     * Runs the handler for a clicked slot, falling back to {@link #fallbackInteraction}.
     * Called by {@link net.kalbskinder.helpers.inventories.listeners.InventoryClickListener};
     * whether the click was blocked by {@link #locked} doesn't affect the handler.
     */
    public void dispatchClick(Player player, int slot, InventoryClickEvent event) {
        final GuiInteraction interaction = interactions.getOrDefault(slot, fallbackInteraction);
        if (interaction != null) {
            interaction.handle(player, this, slot, event);
        }
    }
}
