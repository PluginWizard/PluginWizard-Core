package net.kalbskinder.helpers.items;

import net.kalbskinder.helpers.chat.MiniMessageHelper;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockbukkit.mockbukkit.MockBukkit;

import java.util.Arrays;

public class ItemBuilderTest {

    private ItemHelper itemHelper;

    @Before
    public void setUp() {
        MockBukkit.mock();
        MockBukkit.createMockPlugin();
        itemHelper = new ItemHelper(new MiniMessageHelper());
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void buildWithDefaults() {
        ItemStack item = itemHelper.newItem()
                .name("<green>Custom item name")
                .material(Material.DIAMOND_SWORD)
                .enchants()
                .build();

        Assert.assertEquals(Material.DIAMOND_SWORD, item.getType());
        Assert.assertEquals(1, item.getAmount());
        Assert.assertTrue(item.getEnchantments().isEmpty());
    }

    @Test
    public void buildWithAllOptionalData() {
        ItemStack item = itemHelper.newItem()
                .material(Material.DIAMOND_SWORD)
                .amount(2)
                .enchant(Enchantment.UNBREAKING, 3)
                .lore(Arrays.asList("line1", null, "line2"))
                .itemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        Assert.assertEquals(Material.DIAMOND_SWORD, item.getType());
        Assert.assertEquals(2, item.getAmount());
        Assert.assertTrue(item.containsEnchantment(Enchantment.UNBREAKING));
        Assert.assertEquals(3, item.getEnchantmentLevel(Enchantment.UNBREAKING));
        Assert.assertNotNull(item.getItemMeta());
        Assert.assertEquals(2, item.getItemMeta().lore().size());
        Assert.assertTrue(item.getItemMeta().hasItemFlag(ItemFlag.HIDE_ATTRIBUTES));
    }

    @Test
    public void buildRequiresMaterial() {
        Assert.assertThrows(IllegalStateException.class, () -> itemHelper.newItem().build());
    }

    @Test
    public void amountMustBePositive() {
        Assert.assertThrows(IllegalArgumentException.class, () -> itemHelper.newItem().amount(0));
    }

    @Test
    public void itemEnchantmentValidation() {
        Assert.assertThrows(NullPointerException.class, () -> ItemEnchantment.of(null, 1));
        Assert.assertThrows(IllegalArgumentException.class, () -> ItemEnchantment.of(Enchantment.UNBREAKING, 0));
    }
}




