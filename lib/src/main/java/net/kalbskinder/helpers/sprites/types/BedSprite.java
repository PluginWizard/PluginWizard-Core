package net.kalbskinder.helpers.sprites.types;

public enum BedSprite {
    BLACK("entity/bed/black"),
    BLUE("entity/bed/blue"),
    BROWN("entity/bed/brown"),
    CYAN("entity/bed/cyan"),
    GRAY("entity/bed/gray"),
    GREEN("entity/bed/green"),
    LIGHT_BLUE("entity/bed/light_blue"),
    LIGHT_GRAY("entity/bed/light_gray"),
    LIME("entity/bed/lime"),
    MAGENTA("entity/bed/magenta"),
    ORANGE("entity/bed/orange"),
    PINK("entity/bed/pink"),
    PURPLE("entity/bed/purple"),
    RED("entity/bed/red"),
    WHITE("entity/bed/white"),
    YELLOW("entity/bed/yellow");

    private final String texture;

    BedSprite(String texture) {
        this.texture = texture;
    }

    public String texture() {
        return texture;
    }

    public String atlas() {
        return "minecraft:beds";
    }
}
