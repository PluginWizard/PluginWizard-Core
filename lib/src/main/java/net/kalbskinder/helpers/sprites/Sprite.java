package net.kalbskinder.helpers.sprites;

import lombok.Data;
import net.kalbskinder.helpers.chat.MiniMessageHelper;

@Data
abstract class Sprite {
    protected static final MiniMessageHelper miniMessageHelper = new MiniMessageHelper();
    protected final AtlasType type;
}
