package me.lukasabbe.trustedshopping.screens;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class CreateShopScreen extends Screen {

    private final Screen parentScreen;

    private TextFieldWidget nameWidget;

    public CreateShopScreen(Screen parent) {
        super(Text.translatable("trustedshopping.screen.title.create"));
        parentScreen = parent;
    }

    @Override
    protected void init() {
        final Window window = MinecraftClient.getInstance().getWindow();
        int width = window.getScaledWidth();
        int height = window.getScaledHeight();
        addDrawable(
                new TextWidget(
                        (width / 2) - 100,
                        10,
                        200,
                        15,
                        Text.translatable("trustedshopping.screen.name.widget"),
                        textRenderer)
        );

        nameWidget = new TextFieldWidget(this.textRenderer, (width / 2) - 50, 30, 100, 15, Text.literal("Test"));
        nameWidget.setFocusUnlocked(true);
        nameWidget.setDrawsBackground(true);
        nameWidget.setMaxLength(30);
        addDrawableChild(nameWidget);
        nameWidget.setEditable(true);


    }

    @Override
    protected void setInitialFocus() {
        this.setInitialFocus(nameWidget);
    }

    @Override
    public void close() {
        client.setScreen(parentScreen);
    }
}
