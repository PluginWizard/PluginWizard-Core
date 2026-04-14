package net.kalbskinder.helpers.events;

import org.bukkit.event.Event;

@FunctionalInterface
public interface EventValue<E extends Event> {
    void eventValue(E event);
}
