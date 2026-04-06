package net.kalbskinder.helpers.regions;

import io.papermc.paper.event.player.PlayerOpenSignEvent;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
public class RegionEvents implements Listener {
    private final RegionManager regionManager;
    private final Plugin plugin;
    private final Set<Player> regeneratingPlayers = new HashSet<>();

    private void regenPlayer(Player player) {
        Region region = currentRegion(player);
        if (region == null || !region.getFlags().contains(RegionFlag.NATURAL_REGENERATION)) {
            regeneratingPlayers.remove(player);
            return;
        }

        if (player.getHealth() < Objects.requireNonNull(player.getAttribute(Attribute.MAX_HEALTH)).getValue()) {
            regeneratingPlayers.add(player);
            scheduleRegainHealth(player);
        }
    }

    private void scheduleRegainHealth(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isOnline() && !player.isDead()) {
                    double currentHealth = player.getHealth();
                    double maxHealth = Objects.requireNonNull(player.getAttribute(Attribute.MAX_HEALTH)).getValue();
                    if (currentHealth < maxHealth) {
                        double newHealth = Math.min(currentHealth + 1, maxHealth);
                        player.setHealth(newHealth);
                    } else {
                        regeneratingPlayers.remove(player);
                        this.cancel();
                    }
                } else {
                    regeneratingPlayers.remove(player);
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 80, 80);
    }

    private boolean isInRegionArea(Player player, Region region) {
        Location playerLocation = player.getLocation();
        Location loc1 = region.getLoc1();
        Location loc2 = region.getLoc2();

        double minX = Math.min(loc1.getX(), loc2.getX());
        double maxX = Math.max(loc1.getX(), loc2.getX());
        double minY = Math.min(loc1.getY(), loc2.getY());
        double maxY = Math.max(loc1.getY(), loc2.getY());
        double minZ = Math.min(loc1.getZ(), loc2.getZ());
        double maxZ = Math.max(loc1.getZ(), loc2.getZ());

        return playerLocation.getX() >= minX && playerLocation.getX() <= maxX &&
               playerLocation.getY() >= minY && playerLocation.getY() <= maxY &&
               playerLocation.getZ() >= minZ && playerLocation.getZ() <= maxZ;
    }

    private Region currentRegion(Player player) {
        for (Region region : regionManager.getRegions().values()) {
            if (region.containsPlayer(player.getUniqueId())) {
                return region;
            }
        }
        return null;
    }

    private void removePlayerFromRegion(Player player) {
        Region currentRegion = currentRegion(player);
        if (currentRegion != null) {
            if (currentRegion.getFlags().contains(RegionFlag.FLIGHT) && !player.isOp()) {
                player.setAllowFlight(false);
                player.setFlying(false);
            } else if (currentRegion.getFlags().contains(RegionFlag.FLIGHT) && player.isOp() &&
                    (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)) {
                player.setAllowFlight(false);
                player.setFlying(false);
            }
            currentRegion.removePlayer(player.getUniqueId());
            currentRegion.playerLeft(player);
        }
    }

    private void cancelInteractionEvents(Cancellable event, Player player) {
        if (player.isOp()) return; // ignore interactions for operators
        Region currentRegion = currentRegion(player);
        if (currentRegion != null && !currentRegion.getFlags().contains(RegionFlag.BLOCK_INTERACTION)) {
            event.setCancelled(true);
        }
    }

    private void cancelExternalBlockEvents(Cancellable event, Region region, Player player) {
        if (player.isOp()) return; // ignore interactions for operators
        if (region != null && !region.getFlags().contains(RegionFlag.BLOCK_INTERACTION)) {
            event.setCancelled(true);
        }
    }

    private Region isBlockInRegion(Block block) {
        Location blockLocation = block.getLocation();
        for (Region region : regionManager.getRegions().values()) {
            Location loc1 = region.getLoc1();
            Location loc2 = region.getLoc2();

            double minX = Math.min(loc1.getX(), loc2.getX());
            double maxX = Math.max(loc1.getX(), loc2.getX());
            double minY = Math.min(loc1.getY(), loc2.getY());
            double maxY = Math.max(loc1.getY(), loc2.getY());
            double minZ = Math.min(loc1.getZ(), loc2.getZ());
            double maxZ = Math.max(loc1.getZ(), loc2.getZ());

            if (blockLocation.getX() >= minX && blockLocation.getX() <= maxX &&
                blockLocation.getY() >= minY && blockLocation.getY() <= maxY &&
                blockLocation.getZ() >= minZ && blockLocation.getZ() <= maxZ) {
                return region;
            }
        }
        return null;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Region currentRegion = currentRegion(player);
        if (currentRegion != null && !isInRegionArea(player, currentRegion)) {
            removePlayerFromRegion(player);
        } else if (currentRegion == null) {
            for (Region region : regionManager.getRegions().values()) {
                if (isInRegionArea(player, region)) {
                    region.addPlayer(player);
                    region.playerEntered(player);
                    regenPlayer(player);
                    if (region.getFlags().contains(RegionFlag.FLIGHT)) {
                        player.setAllowFlight(true);
                    }
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        removePlayerFromRegion(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        removePlayerFromRegion(event.getPlayer());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        for (Region region : regionManager.getRegions().values()) {
            if (isInRegionArea(player, region)) {
                region.addPlayer(player);
                region.playerEntered(player);
                if (region.getFlags().contains(RegionFlag.FLIGHT)) {
                    player.setAllowFlight(true);
                }
                break;
            }
        }
    }

    @EventHandler
    public void onPlayerInteractBlock(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_AIR) return;
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) return;

        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null) return;

        Region blockRegion = isBlockInRegion(clickedBlock);
        if (blockRegion != null) {
            cancelExternalBlockEvents(event, blockRegion, event.getPlayer());
        } else {
            cancelInteractionEvents(event, event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent event) {
        if (isBlockInRegion(event.getBlock()) != null) {
            cancelExternalBlockEvents(event, isBlockInRegion(event.getBlock()), event.getPlayer());
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
        if (isBlockInRegion(event.getSign().getBlock()) != null) {
            cancelExternalBlockEvents(event, isBlockInRegion(event.getSign().getBlock()), event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerBucketFish(PlayerBucketEntityEvent event) {
        cancelInteractionEvents(event, event.getPlayer());
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        DamageType damageType = event.getDamageSource().getDamageType();
        Region currentRegion = currentRegion(player);
        if (currentRegion == null) return;

        if (damageType == DamageType.FALL && !currentRegion.getFlags().contains(RegionFlag.FALL_DAMAGE)) event.setCancelled(true);
        else if ((damageType == DamageType.ON_FIRE || damageType == DamageType.IN_FIRE) && !currentRegion.getFlags().contains(RegionFlag.FIRE_DAMAGE)) event.setCancelled(true);
        else if (damageType == DamageType.IN_WALL && !currentRegion.getFlags().contains(RegionFlag.SUFFOCATION_DAMAGE)) event.setCancelled(true);
        else if (damageType == DamageType.DROWN && !currentRegion.getFlags().contains(RegionFlag.DROWNING_DAMAGE)) event.setCancelled(true);
        else if ((damageType == DamageType.MAGIC || damageType == DamageType.WITHER) && !currentRegion.getFlags().contains(RegionFlag.POISON_WITHER_DAMAGE)) event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Region region = currentRegion(event.getPlayer());
        if (region != null && !region.getFlags().contains(RegionFlag.DROP_ITEMS)) event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerAttemptPickupItemEvent event) {
        Region region = currentRegion(event.getPlayer());
        if (region != null && !region.getFlags().contains(RegionFlag.PICKUP_ITEMS)) event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        Region region = currentRegion(event.getPlayer());
        if (region != null && !region.getFlags().contains(RegionFlag.BREAK_BLOCKS)) event.setCancelled(true);
        if (isBlockInRegion(event.getBlock()) != null) {
            cancelExternalBlockEvents(event, isBlockInRegion(event.getBlock()), event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        Region region = currentRegion(player);
        if (player.isOp()) return;

        if (region != null && !region.getFlags().contains(RegionFlag.FLIGHT) && event.isFlying()) {
            event.setCancelled(true);
            player.setAllowFlight(false);
            player.setFlying(false);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Region region = currentRegion(event.getEntity());
        if (region != null) {
            boolean keepInventory = region.getFlags().contains(RegionFlag.KEEP_INVENTORY);
            event.setShowDeathMessages(region.getFlags().contains(RegionFlag.KILL_DEATH_MESSAGES));
            event.setKeepInventory(keepInventory);
            if (region.getFlags().contains(RegionFlag.INSTANT_RESPAWN)) {
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
    }

    @EventHandler
    public void onPlayerDamagePlayer(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player victim) || !(event.getDamager() instanceof Player damager)) return;
        Region victimRegion = currentRegion(victim);
        Region damagerRegion = currentRegion(damager);

        if ((victimRegion != null && !victimRegion.getFlags().contains(RegionFlag.PVP)) ||
            (damagerRegion != null && !damagerRegion.getFlags().contains(RegionFlag.PVP))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        Region region = currentRegion(player);
        if (region == null) return;
        if (region.getFlags().contains(RegionFlag.NATURAL_REGENERATION)) {
            regenPlayer(player);
        }
    }

    @EventHandler
    public void onPlayerHunger(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        Region region = currentRegion(player);
        if (region != null && !region.getFlags().contains(RegionFlag.HUNGER)) {
            event.setCancelled(true);
        }
    }
}
