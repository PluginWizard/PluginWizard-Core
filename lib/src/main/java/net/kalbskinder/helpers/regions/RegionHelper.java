package net.kalbskinder.helpers.regions;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class RegionHelper {
    private final RegionManager regionManager;

    public boolean isPlayerInRegion(Player player, Region region) {
        return region.containsPlayer(player.getUniqueId());
    }

    public boolean isPlayerInRegion(Player player, String regionName) {
        Map<UUID, Region> regions = regionManager.getRegions();
        for (Region region : regions.values()) {
            if (region.getName().equalsIgnoreCase(regionName)) {
                return region.containsPlayer(player.getUniqueId());
            }
        }
        return false;
    }

    public void addRegion(Region region) {
        regionManager.addRegion(region);
    }

    public void addRegionFlag(Region region, RegionFlag flag) {
        region.addFlag(flag);
        regionManager.addRegion(region);
    }

    public void removeRegionFlag(Region region, RegionFlag flag) {
        region.removeFlag(flag);
        regionManager.addRegion(region);
    }
}
