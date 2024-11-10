package me.lukasabbe.trustedshopping.mixin.client;

import me.lukasabbe.trustedshopping.screens.CreateShopScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GenericContainerScreen.class)
public class ChestScreenMixin extends Screen {

    protected ChestScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    public void initScreen(GenericContainerScreenHandler handler, PlayerInventory inventory, Text title, CallbackInfo ci){
        ButtonWidget createShopButton = ButtonWidget.builder(Text.translatable("trustedshopping.container.button_create"), button -> {
            CreateShopScreen shopScreen = new CreateShopScreen(this);
            client.setScreen(shopScreen);
        }).dimensions(10, MinecraftClient.getInstance().getWindow().getScaledHeight()-30, 80, 20).build();
        addDrawableChild(createShopButton);
    }
}
