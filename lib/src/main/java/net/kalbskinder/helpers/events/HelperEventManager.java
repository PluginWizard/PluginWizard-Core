package net.kalbskinder.helpers.events;

import net.kalbskinder.helpers.regions.RegionEvents;
import net.kalbskinder.helpers.regions.RegionManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.Set;

public record HelperEventManager(RegionManager regionManager) {
    public Set<Listener> createListeners(Plugin plugin) {
        return Set.of(new RegionEvents(regionManager, plugin));
    }

    public void registerAll(Plugin plugin) {
        createListeners(plugin).forEach(listener ->
                plugin.getServer().getPluginManager().registerEvents(listener, plugin)
        );
    }
}
