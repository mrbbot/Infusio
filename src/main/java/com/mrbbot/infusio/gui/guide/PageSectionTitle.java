package com.mrbbot.infusio.gui.guide;

import com.mrbbot.infusio.gui.GuiInfusersGuide;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

class PageSectionTitle implements IPageSingle {
    private final ItemStack itemStack;
    private final String title;

    PageSectionTitle(ItemStack itemStack, String title) {
        this.itemStack = itemStack;
        this.title = title;
    }

    @Override
    public void renderPage(GuiInfusersGuide gui, FontRenderer fontRendererObj, RenderItem itemRender, int width, int height, int mouseX, int mouseY, int xOffset, int yOffset) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(1.5, 1.5, 1.5);
        float currentY = (height / 3.0f) + (yOffset / 1.5f) + 16.0f;
        for(String line : fontRendererObj.listFormattedStringToWidth(title, (int) (width / 1.5))) {
            int lineWidth = fontRendererObj.getStringWidth(line);
            fontRendererObj.drawString(line, (int)((width / 3f) + (xOffset / 1.5f) - (lineWidth / 2f)), (int)currentY, 0xFF333333, false);
            currentY += fontRendererObj.FONT_HEIGHT * 1.25;
        }
        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.scale(4, 4, 4);
        itemRender.renderItemIntoGUI(itemStack, (int) ((width / 8f) - 8f + (xOffset / 4f)), (int) ((height / 8f) - 12f + (yOffset / 4f)));
        GlStateManager.popMatrix();
    }
}
