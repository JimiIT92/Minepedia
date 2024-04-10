package org.minepedia.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import org.minepedia.Minepedia;
import org.minepedia.screen.widget.MinepediaMenuWidget;

import java.io.*;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Optional;

/**
 * Utility methods for getting Assets
 */
@Environment(EnvType.CLIENT)
public final class AssetUtils {

    /**
     * The {@link GameOptions Game Options reference}
     */
    private static final GameOptions OPTIONS = MinecraftClient.getInstance().options;

    /**
     * Get the {@link ModContainer Mod Container}
     *
     * @return The {@link Optional<ModContainer> Mod Container}
     */
    public static Optional<ModContainer> getModContainer() {
        return FabricLoader.getInstance().getModContainer(Minepedia.MOD_ID);
    }

    /**
     * Get an {@link Path asset}
     *
     * @param path {@link String The asset path}
     * @return The {@link Optional<Path> asset path, if any}
     */
    public static Optional<Path> getAsset(final String path) {
        return getModContainer().flatMap(modContainer -> modContainer.findPath("assets/" + Minepedia.MOD_ID + "/" + path));
    }

    /**
     * Read a {@link Minepedia Minepedia entry file}
     *
     * @param section {@link MinepediaMenuWidget.MinepediaSection The entry section}
     * @param entryTitle {@link String The entry title}
     * @return {@link String The entry file text}
     */
    public static String readEntry(final MinepediaMenuWidget.MinepediaSection section, final String entryTitle) {
        return getEntry(section, entryTitle).map(AssetUtils::readEntryFile).orElse("");
    }

    /**
     * Get a {@link Minepedia Minepedia entry file path}
     *
     * @param section {@link MinepediaMenuWidget.MinepediaSection The entry section}
     * @param entryTitle {@link String The entry title}
     * @return The {@link Optional<Path> Entry file path, if any}
     */
    private static Optional<Path> getEntry(final MinepediaMenuWidget.MinepediaSection section, final String entryTitle) {
        return getAsset("entries/" + OPTIONS.language.toLowerCase(Locale.ROOT) + "/" + section.name().toLowerCase(Locale.ROOT) + "/" + entryTitle + ".mpe");
    }

    /**
     * Read a {@link Path text file}
     *
     * @param filePath {@link Path The file path}
     * @return {@link String The file text}
     */
    private static String readFile(final Path filePath) {
        try {
            InputStream inputStream = new FileInputStream(filePath.toFile());
            StringBuilder resultStringBuilder = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    resultStringBuilder.append(line).append("\n");
                }
            } catch (IOException e) {
                return "";
            }
            return resultStringBuilder.toString();
        } catch (FileNotFoundException e) {
            return "";
        }
    }

    /**
     * Read a {@link Minepedia Minepedia entry file}
     *
     * @param path {@link Path The entry file path}
     * @return {@link String The entry text}
     */
    private static String readEntryFile(final Path path) {
        return replacePlaceholderEntryValues(readFile(path));
    }

    /**
     * Replace the {@link String entry file text} placeholder values
     *
     * @param rawEntryFile {@link String The raw entry file text}
     * @return {@link String The finalized entry file text}
     */
    private static String replacePlaceholderEntryValues(String rawEntryFile) {
        rawEntryFile = rawEntryFile.replace("{{ctrl.up}}", getKey(OPTIONS.forwardKey));
        rawEntryFile = rawEntryFile.replace("{{ctrl.left}}", getKey(OPTIONS.leftKey));
        rawEntryFile = rawEntryFile.replace("{{ctrl.down}}", getKey(OPTIONS.backKey));
        rawEntryFile = rawEntryFile.replace("{{ctrl.right}}", getKey(OPTIONS.rightKey));
        rawEntryFile = rawEntryFile.replace("{{ctrl.jump}}", getKey(OPTIONS.jumpKey));
        rawEntryFile = rawEntryFile.replace("{{ctrl.attack}}", getKey(OPTIONS.attackKey));
        rawEntryFile = rawEntryFile.replace("{{ctrl.use}}", getKey(OPTIONS.useKey));
        rawEntryFile = rawEntryFile.replace("{{ctrl.inventory}}", getKey(OPTIONS.inventoryKey));
        rawEntryFile = rawEntryFile.replace("{{ctrl.hotbar.first}}", getKey(OPTIONS.hotbarKeys[0]));
        rawEntryFile = rawEntryFile.replace("{{ctrl.hotbar.last}}", getKey(OPTIONS.hotbarKeys[OPTIONS.hotbarKeys.length - 1]));
        return Text.translatable(rawEntryFile).getString();
    }

    /**
     * Get the {@link String translated bound key}
     *
     * @param keyBinding {@link KeyBinding The key binding}
     * @return The {@link String translated bound key}
     */
    private static String getKey(final KeyBinding keyBinding) {
        return keyBinding.getBoundKeyLocalizedText().getString();
    }

}