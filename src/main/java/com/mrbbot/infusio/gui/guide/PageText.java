package com.mrbbot.infusio.gui.guide;

import com.mrbbot.infusio.gui.GuiInfusersGuide;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.resources.I18n;

class PageText implements IPageSingle {
    private static final Object[] parameters = new Object[0];
    private String text;

    PageText(String text) {
        this.text = text;
    }

    @Override
    public void renderPage(GuiInfusersGuide gui, FontRenderer fontRendererObj, RenderItem itemRender, int width, int height, int mouseX, int mouseY, int xOffset, int yOffset) {
        fontRendererObj.drawSplitString(I18n.format(text, parameters), xOffset, yOffset, width, 0);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
