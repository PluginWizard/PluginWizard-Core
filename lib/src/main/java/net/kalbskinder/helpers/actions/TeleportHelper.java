package net.kalbskinder.helpers.actions;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class TeleportHelper {
    /**
     * Teleports a player to specified coordinates in the player's current world.
     * If the player or the player's world is null, the method returns without action.
     *
     * @param player The player to teleport (may not be null)
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param z      The z coordinate
     */
    public void teleportEntity(Player player, double x, double y, double z) {
        if (player == null || player.getWorld() == null) return;
        Location location = new Location(player.getWorld(), x, y, z);
        player.teleport(location);
    }

    /**
     * Teleports a player to specified coordinates in the provided world.
     * If the world or player is null, the method returns without action.
     *
     * @param world  The world to teleport the player in (may not be null)
     * @param player The player to teleport (may not be null)
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param z      The z coordinate
     */
    public void teleportEntity(World world, Player player, double x, double y, double z) {
        if (world == null || player == null) return;
        Location location = new Location(world, x, y, z);
        player.teleport(location);
    }

    /**
     * Teleports an entity to a specified location.
     * If the entity or location is null, the method returns without action.
     *
     * @param entity   The entity to teleport (may not be null)
     * @param location The target location (may not be null)
     */
    public void teleportEntity(Entity entity, Location location) {
        if (entity == null || location == null) return;
        entity.teleport(location);
    }

    /**
     * Teleports an entity to specified coordinates in the entity's current world.
     * If the entity or its world is null, the method returns without action.
     *
     * @param entity The entity to teleport (may not be null)
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param z      The z coordinate
     */
    public void teleportEntity(Entity entity, double x, double y, double z) {
        if (entity == null || entity.getWorld() == null) return;
        Location location = new Location(entity.getWorld(), x, y, z);
        entity.teleport(location);
    }

    /**
     * Teleports an entity to specified coordinates in the provided world.
     * If the world or entity is null, the method returns without action.
     *
     * @param world  The world to teleport the entity in (may not be null)
     * @param entity The entity to teleport (may not be null)
     * @param x      The x coordinate
     * @param y      The y coordinate
     * @param z      The z coordinate
     */
    public void teleportEntity(World world, Entity entity, double x, double y, double z) {
        if (world == null || entity == null) return;
        Location location = new Location(world, x, y, z);
        entity.teleport(location);
    }
}