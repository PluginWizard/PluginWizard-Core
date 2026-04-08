package net.kalbskinder.helpers.sprites.types;

public enum ShulkerBoxSprite {
    SHULKER("entity/shulker/shulker"),
    SHULKER_BLACK("entity/shulker/shulker_black"),
    SHULKER_BLUE("entity/shulker/shulker_blue"),
    SHULKER_BROWN("entity/shulker/shulker_brown"),
    SHULKER_CYAN("entity/shulker/shulker_cyan"),
    SHULKER_GRAY("entity/shulker/shulker_gray"),
    SHULKER_GREEN("entity/shulker/shulker_green"),
    SHULKER_LIGHT_BLUE("entity/shulker/shulker_light_blue"),
    SHULKER_LIGHT_GRAY("entity/shulker/shulker_light_gray"),
    SHULKER_LIME("entity/shulker/shulker_lime"),
    SHULKER_MAGENTA("entity/shulker/shulker_magenta"),
    SHULKER_ORANGE("entity/shulker/shulker_orange"),
    SHULKER_PINK("entity/shulker/shulker_pink"),
    SHULKER_PURPLE("entity/shulker/shulker_purple"),
    SHULKER_RED("entity/shulker/shulker_red"),
    SHULKER_WHITE("entity/shulker/shulker_white"),
    SHULKER_YELLOW("entity/shulker/shulker_yellow"),
    SPARK("entity/shulker/spark");

    private final String texture;

    ShulkerBoxSprite(String texture) {
        this.texture = texture;
    }

    public String texture() {
        return texture;
    }

    public String atlas() {
        return "minecraft:shulker_boxes";
    }
}
