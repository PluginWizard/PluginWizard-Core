package net.kalbskinder.helpers.sprites.types;

public enum SignSprite {
    SIGNS_ACACIA("entity/signs/acacia"),
    SIGNS_BAMBOO("entity/signs/bamboo"),
    SIGNS_BIRCH("entity/signs/birch"),
    SIGNS_CHERRY("entity/signs/cherry"),
    SIGNS_CRIMSON("entity/signs/crimson"),
    SIGNS_DARK_OAK("entity/signs/dark_oak"),
    SIGNS_HANGING_ACACIA("entity/signs/hanging/acacia"),
    SIGNS_HANGING_BAMBOO("entity/signs/hanging/bamboo"),
    SIGNS_HANGING_BIRCH("entity/signs/hanging/birch"),
    SIGNS_HANGING_CHERRY("entity/signs/hanging/cherry"),
    SIGNS_HANGING_CRIMSON("entity/signs/hanging/crimson"),
    SIGNS_HANGING_DARK_OAK("entity/signs/hanging/dark_oak"),
    SIGNS_HANGING_JUNGLE("entity/signs/hanging/jungle"),
    SIGNS_HANGING_MANGROVE("entity/signs/hanging/mangrove"),
    SIGNS_HANGING_OAK("entity/signs/hanging/oak"),
    SIGNS_HANGING_PALE_OAK("entity/signs/hanging/pale_oak"),
    SIGNS_HANGING_SPRUCE("entity/signs/hanging/spruce"),
    SIGNS_HANGING_WARPED("entity/signs/hanging/warped"),
    SIGNS_JUNGLE("entity/signs/jungle"),
    SIGNS_MANGROVE("entity/signs/mangrove"),
    SIGNS_OAK("entity/signs/oak"),
    SIGNS_PALE_OAK("entity/signs/pale_oak"),
    SIGNS_SPRUCE("entity/signs/spruce"),
    SIGNS_WARPED("entity/signs/warped");

    private final String texture;

    SignSprite(String texture) {
        this.texture = texture;
    }

    public String texture() {
        return texture;
    }

    public String atlas() {
        return "minecraft:signs";
    }
}
