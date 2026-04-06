package net.kalbskinder.helpers.regions;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegionManager {
    @Getter private final Map<UUID, Region> regions = new HashMap<>();

    public void addRegion(Region region) {
        this.regions.put(region.getUuid(), region);
    }

    public void removeRegion(UUID regionUuid) {
        this.regions.remove(regionUuid);
    }
}
