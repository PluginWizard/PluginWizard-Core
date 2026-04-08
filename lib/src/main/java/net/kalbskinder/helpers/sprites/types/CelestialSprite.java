package net.kalbskinder.helpers.sprites.types;

public enum CelestialSprite {
    END_FLASH("end_flash"),
    FIRST_QUARTER("first_quarter"),
    FULL_MOON("full_moon"),
    NEW_MOON("new_moon"),
    THIRD_QUARTER("third_quarter"),
    WANING_CRESCENT("waning_crescent"),
    WANING_GIBBOUS("waning_gibbous"),
    WAXING_CRESCENT("waxing_crescent"),
    WAXING_GIBBOUS("waxing_gibbous"),
    SUN("sun");

    private final String texture;

    CelestialSprite(String texture) {
        this.texture = texture;
    }

    public String texture() {
        return texture;
    }

    public String atlas() {
        return "minecraft:celestials";
    }
}
