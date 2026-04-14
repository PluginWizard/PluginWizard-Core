package net.kalbskinder.helpers.events;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public interface EventSubscription {
    <E extends Event> Listener subscribe(Class<E> eventClass, EventValue<E> eventValue);

    <E extends Event> Listener subscribe(
            Class<E> eventClass,
            EventPriority priority,
            boolean ignoreCancelled,
            EventValue<E> eventValue
    );
}
