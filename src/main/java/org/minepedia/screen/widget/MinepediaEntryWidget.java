package org.minepedia.screen.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractScrollArea;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.minepedia.Minepedia;
import org.minepedia.util.AssetUtils;

/**
 * Widget for a {@link Minepedia Minepedia} entry
 */
@Environment(EnvType.CLIENT)
public class MinepediaEntryWidget extends AbstractScrollArea {

    /**
     * The {@link Integer X Coordinate Offset}
     */
    private final int OFFSET_X = 5;
    /**
     * The {@link Font Text Renderer instance}
     */
    private final Font textRenderer;
    /**
     * The {@link MultiLineTextWidget Multiline Text Widget}
     */
    private MultiLineTextWidget text;
    /**
     * The {@link MinepediaMenuWidget.MinepediaMenuItem related Menu Item Entry}
     */
    private MinepediaMenuWidget.MinepediaMenuItem entry;
    /**
     * The {@link Float Image scale factor}
     */
    private final float IMAGE_SCALE_FACTOR = 0.4F;

    private int imageY = 0;

    /**
     * Constructor. Set the widget properties
     *
     * @param x {@link Integer The widget X coordinate}
     * @param y {@link Integer The widget Y coordinate}
     * @param width {@link Integer The widget width}
     * @param height {@link Integer The widget height}
     * @param textRenderer {@link Font The Text Renderer instance}
     */
    public MinepediaEntryWidget(final int x, final int y, final int width, final int height, final Font textRenderer) {
        super(x, y, width, height, Component.empty(), AbstractScrollArea.defaultSettings(8));
        this.text = new MultiLineTextWidget(Component.empty(), textRenderer).setMaxWidth(this.getWidth() - this.getPadding());
        this.textRenderer = textRenderer;
    }

    /**
     * Change the {@link MultiLineTextWidget text} when a new {@link MinepediaMenuWidget.MinepediaMenuItem Menu Item entry} is selected
     *
     * @param entry {@link MinepediaMenuWidget.MinepediaMenuItem The selected Menu Item entry}
     */
    public void selectEntry(final MinepediaMenuWidget.MinepediaMenuItem entry) {
        this.entry = entry;
        final String entryText = AssetUtils.readEntry(entry.getSection(), entry.getKey());
        this.text = new MultiLineTextWidget(entryText.isBlank() ? Component.empty() : this.getText(entryText), textRenderer).setMaxWidth(this.getWidth() - this.getPadding());
        final int textY = this.getY() + 10;
        this.text.setPosition(this.getX() + OFFSET_X, textY);
        this.imageY = 0;
        final MinepediaMenuWidget.ImageData image = this.entry.getImage();
        if(image != null) {
            final boolean isStartImage = image.position().equals(MinepediaMenuWidget.ImagePosition.START);
            if(isStartImage) {
                this.text.setPosition(this.getX() + OFFSET_X, textY + (image.height() / 2) + image.imageOffset());
            }
            this.imageY = getImageY(image);
        }
        this.setScrollAmount(0);
        this.refreshScrollAmount();
    }

    /**
     * Get the {@link Integer X padding}
     *
     * @return The {@link Integer X padding}
     */
    private int getPadding() {
        return 8;
    }

    /**
     * Render the entry details
     *
     * @param context {@link GuiGraphicsExtractor The Draw Context}
     * @param mouseX {@link Integer The mouse X coordinate}
     * @param mouseY {@link Integer The mouse Y coordinate}
     * @param delta {@link Float The screen delta time}
     */
    @Override
    public void extractWidgetRenderState(final GuiGraphicsExtractor context, final int mouseX, final int mouseY, final float delta) {
        if (!this.visible) {
            return;
        }
        this.renderBackground(context);
        this.renderContents(context, mouseX, mouseY, delta);
    }

    /**
     * Render the background
     *
     * @param context {@link GuiGraphicsExtractor The Draw Context}
     */
    private void renderBackground(final GuiGraphicsExtractor context) {
        drawTexture(context, Screen.MENU_BACKGROUND, this.getX(), this.getY(), this.getRight(), this.getBottom(), this.width, this.height,32, 32);
    }

    /**
     * Add the narrations to the {@link NarrationElementOutput narrator}
     *
     * @param builder The {@link NarrationElementOutput Narration Message Builder}
     */
    @Override
    protected void updateWidgetNarration(final NarrationElementOutput builder) {
        builder.add(NarratedElementType.TITLE, this.getMessage());
    }

    /**
     * Render the entry details when the scrollbars are visible
     *
     * @param context {@link GuiGraphicsExtractor The Draw Context}
     * @param mouseX {@link Integer The mouse X coordinate}
     * @param mouseY {@link Integer The mouse Y coordinate}
     * @param delta {@link Float The screen delta time}
     */
    protected void renderContents(final GuiGraphicsExtractor context, final int mouseX, final int mouseY, final float delta) {
        if(this.entry != null) {
            final MinepediaMenuWidget.ImageData imageData = this.entry.getImage();
            context.enableScissor(this.getX() + 1, this.getY() + 1, this.getX() + this.width - 1, this.getY() + this.height - 1);

            if(imageData != null && imageData.position().equals(MinepediaMenuWidget.ImagePosition.START)) {
                this.drawEntryImage(context);
            }

            this.text.extractRenderState(context, mouseX, mouseY, delta);

            if(imageData != null && imageData.position().equals(MinepediaMenuWidget.ImagePosition.END)) {
                this.drawEntryImage(context);
            }

            context.disableScissor();
        }
    }

    /**
     * Draw a texture
     *
     * @param context {@link GuiGraphicsExtractor The Draw Context}
     * @param texture {@link Identifier The texture Identifier}
     * @param x {@link Integer The texture X coordinate}
     * @param y {@link Integer The texture Y coordinate}
     * @param u {@link Integer The texture U coordinate}
     * @param v {@link Integer The texture V coordinate}
     * @param width {@link Integer The texture rendered width}
     * @param height {@link Integer The texture rendered height}
     * @param textureWidth {@link Integer The texture total width}
     * @param textureHeight {@link Integer The texture total height}
     */
    private void drawTexture(final GuiGraphicsExtractor context, final Identifier texture, final int x, final int y, final int u, final int v, final int width, final int height, final int textureWidth, final int textureHeight) {
        context.blit(RenderPipelines.GUI_TEXTURED, texture, x, y, u, v, width, height, textureWidth, textureHeight);
    }

    /**
     * Render the entry image
     *
     * @param context {@link GuiGraphicsExtractor The Draw Context}
     */
    private void drawEntryImage(final GuiGraphicsExtractor context) {
        final MinepediaMenuWidget.ImageData image = this.entry.getImage();
        context.pose().pushMatrix();
        context.pose().scaling(IMAGE_SCALE_FACTOR);
        final int x = this.getX() + this.getWidth() + (this.getWidth() / 2) + OFFSET_X;
        drawTexture(context, image.getTexture(), x, this.imageY, 0, 0, image.width(), image.height(), 512, 512);
        context.pose().popMatrix();
    }


    /**
     * Get the total {@link Integer height} of contents
     *
     * @return {@link Integer The height contents}
     */
    private int getContentHeight() {
        final int textHeight = this.text.getHeight();
        if(this.entry != null && this.entry.getImage() != null) {
            final MinepediaMenuWidget.ImageData imageData = this.entry.getImage();
            return (textHeight * 10) + (int)(imageData.height() * IMAGE_SCALE_FACTOR) + (imageData.imageOffset() / 3);
        }
        return this.height > textHeight ? textHeight : textHeight * 20;
    }

    private int getImageHeight() {
        final int textHeight = this.text.getHeight();
        final MinepediaMenuWidget.ImageData imageData = this.entry.getImage();
        return textHeight + (int)(imageData.height() * IMAGE_SCALE_FACTOR) + (imageData.imageOffset() / 3);
    }

    @Override
    protected int contentHeight() {
        return this.getContentHeight() + 20;
    }

    /**
     * Get the {@link Double Delta amount} for the scrollbar
     *
     * @return {@link Double The scrollbar delta amount}
     */
    @Override
    protected double scrollRate() {
        return this.textRenderer.lineHeight;
    }

    /**
     * Get the {@link Component translated entry text}
     *
     * @param rawText {@link String The raw entry text}
     * @return {@link Component The translated entry text}
     */
    private Component getText(final String rawText) {
        return Component.literal(I18n.get(rawText).replace("Â", "").replace("â", ""));
    }

    private int getImageY(final MinepediaMenuWidget.ImageData image) {
        return (image.position().equals(MinepediaMenuWidget.ImagePosition.START) ? this.getY() + (this.getHeight() / 2) : (this.getImageHeight() + (this.getImageHeight() / 2)));
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        verticalAmount *= 5;
        final double previousScrollY = this.scrollBarY();
        final boolean scrolled = super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
        if(scrolled && (previousScrollY != this.scrollBarY())) {
            this.text.setY(this.text.getY() + (int)verticalAmount);
            final MinepediaMenuWidget.ImageData image = this.entry.getImage();
            if(image != null) {
                this.imageY += ((int) verticalAmount * 2);
            }
        }
        return scrolled;
    }
}