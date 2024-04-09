package org.minepedia.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.minepedia.Minepedia;
import org.minepedia.screen.widget.MinepediaEntryWidget;
import org.minepedia.screen.widget.MinepediaMenuWidget;

/**
 * The main {@link Minepedia Minepedia} {@link Screen Screen}
 */
@Environment(EnvType.CLIENT)
public final class MinepediaScreen extends Screen {

    /**
     * {@link MinepediaMenuWidget.MinepediaMenuItem The selected menu item}
     */
    private MinepediaMenuWidget.MinepediaMenuItem selectedMenuItem;
    /**
     * {@link MinepediaMenuWidget The Minepedia Menu widget}
     */
    private MinepediaMenuWidget menuWidget;
    /**
     * The {@link MinepediaEntryWidget selected menu item text wdget}
     */
    private MinepediaEntryWidget textWidget;

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
        final int menuOffsetX = 7;
        menuWidget = new MinepediaMenuWidget(this.client, this, menuOffsetX);
        addDrawableChild(menuWidget);
        textWidget = new MinepediaEntryWidget(menuOffsetX + menuWidget.getWidth() + menuOffsetX + (menuOffsetX / 2), MinepediaMenuWidget.WIDGET_Y, this.width - menuWidget.getWidth() - (menuOffsetX * 3), menuWidget.getHeight(), this.textRenderer);
        addDrawableChild(textWidget);
    }

    /*@Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }*/

    /**
     * Make the {@link Screen Screen} not pausing the game
     *
     * @return {@link Boolean#FALSE False}
     */
    @Override
    public boolean shouldPause() {
        return false;
    }

    /**
     * Set the {@link MinepediaMenuWidget.MinepediaMenuItem selected menu item}
     *
     * @param menuItem The {@link MinepediaMenuWidget.MinepediaMenuItem selected menu item}
     */
    public void selectMenuItem(final MinepediaMenuWidget.MinepediaMenuItem menuItem) {
        this.selectedMenuItem = menuItem;
        this.textWidget.selectEntry(this.selectedMenuItem);
    }



}