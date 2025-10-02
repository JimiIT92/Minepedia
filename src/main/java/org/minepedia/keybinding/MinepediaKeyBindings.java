package org.minepedia.keybinding;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import org.minepedia.Minepedia;
import org.minepedia.screen.MinepediaIndexScreen;

/**
 * {@link Minepedia Minepedia} {@link KeyBinding Key Bindings}
 */
@Environment(EnvType.CLIENT)
public final class MinepediaKeyBindings {

    /**
     * The {@link Minepedia Minepedia} {@link KeyBinding screen Key Binding}
     */
    private static KeyBinding MINEPEDIA_KEY;

    /**
     * Initialize the {@link KeyBinding Key Bindings}
     */
    public static void init() {
        MINEPEDIA_KEY = registerKeyBinding("screen", GLFW.GLFW_KEY_K);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (MINEPEDIA_KEY.wasPressed()) {
                client.setScreen(new MinepediaIndexScreen());
            }
        });
    }

    /**
     * Register a {@link KeyBinding Key Binding}
     *
     * @param name {@link String The Key Binding name}
     * @param keyCode {@link Integer The Key Binding key code}
     * @return {@link KeyBinding The registered Key Binding}
     */
    private static KeyBinding registerKeyBinding(final String name, final int keyCode) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key." + Minepedia.MOD_ID + "."  + name,
                InputUtil.Type.KEYSYM,
                keyCode,
                KeyBinding.Category.create(Identifier.of("category." + Minepedia.MOD_ID))
        ));
    }

}