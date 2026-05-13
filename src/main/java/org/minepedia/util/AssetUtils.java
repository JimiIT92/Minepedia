package org.minepedia.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.minepedia.Minepedia;
import org.minepedia.screen.widget.MinepediaMenuWidget;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;

/**
 * Utility methods for getting Assets
 */
@Environment(EnvType.CLIENT)
public final class AssetUtils {

    /**
     * The {@link Options Game Options reference}
     */
    private static final Options OPTIONS = Minecraft.getInstance().options;

    /**
     * Get an {@link Identifier asset Identifier}
     *
     * @param asset {@link String The asset Identifier}
     * @return The {@link Identifier asset Identifier, if any}
     */
    public static Identifier getAsset(final String asset) {
        return Identifier.fromNamespaceAndPath(Minepedia.MOD_ID, asset);
    }

    /**
     * Read a {@link Minepedia Minepedia entry file}
     *
     * @param section {@link MinepediaMenuWidget.MinepediaSection The entry section}
     * @param entryTitle {@link String The entry title}
     * @return {@link String The entry file text}
     */
    public static String readEntry(final MinepediaMenuWidget.MinepediaSection section, final String entryTitle) {
        final Identifier assetIdentifier = getEntry(section, entryTitle);
        return readEntryFile(assetIdentifier);
    }

    /**
     * Get a {@link Minepedia Minepedia entry file Identifier}
     *
     * @param section {@link MinepediaMenuWidget.MinepediaSection The entry section}
     * @param entryTitle {@link String The entry title}
     * @return The {@link Identifier Entry file Identifier, if any}
     */
    private static Identifier getEntry(final MinepediaMenuWidget.MinepediaSection section, final String entryTitle) {
        return getAsset("entries/" + OPTIONS.languageCode.toLowerCase(Locale.ROOT) + "/" + section.name().toLowerCase(Locale.ROOT) + "/" + entryTitle + ".mpe");
    }

    /**
     * Read a {@link Path text file}
     *
     * @param fileIdentifier {@link Identifier The file identifier}
     * @return {@link String The file text}
     */
    private static String readFile(final Identifier fileIdentifier) {
        final StringBuilder resultStringBuilder = new StringBuilder();
        try {
            try (final BufferedReader reader = Minecraft.getInstance().getResourceManager().openAsReader(fileIdentifier)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    resultStringBuilder.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            return "";
        }
        return resultStringBuilder.toString();
    }

    /**
     * Read a {@link Minepedia Minepedia entry file}
     *
     * @param identifier {@link Identifier The entry file Identifier}
     * @return {@link String The entry text}
     */
    private static String readEntryFile(final Identifier identifier) {
        return replacePlaceholderEntryValues(readFile(identifier));
    }

    /**
     * Replace the {@link String entry file text} placeholder values
     *
     * @param rawEntryFile {@link String The raw entry file text}
     * @return {@link String The finalized entry file text}
     */
    private static String replacePlaceholderEntryValues(String rawEntryFile) {
        rawEntryFile = rawEntryFile.replace("{{ctrl.up}}", getKey(OPTIONS.keyUp));
        rawEntryFile = rawEntryFile.replace("{{ctrl.left}}", getKey(OPTIONS.keyLeft));
        rawEntryFile = rawEntryFile.replace("{{ctrl.down}}", getKey(OPTIONS.keyDown));
        rawEntryFile = rawEntryFile.replace("{{ctrl.right}}", getKey(OPTIONS.keyRight));
        rawEntryFile = rawEntryFile.replace("{{ctrl.jump}}", getKey(OPTIONS.keyJump));
        rawEntryFile = rawEntryFile.replace("{{ctrl.attack}}", getKey(OPTIONS.keyAttack));
        rawEntryFile = rawEntryFile.replace("{{ctrl.use}}", getKey(OPTIONS.keyUse));
        rawEntryFile = rawEntryFile.replace("{{ctrl.inventory}}", getKey(OPTIONS.keyInventory));
        rawEntryFile = rawEntryFile.replace("{{ctrl.sneak}}", getKey(OPTIONS.keyShift));
        rawEntryFile = rawEntryFile.replace("{{ctrl.sprint}}", getKey(OPTIONS.keySprint));
        rawEntryFile = rawEntryFile.replace("{{ctrl.hotbar.first}}", getKey(OPTIONS.keyHotbarSlots[0]));
        rawEntryFile = rawEntryFile.replace("{{ctrl.hotbar.last}}", getKey(OPTIONS.keyHotbarSlots[OPTIONS.keyHotbarSlots.length - 1]));
        return Component.translatable(rawEntryFile).getString();
    }

    /**
     * Get the {@link String translated bound key}
     *
     * @param keyBinding {@link KeyMapping The key binding}
     * @return The {@link String translated bound key}
     */
    private static String getKey(final KeyMapping keyBinding) {
        return keyBinding.getTranslatedKeyMessage().getString().toUpperCase(Locale.ROOT);
    }

}