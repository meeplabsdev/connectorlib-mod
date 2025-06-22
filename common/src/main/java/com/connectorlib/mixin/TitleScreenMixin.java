package com.connectorlib.mixin;

import com.connectorlib.ModConnector;
import dev.architectury.platform.Platform;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
	protected TitleScreenMixin(Text title) {
		super(title);
	}

	@Inject(method = "initWidgetsNormal", at = @At("TAIL"))
	private void initWidgetsNormal(int y, int spacingY, CallbackInfo ci) {
		if (Platform.isDevelopmentEnvironment()) {
			this.addDrawableChild(ButtonWidget.builder(Text.literal("Reconnect CL"), button -> {
				MinecraftClient minecraftClient = MinecraftClient.getInstance();
				String username = minecraftClient.getSession().getUsername();
				String uuid = minecraftClient.getSession().getUuid();
				ModConnector.setup(username, uuid);
			}).position(this.width / 2 + 110, this.height / 4 + 48).size(100, 20).build());
		}
	}
}
