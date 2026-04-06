package net.kalbskinder.helpers.regions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegionEvent {
    private Player player;
    private Region region;
}
