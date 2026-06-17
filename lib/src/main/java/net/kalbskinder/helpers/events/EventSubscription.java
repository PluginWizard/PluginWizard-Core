package net.kalbskinder.helpers.events;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.entity.Entity;

import java.util.function.Function;

public interface EventSubscription {
    <E extends Event> Listener subscribe(Class<E> eventClass, EventValue<E> eventValue);

    <E extends Event> Listener subscribe(
            Class<E> eventClass,
            EventPriority priority,
            boolean ignoreCancelled,
            EventValue<E> eventValue
    );

    <E extends Event> Listener subscribeForEntity(
            String entityId,
            Class<E> eventClass,
            Function<E, Entity> entityExtractor,
            EventValue<E> eventValue
    );

    <E extends Event> Listener subscribeForEntity(
            String entityId,
            Class<E> eventClass,
            Function<E, Entity> entityExtractor,
            EventPriority priority,
            boolean ignoreCancelled,
            EventValue<E> eventValue
    );
}
