package org.minepedia.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.minepedia.Minepedia;

/**
 * The main {@link Minepedia Minepedia} {@link Screen Screen}
 */
@Environment(EnvType.CLIENT)
public class MinepediaScreen extends Screen {

    /**
     * Constructor. Set the {@link Screen Screen} {@link Text Title}
     */
    public MinepediaScreen() {
        super(Text.translatable("screen." + Minepedia.MOD_ID + ".title"));
    }

    /**
     * Initialize the {@link Screen Screen}
     */
    @Override
    protected void init() {

    }

    /**
     * Make the {@link Screen Screen} not pausing the game
     *
     * @return {@link Boolean#FALSE False}
     */
    @Override
    public boolean shouldPause() {
        return false;
    }

}