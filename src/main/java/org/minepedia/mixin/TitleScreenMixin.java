package org.minepedia.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
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
     * Constructor. Set the {@link Text screen title}
     *
     * @param title {@link Text The screen title}
     */
    public TitleScreenMixin(final Text title) {
        super(title);
    }

    /**
     * Add the {@link Minepedia Minepedia} button to the main menu
     *
     * @param callbackInfo {@link CallbackInfo The callback info}
     */
    @Inject(method = "init", at = @At(value = "RETURN", target = "Lnet/minecraft/client/gui/screen/TitleScreen;init()V"))
    private void onInitWidgets(final CallbackInfo callbackInfo) {
        final TextIconButtonWidget minepediaButton = this.addDrawableChild(
                TextIconButtonWidget.builder(Text.translatable("ui.minepedia.how_to_play"), button -> MinecraftClient.getInstance().setScreen(new MinepediaIndexScreen()), true)
                        .width(20)
                        .texture(new Identifier(Minepedia.MOD_ID, "icon/" + Minepedia.MOD_ID), 16, 16)
                        .build()
        );
        final Element singlePlayerElement = this.children().stream().filter(children -> children instanceof ButtonWidget buttonWidget && buttonWidget.getMessage().equals(Text.translatable("menu.singleplayer"))).findFirst().orElse(null);
        if(singlePlayerElement instanceof ButtonWidget singlePlayerButton) {
            minepediaButton.setPosition(singlePlayerButton.getRight() + 4, singlePlayerButton.getY());
        }
    }

}