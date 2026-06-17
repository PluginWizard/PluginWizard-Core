package net.kalbskinder.helpers.events;

import net.kalbskinder.helpers.enitty.EntityHelper;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.Objects;
import java.util.function.Function;

public class EventHelper implements EventSubscription {
    private final Plugin plugin;
    private final PluginManager pluginManager;
    private final EntityHelper entityHelper;

    public EventHelper(Plugin plugin) {
        this.plugin = Objects.requireNonNull(plugin, "plugin");
        this.pluginManager = plugin.getServer().getPluginManager();
        this.entityHelper = new EntityHelper();
    }

    @Override
    public <E extends Event> Listener subscribe(Class<E> eventClass, EventValue<E> eventValue) {
        return subscribe(eventClass, EventPriority.NORMAL, false, eventValue);
    }

    @Override
    public <E extends Event> Listener subscribe(
            Class<E> eventClass,
            EventPriority priority,
            boolean ignoreCancelled,
            EventValue<E> eventValue
    ) {
        Objects.requireNonNull(eventClass, "eventClass");
        Objects.requireNonNull(priority, "priority");
        Objects.requireNonNull(eventValue, "eventValue");

        Listener listener = new Listener() {
        };

        pluginManager.registerEvent(
                eventClass,
                listener,
                priority,
                (registeredListener, event) -> {
                    if (eventClass.isInstance(event)) {
                        eventValue.eventValue(eventClass.cast(event));
                    }
                },
                plugin,
                ignoreCancelled
        );

        return listener;
    }

    @Override
    public <E extends Event> Listener subscribeForEntity(
            String entityId,
            Class<E> eventClass,
            Function<E, Entity> entityExtractor,
            EventValue<E> eventValue
    ) {
        return subscribeForEntity(entityId, eventClass, entityExtractor, EventPriority.NORMAL, false, eventValue);
    }

    @Override
    public <E extends Event> Listener subscribeForEntity(
            String entityId,
            Class<E> eventClass,
            Function<E, Entity> entityExtractor,
            EventPriority priority,
            boolean ignoreCancelled,
            EventValue<E> eventValue
    ) {
        Objects.requireNonNull(entityId, "entityId");
        Objects.requireNonNull(entityExtractor, "entityExtractor");
        Objects.requireNonNull(eventValue, "eventValue");

        return subscribe(eventClass, priority, ignoreCancelled, event -> {
            Entity target = entityExtractor.apply(event);
            if (entityHelper.isTargetEntity(target, entityId)) {
                eventValue.eventValue(event);
            }
        });
    }

    public EntityEventRegistrar forEntity(String entityId) {
        return new EntityEventRegistrar(this, entityId);
    }
}
