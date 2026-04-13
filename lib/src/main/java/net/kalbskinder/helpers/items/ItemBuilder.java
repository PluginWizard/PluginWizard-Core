package net.kalbskinder.helpers.items;

import net.kalbskinder.helpers.chat.MiniMessageHelper;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ItemBuilder {
    private final MiniMessageHelper miniMessageHelper;

    private String name;
    private Material material;
    private int amount = 1;
    private final List<ItemEnchantment> enchantments = new ArrayList<>();
    private final List<String> lore = new ArrayList<>();
    private final List<ItemFlag> itemFlags = new ArrayList<>();

    ItemBuilder(MiniMessageHelper miniMessageHelper) {
        this.miniMessageHelper = Objects.requireNonNull(miniMessageHelper, "miniMessageHelper");
    }

    public ItemBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder material(Material material) {
        this.material = Objects.requireNonNull(material, "material");
        return this;
    }

    public ItemBuilder amount(int amount) {
        if (amount < 1) {
            throw new IllegalArgumentException("amount must be at least 1");
        }
        this.amount = amount;
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        this.lore.clear();
        if (lore != null) {
            this.lore.addAll(lore.stream().filter(Objects::nonNull).toList());
        }
        return this;
    }

    public ItemBuilder addLoreLine(String loreLine) {
        if (loreLine != null) {
            this.lore.add(loreLine);
        }
        return this;
    }

    public ItemBuilder itemFlags(List<ItemFlag> itemFlags) {
        this.itemFlags.clear();
        if (itemFlags != null) {
            this.itemFlags.addAll(itemFlags.stream().filter(Objects::nonNull).toList());
        }
        return this;
    }

    public ItemBuilder itemFlag(ItemFlag itemFlag) {
        if (itemFlag != null) {
            this.itemFlags.add(itemFlag);
        }
        return this;
    }

    /** No-op readability helper: {@code .enchants().lore(...)} */
    public ItemBuilder enchants() {
        return this;
    }

    public ItemBuilder enchants(ItemEnchantment... enchantments) {
        if (enchantments != null) {
            Arrays.stream(enchantments)
                    .filter(Objects::nonNull)
                    .forEach(this.enchantments::add);
        }
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        this.enchantments.add(ItemEnchantment.of(enchantment, level));
        return this;
    }

    public ItemStack build() {
        if (material == null) {
            throw new IllegalStateException("material is required");
        }

        ItemStack stack = new ItemStack(material, amount);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            if (name != null && !name.isBlank()) {
                meta.displayName(miniMessageHelper.parse(name));
            }
            if (!lore.isEmpty()) {
                List<Component> loreComponents = lore.stream()
                        .map(miniMessageHelper::parse)
                        .toList();
                meta.lore(loreComponents);
            }
            if (!itemFlags.isEmpty()) {
                meta.addItemFlags(itemFlags.toArray(new ItemFlag[0]));
            }
            stack.setItemMeta(meta);
        }

        for (ItemEnchantment e : enchantments) {
            stack.addUnsafeEnchantment(e.getEnchantment(), e.getLevel());
        }

        return stack;
    }
}
