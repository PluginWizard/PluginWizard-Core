package net.kalbskinder.helpers.regions;

import lombok.Getter;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class RegionManager {
    @Getter private final Map<UUID, Region> regions = new HashMap<>();

    public void addRegion(Region region) {
        this.regions.put(region.getUuid(), region);
    }

    /** Removes the region and fires its leave event for every player still inside it. */
    public void removeRegion(UUID regionUuid) {
        Region region = regions.get(regionUuid);
        if (region == null) return;

        for (Player player : new ArrayList<>(region.getPlayers().values())) {
            boolean hadFlight = isGranted(getRegionsOf(player), RegionFlag.FLIGHT);
            region.removePlayer(player.getUniqueId());
            region.playerLeft(player);
            updateFlight(player, hadFlight);
        }
        regions.remove(regionUuid);
    }

    /** All regions containing the location. */
    public List<Region> getRegionsAt(Location location) {
        List<Region> result = new ArrayList<>();
        for (Region region : regions.values()) {
            if (region.contains(location)) result.add(region);
        }
        return result;
    }

    /** All regions the player is currently tracked in. */
    public List<Region> getRegionsOf(Player player) {
        List<Region> result = new ArrayList<>();
        for (Region region : regions.values()) {
            if (region.containsPlayer(player.getUniqueId())) result.add(region);
        }
        return result;
    }

    /** True if any of the regions lacks the flag (most restrictive region wins). */
    static boolean isDenied(List<Region> regions, RegionFlag flag) {
        for (Region region : regions) {
            if (!region.getFlags().contains(flag)) return true;
        }
        return false;
    }

    /** True if there is at least one region and all of them have the flag. */
    static boolean isGranted(List<Region> regions, RegionFlag flag) {
        return !regions.isEmpty() && !isDenied(regions, flag);
    }

    /**
     * Syncs the player's region membership with the given location, firing leave events
     * before enter events.
     *
     * @return whether the player entered or left any region
     */
    boolean updatePlayer(Player player, Location location) {
        UUID playerUuid = player.getUniqueId();
        boolean hadFlight = isGranted(getRegionsOf(player), RegionFlag.FLIGHT);
        List<Region> entered = new ArrayList<>();
        List<Region> left = new ArrayList<>();

        for (Region region : regions.values()) {
            boolean inside = region.contains(location);
            boolean tracked = region.containsPlayer(playerUuid);
            if (inside && !tracked) entered.add(region);
            else if (!inside && tracked) left.add(region);
        }
        if (entered.isEmpty() && left.isEmpty()) return false;

        for (Region region : left) {
            region.removePlayer(playerUuid);
            region.playerLeft(player);
        }
        for (Region region : entered) {
            region.addPlayer(player);
            region.playerEntered(player);
        }
        updateFlight(player, hadFlight);
        return true;
    }

    /** Removes the player from every region they are in, firing leave events. */
    void removePlayer(Player player) {
        boolean hadFlight = isGranted(getRegionsOf(player), RegionFlag.FLIGHT);
        for (Region region : getRegionsOf(player)) {
            region.removePlayer(player.getUniqueId());
            region.playerLeft(player);
        }
        updateFlight(player, hadFlight);
    }

    /** Re-applies region flight, e.g. after the server reset the player's abilities. */
    void reapplyFlight(Player player) {
        if (isGranted(getRegionsOf(player), RegionFlag.FLIGHT)) player.setAllowFlight(true);
    }

    private void updateFlight(Player player, boolean hadFlight) {
        boolean hasFlight = isGranted(getRegionsOf(player), RegionFlag.FLIGHT);
        if (hadFlight == hasFlight) return;

        if (hasFlight) {
            player.setAllowFlight(true);
        } else if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
            player.setFlying(false);
            player.setAllowFlight(false);
        }
    }
}
