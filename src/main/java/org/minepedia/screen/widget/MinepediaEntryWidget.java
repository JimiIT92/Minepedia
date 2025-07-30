package org.minepedia.screen.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.MultilineTextWidget;
import net.minecraft.client.gui.widget.ScrollableWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Matrix3x2f;
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
    private final float IMAGE_SCALE_FACTOR = 0.4F;

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
        this.text = new MultilineTextWidget(Text.empty(), textRenderer).setMaxWidth(this.getWidth() - this.getPadding());
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
        this.text = new MultilineTextWidget(entryText.isBlank() ? Text.empty() : this.getText(entryText), textRenderer).setMaxWidth(this.getWidth() - this.getPadding());
        final int textY = this.getY() + 10;
        this.text.setPosition(this.getX() + OFFSET_X, textY);
        final MinepediaMenuWidget.ImageData image = this.entry.getImage();
        if(image != null && image.position().equals(MinepediaMenuWidget.ImagePosition.START)) {
            this.text.setPosition(this.getX() + OFFSET_X, textY + (image.height() / 2) + image.imageOffset());
        }
        this.refreshScroll();
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
        this.renderContents(context, mouseX, mouseY, delta);
    }

    /**
     * Render the background
     *
     * @param context {@link DrawContext The Draw Context}
     */
    private void renderBackground(final DrawContext context) {
        drawTexture(context, Screen.MENU_BACKGROUND_TEXTURE, this.getX(), this.getY(), this.getRight(), this.getBottom(), this.width, this.height,32, 32);
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
    protected void renderContents(final DrawContext context, final int mouseX, final int mouseY, final float delta) {
        if(this.entry != null) {
            final MinepediaMenuWidget.ImageData imageData = this.entry.getImage();
            if(this.overflows()) {
                context.enableScissor(this.getX() + 1, this.getY() + 1, this.getX() + this.width - 1, this.getY() + this.height - 1);
            }
            context.getMatrices().pushMatrix();
            context.getMatrices().translate(this.getX(), (float)(this.getY() + (this.overflows() ? -this.getScrollY() : 0)), new Matrix3x2f());

            if(imageData != null && imageData.position().equals(MinepediaMenuWidget.ImagePosition.START)) {
                this.drawEntryImage(context);
            }

            this.text.render(context, mouseX, mouseY, delta);

            if(imageData != null && imageData.position().equals(MinepediaMenuWidget.ImagePosition.END)) {
                this.drawEntryImage(context);
            }

            context.getMatrices().popMatrix();
            if(this.overflows()) {
                context.disableScissor();
            }
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
        context.drawTexture(RenderPipelines.GUI_TEXTURED, texture, x, y, u, v, width, height, textureWidth, textureHeight);
    }

    /**
     * Render the entry image
     *
     * @param context {@link DrawContext The Draw Context}
     */
    private void drawEntryImage(final DrawContext context) {
        final MinepediaMenuWidget.ImageData image = this.entry.getImage();
        context.getMatrices().pushMatrix();
        context.getMatrices().scaling(IMAGE_SCALE_FACTOR);
        final int x = this.getX() + this.getWidth() + (this.getWidth() / 2) + OFFSET_X;
        final int y = image.position().equals(MinepediaMenuWidget.ImagePosition.START) ? this.getY() + (this.getHeight() / 2) : (this.getContentHeight() + (this.getContentHeight() / 2));
        drawTexture(context, image.getTexture(), x, y, 0, 0, image.width(), image.height(), 512, 512);
        context.getMatrices().popMatrix();
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
        return textHeight * 10;
    }

    @Override
    protected int getContentsHeightWithPadding() {
        return this.getContentHeight() + 20;
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