package org.minepedia;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.minepedia.config.MinepediaConfig;
import org.minepedia.keybinding.MinepediaKeyBindings;

/**
 * Minepedia: bring the Bedrock edition Encyclopaedia to Minecraft: Java Edition
 */
public final class Minepedia implements ClientModInitializer {

    /**
     * The {@link Minepedia Minepedia} {@link String Mod ID}
     */
    public static final String MOD_ID = "minepedia";
    /**
     * The {@link MinepediaConfig Mod Configuration}
     */
    private static MinepediaConfig CONFIG;

    /**
     * Initialize the mod
     */
    @Override
    public void onInitializeClient() {
        MinepediaKeyBindings.init();
        if(isClothConfigInstalled()) {
            AutoConfig.register(MinepediaConfig.class, GsonConfigSerializer::new);
        }
    }

    /**
     * Check whether the Cloth Config mod is installed
     *
     * @return {@link Boolean True} if Cloth Config is installed
     */
    public static boolean isClothConfigInstalled() {
        return FabricLoader.getInstance().isModLoaded("cloth-config");
    }

    /**
     * Get the {@link #CONFIG} instance or create a new one
     *
     * @return The {@link #CONFIG} instance
     */
    public static MinepediaConfig config() {
        if(CONFIG == null) {
            CONFIG = AutoConfig.getConfigHolder(MinepediaConfig.class).getConfig();
        }
        return CONFIG;
    }
}