package org.minepedia.screen.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.minepedia.Minepedia;
import org.minepedia.screen.MinepediaScreen;

import java.util.Objects;

/**
 * {@link Minepedia Minepedia} menu list widget
 */
@Environment(EnvType.CLIENT)
public class MinepediaMenuWidget extends AlwaysSelectedEntryListWidget<MinepediaMenuWidget.MinepediaMenuItem> {

    /**
     * {@link MinepediaScreen The Minepedia main screen}
     */
    private final MinepediaScreen parentScreen;
    /**
     * {@link Integer The Widget Y offset}
     */
    public static final int WIDGET_Y_OFFSET = 40;
    /**
     * {@link Integer The Widget Y coordinate}
     */
    public static final int WIDGET_Y = WIDGET_Y_OFFSET / 2 + (WIDGET_Y_OFFSET / 4);

    /**
     * Constructor. Set the widget properties
     *
     * @param minecraftClient {@link MinecraftClient The Minecraft Client instance}
     * @param parentScreen {@link MinepediaScreen The Minepedia main screen}
     * @param x {@link Integer The widget X coordinate}
     */
    public MinepediaMenuWidget(final MinecraftClient minecraftClient, final MinepediaScreen parentScreen, final int x) {
        super(minecraftClient, 150, Objects.requireNonNull(minecraftClient.currentScreen).height - WIDGET_Y_OFFSET, WIDGET_Y_OFFSET, 20);
        this.setPosition(x, WIDGET_Y);
        this.parentScreen = parentScreen;
        this.addEntry(new MinepediaMenuItem("getting_started", true));
        this.addEntry(new MinepediaMenuItem("moving_around", new ImageData("moving_around", 512, 304, ImagePosition.END)));
        this.addEntry(new MinepediaMenuItem("gathering_resources"));
        this.addEntry(new MinepediaMenuItem("selecting_items", new ImageData("selecting_items", 512, 272, ImagePosition.END)));
        this.addEntry(new MinepediaMenuItem("placing_blocks"));
        this.addEntry(new MinepediaMenuItem("inventory", new ImageData("inventory", 512, 281, ImagePosition.START)));
        this.addEntry(new MinepediaMenuItem("preparing_for_the_night", true));
        this.addEntry(new MinepediaMenuItem("your_first_craft", new ImageData("your_first_craft", 512, 272, ImagePosition.START)));
        this.addEntry(new MinepediaMenuItem("recipe_book"));
        this.addEntry(new MinepediaMenuItem("the_crafting_table", new ImageData("the_crafting_table", 512, 304, ImagePosition.START)));
        this.addEntry(new MinepediaMenuItem("crafting_a_tool"));
        this.addEntry(new MinepediaMenuItem("mining", new ImageData("mining", 512, 304, ImagePosition.START)));
        this.addEntry(new MinepediaMenuItem("surviving_the_first_night", true));
        this.addEntry(new MinepediaMenuItem("nightfall"));
        this.addEntry(new MinepediaMenuItem("building_a_shelter", new ImageData("building_a_shelter", 512, 304, ImagePosition.START)));
        this.addEntry(new MinepediaMenuItem("death_and_respawn"));
        this.addEntry(new MinepediaMenuItem("getting_settled", true));
        this.addEntry(new MinepediaMenuItem("food"));
        this.addEntry(new MinepediaMenuItem("beds"));
        this.addEntry(new MinepediaMenuItem("improved_tools"));
        this.addEntry(new MinepediaMenuItem("", true));
        this.addEntry(new MinepediaMenuItem("encyclopaedia"));
    }

    /**
     * Select a {@link MinepediaMenuItem Menu Item}
     *
     * @param entry {@link MinepediaMenuItem The Menu Item to select}
     */
    @Override
    public void setSelected(final @Nullable MinepediaMenuItem entry) {
        if (entry != null && !entry.isHeader) {
            super.setSelected(entry);
            this.parentScreen.selectMenuItem(entry);
        }
    }

    /**
     * Get the {@link Integer Scrollbar X coordinate}
     *
     * @return The {@link Integer Scrollbar X coordinate}
     */
    @Override
    protected int getScrollbarPositionX() {
        return this.width + 5;
    }

    /**
     * Get the {@link Integer Entry Row Width}
     *
     * @return The {@link Integer Entry Row Width}
     */
    @Override
    public int getRowWidth() {
        return this.width - (Math.max(0, this.getMaxPosition() - (this.getBottom() - this.getY() - 4)) > 0 ? 18 : 12);
    }

    /**
     * Get the {@link Integer Entry Row starting coordinate}
     *
     * @return The {@link Integer Entry Row starting coordinate}
     */
    @Override
    public int getRowLeft() {
        return this.getX() + 6;
    }

    /**
     * Get the {@link Integer maximum entry position}
     *
     * @return The {@link Integer maximum entry position}
     */
    @Override
    protected int getMaxPosition() {
        return super.getMaxPosition() + 4;
    }

    /**
     * Inner class for a {@link MinepediaMenuWidget Minepedia Menu Entry}
     */
    @Environment(EnvType.CLIENT)
    public class MinepediaMenuItem extends AlwaysSelectedEntryListWidget.Entry<MinepediaMenuItem> {

        /**
         * {@link String The entry key}
         */
        private final String key;
        /**
         * {@link Text The Entry Text}
         */
        private final Text text;
        /**
         * {@link ImageData The Image data}
         */
        private final ImageData image;
        /**
         * {@link Boolean If the Entry represents an header}
         */
        private final boolean isHeader;

        /**
         * Constructor. Set the {@link String Entry Title}
         *
         * @param title {@link String The Entry Title}
         */
        public MinepediaMenuItem(final String title) {
            this(title, false, null);
        }

        /**
         * Constructor. Set the {@link String Entry Title}
         *
         * @param title {@link String The Entry Title}
         * @param image {@link ImageData The Image data}
         */
        public MinepediaMenuItem(final String title, final ImageData image) {
            this(title, false, image);
        }

        /**
         * Constructor. Set the {@link String Entry Title} and if
         * the entry represents an header
         *
         * @param title {@link String The Entry Title}
         * @param isHeader {@link Boolean If the Entry represents an header}
         */
        public MinepediaMenuItem(final String title, final boolean isHeader) {
            this(title, isHeader, null);
        }

        /**
         * Constructor. Set the {@link String Entry Title} and if
         * the entry represents an header
         *
         * @param title {@link String The Entry Title}
         * @param isHeader {@link Boolean If the Entry represents an header}
         * @param image {@link ImageData The Image data}
         */
        public MinepediaMenuItem(final String title, final boolean isHeader, final ImageData image) {
            this.key = title;
            this.text = title == null || title.isBlank() || title.isEmpty() ? Text.empty() : Text.translatable("menu." + Minepedia.MOD_ID + "." + (isHeader ? "header." : "") + title);
            this.isHeader = isHeader;
            this.image = image;
        }

        /**
         * Get the {@link Text Narrator Text}
         *
         * @return The {@link Text Narrator Text}
         */
        @Override
        public Text getNarration() {
            return Text.translatable("narrator.select", this.text);
        }

        /**
         * Get the {@link Text styled Text} to render inside the entry
         *
         * @return The {@link Text styled Text}
         */
        private Text getStyledText() {
            return this.text.copy().setStyle(Style.EMPTY.withItalic(this.isHeader));
        }

        /**
         * Get the {@link Integer Text color}
         *
         * @return The {@link Integer Text color}
         */
        private int getTextColor() {
            return this.isHeader ? 0x666666 : 0xFFFFFF;
        }

        /**
         * Render a menu entry
         *
         * @param context {@link DrawContext The Draw Context}
         * @param index {@link Integer The entry index}
         * @param y {@link Integer The entry Y coordinate}
         * @param x {@link Integer The entry X coordinate}
         * @param entryWidth {@link Integer The entry width}
         * @param entryHeight {@link Integer The entry width}
         * @param mouseX {@link Integer The mouse X coordinate}
         * @param mouseY {@link Integer The mouse Y coordinate}
         * @param hovered {@link Boolean If the entry is hovered}
         * @param tickDelta {@link Float The screen delta time}
         */
        @Override
        public void render(final DrawContext context, final int index, final int y, final int x, final int entryWidth, final int entryHeight, final int mouseX, final int mouseY, final boolean hovered, final float tickDelta) {
            context.drawTextWithShadow(MinepediaMenuWidget.this.client.textRenderer, this.getStyledText(), x + 5, y + 2, this.getTextColor());
        }

        /**
         * Select the entry on mouse click
         *
         * @param mouseX {@link Integer The mouse X coordinate}
         * @param mouseY {@link Integer The mouse Y coordinate}
         * @param button {@link Integer The mouse button that has been clicked}
         * @return {@link Boolean#TRUE True}
         */
        @Override
        public boolean mouseClicked(final double mouseX, final double mouseY, final int button) {
            MinepediaMenuWidget.this.setSelected(this);
            return true;
        }

        /**
         * Get the {@link String entry key}
         *
         * @return The {@link String entry key}
         */
        public String getKey() {
            return this.key;
        }

        /**
         * Get the {@link ImageData Image data}
         *
         * @return {@link ImageData The Image data}
         */
        public ImageData getImage() {
            return this.image;
        }

    }

    /**
     * Record class for some Image data
     *
     * @param name {@link String The image name}
     * @param width {@link Integer The image width}
     * @param height {@link Integer The image height}
     * @param position {@link ImagePosition The image position}
     */
    public record ImageData(String name, int width, int height, ImagePosition position) {

        /**
         * Get the {@link Identifier texture identifier}
         *
         * @return The {@link Identifier texture identifier}
         */
        public Identifier getTexture() {
            return new Identifier(Minepedia.MOD_ID, "entries/images/" + name + ".png");
        }

    }

    /**
     * The image positions
     */
    enum ImagePosition {
        START,
        END
    }

}