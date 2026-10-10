package net.kalbskinder.helpers.regions;

import io.papermc.paper.event.player.PlayerOpenSignEvent;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static net.kalbskinder.helpers.regions.RegionManager.isDenied;
import static net.kalbskinder.helpers.regions.RegionManager.isGranted;

@RequiredArgsConstructor
public class RegionEvents implements Listener {
    private final RegionManager regionManager;
    private final Plugin plugin;
    private final Set<UUID> regeneratingPlayers = new HashSet<>();

    private boolean canRegenerate(Player player) {
        return isGranted(regionManager.getRegionsOf(player), RegionFlag.NATURAL_REGENERATION);
    }

    private double maxHealth(Player player) {
        return Objects.requireNonNull(player.getAttribute(Attribute.MAX_HEALTH)).getValue();
    }

    private void regenPlayer(Player player) {
        // only one regeneration task per player
        if (regeneratingPlayers.contains(player.getUniqueId())) return;
        if (!canRegenerate(player) || player.getHealth() >= maxHealth(player)) return;

        regeneratingPlayers.add(player.getUniqueId());
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline() || player.isDead() || !canRegenerate(player) || player.getHealth() >= maxHealth(player)) {
                    regeneratingPlayers.remove(player.getUniqueId());
                    this.cancel();
                    return;
                }
                player.setHealth(Math.min(player.getHealth() + 1, maxHealth(player)));
            }
        }.runTaskTimer(plugin, 80, 80);
    }

    private void updatePlayer(Player player, Location location) {
        if (regionManager.updatePlayer(player, location)) {
            regenPlayer(player);
        }
    }

    private void cancelInteractionEvents(Cancellable event, Player player) {
        if (player.isOp()) return; // ignore interactions for operators
        if (isDenied(regionManager.getRegionsOf(player), RegionFlag.BLOCK_INTERACTION)) {
            event.setCancelled(true);
        }
    }

    private void cancelExternalBlockEvents(Cancellable event, List<Region> blockRegions, Player player, RegionFlag flag) {
        if (player.isOp()) return; // ignore interactions for operators
        if (isDenied(blockRegions, flag)) {
            event.setCancelled(true);
        }
    }

    private List<Region> regionsAt(Block block) {
        return regionManager.getRegionsAt(block.getLocation());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!event.hasChangedBlock()) return;
        updatePlayer(event.getPlayer(), event.getTo());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        updatePlayer(event.getPlayer(), event.getTo());
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        updatePlayer(player, player.getLocation());
        regionManager.reapplyFlight(player); // changing worlds resets flight abilities
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        // the player is only at the respawn location after the event
        Bukkit.getScheduler().runTask(plugin, () -> {
            if (!player.isOnline()) return;
            updatePlayer(player, player.getLocation());
            regionManager.reapplyFlight(player);
        });
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        regionManager.removePlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        regionManager.removePlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        updatePlayer(player, player.getLocation());
    }

    @EventHandler
    public void onPlayerInteractBlock(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_AIR) return;
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) return;

        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null) return;

        List<Region> blockRegions = regionsAt(clickedBlock);
        if (!blockRegions.isEmpty()) {
            cancelExternalBlockEvents(event, blockRegions, event.getPlayer(), RegionFlag.BLOCK_INTERACTION);
        } else {
            cancelInteractionEvents(event, event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent event) {
        List<Region> blockRegions = regionsAt(event.getBlock());
        if (!blockRegions.isEmpty()) {
            cancelExternalBlockEvents(event, blockRegions, event.getPlayer(), RegionFlag.BLOCK_INTERACTION);
        } else {
            cancelInteractionEvents(event, event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerShearEntity(PlayerShearEntityEvent event) {
        cancelInteractionEvents(event, event.getPlayer());
    }

    @EventHandler
    public void onPlayerLeashEntity(PlayerLeashEntityEvent event) {
        cancelInteractionEvents(event, event.getPlayer());
    }

    @EventHandler
    public void onPlayerUnleashEntity(PlayerUnleashEntityEvent event) {
        cancelInteractionEvents(event, event.getPlayer());
    }

    @EventHandler
    public void onPlayerOpenSign(PlayerOpenSignEvent event) {
        cancelInteractionEvents(event, event.getPlayer());
        cancelExternalBlockEvents(event, regionsAt(event.getSign().getBlock()), event.getPlayer(), RegionFlag.BLOCK_INTERACTION);
    }

    @EventHandler
    public void onPlayerBucketFish(PlayerBucketEntityEvent event) {
        cancelInteractionEvents(event, event.getPlayer());
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        DamageType damageType = event.getDamageSource().getDamageType();
        List<Region> regions = regionManager.getRegionsOf(player);
        if (regions.isEmpty()) return;

        if (damageType == DamageType.FALL && isDenied(regions, RegionFlag.FALL_DAMAGE)) event.setCancelled(true);
        else if ((damageType == DamageType.ON_FIRE || damageType == DamageType.IN_FIRE) && isDenied(regions, RegionFlag.FIRE_DAMAGE)) event.setCancelled(true);
        else if (damageType == DamageType.IN_WALL && isDenied(regions, RegionFlag.SUFFOCATION_DAMAGE)) event.setCancelled(true);
        else if (damageType == DamageType.DROWN && isDenied(regions, RegionFlag.DROWNING_DAMAGE)) event.setCancelled(true);
        else if ((damageType == DamageType.MAGIC || damageType == DamageType.WITHER) && isDenied(regions, RegionFlag.POISON_WITHER_DAMAGE)) event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (isDenied(regionManager.getRegionsOf(event.getPlayer()), RegionFlag.DROP_ITEMS)) event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerAttemptPickupItemEvent event) {
        if (isDenied(regionManager.getRegionsOf(event.getPlayer()), RegionFlag.PICKUP_ITEMS)) event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        if (isDenied(regionManager.getRegionsOf(event.getPlayer()), RegionFlag.BREAK_BLOCKS)) event.setCancelled(true);
        cancelExternalBlockEvents(event, regionsAt(event.getBlock()), event.getPlayer(), RegionFlag.BREAK_BLOCKS);
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (player.isOp()) return;

        if (event.isFlying() && isDenied(regionManager.getRegionsOf(player), RegionFlag.FLIGHT)) {
            event.setCancelled(true);
            player.setAllowFlight(false);
            player.setFlying(false);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        List<Region> regions = regionManager.getRegionsOf(event.getEntity());
        if (regions.isEmpty()) return;

        boolean keepInventory = isGranted(regions, RegionFlag.KEEP_INVENTORY);
        event.setShowDeathMessages(!isDenied(regions, RegionFlag.KILL_DEATH_MESSAGES));
        event.setKeepInventory(keepInventory);
        if (isGranted(regions, RegionFlag.INSTANT_RESPAWN)) {
            Player player = event.getPlayer();
            event.setCancelled(true);
            Location respawnLocation = player.getRespawnLocation() != null ? player.getRespawnLocation() : player.getWorld().getSpawnLocation();
            if (!keepInventory) {
                // Drop all items at the player's location
                for (ItemStack item : player.getInventory().getContents()) {
                    if (item != null) {
                        player.getWorld().dropItemNaturally(player.getLocation(), item);
                    }
                }
                player.getInventory().clear();
                // Drop XP
                int xp = player.getTotalExperience();
                if (xp > 0) {
                    ExperienceOrb orb = (ExperienceOrb) player.getWorld().spawnEntity(player.getLocation(), EntityType.EXPERIENCE_ORB);
                    orb.setExperience(xp / 7);
                    player.setTotalExperience(0);
                    player.setExp(0);
                    player.setLevel(0);
                }
            }
            Bukkit.getScheduler().runTaskLater(plugin, () -> player.teleport(respawnLocation), 1L);
        }
    }

    @EventHandler
    public void onPlayerDamagePlayer(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player victim) || !(event.getDamager() instanceof Player damager)) return;

        if (isDenied(regionManager.getRegionsOf(victim), RegionFlag.PVP) ||
            isDenied(regionManager.getRegionsOf(damager), RegionFlag.PVP)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        // damage is applied after the event, so check the health on the next tick
        Bukkit.getScheduler().runTask(plugin, () -> {
            if (player.isOnline()) regenPlayer(player);
        });
    }

    @EventHandler
    public void onPlayerHunger(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (isDenied(regionManager.getRegionsOf(player), RegionFlag.HUNGER)) {
            event.setCancelled(true);
        }
    }
}
