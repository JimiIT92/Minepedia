package org.minepedia.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import org.minepedia.Minepedia;
import org.minepedia.screen.widget.MinepediaMenuWidget;

/**
 * {@link Minepedia Minepedia} Encyclopedia screen
 */
@Environment(EnvType.CLIENT)
public final class MinepediaEncyclopediaScreen extends MinepediaScreen {

    /**
     * Constructor. Set the {@link MinepediaMenuWidget.MinepediaMenuItem Screen Entries}
     */
    public MinepediaEncyclopediaScreen() {
        super(
                "encyclopedia",
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "overworld_a_z", true),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "armor"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "armor_stand"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "banners"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "beacons"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "beds"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "blocks"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "book_and_quill"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "chests"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "conduits"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "dyes"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "farming"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "fireworks"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "fishing"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "mounts"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "navigation"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "nether_portal", new MinepediaMenuWidget.ImageData("nether_portal", 512, 512, MinepediaMenuWidget.ImagePosition.END, -300)),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "pets"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "raids"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "ranching"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "scaffolding", new MinepediaMenuWidget.ImageData("scaffolding", 512, 512, MinepediaMenuWidget.ImagePosition.END)),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "structure_blocks"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "tools"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "transportation"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "villager_trading"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "weapons"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "stands_and_tables", true),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "anvil"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "brewing_stand"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "cauldron"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "crafting_table"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "enchanting_table"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "furnace"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "loom"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "smithing_table"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "redstone_engineering", true),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "droppers"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "dispensers"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "hoppers"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "jukebox"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "redstone"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "the_end_dimension", true),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "the_end"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "eye_of_ender"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "end_cities"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "elytra"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "shulker_boxes"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "options_and_cheats", true),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "game_settings"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "difficulty"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "adventure_mode"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "creative_mode"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "commands"),
                new MinepediaMenuWidget.MinepediaMenuItem(MinepediaMenuWidget.MinepediaSection.ENCYCLOPEDIA, "command_blocks")
        );
    }

    /**
     * Get the {@link Screen Parent Screen}
     *
     * @return {@link Screen The Parent Screen}
     */
    protected MinepediaScreen getParent() {
        return new MinepediaIndexScreen();
    }

}