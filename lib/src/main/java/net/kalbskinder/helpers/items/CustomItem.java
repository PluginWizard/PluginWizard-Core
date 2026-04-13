package net.kalbskinder.helpers.items;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CustomItem {
    private String name;
    private Material material;
    private int amount = 1;
    private List<ItemEnchantment> enchantments = new ArrayList<>();
    private List<String> lore = new ArrayList<>();
    private List<ItemFlag> itemFlags = new ArrayList<>();

    public CustomItem(String name,
                      Material material,
                      int amount,
                      List<ItemEnchantment> enchantments,
                      List<String> lore,
                      List<ItemFlag> itemFlags) {
        this.name = name;
        this.material = material;
        this.amount = Math.max(1, amount);
        this.enchantments = new ArrayList<>(enchantments == null ? Collections.emptyList() : enchantments);
        this.lore = new ArrayList<>(lore == null ? Collections.emptyList() : lore);
        this.itemFlags = new ArrayList<>(itemFlags == null ? Collections.emptyList() : itemFlags);
    }
}
