package net.kalbskinder.helpers.actions;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class SoundHelper {
    /**
     * Plays a sound to a player at the player's current location.
     *
     * @param player The player to play the sound to (may not be null)
     * @param sound  The sound to play (may not be null)
     * @param volume The volume of the sound
     * @param pitch  The pitch of the sound
     */
    public void playSound(Player player, Sound sound, float volume, float pitch) {
        if (player == null || sound == null) return;
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    /**
     * Plays a sound at a specific location.
     *
     * @param location The location to play the sound at (may not be null)
     * @param sound    The sound to play (may not be null)
     * @param volume   The volume of the sound
     * @param pitch    The pitch of the sound
     */
    public void playSound(Location location, Sound sound, float volume, float pitch) {
        if (location == null || sound == null) return;
        World world = location.getWorld();
        if (world == null) return;
        world.playSound(location, sound, volume, pitch);
    }
}
