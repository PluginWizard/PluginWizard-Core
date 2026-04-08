package net.kalbskinder.helpers.sprites.types;

public enum MapDecorationSprite {
    BLACK_BANNER("black_banner"),
    BLUE_BANNER("blue_banner"),
    BLUE_MARKER("blue_marker"),
    BROWN_BANNER("brown_banner"),
    CYAN_BANNER("cyan_banner"),
    DESERT_VILLAGE("desert_village"),
    FRAME("frame"),
    GRAY_BANNER("gray_banner"),
    GREEN_BANNER("green_banner"),
    JUNGLE_TEMPLE("jungle_temple"),
    LIGHT_BLUE_BANNER("light_blue_banner"),
    LIGHT_GRAY_BANNER("light_gray_banner"),
    LIME_BANNER("lime_banner"),
    MAGENTA_BANNER("magenta_banner"),
    OCEAN_MONUMENT("ocean_monument"),
    ORANGE_BANNER("orange_banner"),
    PINK_BANNER("pink_banner"),
    PLAINS_VILLAGE("plains_village"),
    PLAYER("player"),
    PLAYER_OFF_LIMITS("player_off_limits"),
    PLAYER_OFF_MAP("player_off_map"),
    PURPLE_BANNER("purple_banner"),
    RED_BANNER("red_banner"),
    RED_MARKER("red_marker"),
    RED_X("red_x"),
    SAVANNA_VILLAGE("savanna_village"),
    SNOWY_VILLAGE("snowy_village"),
    SWAMP_HUT("swamp_hut"),
    TAIGA_VILLAGE("taiga_village"),
    TARGET_POINT("target_point"),
    TARGET_X("target_x"),
    TRIAL_CHAMBERS("trial_chambers"),
    WHITE_BANNER("white_banner"),
    WOODLAND_MANSION("woodland_mansion"),
    YELLOW_BANNER("yellow_banner");

    private final String texture;

    MapDecorationSprite(String texture) {
        this.texture = texture;
    }

    public String texture() {
        return texture;
    }

    public String atlas() {
        return "minecraft:map_decorations";
    }
}
