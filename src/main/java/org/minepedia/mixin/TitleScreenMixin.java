package org.minepedia.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.minepedia.Minepedia;
import org.minepedia.screen.MinepediaIndexScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * {@link TitleScreen Game Menu Screen} mixin
 */
@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    /**
     * Constructor. Set the {@link Component screen title}
     *
     * @param title {@link Component The screen title}
     */
    public TitleScreenMixin(final Component title) {
        super(title);
    }

    /**
     * Add the {@link Minepedia Minepedia} button to the main menu
     *
     * @param callbackInfo {@link CallbackInfo The callback info}
     */
    @Inject(method = "init", at = @At(value = "RETURN", target = "Lnet/minecraft/client/gui/screen/TitleScreen;init()V"))
    private void onInitWidgets(final CallbackInfo callbackInfo) {
        if(Minepedia.config().SHOW_MINEPEDIA_BUTTON) {
            final SpriteIconButton minepediaButton = this.addRenderableWidget(
                    SpriteIconButton.builder(Component.translatable("ui.minepedia.how_to_play"),  button -> Minecraft.getInstance().setScreen(new MinepediaIndexScreen()), true)
                            .width(20)
                            .sprite(Identifier.fromNamespaceAndPath(Minepedia.MOD_ID, "icon/" + Minepedia.MOD_ID), 16, 16)
                            .build()
            );
            var singlePlayerElement = this.children().stream().filter(children -> children instanceof Button buttonWidget && buttonWidget.getMessage().equals(Component.translatable("menu.singleplayer"))).findFirst().orElse(null);
            if(singlePlayerElement instanceof Button singlePlayerButton) {
                minepediaButton.setPosition(singlePlayerButton.getRight() + 4, singlePlayerButton.getY());
            }
        }
    }

}