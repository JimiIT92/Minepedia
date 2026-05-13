package org.minepedia.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import org.minepedia.Minepedia;

/**
 * {@link Minepedia} {@link ConfigData Configuration class}
 */
@Config(name = Minepedia.MOD_ID)
public final class MinepediaConfig implements ConfigData {
    /**
     * Whether fog should be enabled
     */
    @ConfigEntry.Gui.Tooltip
    public boolean SHOW_MINEPEDIA_BUTTON = true;
}
