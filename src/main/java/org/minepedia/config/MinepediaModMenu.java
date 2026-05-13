package org.minepedia.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfigClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.minepedia.Minepedia;

/**
 * {@link Minepedia} {@link ModMenuApi Mod Menu Api integration}
 */
@Environment(EnvType.CLIENT)
public final class MinepediaModMenu implements ModMenuApi {

    /**
     * Get the mod's config screen
     *
     * @return The mod's config screen
     */
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return Minepedia.isClothConfigInstalled() ? parent -> AutoConfigClient.getConfigScreen(MinepediaConfig.class, parent).get() : null;
    }
}