package org.minepedia.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LayoutSettings;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ARGB;
import org.jetbrains.annotations.Nullable;
import org.minepedia.Minepedia;
import org.minepedia.screen.widget.MinepediaEntryWidget;
import org.minepedia.screen.widget.MinepediaMenuWidget;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public abstract class MinepediaScreen extends Screen {

    private final Identifier ARROWS_TEXTURE = Identifier.fromNamespaceAndPath(Minepedia.MOD_ID, "textures/gui/arrows.png");
    private MinepediaEntriesWidget menuEntries;
    private final List<MinepediaMenuWidget.MinepediaMenuItem> menuItems;
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
    private MinepediaMenuWidget.MinepediaMenuItem selectedMenuEntry;
    private MinepediaEntryWidget content;
    private StringWidget header;

    /**
     * Constructor. Set the {@link Screen Screen} {@link Component Title}
     *
     * @param title {@link String The Screen title}
     * @param menuItems {@link MinepediaMenuWidget.MinepediaMenuItem The menu items}
     */
    public MinepediaScreen(final String title, final MinepediaMenuWidget.MinepediaMenuItem... menuItems) {
        super(Component.translatable("screen." + Minepedia.MOD_ID + "." + title));
        this.menuItems = Arrays.asList(menuItems);
    }

    /**
     * Close the {@link Screen Screen}
     */
    @Override
    public void onClose() {
        super.onClose();
        final Screen parent = this.getParent();
        if(parent != null) {
            this.minecraft.setScreen(parent);
        }
    }

    /**
     * Get the {@link Screen Parent Screen}
     *
     * @return {@link Screen The Parent Screen}
     */
    protected abstract Screen getParent();

    protected void init() {
        this.header = new StringWidget(Component.literal(""), this.font);
        this.header.setWidth(this.width);
        LinearLayout directionalLayoutWidget = this.layout.addToHeader(LinearLayout.vertical().spacing(8));
        directionalLayoutWidget.addChild(this.header, LayoutSettings::alignHorizontallyCenter);
        GridLayout gridWidget = new GridLayout().columnSpacing(8);
        gridWidget.defaultCellSetting().paddingHorizontal(4).paddingBottom(4).alignHorizontallyCenter();
        GridLayout.RowHelper adder = gridWidget.createRowHelper(2);

        this.menuEntries = new MinepediaEntriesWidget();
        this.content = new MinepediaEntryWidget(this.width / 2, this.layout.getHeaderHeight(), this.width / 2, this.height - 77, this.font);

        this.menuEntries.setSelected(this.menuEntries.children().stream().filter(item -> !item.menuItem.isHeader()).findFirst().orElse(null));

        adder.addChild(this.menuEntries);
        adder.addChild(this.content);

        this.layout.addToContents(gridWidget);
        this.layout.visitWidgets(this::addRenderableWidget);
        this.repositionElements();
    }

    protected void repositionElements() {
        this.layout.arrangeElements();
        this.menuEntries.updateSize(this.width / 2, this.layout);
        this.content.setSize(this.width / 2, this.layout.getContentHeight());
        this.content.setPosition(this.width / 2, this.layout.getHeaderHeight());
        this.setHeader();
        this.setContent();
    }

    private void setHeader() {
        if(this.selectedMenuEntry != null) {
            this.header.setMessage(this.selectedMenuEntry.getText());
        }
    }

    private void setContent() {
        if(this.selectedMenuEntry != null && this.content != null) {
            this.content.selectEntry(this.selectedMenuEntry);
        }
    }

    @Environment(EnvType.CLIENT)
    class MinepediaEntriesWidget extends ObjectSelectionList<MinepediaEntriesWidget.MinepediaEntryItem> {
        MinepediaEntriesWidget() {
            super(MinepediaScreen.this.minecraft, MinepediaScreen.this.width / 2, MinepediaScreen.this.height - 77, 0, 16);
            MinepediaScreen.this.menuItems.stream().map(MinepediaEntryItem::new).forEach(this::addEntry);
        }

        @Override
        public int getRowWidth() {
            return this.width - (this.width / 6);
        }

        public void setSelected(@Nullable MinepediaScreen.MinepediaEntriesWidget.MinepediaEntryItem menuItem) {
            super.setSelected(menuItem);
            if (menuItem != null && !menuItem.menuItem.isHeader()) {
                if(menuItem.menuItem.screenSupplier != null) {
                    Objects.requireNonNull(MinepediaScreen.this.minecraft).setScreen(menuItem.menuItem.screenSupplier.get());
                } else {
                    MinepediaScreen.this.selectedMenuEntry = menuItem.menuItem;
                    MinepediaScreen.this.setHeader();
                    MinepediaScreen.this.setContent();
                }
            }
        }

        @Environment(EnvType.CLIENT)
        class MinepediaEntryItem extends ObjectSelectionList.Entry<MinepediaScreen.MinepediaEntriesWidget.MinepediaEntryItem> {
            final MinepediaMenuWidget.MinepediaMenuItem menuItem;
            final Component text;

            public MinepediaEntryItem(final MinepediaMenuWidget.MinepediaMenuItem menuItem) {
                this.menuItem = menuItem;
                this.text = menuItem.getStyledText();
            }

            public Component getNarration() {
                return Component.translatable("narrator.select", this.text);
            }

            /**
             * Render a menu entry
             *
             * @param context {@link GuiGraphicsExtractor The Draw Context}
             * @param mouseX {@link Integer The mouse X coordinate}
             * @param mouseY {@link Integer The mouse Y coordinate}
             * @param hovered {@link Boolean If the entry is hovered}
             * @param deltaTicks {@link Float The delta ticks}
             */
            @Override
            public void extractContent(final GuiGraphicsExtractor context, final int mouseX, final int mouseY, final boolean hovered, final float deltaTicks) {
                int color = this.menuItem.isHeader() ? -1 : ARGB.colorFromFloat(1F, 0.6F, 0.6F, 0.6F);
                context.text(MinepediaScreen.this.font, this.text, this.getX() + 5, this.getY() + 2, color, false);
                if(this.menuItem.screenSupplier != null) {
                    context.blit(RenderPipelines.GUI_TEXTURED, MinepediaScreen.this.ARROWS_TEXTURE, this.getX() + this.getWidth() - 15, this.getY() - 5, hovered ? 14 : 0 ,0, 14, 22, 32, 32);
                }
            }

            /**
             * Select the entry on mouse click
             *
             * @param click {@link MouseButtonEvent The mouse click}
             * @param doubled {@link Boolean Whether there has been a double click}
             * @return {@link Boolean#TRUE True}
             */
            @Override
            public boolean mouseClicked(final MouseButtonEvent click, final boolean doubled) {
                if(!this.menuItem.isHeader()) {
                    MinepediaScreen.MinepediaEntriesWidget.this.setSelected(this);
                    Objects.requireNonNull(MinepediaScreen.this.minecraft).getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0f));
                    return super.mouseClicked(click, doubled);
                }
                return false;
            }
        }
    }
}
