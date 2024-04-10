package org.minepedia.screen;

import net.minecraft.client.gui.screen.Screen;
import org.minepedia.Minepedia;
import org.minepedia.screen.widget.MinepediaMenuWidget;

/**
 * {@link Minepedia Minepedia} Encyclopedia screen
 */
public final class MinepediaEncyclopediaScreen extends MinepediaScreen {

    /**
     * Constructor. Set the {@link MinepediaMenuWidget.MinepediaMenuItem Screen Entries}
     */
    public MinepediaEncyclopediaScreen() {
        super(
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "encyclopedia", true)
        );
    }

    /**
     * Get the {@link Screen Parent Screen}
     *
     * @return {@link Screen The Parent Screen}
     */
    protected Screen getParent() {
        return new MinepediaIndexScreen();
    }

}