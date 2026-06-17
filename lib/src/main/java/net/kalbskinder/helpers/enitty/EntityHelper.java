package net.kalbskinder.helpers.enitty;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class EntityHelper {

    /**
     * Summons an entity with an id in a world
     * @param entity The entity to be summoned formatted as minecraft:entity
     * @param id A unique id that references the entity for later access
     * @param world The world to summon the entity in
     */
    public Entity summonEntity(String entity, String id, World world) {
        if (world == null || entity == null || entity.isBlank()) return null;

        NamespacedKey key = NamespacedKey.fromString(entity);
        if (key == null) {
            key = NamespacedKey.minecraft(entity.toLowerCase());
        }

        EntityType type = Registry.ENTITY_TYPE.get(key);
        if (type == null || !type.isSpawnable()) return null;

        Entity spawned = world.spawnEntity(world.getSpawnLocation(), type);
        if (id != null && !id.isBlank()) {
            // Store a stable lookup marker without changing visible name tags.
            spawned.addScoreboardTag("pluginwizard:id=" + id);
        }

        return spawned;
    }

    /**
     * Checks whether an existing entity has the provided PluginWizard entity id.
     *
     * @param entity The entity to check
     * @param id The expected id
     * @return true when the entity contains the matching id tag, otherwise false
     */
    public boolean isTargetEntity(Entity entity, String id) {
        if (entity == null || id == null || id.isBlank()) return false;
        return entity.getScoreboardTags().contains("pluginwizard:id=" + id);
    }
}
