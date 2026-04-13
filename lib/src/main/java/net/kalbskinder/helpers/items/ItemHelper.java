package net.kalbskinder.helpers.items;

import lombok.RequiredArgsConstructor;
import net.kalbskinder.helpers.chat.MiniMessageHelper;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ItemHelper {
    private final MiniMessageHelper miniMessageUtils;

    public ItemBuilder newItem() {
        return new ItemBuilder(miniMessageUtils);
    }

    /*
     * Converts a string representation of an item to an ItemStack.
     * @param item The string representation of the item (e.g., "minecraft:diamond_sword").
     * @return The corresponding ItemStack, or null if the material is invalid.
     */
    public ItemStack toItemStack(String item) {
        String materialName = item.toUpperCase().replace("MINECRAFT:", "");

        Material material = Material.matchMaterial(materialName);
        if (material == null) {
            return null;
        }
        return new ItemStack(material, 1);
    }

    /*
     * Converts a string representation of an item to an ItemStack with the specified amount.
     * @param item The string representation of the item (e.g., "minecraft:diamond_sword").
     * @param amount   The amount of the item in the ItemStack.
     * @return The corresponding ItemStack, or null if the material is invalid.
     */
    public ItemStack toItemStack(String item, int amount) {
        String materialName = item.toUpperCase().replace("MINECRAFT:", "");

        Material material = Material.matchMaterial(materialName);
        if (material == null) {
            return null;
        }
        return new ItemStack(material, amount);
    }

    /*
     * Creates an ItemStack with the specified lore and item flags.
     * @param item      The string representation of the item (e.g., "minecraft:diamond_sword").
     * @param lore      A list of strings representing the lore lines.
     * @param itemFlags A list of ItemFlags to apply to the item.
     * @return The constructed ItemStack, or null if the input is invalid.
     */
    public ItemStack createItem(String item, List<String> lore, List<ItemFlag> itemFlags) {
        ItemStack itemStack = this.toItemStack(item);
        if (itemStack == null) {
            return null;
        }

        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) {
            return itemStack;
        }

        if (lore != null && !lore.isEmpty()) {
            List<Component> loreComponents = lore.stream()
                    .filter(Objects::nonNull)
                    .map(miniMessageUtils::parse)
                    .collect(Collectors.toList());

            meta.lore(loreComponents);
        }

        if (itemFlags != null && !itemFlags.isEmpty()) {
            meta.addItemFlags(itemFlags.toArray(new ItemFlag[0]));
        }
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /*
     * Creates an ItemStack with the specified name, lore, and item flags.
     * @param item      The string representation of the item (e.g., "minecraft:diamond_sword").
     * @param itemName  The display name of the item.
     * @param lore      A list of strings representing the lore lines.
     * @param itemFlags A list of ItemFlags to apply to the item.
     * @return The constructed ItemStack, or null if the input is invalid.
     */
    public ItemStack createItem(String item, String itemName, List<String> lore, List<ItemFlag> itemFlags) {
        ItemStack itemStack = this.toItemStack(item);
        if (itemStack == null) {
            return null;
        }

        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) {
            return itemStack;
        }

        if (lore != null && !lore.isEmpty()) {
            List<Component> loreComponents = lore.stream()
                    .filter(Objects::nonNull)
                    .map(miniMessageUtils::parse)
                    .collect(Collectors.toList());

            meta.lore(loreComponents);
        }

        if (itemName != null && !itemName.trim().isEmpty()) {
            meta.displayName(miniMessageUtils.parse(itemName));
        }

        if (itemFlags != null && !itemFlags.isEmpty()) {
            meta.addItemFlags(itemFlags.toArray(new ItemFlag[0]));
        }

        itemStack.setItemMeta(meta);
        return itemStack;
    }
}