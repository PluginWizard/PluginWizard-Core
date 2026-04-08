package net.kalbskinder.helpers.sprites.types;

public enum ChestSprite {
    CHRISTMAS("entity/chest/christmas"),
    CHRISTMAS_LEFT("entity/chest/christmas_left"),
    CHRISTMAS_RIGHT("entity/chest/christmas_right"),
    COPPER("entity/chest/copper"),
    COPPER_EXPOSED("entity/chest/copper_exposed"),
    COPPER_EXPOSED_LEFT("entity/chest/copper_exposed_left"),
    COPPER_EXPOSED_RIGHT("entity/chest/copper_exposed_right"),
    COPPER_LEFT("entity/chest/copper_left"),
    COPPER_OXIDIZED("entity/chest/copper_oxidized"),
    COPPER_OXIDIZED_LEFT("entity/chest/copper_oxidized_left"),
    COPPER_OXIDIZED_RIGHT("entity/chest/copper_oxidized_right"),
    COPPER_RIGHT("entity/chest/copper_right"),
    COPPER_WEATHERED("entity/chest/copper_weathered"),
    COPPER_WEATHERED_LEFT("entity/chest/copper_weathered_left"),
    COPPER_WEATHERED_RIGHT("entity/chest/copper_weathered_right"),
    ENDER("entity/chest/ender"),
    NORMAL("entity/chest/normal"),
    NORMAL_LEFT("entity/chest/normal_left"),
    NORMAL_RIGHT("entity/chest/normal_right"),
    TRAPPED("entity/chest/trapped"),
    TRAPPED_LEFT("entity/chest/trapped_left"),
    TRAPPED_RIGHT("entity/chest/trapped_right");

    private final String texture;

    ChestSprite(String texture) {
        this.texture = texture;
    }

    public String texture() {
        return texture;
    }

    public String atlas() {
        return "minecraft:chests";
    }
}
