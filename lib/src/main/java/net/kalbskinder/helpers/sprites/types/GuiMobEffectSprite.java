package net.kalbskinder.helpers.sprites.types;

public enum GuiMobEffectSprite {
    ABSORPTION("mob_effect/absorption"),
    BAD_OMEN("mob_effect/bad_omen"),
    BLINDNESS("mob_effect/blindness"),
    BREATH_OF_THE_NAUTILUS("mob_effect/breath_of_the_nautilus"),
    CONDUIT_POWER("mob_effect/conduit_power"),
    DARKNESS("mob_effect/darkness"),
    DOLPHINS_GRACE("mob_effect/dolphins_grace"),
    FIRE_RESISTANCE("mob_effect/fire_resistance"),
    GLOWING("mob_effect/glowing"),
    HASTE("mob_effect/haste"),
    HEALTH_BOOST("mob_effect/health_boost"),
    HERO_OF_THE_VILLAGE("mob_effect/hero_of_the_village"),
    HUNGER("mob_effect/hunger"),
    INFESTED("mob_effect/infested"),
    INSTANT_DAMAGE("mob_effect/instant_damage"),
    INSTANT_HEALTH("mob_effect/instant_health"),
    INVISIBILITY("mob_effect/invisibility"),
    JUMP_BOOST("mob_effect/jump_boost"),
    LEVITATION("mob_effect/levitation"),
    LUCK("mob_effect/luck"),
    MINING_FATIGUE("mob_effect/mining_fatigue"),
    NAUSEA("mob_effect/nausea"),
    NIGHT_VISION("mob_effect/night_vision"),
    OOZING("mob_effect/oozing"),
    POISON("mob_effect/poison"),
    RAID_OMEN("mob_effect/raid_omen"),
    REGENERATION("mob_effect/regeneration"),
    RESISTANCE("mob_effect/resistance"),
    SATURATION("mob_effect/saturation"),
    SLOW_FALLING("mob_effect/slow_falling"),
    SLOWNESS("mob_effect/slowness"),
    SPEED("mob_effect/speed"),
    STRENGTH("mob_effect/strength"),
    TRIAL_OMEN("mob_effect/trial_omen"),
    UNLUCK("mob_effect/unluck"),
    WATER_BREATHING("mob_effect/water_breathing"),
    WEAKNESS("mob_effect/weakness"),
    WEAVING("mob_effect/weaving"),
    WIND_CHARGED("mob_effect/wind_charged"),
    WITHER("mob_effect/wither");

    private final String texture;

    GuiMobEffectSprite(String texture) {
        this.texture = texture;
    }

    public String texture() {
        return texture;
    }

    public String atlas() {
        return "minecraft:gui";
    }
}
