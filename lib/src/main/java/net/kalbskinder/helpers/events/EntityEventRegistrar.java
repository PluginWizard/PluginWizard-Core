package net.kalbskinder.helpers.events;

import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.Objects;
import java.util.function.Function;

/**
 * Fluent API to register callbacks for a single tracked entity id.
 */
public class EntityEventRegistrar {
    private final EventSubscription subscription;
    private final String entityId;

    public EntityEventRegistrar(EventSubscription subscription, String entityId) {
        this.subscription = Objects.requireNonNull(subscription, "subscription");
        this.entityId = Objects.requireNonNull(entityId, "entityId");
    }

    public <E extends Event> Listener on(
            Class<E> eventClass,
            Function<E, Entity> entityExtractor,
            EventValue<E> eventValue
    ) {
        return subscription.subscribeForEntity(entityId, eventClass, entityExtractor, eventValue);
    }

    public <E extends Event> Listener on(
            Class<E> eventClass,
            Function<E, Entity> entityExtractor,
            EventPriority priority,
            boolean ignoreCancelled,
            EventValue<E> eventValue
    ) {
        return subscription.subscribeForEntity(
                entityId,
                eventClass,
                entityExtractor,
                priority,
                ignoreCancelled,
                eventValue
        );
    }

    public Listener onDeath(EventValue<EntityDeathEvent> eventValue) {
        return on(EntityDeathEvent.class, EntityDeathEvent::getEntity, eventValue);
    }

    public Listener onDamage(EventValue<EntityDamageEvent> eventValue) {
        return on(EntityDamageEvent.class, EntityDamageEvent::getEntity, eventValue);
    }

    public Listener onDamageByEntity(EventValue<EntityDamageByEntityEvent> eventValue) {
        return on(EntityDamageByEntityEvent.class, EntityDamageByEntityEvent::getEntity, eventValue);
    }

    public Listener onDamagesOther(EventValue<EntityDamageByEntityEvent> eventValue) {
        return on(EntityDamageByEntityEvent.class, EntityDamageByEntityEvent::getDamager, eventValue);
    }

    public Listener onSpawn(EventValue<EntitySpawnEvent> eventValue) {
        return on(EntitySpawnEvent.class, EntitySpawnEvent::getEntity, eventValue);
    }

    public Listener onPickupItem(EventValue<EntityPickupItemEvent> eventValue) {
        return on(EntityPickupItemEvent.class, EntityPickupItemEvent::getEntity, eventValue);
    }

    public Listener onInteract(EventValue<PlayerInteractEntityEvent> eventValue) {
        return on(PlayerInteractEntityEvent.class, PlayerInteractEntityEvent::getRightClicked, eventValue);
    }

    public Listener onMove(EventValue<EntityMoveEvent> eventValue) {
        return on(EntityMoveEvent.class, EntityMoveEvent::getEntity, eventValue);
    }

    public Listener onInteractAt(EventValue<PlayerInteractAtEntityEvent> eventValue) {
        return on(PlayerInteractAtEntityEvent.class, PlayerInteractAtEntityEvent::getRightClicked, eventValue);
    }
}

