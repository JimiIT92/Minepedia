package org.minepedia;

import net.fabricmc.api.ClientModInitializer;
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
     * Initialize the mod
     */
    @Override
    public void onInitializeClient() {
        MinepediaKeyBindings.init();
    }

}