package net.kalbskinder.helpers;

import lombok.RequiredArgsConstructor;
import net.kalbskinder.helpers.actions.*;
import net.kalbskinder.helpers.chat.MiniMessageHelper;
import net.kalbskinder.helpers.items.ItemHelper;
import net.kalbskinder.helpers.location.LocationHelper;
import net.kalbskinder.helpers.regions.RegionHelper;
import net.kalbskinder.helpers.regions.RegionManager;

@RequiredArgsConstructor
public class Helpers {
    private final RegionManager regionManager;

    // actions
    public static MessageHelper messageHelper;
    public static PlayerItemHelper playerItemHelper;
    public static SoundHelper soundHelper;
    public static TeleportHelper teleportHelper;
    public static TitleHelper titleHelper;
    public static LocationHelper locationHelper;
    public static MiniMessageHelper miniMessageHelper;

    // items
    public static ItemHelper itemHelper;

    // regions
    public static RegionHelper regionHelper;

    public void initialize() {
        miniMessageHelper = new MiniMessageHelper();
        messageHelper = new MessageHelper(miniMessageHelper);
        playerItemHelper = new PlayerItemHelper();
        soundHelper = new SoundHelper();
        teleportHelper = new TeleportHelper();
        titleHelper = new TitleHelper(miniMessageHelper);
        itemHelper = new ItemHelper(miniMessageHelper);
        regionHelper = new RegionHelper(regionManager);
        locationHelper = new LocationHelper();
    }
}
