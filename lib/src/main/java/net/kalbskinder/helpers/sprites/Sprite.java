package net.kalbskinder.helpers.sprites;

import lombok.Data;
import net.kalbskinder.helpers.chat.MiniMessageHelper;
import net.kalbskinder.helpers.sprites.types.*;
import net.kyori.adventure.text.Component;

import java.util.UUID;

@Data
public class Sprite {
    private Sprite() {
        /* This utility class should not be instantiated */
    }

    private static final MiniMessageHelper miniMessageHelper = new MiniMessageHelper();

    /**
     * @param identifier Player's UUID or username
     * @return MiniMessage formatted string
     */
    public static String playerSprite(String identifier) {
        return "<head:%s>".formatted(identifier);
    }

    /**
     * @param playerUuid Player's UUID
     * @return MiniMessage formatted string
     */
    public static String playerSprite(UUID playerUuid) {
        return playerSprite(playerUuid.toString());
    }

    public static String of(String atlas, String texture) {
        return buildAtlasString(atlas, texture);
    }

    public static String of(BannerSprite sprite) {
        return of(sprite.atlas(), sprite.texture());
    }

    public static String of(BedSprite sprite) {
        return of(sprite.atlas(), sprite.texture());
    }

    // Block sprites are not part of an atlas, so we use a different format for them
    public static String of(BlockSprite sprite) {
        return buildString(sprite.atlas(), sprite.texture());
    }

    public static String of(CelestialSprite sprite) {
        return buildAtlasString(sprite.atlas(), sprite.texture());
    }

    public static String of(ChestSprite sprite) {
        return of(sprite.atlas(), sprite.texture());
    }

    public static String of(DecoratedPotSprite sprite) {
        return of(sprite.atlas(), sprite.texture());
    }

    public static String of(GuiMobEffectSprite sprite) {
        return of(sprite.atlas(), sprite.texture());
    }

    public static String of(GuiSprite sprite) {
        return of(sprite.atlas(), sprite.texture());
    }

    public static String of(ItemSprite sprite) {
        return of(sprite.atlas(), sprite.texture());
    }

    public static String of(MapDecorationSprite sprite) {
        return of(sprite.atlas(), sprite.texture());
    }

    public static String of(PaintingSprite sprite) {
        return of(sprite.atlas(), sprite.texture());
    }

    public static String of(ParticleSprite sprite) {
        return of(sprite.atlas(), sprite.texture());
    }

    public static String of(ShulkerBoxSprite sprite) {
        return of(sprite.atlas(), sprite.texture());
    }

    public static String of(SignSprite sprite) {
        return of(sprite.atlas(), sprite.texture());
    }

    /**
     * Parses a sprite string into a TextComponent.
     * @param string MiniMessage formatted string
     */
    public static Component parse(String string) {
        return miniMessageHelper.parse(string);
    }

    private static String buildAtlasString(String atlas, String texture) {
        return "<sprite:'%s':%s>".formatted(atlas, texture);
    }

    private static String buildString(String atlas, String texture) {
        return "<sprite:%s:%s>".formatted(atlas, texture);
    }
}
