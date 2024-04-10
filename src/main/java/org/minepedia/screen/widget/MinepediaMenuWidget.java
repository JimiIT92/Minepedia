package org.minepedia.screen.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.minepedia.Minepedia;
import org.minepedia.screen.MinepediaScreen;

import java.util.Arrays;
import java.util.Objects;

/**
 * {@link Minepedia Minepedia} menu list widget
 */
@Environment(EnvType.CLIENT)
public class MinepediaMenuWidget extends AlwaysSelectedEntryListWidget<MinepediaMenuWidget.MinepediaMenuItem> {

    /**
     * The {@link Identifier GUI Arrows Texture Identifier}
     */
    private final Identifier ARROWS_TEXTURE = new Identifier(Minepedia.MOD_ID, "textures/gui/arrows.png");
    /**
     * {@link MinepediaScreen The Minepedia main screen}
     */
    private final MinepediaScreen parentScreen;
    /**
     * {@link Integer The Widget Y offset}
     */
    public static final int WIDGET_Y_OFFSET = 20;
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
    }

    /**
     * Initialize the {@link MinepediaMenuWidget Menu} with the provided {@link MinepediaMenuItem Menu Items}
     *
     * @param menuItems The {@link MinepediaMenuItem Menu Items}
     */
    public void init(final MinepediaMenuItem... menuItems) {
        if(menuItems != null) {
            Arrays.stream(menuItems).map(entry -> entry.setMenu(this)).forEach(this::addEntry);
        }
    }

    /**
     * Select a {@link MinepediaMenuItem Menu Item}
     *
     * @param entry {@link MinepediaMenuItem The Menu Item to select}
     */
    @Override
    public void setSelected(final @Nullable MinepediaMenuItem entry) {
        if (entry != null && !entry.isHeader) {
            if(entry.screen != null) {
                this.client.setScreen(entry.screen);
            } else {
                super.setSelected(entry);
                this.parentScreen.selectMenuItem(entry);
            }
        }
    }

    /**
     * Check if a {@link MinepediaMenuItem Menu Item} is selected
     *
     * @param index {@link Integer The list index}
     * @return {@link Boolean True if the Menu Item is selected}
     */
    @Override
    protected boolean isSelectedEntry(final int index) {
        final MinepediaMenuItem selected =  this.getSelectedOrNull();
        return selected != null && this.getEntry(index).getKey().equals(selected.getKey());
    }

    /**
     * Get the {@link Integer Scrollbar X coordinate}
     *
     * @return The {@link Integer Scrollbar X coordinate}
     */
    @Override
    protected int getScrollbarPositionX() {
        return this.width;
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
    public static class MinepediaMenuItem extends AlwaysSelectedEntryListWidget.Entry<MinepediaMenuItem> {

        /**
         * {@link MinepediaSection The entry section}
         */
        private final MinepediaSection section;
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
         * The {@link MinepediaMenuWidget parent Menu}
         */
        private MinepediaMenuWidget menu;
        /**
         * The {@link MinepediaScreen Screen} to open when selecting this item
         */
        private MinepediaScreen screen;

        /**
         * Constructor. Set the {@link String Entry Title}
         *
         * @param section {@link MinepediaSection The Entry Section}
         * @param title {@link String The Entry Title}
         */
        public MinepediaMenuItem(final MinepediaSection section, final String title) {
            this(section, title, false, null);
        }

        /**
         * Constructor. Set the {@link String Entry Title}
         *
         * @param section {@link MinepediaSection The Entry Section}
         * @param title {@link String The Entry Title}
         * @param image {@link ImageData The Image data}
         */
        public MinepediaMenuItem(final MinepediaSection section, final String title, final ImageData image) {
            this(section, title, false, image);
        }

        /**
         * Constructor. Set the {@link String Entry Title} and if
         * the entry represents an header
         *
         * @param section {@link MinepediaSection The Entry Section}
         * @param title {@link String The Entry Title}
         * @param isHeader {@link Boolean If the Entry represents an header}
         */
        public MinepediaMenuItem(final MinepediaSection section, final String title, final boolean isHeader) {
            this(section, title, isHeader, null);
        }

        /**
         * Constructor. Set the {@link String Entry Title} and if
         * the entry represents an header
         *
         * @param section {@link MinepediaSection The Entry Section}
         * @param title {@link String The Entry Title}
         * @param isHeader {@link Boolean If the Entry represents an header}
         * @param image {@link ImageData The Image data}
         */
        public MinepediaMenuItem(final MinepediaSection section, final String title, final boolean isHeader, final ImageData image) {
            this.section = section;
            this.key = title;
            this.text = title == null || title.isBlank() || title.isEmpty() ? Text.empty() : Text.translatable("menu." + Minepedia.MOD_ID + "." + (isHeader ? "header." : "") + title);
            this.isHeader = isHeader;
            this.image = image;
        }

        /**
         * Set the {@link MinepediaMenuWidget parent menu}
         *
         * @param menu {@link MinepediaMenuWidget The parent menu}
         * @return {@link MinepediaMenuItem The menu Item}
         */
        public MinepediaMenuItem setMenu(final MinepediaMenuWidget menu) {
            this.menu = menu;
            return this;
        }

        /**
         * Set the {@link MinepediaScreen Screen to open when selecting this item}
         *
         * @param screen {@link MinepediaScreen The Screen to open when selecting this item}
         * @return {@link MinepediaMenuItem The menu Item}
         */
        public MinepediaMenuItem setScreen(final MinepediaScreen screen) {
            this.screen = screen;
            return this;
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
            if(this.menu != null) {
                context.drawText(this.menu.client.textRenderer, this.getStyledText(), x + 5, y + 2, this.getTextColor(), false);
                if(this.screen != null) {
                    context.drawTexture(this.menu.ARROWS_TEXTURE, entryWidth - 5, y - 5, hovered ? 14 : 0 ,0, 14, 22, 32, 32);
                }
            }
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
            if(this.menu != null) {
                this.menu.setSelected(this);
                this.menu.client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0f));
            }
            return true;
        }

        /**
         * Get the {@link MinepediaSection entry section}
         *
         * @return The {@link MinepediaSection entry section}
         */
        public MinepediaSection getSection() {
            return this.section;
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
    public enum ImagePosition {
        START,
        END
    }

    /**
     * {@link Minepedia Minepedia} sections
     */
    public enum MinepediaSection {
        INDEX,
        ENCYCLOPEDIA
    }

}