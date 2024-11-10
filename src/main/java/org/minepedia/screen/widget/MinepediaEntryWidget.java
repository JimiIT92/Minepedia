package org.minepedia.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.MultilineTextWidget;
import net.minecraft.client.gui.widget.ScrollableWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.minepedia.Minepedia;
import org.minepedia.util.AssetUtils;

/**
 * Widget for a {@link Minepedia Minepedia} entry
 */
@Environment(EnvType.CLIENT)
public class MinepediaEntryWidget extends ScrollableWidget {

    /**
     * The {@link Integer X Coordinate Offset}
     */
    private final int OFFSET_X = 5;
    /**
     * The {@link TextRenderer Text Renderer instance}
     */
    private final TextRenderer textRenderer;
    /**
     * The {@link MultilineTextWidget Multiline Text Widget}
     */
    private MultilineTextWidget text;
    /**
     * The {@link MinepediaMenuWidget.MinepediaMenuItem related Menu Item Entry}
     */
    private MinepediaMenuWidget.MinepediaMenuItem entry;
    /**
     * The {@link Float Image scale factor}
     */
    private final float IMAGE_SCALE_FACTOR = 0.5F;

    /**
     * Constructor. Set the widget properties
     *
     * @param x {@link Integer The widget X coordinate}
     * @param y {@link Integer The widget Y coordinate}
     * @param width {@link Integer The widget width}
     * @param height {@link Integer The widget height}
     * @param textRenderer {@link TextRenderer The Text Renderer instance}
     */
    public MinepediaEntryWidget(final int x, final int y, final int width, final int height, final TextRenderer textRenderer) {
        super(x, y, width, height, Text.empty());
        this.text = new MultilineTextWidget(Text.empty(), textRenderer).setMaxWidth(this.getWidth() - this.getPaddingDoubled());
        this.textRenderer = textRenderer;
    }

    /**
     * Change the {@link MultilineTextWidget text} when a new {@link MinepediaMenuWidget.MinepediaMenuItem Menu Item entry} is selected
     *
     * @param entry {@link MinepediaMenuWidget.MinepediaMenuItem The selected Menu Item entry}
     */
    public void selectEntry(final MinepediaMenuWidget.MinepediaMenuItem entry) {
        this.entry = entry;
        final String entryText = AssetUtils.readEntry(entry.getSection(), entry.getKey());
        this.text = new MultilineTextWidget(entryText.isBlank() || entryText.isEmpty() ? Text.empty() : this.getText(entryText), textRenderer).setMaxWidth(this.getWidth() - this.getPaddingDoubled());
        final int textY = 10;
        this.text.setPosition(OFFSET_X, textY);
        final MinepediaMenuWidget.ImageData image = this.entry.getImage();
        if(image != null && image.position().equals(MinepediaMenuWidget.ImagePosition.START)) {
            this.text.setPosition(OFFSET_X, textY + (image.height() / 2) + image.imageOffset());
        }
        this.setScrollY(0D);
    }

    /**
     * Render the entry details
     *
     * @param context {@link DrawContext The Draw Context}
     * @param mouseX {@link Integer The mouse X coordinate}
     * @param mouseY {@link Integer The mouse Y coordinate}
     * @param delta {@link Float The screen delta time}
     */
    @Override
    public void renderWidget(final DrawContext context, final int mouseX, final int mouseY, final float delta) {
        if (!this.visible) {
            return;
        }
        this.renderBackground(context);
        if (!this.overflows()) {
            this.renderContents(context, mouseX, mouseY, delta);
        } else {
            super.renderWidget(context, mouseX, mouseY, delta);
        }
    }

    /**
     * Prevent the overflow box from drawing
     *
     * @param context {@link DrawContext The Draw Context}
     */
    protected void drawBox(final DrawContext context) { }

    /**
     * Render the background
     *
     * @param context {@link DrawContext The Draw Context}
     */
    private void renderBackground(final DrawContext context) {
        RenderSystem.setShaderColor(0.125f, 0.125f, 0.125f, 1.0f);
        drawTexture(context, Screen.MENU_BACKGROUND_TEXTURE, this.getX(), this.getY(), this.getRight(), this.getBottom(), this.width, this.height,32, 32);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    /**
     * Add the narrations to the {@link NarrationMessageBuilder narrator}
     *
     * @param builder The {@link NarrationMessageBuilder Narration Message Builder}
     */
    @Override
    protected void appendClickableNarrations(final NarrationMessageBuilder builder) {
        builder.put(NarrationPart.TITLE, this.getMessage());
    }

    /**
     * Render the entry details when the scrollbars are visible
     *
     * @param context {@link DrawContext The Draw Context}
     * @param mouseX {@link Integer The mouse X coordinate}
     * @param mouseY {@link Integer The mouse Y coordinate}
     * @param delta {@link Float The screen delta time}
     */
    @Override
    protected void renderContents(final DrawContext context, final int mouseX, final int mouseY, final float delta) {
        if(this.entry != null) {
            final MinepediaMenuWidget.ImageData imageData = this.entry.getImage();
            context.getMatrices().push();
            context.getMatrices().translate(this.getX(), this.getY(), 0.0f);

            if(imageData != null && imageData.position().equals(MinepediaMenuWidget.ImagePosition.START)) {
                this.drawEntryImage(context);
            }

            this.text.render(context, mouseX, mouseY, delta);

            if(imageData != null && imageData.position().equals(MinepediaMenuWidget.ImagePosition.END)) {
                this.drawEntryImage(context);
            }

            context.getMatrices().pop();
        }
    }

    /**
     * Draw a texture
     *
     * @param context {@link DrawContext The Draw Context}
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
    private void drawTexture(final DrawContext context, final Identifier texture, final int x, final int y, final int u, final int v, final int width, final int height, final int textureWidth, final int textureHeight) {
        context.drawTexture(RenderLayer::getGuiTextured, texture, x, y, u, v, width, height, textureWidth, textureHeight);
    }

    /**
     * Render the entry image
     *
     * @param context {@link DrawContext The Draw Context}
     */
    private void drawEntryImage(final DrawContext context) {
        final MinepediaMenuWidget.ImageData image = this.entry.getImage();
        context.getMatrices().push();
        context.getMatrices().scale(IMAGE_SCALE_FACTOR, IMAGE_SCALE_FACTOR, IMAGE_SCALE_FACTOR);
        final int x = this.getX() + (this.getWidth() / 2) - (image.width() / 2) - OFFSET_X;
        final int y = image.position().equals(MinepediaMenuWidget.ImagePosition.START) ? 7 : (image.imageOffset() > 0 ? this.text.getHeight() + image.imageOffset() : this.getContentHeight());
        drawTexture(context, image.getTexture(), x, y, 0, 0, image.width(), image.height(), 512, 512);
        context.getMatrices().pop();
    }

    /**
     * Get the total {@link Integer height} of contents
     *
     * @return {@link Integer The height contents}
     */
    @Override
    protected int getContentsHeight() {
        return getContentHeight();
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
            return textHeight + (int)(imageData.height() * IMAGE_SCALE_FACTOR) + (imageData.imageOffset() / 3);
        }
        return textHeight;
    }

    /**
     * Get the {@link Double Delta amount} for the scrollbar
     *
     * @return {@link Double The scrollbar delta amount}
     */
    @Override
    protected double getDeltaYPerScroll() {
        return this.textRenderer.fontHeight;
    }

    /**
     * Get the {@link Text translated entry text}
     *
     * @param rawText {@link String The raw entry text}
     * @return {@link Text The translated entry text}
     */
    private Text getText(final String rawText) {
        return Text.literal(I18n.translate(rawText).replace("Â", "").replace("â", ""));
    }

}