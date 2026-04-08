package net.kalbskinder.helpers.sprites.types;

public enum BannerSprite {
    BASE("entity/banner/base"),
    BORDER("entity/banner/border"),
    BRICKS("entity/banner/bricks"),
    CIRCLE("entity/banner/circle"),
    CREEPER("entity/banner/creeper"),
    CROSS("entity/banner/cross"),
    CURLY_BORDER("entity/banner/curly_border"),
    DIAGONAL_LEFT("entity/banner/diagonal_left"),
    DIAGONAL_RIGHT("entity/banner/diagonal_right"),
    DIAGONAL_UP_LEFT("entity/banner/diagonal_up_left"),
    DIAGONAL_UP_RIGHT("entity/banner/diagonal_up_right"),
    FLOW("entity/banner/flow"),
    FLOWER("entity/banner/flower"),
    GLOBE("entity/banner/globe"),
    GRADIENT("entity/banner/gradient"),
    GRADIENT_UP("entity/banner/gradient_up"),
    GUSTER("entity/banner/guster"),
    HALF_HORIZONTAL("entity/banner/half_horizontal"),
    HALF_HORIZONTAL_BOTTOM("entity/banner/half_horizontal_bottom"),
    HALF_VERTICAL("entity/banner/half_vertical"),
    HALF_VERTICAL_RIGHT("entity/banner/half_vertical_right"),
    MOJANG("entity/banner/mojang"),
    PIGLIN("entity/banner/piglin"),
    RHOMBUS("entity/banner/rhombus"),
    SKULL("entity/banner/skull"),
    SMALL_STRIPES("entity/banner/small_stripes"),
    SQUARE_BOTTOM_LEFT("entity/banner/square_bottom_left"),
    SQUARE_BOTTOM_RIGHT("entity/banner/square_bottom_right"),
    SQUARE_TOP_LEFT("entity/banner/square_top_left"),
    SQUARE_TOP_RIGHT("entity/banner/square_top_right"),
    STRAIGHT_CROSS("entity/banner/straight_cross"),
    STRIPE_BOTTOM("entity/banner/stripe_bottom"),
    STRIPE_CENTER("entity/banner/stripe_center"),
    STRIPE_DOWNLEFT("entity/banner/stripe_downleft"),
    STRIPE_DOWNRIGHT("entity/banner/stripe_downright"),
    STRIPE_LEFT("entity/banner/stripe_left"),
    STRIPE_MIDDLE("entity/banner/stripe_middle"),
    STRIPE_RIGHT("entity/banner/stripe_right"),
    STRIPE_TOP("entity/banner/stripe_top"),
    TRIANGLE_BOTTOM("entity/banner/triangle_bottom"),
    TRIANGLE_TOP("entity/banner/triangle_top"),
    TRIANGLES_BOTTOM("entity/banner/triangles_bottom");

    private final String texture;

    BannerSprite(String texture) {
        this.texture = texture;
    }

    public String texture() {
        return texture;
    }

    public String atlas() {
        return "minecraft:banner_patterns";
    }
}
