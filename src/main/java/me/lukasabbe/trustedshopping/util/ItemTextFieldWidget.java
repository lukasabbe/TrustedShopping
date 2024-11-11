package me.lukasabbe.trustedshopping.util;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemTextFieldWidget extends TextFieldWidget {
    private final List<ItemCheckerData> itemIds = new ArrayList<>();
    private Item currentItem = null;

    public ItemTextFieldWidget(TextRenderer textRenderer, int x, int y, int width, int height, Text text) {
        super(textRenderer, x, y, width, height, text);
        Registries.ITEM.forEach(item -> itemIds.add(new ItemCheckerData(item, getKey(item.getTranslationKey()))));
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        if(this.isVisible()){
            if(currentItem!=null)
                context.drawItem(currentItem.getDefaultStack(), this.getX()+this.width,this.getY());
        }
        super.renderWidget(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        var t = super.charTyped(chr, modifiers);
        itemChecker();
        return t;
    }

    @Override
    public void eraseCharactersTo(int position) {
        super.eraseCharactersTo(position);
        itemChecker();
    }

    private void itemChecker(){
        if(this.getText().length() < 3){
            currentItem = null;
            return;
        }
        List<ItemCheckerData> filteredIds = itemIds.stream().filter(item -> getKey(item.translationKey).contains(this.getText())).toList();
        if(!filteredIds.isEmpty())
            System.out.println(getKey(filteredIds.getFirst().translationKey));
        if(filteredIds.stream().anyMatch(item -> getKey(item.translationKey).equals(this.getText()))){
            System.out.println("test");
            currentItem = filteredIds.stream().filter(item -> getKey(item.translationKey).equals(this.getText())).findFirst().get().item;
            return;
        }
        List<ItemCheckerData> moreFilteredIds = filteredIds.stream().filter(item -> getKey(item.translationKey).startsWith(this.getText())).toList();
        if(!moreFilteredIds.isEmpty()){
            currentItem = moreFilteredIds.getFirst().item;
        }else if(!filteredIds.isEmpty()){
            currentItem = filteredIds.getFirst().item;
        }else {
            currentItem = null;
        }

    }
    private String getKey(String translationKey){
        if(!translationKey.isEmpty()) {
            final String[] split = translationKey.split("\\.");
            return split[split.length-1];
        }
        else
            return translationKey;
    }
    private class ItemCheckerData{
        public Item item;
        public String translationKey;

        public ItemCheckerData(Item item, String translationKey) {
            this.item = item;
            this.translationKey = translationKey;
        }
    }


}
