package org.minepedia.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.jetbrains.annotations.Nullable;
import org.minepedia.Minepedia;
import org.minepedia.screen.widget.MinepediaEntryWidget;
import org.minepedia.screen.widget.MinepediaMenuWidget;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public abstract class MinepediaScreen extends Screen {

    private final Identifier ARROWS_TEXTURE = Identifier.of(Minepedia.MOD_ID, "textures/gui/arrows.png");
    private MinepediaEntriesWidget menuEntries;
    private final List<MinepediaMenuWidget.MinepediaMenuItem> menuItems;
    private final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this);
    private MinepediaMenuWidget.MinepediaMenuItem selectedMenuEntry;
    private MinepediaEntryWidget content;
    private TextWidget header;

    /**
     * Constructor. Set the {@link Screen Screen} {@link Text Title}
     *
     * @param title {@link String The Screen title}
     * @param menuItems {@link MinepediaMenuWidget.MinepediaMenuItem The menu items}
     */
    public MinepediaScreen(final String title, final MinepediaMenuWidget.MinepediaMenuItem... menuItems) {
        super(Text.translatable("screen." + Minepedia.MOD_ID + "." + title));
        this.menuItems = Arrays.asList(menuItems);
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

    protected void init() {
        this.header = new TextWidget(Text.literal(""), this.textRenderer);
        this.header.setWidth(this.width);
        DirectionalLayoutWidget directionalLayoutWidget = this.layout.addHeader(DirectionalLayoutWidget.vertical().spacing(8));
        directionalLayoutWidget.add(this.header, Positioner::alignHorizontalCenter);
        GridWidget gridWidget = new GridWidget().setColumnSpacing(8);
        gridWidget.getMainPositioner().marginX(4).marginBottom(4).alignHorizontalCenter();
        GridWidget.Adder adder = gridWidget.createAdder(2);

        this.menuEntries = new MinepediaEntriesWidget();
        this.content = new MinepediaEntryWidget(this.width / 2, this.layout.getHeaderHeight(), this.width / 2, this.height - 77, this.textRenderer);

        this.menuEntries.setSelected(this.menuEntries.children().stream().filter(item -> !item.menuItem.isHeader()).findFirst().orElse(null));

        adder.add(this.menuEntries);
        adder.add(this.content);

        this.layout.addBody(gridWidget);
        this.layout.forEachChild(this::addDrawableChild);
        this.refreshWidgetPositions();
    }

    protected void refreshWidgetPositions() {
        this.layout.refreshPositions();
        this.menuEntries.position(this.width / 2, this.layout);
        this.content.setDimensions(this.width / 2, this.layout.getContentHeight());
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
    class MinepediaEntriesWidget extends AlwaysSelectedEntryListWidget<MinepediaEntriesWidget.MinepediaEntryItem> {
        MinepediaEntriesWidget() {
            super(MinepediaScreen.this.client, MinepediaScreen.this.width / 2, MinepediaScreen.this.height - 77, 0, 16);
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
                    Objects.requireNonNull(MinepediaScreen.this.client).setScreen(menuItem.menuItem.screenSupplier.get());
                } else {
                    MinepediaScreen.this.selectedMenuEntry = menuItem.menuItem;
                    MinepediaScreen.this.setHeader();
                    MinepediaScreen.this.setContent();
                }
            }
        }

        @Environment(EnvType.CLIENT)
        class MinepediaEntryItem extends AlwaysSelectedEntryListWidget.Entry<MinepediaScreen.MinepediaEntriesWidget.MinepediaEntryItem> {
            final MinepediaMenuWidget.MinepediaMenuItem menuItem;
            final Text text;

            public MinepediaEntryItem(final MinepediaMenuWidget.MinepediaMenuItem menuItem) {
                this.menuItem = menuItem;
                this.text = menuItem.getStyledText();
            }

            public Text getNarration() {
                return Text.translatable("narrator.select", this.text);
            }

            /**
             * Render a menu entry
             *
             * @param context {@link DrawContext The Draw Context}
             * @param mouseX {@link Integer The mouse X coordinate}
             * @param mouseY {@link Integer The mouse Y coordinate}
             * @param hovered {@link Boolean If the entry is hovered}
             * @param deltaTicks {@link Float The delta ticks}
             */
            @Override
            public void render(final DrawContext context, final int mouseX, final int mouseY, final boolean hovered, final float deltaTicks) {
                int color = this.menuItem.isHeader() ? -1 : ColorHelper.fromFloats(1F, 0.6F, 0.6F, 0.6F);
                context.drawText(MinepediaScreen.this.textRenderer, this.text, this.getX() + 5, this.getY() + 2, color, false);
                if(this.menuItem.screenSupplier != null) {
                    context.drawTexture(RenderPipelines.GUI_TEXTURED, MinepediaScreen.this.ARROWS_TEXTURE, this.getX() + this.getWidth() - 15, this.getY() - 5, hovered ? 14 : 0 ,0, 14, 22, 32, 32);
                }
            }

            /**
             * Select the entry on mouse click
             *
             * @param click {@link Click The mouse click}
             * @param doubled {@link Boolean Whether there has been a double click}
             * @return {@link Boolean#TRUE True}
             */
            @Override
            public boolean mouseClicked(final Click click, final boolean doubled) {
                if(!this.menuItem.isHeader()) {
                    MinepediaScreen.MinepediaEntriesWidget.this.setSelected(this);
                    Objects.requireNonNull(MinepediaScreen.this.client).getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0f));
                    return super.mouseClicked(click, doubled);
                }
                return false;
            }
        }
    }
}
