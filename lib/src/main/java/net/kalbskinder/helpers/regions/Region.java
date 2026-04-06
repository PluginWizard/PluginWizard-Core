package net.kalbskinder.helpers.regions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class Region {
    @Getter private final UUID uuid;
    @Getter private final String name;
    @Getter private final Location loc1;
    @Getter private final Location loc2;
    @Getter private final Map<UUID, Player> players = new HashMap<>(); // List of player UUIDs who are currently inside the region

    private Consumer<RegionEvent> onEnter;
    private Consumer<RegionEvent> onLeave;

    @Getter
    @Setter
    private Set<RegionFlag> flags = new HashSet<>(Set.of(
            RegionFlag.PVP,
            RegionFlag.BREAK_BLOCKS,
            RegionFlag.DROP_ITEMS,
            RegionFlag.PICKUP_ITEMS,
            RegionFlag.FIRE_DAMAGE,
            RegionFlag.FALL_DAMAGE,
            RegionFlag.SUFFOCATION_DAMAGE,
            RegionFlag.DROWNING_DAMAGE,
            RegionFlag.POISON_WITHER_DAMAGE,
            RegionFlag.HUNGER,
            RegionFlag.BLOCK_INTERACTION,
            RegionFlag.KILL_DEATH_MESSAGES
    )); // Default flags for a region


    public boolean containsPlayer(UUID playerUuid) {
        return players.containsKey(playerUuid);
    }

    public void addPlayer(Player player) {
        if (!players.containsKey(player.getUniqueId())) {
            players.put(player.getUniqueId(), player);
        }
    }

    public void removePlayer(UUID playerUuid) {
        players.remove(playerUuid);
    }

    public void removeFlag(RegionFlag flag) {
        this.flags.remove(flag);
    }

    public void addFlag(RegionFlag flag) {
        this.flags.add(flag);
    }

    public void onRegionEnter(Consumer<RegionEvent> onEnter) {
        this.onEnter = onEnter;
    }

    public void onRegionLeave(Consumer<RegionEvent> onLeave) {
        this.onLeave = onLeave;
    }

    public void playerEntered(Player player) {
        if (onEnter != null) {
            onEnter.accept(new RegionEvent(player, this));
        }
    }

    public void playerLeft(Player player) {
        if (onLeave != null) {
            onLeave.accept(new RegionEvent(player, this));
        }
    }
}
