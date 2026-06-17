package net.kalbskinder.helpers;

import lombok.NoArgsConstructor;
import net.kalbskinder.helpers.actions.*;
import net.kalbskinder.helpers.chat.MiniMessageHelper;
import net.kalbskinder.helpers.enitty.EntityHelper;
import net.kalbskinder.helpers.events.EventHelper;
import net.kalbskinder.helpers.items.ItemHelper;
import net.kalbskinder.helpers.location.LocationHelper;
import net.kalbskinder.helpers.math.MathHelper;
import net.kalbskinder.helpers.regions.RegionHelper;
import net.kalbskinder.helpers.regions.RegionManager;
import org.bukkit.plugin.Plugin;

@NoArgsConstructor
public class Helpers {

    // actions
    public static MessageHelper messageHelper;
    public static PlayerItemHelper playerItemHelper;
    public static SoundHelper soundHelper;
    public static TeleportHelper teleportHelper;
    public static TitleHelper titleHelper;
    public static LocationHelper locationHelper;
    public static MiniMessageHelper miniMessageHelper;
    public static EventHelper eventHelper;
    public static EntityHelper entityHelper;
    public static ItemHelper itemHelper;
    public static RegionHelper regionHelper;

    // other
    public static MathHelper mathHelper;

    public static void initialize(Plugin plugin) {
        RegionManager regionManager = new RegionManager();
        miniMessageHelper = new MiniMessageHelper();
        messageHelper = new MessageHelper(miniMessageHelper);
        playerItemHelper = new PlayerItemHelper();
        soundHelper = new SoundHelper();
        teleportHelper = new TeleportHelper();
        titleHelper = new TitleHelper(miniMessageHelper);
        itemHelper = new ItemHelper(miniMessageHelper);
        regionHelper = new RegionHelper(regionManager);
        locationHelper = new LocationHelper();
        eventHelper = new EventHelper(plugin);
        entityHelper = new EntityHelper();

        mathHelper = new MathHelper();
    }
}
