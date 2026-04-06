package net.kalbskinder.helpers.actions;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class PlayerItemHelper {

    /*
    * Gives the specified item to the player in the specified amount. If the player's inventory is full,
    * the remaining items will be dropped at the player's location.
    * @param item   The ItemStack to give.
    * @param amount The amount of the item to give.
    * @param player The player to give the item to.
     */
    public void giveItem(ItemStack item, int amount, Player player) {
        if (player == null || item == null || amount <= 0) return;

        ItemStack toGive = item.clone();
        toGive.setAmount(Math.max(1, amount));

        Map<Integer, ItemStack> leftovers = player.getInventory().addItem(toGive);
        if (!leftovers.isEmpty()) {
            for (ItemStack leftover : leftovers.values()) {
                if (leftover == null) continue;
                player.getWorld().dropItemNaturally(player.getLocation(), leftover);
            }
        }
    }

    /*
    * Sets the specified item in the player's inventory at the given slot with the specified amount.
    * @param item   The ItemStack to set.
    * @param slot   The inventory slot to set the item in.
    * @param amount The amount of the item to set.
    * @param player The player whose inventory to modify.
     */
    public void setItem(ItemStack item, int slot, int amount, Player player) {
        if (player == null || item == null || amount <= 0) return;

        ItemStack toGive = item.clone();
        toGive.setAmount(Math.max(1, amount));

        player.getInventory().setItem(slot, toGive);
    }
}