package org.minepedia.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.ScrollableLayout;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LayoutSettings;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.minepedia.Minepedia;

@Environment(EnvType.CLIENT)
public class MinepediaScreenNew extends Screen {

    private static final Component TITLE = Component.translatable("screen." + Minepedia.MOD_ID + ".encyclopedia");
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 61, 33);

    public MinepediaScreenNew() {
        super(TITLE);
    }

    @Override
    protected void init() {
        LinearLayout header = this.layout.addToHeader(LinearLayout.vertical().spacing(8));
        header.addChild(new StringWidget(TITLE, this.font), LayoutSettings::alignHorizontallyCenter);
        GridLayout gridLayout = new GridLayout();
        gridLayout.defaultCellSetting().paddingHorizontal(4).paddingBottom(4).alignHorizontallyCenter().alignVerticallyMiddle();
        GridLayout.RowHelper helper = gridLayout.createRowHelper(2);

        GridLayout entriesLayout = new GridLayout();
        entriesLayout.defaultCellSetting().paddingHorizontal(4).paddingBottom(4).alignVerticallyMiddle();
        GridLayout.RowHelper entriesHelper = entriesLayout.createRowHelper(1);
        entriesHelper.addChild(new StringWidget(Component.literal("1"), this.getFont()));
        entriesHelper.addChild(new StringWidget(Component.literal("2"), this.getFont()));
        entriesHelper.addChild(new StringWidget(Component.literal("3"), this.getFont()));
        entriesHelper.addChild(new StringWidget(Component.literal("4"), this.getFont()));
        entriesHelper.addChild(new StringWidget(Component.literal("5"), this.getFont()));
        entriesHelper.addChild(new StringWidget(Component.literal("6"), this.getFont()));
        entriesHelper.addChild(new StringWidget(Component.literal("7"), this.getFont()));
        entriesHelper.addChild(new StringWidget(Component.literal("8"), this.getFont()));
        entriesHelper.addChild(new StringWidget(Component.literal("9"), this.getFont()));
        entriesHelper.addChild(new StringWidget(Component.literal("10"), this.getFont()));
        entriesHelper.addChild(new StringWidget(Component.literal("11"), this.getFont()));
        entriesHelper.addChild(new StringWidget(Component.literal("12"), this.getFont()));
        entriesHelper.addChild(new StringWidget(Component.literal("13"), this.getFont()));
        entriesHelper.addChild(new StringWidget(Component.literal("14"), this.getFont()));
        entriesHelper.addChild(new StringWidget(Component.literal("15"), this.getFont()));
        entriesHelper.addChild(new StringWidget(Component.literal("16"), this.getFont()));
        entriesHelper.addChild(new StringWidget(Component.literal("17"), this.getFont()));
        entriesHelper.addChild(new StringWidget(Component.literal("18"), this.getFont()));
        entriesHelper.addChild(new StringWidget(Component.literal("19"), this.getFont()));
        entriesHelper.addChild(new StringWidget(Component.literal("20"), this.getFont()));


        GridLayout contentLayout = new GridLayout();
        GridLayout.RowHelper contentHelper = contentLayout.createRowHelper(1);
        contentHelper.addChild(new StringWidget(Component.literal("content1"), this.getFont()));
        contentHelper.addChild(new StringWidget(Component.literal("content2"), this.getFont()));
        contentHelper.addChild(new StringWidget(Component.literal("content3"), this.getFont()));


        helper.addChild(new ScrollableLayout(this.minecraft, entriesLayout, 200));
        helper.addChild(contentLayout);
        this.layout.addToContents(gridLayout);
        this.layout.visitWidgets(this::addRenderableWidget);
        this.repositionElements();
    }

    @Override
    protected void repositionElements() {
        this.layout.arrangeElements();
    }

}
