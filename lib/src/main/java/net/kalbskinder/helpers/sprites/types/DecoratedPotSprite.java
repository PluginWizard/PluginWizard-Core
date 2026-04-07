package net.kalbskinder.helpers.sprites.types;

public enum DecoratedPotSprite {
    ANGLER_POTTERY_PATTERN("entity/decorated_pot/angler_pottery_pattern"),
    ARCHER_POTTERY_PATTERN("entity/decorated_pot/archer_pottery_pattern"),
    ARMS_UP_POTTERY_PATTERN("entity/decorated_pot/arms_up_pottery_pattern"),
    BLADE_POTTERY_PATTERN("entity/decorated_pot/blade_pottery_pattern"),
    BREWER_POTTERY_PATTERN("entity/decorated_pot/brewer_pottery_pattern"),
    BURN_POTTERY_PATTERN("entity/decorated_pot/burn_pottery_pattern"),
    DANGER_POTTERY_PATTERN("entity/decorated_pot/danger_pottery_pattern"),
    DECORATED_POT_BASE("entity/decorated_pot/decorated_pot_base"),
    DECORATED_POT_SIDE("entity/decorated_pot/decorated_pot_side"),
    EXPLORER_POTTERY_PATTERN("entity/decorated_pot/explorer_pottery_pattern"),
    FLOW_POTTERY_PATTERN("entity/decorated_pot/flow_pottery_pattern"),
    FRIEND_POTTERY_PATTERN("entity/decorated_pot/friend_pottery_pattern"),
    GUSTER_POTTERY_PATTERN("entity/decorated_pot/guster_pottery_pattern"),
    HEART_POTTERY_PATTERN("entity/decorated_pot/heart_pottery_pattern"),
    HEARTBREAK_POTTERY_PATTERN("entity/decorated_pot/heartbreak_pottery_pattern"),
    HOWL_POTTERY_PATTERN("entity/decorated_pot/howl_pottery_pattern"),
    MINER_POTTERY_PATTERN("entity/decorated_pot/miner_pottery_pattern"),
    MOURNER_POTTERY_PATTERN("entity/decorated_pot/mourner_pottery_pattern"),
    PLENTY_POTTERY_PATTERN("entity/decorated_pot/plenty_pottery_pattern"),
    PRIZE_POTTERY_PATTERN("entity/decorated_pot/prize_pottery_pattern"),
    SCRAPE_POTTERY_PATTERN("entity/decorated_pot/scrape_pottery_pattern"),
    SHEAF_POTTERY_PATTERN("entity/decorated_pot/sheaf_pottery_pattern"),
    SHELTER_POTTERY_PATTERN("entity/decorated_pot/shelter_pottery_pattern"),
    SKULL_POTTERY_PATTERN("entity/decorated_pot/skull_pottery_pattern"),
    SNORT_POTTERY_PATTERN("entity/decorated_pot/snort_pottery_pattern");

    private final String texture;

    DecoratedPotSprite(String texture) {
        this.texture = texture;
    }

    public String texture() {
        return texture;
    }

    public String atlas() {
        return "minecraft:decorated_pot";
    }
}
