package net.kalbskinder.helpers.sprites.types;

public enum PaintingSprite {
    ALBAN("alban"),
    AZTEC("aztec"),
    AZTEC2("aztec2"),
    BACK("back"),
    BACKYARD("backyard"),
    BAROQUE("baroque"),
    BOMB("bomb"),
    BOUQUET("bouquet"),
    BURNING_SKULL("burning_skull"),
    BUST("bust"),
    CAVEBIRD("cavebird"),
    CHANGING("changing"),
    COTAN("cotan"),
    COURBET("courbet"),
    CREEBET("creebet"),
    DENNIS("dennis"),
    DONKEY_KONG("donkey_kong"),
    EARTH("earth"),
    ENDBOSS("endboss"),
    FERN("fern"),
    FIGHTERS("fighters"),
    FINDING("finding"),
    FIRE("fire"),
    GRAHAM("graham"),
    HUMBLE("humble"),
    KEBAB("kebab"),
    LOWMIST("lowmist"),
    MATCH("match"),
    MEDITATIVE("meditative"),
    ORB("orb"),
    OWLEMONS("owlemons"),
    PASSAGE("passage"),
    PIGSCENE("pigscene"),
    PLANT("plant"),
    POINTER("pointer"),
    POND("pond"),
    POOL("pool"),
    PRAIRIE_RIDE("prairie_ride"),
    SEA("sea"),
    SKELETON("skeleton"),
    SKULL_AND_ROSES("skull_and_roses"),
    STAGE("stage"),
    SUNFLOWERS("sunflowers"),
    SUNSET("sunset"),
    TIDES("tides"),
    UNPACKED("unpacked"),
    VOID("void"),
    WANDERER("wanderer"),
    WASTELAND("wasteland"),
    WATER("water"),
    WIND("wind"),
    WITHER("wither");

    private final String texture;

    PaintingSprite(String texture) {
        this.texture = texture;
    }

    public String texture() {
        return texture;
    }

    public String atlas() {
        return "minecraft:paintings";
    }
}
