package org.minepedia.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import org.minepedia.Minepedia;
import org.minepedia.screen.widget.MinepediaMenuWidget;

/**
 * {@link Minepedia Minepedia} index screen
 */
@Environment(EnvType.CLIENT)
public final class MinepediaIndexScreen extends MinepediaScreen {

    /**
     * Constructor. Set the {@link MinepediaMenuWidget.MinepediaMenuItem Screen Entries}
     */
    public MinepediaIndexScreen() {
        super(
                "how_to_play",
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.INDEX, "getting_started", true),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.INDEX, "moving_around", new MinepediaMenuWidget.ImageData("moving_around", 512, 304, MinepediaMenuWidget.ImagePosition.END, 100)),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.INDEX, "gathering_resources"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.INDEX, "selecting_items", new MinepediaMenuWidget.ImageData("selecting_items", 512, 272, MinepediaMenuWidget.ImagePosition.END, 130)),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.INDEX, "placing_blocks"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.INDEX, "inventory", new MinepediaMenuWidget.ImageData("inventory", 512, 281, MinepediaMenuWidget.ImagePosition.START)),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.INDEX, "preparing_for_the_night", true),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.INDEX, "your_first_craft", new MinepediaMenuWidget.ImageData("your_first_craft", 512, 272, MinepediaMenuWidget.ImagePosition.START)),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.INDEX, "recipe_book"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.INDEX, "the_crafting_table", new MinepediaMenuWidget.ImageData("the_crafting_table", 512, 304, MinepediaMenuWidget.ImagePosition.START)),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.INDEX, "crafting_a_tool"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.INDEX, "mining", new MinepediaMenuWidget.ImageData("mining", 512, 304, MinepediaMenuWidget.ImagePosition.START)),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.INDEX, "surviving_the_first_night", true),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.INDEX, "nightfall"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.INDEX, "building_a_shelter", new MinepediaMenuWidget.ImageData("building_a_shelter", 512, 304, MinepediaMenuWidget.ImagePosition.START)),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.INDEX, "death_and_respawn"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.INDEX, "getting_settled", true),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.INDEX, "food"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.INDEX, "beds"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.INDEX, "improved_tools"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.INDEX, "", true),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.INDEX, "encyclopedia").setScreenSupplier(MinepediaEncyclopediaScreen::new)
        );
    }

    /**
     * Get the {@link Screen Parent Screen}
     *
     * @return {@link Screen The Parent Screen}
     */
    protected MinepediaScreen getParent() {
        return null;
    }

}