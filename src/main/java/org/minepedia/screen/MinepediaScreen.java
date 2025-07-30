package org.minepedia.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.RenderPipelines;
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
        DirectionalLayoutWidget directionalLayoutWidget = this.layout.addHeader(DirectionalLayoutWidget.vertical().spacing(8));
        directionalLayoutWidget.add(new TextWidget(Text.literal("Header"), this.textRenderer), Positioner::alignHorizontalCenter);

        this.menuEntries = this.layout.addBody(new MinepediaEntriesWidget());

        //DirectionalLayoutWidget directionalLayoutWidget2 = this.layout.addFooter(DirectionalLayoutWidget.horizontal().spacing(8));
        this.menuEntries.setSelected(this.menuEntries.children().stream().filter(item -> !item.menuItem.isHeader()).findFirst().orElse(null));
        this.layout.forEachChild(this::addDrawableChild);
        this.refreshWidgetPositions();
    }

    protected void refreshWidgetPositions() {
        this.layout.refreshPositions();
        this.menuEntries.position(this.width, this.layout);
    }

    @Environment(EnvType.CLIENT)
    class MinepediaEntriesWidget extends AlwaysSelectedEntryListWidget<MinepediaEntriesWidget.MinepediaEntryItem> {
        MinepediaEntriesWidget() {
            super(MinepediaScreen.this.client, MinepediaScreen.this.width, MinepediaScreen.this.height - 77, 40, 16);
            MinepediaScreen.this.menuItems.stream().map(MinepediaEntryItem::new).forEach(this::addEntry);
        }

        public void setSelected(@Nullable MinepediaScreen.MinepediaEntriesWidget.MinepediaEntryItem menuItem) {
            super.setSelected(menuItem);
            if (menuItem != null && !menuItem.menuItem.isHeader()) {
                if(menuItem.menuItem.screenSupplier != null) {
                    Objects.requireNonNull(MinepediaScreen.this.client).setScreen(menuItem.menuItem.screenSupplier.get());
                } else {
                    MinepediaScreen.this.selectedMenuEntry = menuItem.menuItem;
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
                return Text.translatable("narrator.select", new Object[]{this.text});
            }

            public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickProgress) {
                int color = this.menuItem.isHeader() ? -1 : ColorHelper.fromFloats(1F, 0.6F, 0.6F, 0.6F);
                context.drawText(MinepediaScreen.this.textRenderer, this.text, x + 5, y + 2, color, false);
                if(this.menuItem.screenSupplier != null) {
                    context.drawTexture(RenderPipelines.GUI_TEXTURED, MinepediaScreen.this.ARROWS_TEXTURE, x + entryWidth - 15, y - 5, hovered ? 14 : 0 ,0, 14, 22, 32, 32);
                }
            }

            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                if(!this.menuItem.isHeader()) {
                    MinepediaScreen.MinepediaEntriesWidget.this.setSelected(this);
                    Objects.requireNonNull(MinepediaScreen.this.client).getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0f));
                    return super.mouseClicked(mouseX, mouseY, button);
                }
                return false;
            }
        }
    }
}
