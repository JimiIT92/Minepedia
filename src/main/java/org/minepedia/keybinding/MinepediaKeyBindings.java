package org.minepedia.keybinding;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;
import org.minepedia.Minepedia;
import org.minepedia.screen.MinepediaIndexScreen;

/**
 * {@link Minepedia Minepedia} {@link KeyMapping Key Bindings}
 */
@Environment(EnvType.CLIENT)
public final class MinepediaKeyBindings {

    /**
     * The {@link Minepedia Minepedia} {@link KeyMapping screen Key Binding}
     */
    private static KeyMapping MINEPEDIA_KEY;

    /**
     * Initialize the {@link KeyMapping Key Bindings}
     */
    public static void init() {
        MINEPEDIA_KEY = registerKeyBinding("screen", GLFW.GLFW_KEY_K);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (MINEPEDIA_KEY.isDown()) {
                client.setScreen(new MinepediaIndexScreen());
            }
        });
    }

    /**
     * Register a {@link KeyMapping Key Binding}
     *
     * @param name {@link String The Key Binding name}
     * @param keyCode {@link Integer The Key Binding key code}
     * @return {@link KeyMapping The registered Key Binding}
     */
    private static KeyMapping registerKeyBinding(final String name, final int keyCode) {
        return KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key." + Minepedia.MOD_ID + "."  + name,
                InputConstants.Type.KEYSYM,
                keyCode,
                KeyMapping.Category.register(Identifier.fromNamespaceAndPath(Minepedia.MOD_ID, "category." + Minepedia.MOD_ID))
        ));
    }

}