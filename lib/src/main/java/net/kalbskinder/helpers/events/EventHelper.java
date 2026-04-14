package net.kalbskinder.helpers.events;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.Objects;

public class EventHelper implements EventSubscription {
    private final Plugin plugin;
    private final PluginManager pluginManager;

    public EventHelper(Plugin plugin) {
        this.plugin = Objects.requireNonNull(plugin, "plugin");
        this.pluginManager = plugin.getServer().getPluginManager();
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
}
