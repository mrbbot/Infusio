package com.mrbbot.infusio.gui.guide;

import com.mrbbot.infusio.gui.GuiInfusersGuide;
import net.minecraft.item.ItemStack;

class PositionedItemStack {
    private ItemStack itemStack;
    private int x;
    private int y;

    PositionedItemStack(ItemStack itemStack, int x, int y) {
        this.itemStack = itemStack;
        this.x = x;
        this.y = y;
    }

    void renderItem(GuiInfusersGuide gui, int xOffset, int yOffset) {
        int xPosition = x + xOffset;
        int yPosition = y + yOffset;
        gui.doRenderItem(itemStack, x + xOffset, y + yOffset);
    }

    void renderItemTooltip(GuiInfusersGuide gui, int xOffset, int yOffset, int mouseX, int mouseY) {
        int xPosition = x + xOffset;
        int yPosition = y + yOffset;
        if(mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + 16 && mouseY < yPosition + 16) {
            gui.doRenderTooltip(itemStack, mouseX, mouseY);
        }
    }
}
