package org.minepedia.screen.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.MultilineTextWidget;
import net.minecraft.client.gui.widget.ScrollableWidget;
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

    private final TextRenderer textRenderer;
    private MultilineTextWidget wrapped;
    private MinepediaMenuWidget.MinepediaMenuItem entry;
    private final float IMAGE_SCALE_FACTOR = 0.5F;

    public MinepediaEntryWidget(int x, int y, int width, int height, TextRenderer textRenderer) {
        super(x, y, width, height, Text.empty());
        this.wrapped = new MultilineTextWidget(Text.empty(), textRenderer).setMaxWidth(this.getWidth() - this.getPaddingDoubled());
        this.textRenderer = textRenderer;
    }

    public void selectEntry(final MinepediaMenuWidget.MinepediaMenuItem entry) {
        this.entry = entry;
        final String entryText = AssetUtils.readEntry(entry.getKey());
        this.wrapped = new MultilineTextWidget(entryText.isBlank() || entryText.isEmpty() ? Text.empty() : this.getText(entryText), textRenderer).setMaxWidth(this.getWidth() - this.getPaddingDoubled());
        final int textX = 5;
        final int textY = 10;
        this.wrapped.setPosition(textX, textY);
        final MinepediaMenuWidget.ImageData image = this.entry.getImage();
        if(image != null && image.position().equals(MinepediaMenuWidget.ImagePosition.START)) {
            this.wrapped.setPosition(textX, textY + (image.height() / 2));
        }
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
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
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

    protected void drawBox(DrawContext context) { }

    /**
     * Render the background
     *
     * @param context {@link DrawContext The Draw Context}
     */
    private void renderBackground(final DrawContext context) {
        context.setShaderColor(0.125f, 0.125f, 0.125f, 1.0f);
        drawTexture(context, Screen.OPTIONS_BACKGROUND_TEXTURE, this.getX(), this.getY(), this.getRight(), this.getBottom(), this.width, this.height,32, 32);
        context.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        builder.put(NarrationPart.TITLE, this.getMessage());
    }

    @Override
    protected void renderContents(DrawContext context, int mouseX, int mouseY, float delta) {
        if(this.entry != null) {
            MinepediaMenuWidget.ImageData imageData = this.entry.getImage();
            context.getMatrices().push();
            context.getMatrices().translate(this.getX(), this.getY(), 0.0f);

            if(imageData != null && imageData.position().equals(MinepediaMenuWidget.ImagePosition.START)) {
                this.drawEntryImage(context);
            }

            this.wrapped.render(context, mouseX, mouseY, delta);

            if(imageData != null && imageData.position().equals(MinepediaMenuWidget.ImagePosition.END)) {
                this.drawEntryImage(context);
            }

            context.getMatrices().pop();
        }
    }


    private void drawTexture(final DrawContext context, final Identifier texture, final int x, final int y, final int u, final int v, final int width, final int height, final int textureWidth, final int textureHeight) {
        context.drawTexture(texture, x, y, u, v, width, height, textureWidth, textureHeight);
    }

    private void drawEntryImage(final DrawContext context) {
        final MinepediaMenuWidget.ImageData image = this.entry.getImage();
        context.getMatrices().push();
        context.getMatrices().scale(IMAGE_SCALE_FACTOR, IMAGE_SCALE_FACTOR, IMAGE_SCALE_FACTOR);
        drawTexture(context, image.getTexture(), this.getX() - (image.width() / 4) + 5, image.position().equals(MinepediaMenuWidget.ImagePosition.START) ? 10 : (this.wrapped.getHeight() + 40), 0, 0, image.width(), image.height(), 512, 512);
        context.getMatrices().pop();
    }

    @Override
    protected int getContentsHeight() {
        final int textHeight = this.wrapped.getHeight();
        if(this.entry != null && this.entry.getImage() != null) {
            return textHeight + (int)(this.entry.getImage().height() * IMAGE_SCALE_FACTOR);
        }
        return textHeight;
    }

    @Override
    protected double getDeltaYPerScroll() {
        return this.textRenderer.fontHeight;
    }

    private Text getText(String rawText) {
        return Text.literal(I18n.translate(rawText).replace("Â", "").replace("â", ""));
    }
}
