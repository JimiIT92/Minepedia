package org.minepedia.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.minepedia.Minepedia;
import org.minepedia.screen.widget.MinepediaEntryWidget;
import org.minepedia.screen.widget.MinepediaMenuWidget;

import java.util.Arrays;

/**
 * The main {@link Minepedia Minepedia} {@link Screen Screen}
 */
@Environment(EnvType.CLIENT)
public abstract class MinepediaScreenOld extends Screen {

    /**
     * {@link MinepediaMenuWidget.MinepediaMenuItem The selected menu item}
     */
    private MinepediaMenuWidget.MinepediaMenuItem selectedMenuItem;
    /**
     * {@link MinepediaMenuWidget The Minepedia Menu widget}
     */
    private MinepediaMenuWidget menuWidget;
    /**
     * The {@link MinepediaEntryWidget selected menu item text widget}
     */
    private MinepediaEntryWidget textWidget;
    /**
     * The {@link MinepediaMenuWidget.MinepediaMenuItem menu items}
     */
    private final MinepediaMenuWidget.MinepediaMenuItem[] menuItems;

    /**
     * Constructor. Set the {@link Screen Screen} {@link Text Title}
     *
     * @param title {@link String The Screen title}
     * @param menuItems {@link MinepediaMenuWidget.MinepediaMenuItem The menu items}
     */
    public MinepediaScreenOld(final String title, final MinepediaMenuWidget.MinepediaMenuItem... menuItems) {
        super(Text.translatable("screen." + Minepedia.MOD_ID + "." + title));
        this.menuItems = menuItems;
    }

    /**
     * Initialize the {@link Screen Screen}
     */
    @Override
    protected void init() {
        final int menuOffsetX = 5;
        menuWidget = new MinepediaMenuWidget(this.client, null, menuOffsetX);
        menuWidget.init(this.menuItems);
        addDrawableChild(menuWidget);
        textWidget = new MinepediaEntryWidget(menuOffsetX + menuWidget.getWidth(), MinepediaMenuWidget.WIDGET_Y, this.width - menuWidget.getWidth() - (menuOffsetX * 3) + menuOffsetX, menuWidget.getHeight(), this.textRenderer);
        addDrawableChild(textWidget);
        if(this.menuItems != null && this.menuItems.length > 0 && this.selectedMenuItem == null) {
            MinepediaMenuWidget.MinepediaMenuItem selectedItem = Arrays.stream(this.menuItems).filter(entry -> !entry.isHeader()).findFirst().orElse(this.menuItems[0]);
            this.menuWidget.setSelected(selectedItem);
            this.menuWidget.setFocused(selectedItem);
            this.menuWidget.setFocused(selectedItem);
        }
    }

    /**
     * Render the {@link Screen Screen}
     *
     * @param context {@link DrawContext The Draw Context}
     * @param mouseX {@link Integer The mouse X coordinate}
     * @param mouseY {@link Integer The mouse Y coordinate}
     * @param delta {@link Float The screen delta time}
     */
    @Override
    public void render(final DrawContext context, final int mouseX, final int mouseY, final float delta) {
        if(this.selectedMenuItem != null) {
            final MutableText title = this.title.copy()
                    .append(Text.literal(": "))
                    .append(this.selectedMenuItem.getText());

            context.drawCenteredTextWithShadow(this.textRenderer, title, this.width / 2, 4, 0xFFFFFF);
        }
        super.render(context, mouseX, mouseY, delta);
    }

    /**
     * Close the {@link Screen Screen}
     */
    @Override
    public void close() {
        super.close();
        final Screen parent = this.getParent();
        if(parent != null && this.client != null) {
            this.client.setScreen(parent);
        }
    }

    /**
     * Get the {@link Screen Parent Screen}
     *
     * @return {@link Screen The Parent Screen}
     */
    protected abstract Screen getParent();

    /**
     * Keep the {@link MinepediaMenuWidget.MinepediaMenuItem selected Menu Item} on screen resize
     *
     * @param client {@link MinecraftClient The Minecraft Client instance}
     * @param width {@link Integer The screen width}
     * @param height {@link Integer The screen height}
     */
    @Override
    public void resize(final MinecraftClient client, final int width, final int height) {
        super.resize(client, width, height);
        this.menuWidget.setSelected(this.selectedMenuItem);
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