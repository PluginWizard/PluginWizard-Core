package net.kalbskinder.helpers.items;

import org.bukkit.enchantments.Enchantment;

import java.util.Objects;

public class ItemEnchantment {
    private final Enchantment enchantment;
    private final int level;

    private ItemEnchantment(Enchantment enchantment, int level) {
        this.enchantment = Objects.requireNonNull(enchantment, "enchantment");
        if (level < 1) {
            throw new IllegalArgumentException("level must be at least 1");
        }
        this.level = level;
    }

    public static ItemEnchantment of(Enchantment enchantment, int level) {
        return new ItemEnchantment(enchantment, level);
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public int getLevel() {
        return level;
    }
}
